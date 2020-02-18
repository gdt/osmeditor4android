package de.blau.android.search;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.orhanobut.mockwebserverplus.MockWebServerPlus;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import de.blau.android.App;
import de.blau.android.Logic;
import de.blau.android.Main;
import de.blau.android.Map;
import de.blau.android.SignalHandler;
import de.blau.android.TestUtils;
import de.blau.android.exception.IllegalOperationException;
import de.blau.android.osm.ApiTest;
import de.blau.android.osm.OsmElement;
import de.blau.android.osm.Tags;
import de.blau.android.osm.ViewBox;
import de.blau.android.osm.Way;
import de.blau.android.prefs.AdvancedPrefDatabase;
import de.blau.android.prefs.AdvancedPrefDatabase.Geocoder;
import de.blau.android.prefs.AdvancedPrefDatabase.GeocoderType;
import de.blau.android.prefs.Preferences;
import de.blau.android.resources.TileLayerServer;
import okhttp3.HttpUrl;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ObjectSearchTest {

    public static final int TIMEOUT    = 90;
    MockWebServerPlus       mockServer = null;
    Context                 context    = null;
    AdvancedPrefDatabase    prefDB     = null;
    Main                    main       = null;
    UiDevice                device     = null;
    Map                     map        = null;
    Logic                   logic      = null;

    @Rule
    public ActivityTestRule<Main> mActivityRule = new ActivityTestRule<>(Main.class);

    /**
     * Pre-test setup
     */
    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        main = mActivityRule.getActivity();
        Preferences prefs = new Preferences(context);
        prefs.setBackGroundLayer(TileLayerServer.LAYER_NONE); // try to avoid downloading tiles
        prefs.setOverlayLayer(TileLayerServer.LAYER_NOOVERLAY);
        main.getMap().setPrefs(main, prefs);

        TestUtils.grantPermissons();
        TestUtils.dismissStartUpDialogs(main);
        final CountDownLatch signal1 = new CountDownLatch(1);
        logic = App.getLogic();
        logic.deselectAll();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream("test2.osm");
        logic.readOsmFile(main, is, false, new SignalHandler(signal1));
        try {
            signal1.await(ApiTest.TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        try {
            is.close();
        } catch (IOException e1) {
        }
        TestUtils.stopEasyEdit(main);
        map = logic.getMap();
        map.getViewBox().fitToBoundingBox(map, map.getDataLayer().getExtent());
        logic.updateStyle();
        map.getDataLayer().setVisible(true);
        map.invalidate();
        TestUtils.unlock();
        device.waitForWindowUpdate(null, 2000);
    }

    /**
     * Post-test teardown
     */
    @After
    public void teardown() {
        logic.deselectAll();
        TestUtils.zoomToLevel(main, 18);
    }

    /**
     * Search for a single object
     */
    @Test
    public void single() {
        TestUtils.clickOverflowButton();
        TestUtils.clickText(device, false, "Search for objects", true);
        UiObject searchEditText = device.findObject(new UiSelector().clickable(true).resourceId("de.blau.android:id/text_line_edit"));
        try {
            searchEditText.click();
            searchEditText.setText("\"addr:street\"=Kirchstrasse \"addr:housenumber\"=4");
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickButton("android:id/button1", true);
        List<OsmElement> selected = logic.getSelectedElements();
        Assert.assertEquals(1, selected.size());
        Assert.assertTrue(selected.get(0) instanceof Way);
        Assert.assertEquals(210558045L, selected.get(0).getOsmId());
    }

    /**
     * Search for multiple objects
     */
    @Test
    public void multiple() {
        TestUtils.clickOverflowButton();
        TestUtils.clickText(device, false, "Search for objects", true);
        UiObject searchEditText = device.findObject(new UiSelector().clickable(true).resourceId("de.blau.android:id/text_line_edit"));
        try {
            searchEditText.click();
            searchEditText.setText("\"addr:housenumber\"=4");
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickButton("android:id/button1", true);
        List<OsmElement> selected = logic.getSelectedElements();
        Assert.assertEquals(4, selected.size());
        for (OsmElement e : selected) {
            Assert.assertTrue(e instanceof Way);
            Assert.assertTrue(e.hasTag(Tags.KEY_BUILDING, "residential"));
        }
    }
}