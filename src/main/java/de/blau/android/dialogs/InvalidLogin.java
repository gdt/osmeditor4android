package de.blau.android.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import de.blau.android.Authorize;
import de.blau.android.Main;
import de.blau.android.R;
import de.blau.android.listener.DoNothingListener;
import de.blau.android.osm.Server;
import de.blau.android.prefs.Preferences;
import de.blau.android.util.ThemeUtils;

/**
 * Display a dialog reporting that the login credentials don't work
 *
 */
public class InvalidLogin extends DialogFragment {

    private static final String DEBUG_TAG = InvalidLogin.class.getSimpleName();

    private static final String TAG = "fragment_invalid_login";

    /**
     * Display a dialog reporting that the login credentials don't work
     * 
     * @param activity the calling Activity
     */
    public static void showDialog(@NonNull FragmentActivity activity) {
        dismissDialog(activity);
        try {
            FragmentManager fm = activity.getSupportFragmentManager();
            InvalidLogin invalidLoginFragment = newInstance();
            invalidLoginFragment.show(fm, TAG);
        } catch (IllegalStateException isex) {
            Log.e(DEBUG_TAG, "showDialog", isex);
        }
    }

    /**
     * Dismiss the dialog
     * 
     * @param activity the calling Activity
     */
    private static void dismissDialog(@NonNull FragmentActivity activity) {
        de.blau.android.dialogs.Util.dismissDialog(activity, TAG);
    }

    /**
     * Get a new instance of InvalidLogin dialog
     * 
     * @return a new InvalidLogin dialog instance
     */
    @NonNull
    private static InvalidLogin newInstance() {
        InvalidLogin f = new InvalidLogin();
        f.setShowsDialog(true);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "onAttach");
        if (!(context instanceof Main)) {
            throw new ClassCastException(context.toString() + " can only be called from Main");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(ThemeUtils.getResIdFromAttribute(getActivity(), R.attr.alert_dialog));
        builder.setTitle(R.string.wrong_login_data_title);
        builder.setMessage(R.string.wrong_login_data_message);
        DoNothingListener doNothingListener = new DoNothingListener();
        builder.setNegativeButton(R.string.cancel, doNothingListener); // logins in the preferences should no longer be
                                                                       // used
        final Server server = new Preferences(getActivity()).getServer();
        if (server.getOAuth()) {
            builder.setPositiveButton(R.string.wrong_login_data_re_authenticate, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Authorize.startForResult(getActivity(), null);
                }
            });
        }
        return builder.create();
    }
}
