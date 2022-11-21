package de.blau.android.geocode;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.openlocationcode.OpenLocationCode;
import com.google.openlocationcode.OpenLocationCode.CodeArea;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import de.blau.android.App;
import de.blau.android.R;
import de.blau.android.dialogs.TextLineDialog;
import de.blau.android.geocode.Search.SearchResult;
import de.blau.android.osm.ViewBox;
import de.blau.android.prefs.AdvancedPrefDatabase;
import de.blau.android.prefs.AdvancedPrefDatabase.Geocoder;
import de.blau.android.util.CoordinateParser;
import de.blau.android.util.ExecutorTask;
import de.blau.android.util.LatLon;
import de.blau.android.util.NetworkStatus;

/**
 * Ask the user for coordinates or an OLC for example WF8Q+WF Praia, Cabo Verde
 * 
 * @author simon
 *
 */
public class CoordinatesOrOLC {

    protected static final String DEBUG_TAG = CoordinatesOrOLC.class.getSimpleName();

    private static final Pattern OLC_SHORT = Pattern.compile("^([23456789CFGHJMPQRVWX]{4,6}\\+[23456789CFGHJMPQRVWX]{2,3})\\s*(.*)$",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern OLC_FULL  = Pattern.compile("^([23456789C][23456789CFGHJMPQRV][23456789CFGHJMPQRVWX]{6}\\+[23456789CFGHJMPQRVWX]{2,3})(\\s|$)",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private static AppCompatDialog dialog;

    public interface HandleResult {

        /**
         * Call this if we successfully determined a pair of coordinates
         * 
         * @param ll a LatLon object with the coordinates
         */
        void onSuccess(@NonNull LatLon ll);

        /**
         * Call this with an error message if things went wrong
         * 
         * @param message the message
         */
        void onError(@NonNull String message);
    }

    /**
     * Show a dialog and ask the user for input
     * 
     * @param context the calling FragmentActivity
     * @param handler a handler for the results
     */
    public static void get(@NonNull final Context context, @NonNull final HandleResult handler) {
        dialog = TextLineDialog.get(context, R.string.go_to_coordinates_title, R.string.go_to_coordinates_hint, (input, check) -> {
            String text = input.getText().toString().trim();
            if ("".equals(text)) {
                return;
            }
            parse(context, handler, text);
        }, false);
        dialog.show();
    }

    /**
     * Parse the entered string either as a coordinate tupel or an OLC
     * 
     * @param context an Android Context
     * @param handler a Handler to use for success and error
     * @param text the user provided text
     */
    private static void parse(@NonNull final Context context, @NonNull final HandleResult handler, @NonNull String text) {
        new ExecutorTask<String, Void, LatLon>() {
            @Override
            protected LatLon doInBackground(String param) {
                try {
                    return CoordinateParser.parseVerbatimCoordinates(text);
                } catch (ParseException pex) {
                    try {
                        OpenLocationCode olc = null;
                        Matcher m = OLC_FULL.matcher(text);
                        if (m.find()) {
                            olc = new OpenLocationCode(m.group(1));
                        } else {
                            m = OLC_SHORT.matcher(text);
                            if (m.find()) {
                                olc = new OpenLocationCode(m.group(1));
                                final String loc = m.group(2);
                                if (!"".equals(loc)) { // user has supplied a location
                                    olc = recoverLocation(context, handler, olc, loc);
                                } else { // relative to screen center
                                    ViewBox box = App.getLogic().getViewBox();
                                    if (box != null) {
                                        double[] c = box.getCenter();
                                        olc = olc.recover(c[1], c[0]);
                                    }
                                }
                            }
                        }
                        if (olc == null) {
                            throw new IOException("Unparseable OLC " + text);
                        }
                        CodeArea ca = olc.decode();
                        return new LatLon(ca.getCenterLatitude(), ca.getCenterLongitude());
                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, e.getMessage());
                        handler.onError(context.getString(R.string.unparseable_coordinates));
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(LatLon result) {
                if (result != null) {
                    handler.onSuccess(result);
                    dismiss();
                }
            }
        }.execute(text);
    }

    /**
     * Dismiss the dialog
     */
    private static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * Recover coordinates for a location from Nominatim and update the provided OLC
     * 
     * @param context an Android Context
     * @param handler handler to use for errors
     * @param olc original OLC
     * @param loc location
     * @return possibly a new OLC with the location set
     */
    @NonNull
    private static OpenLocationCode recoverLocation(@NonNull final Context context, @NonNull final HandleResult handler, @NonNull OpenLocationCode olc,
            @NonNull final String loc) {
        if (new NetworkStatus(context).isConnected()) {
            String url = getNominatimUrl(context);
            if (url != null) {
                QueryNominatim querier = new QueryNominatim(null, url, null, false);
                querier.execute(loc);
                List<SearchResult> results;
                try {
                    results = querier.get(5, TimeUnit.SECONDS);
                    if (results != null && !results.isEmpty()) {
                        SearchResult result = results.get(0);
                        olc = olc.recover(result.getLat(), result.getLon());
                    } else {
                        handler.onError(context.getString(R.string.no_nominatim_result, loc));
                    }
                } catch (InterruptedException | ExecutionException | TimeoutException e) { // NOSONAR
                    querier.cancel();
                    handler.onError(context.getString(R.string.no_nominatim_result, loc));
                }
            } else {
                handler.onError(context.getString(R.string.no_nominatim_server));
            }
        } else {
            handler.onError(context.getString(R.string.network_required));
        }
        return olc;
    }

    /**
     * Get a URL for a Nominatim server
     * 
     * @param context an Android Context
     * @return the url or null
     */
    @Nullable
    private static String getNominatimUrl(@NonNull final Context context) {
        try (AdvancedPrefDatabase db = new AdvancedPrefDatabase(context)) {
            final Geocoder[] geocoders = db.getActiveGeocoders();
            String url = null;
            for (Geocoder g : geocoders) {
                if (g.type == AdvancedPrefDatabase.GeocoderType.NOMINATIM) {
                    url = g.url;
                    break;
                }
            }
            return url;
        }
    }
}
