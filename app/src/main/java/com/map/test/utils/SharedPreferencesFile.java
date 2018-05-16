package com.map.test.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreferencesFile {

    private static final String APP_SHARED_PREFS = SharedPreferencesFile.class.getSimpleName();
    private static final String LOCATION_POS_X = "LOCATION_POS_X";
    private static final String LOCATION_POS_Y = "LOCATION_POS_Y";
    private static final String LOCATION_POS_ZOOM = "LOCATION_POS_ZOOM";
    private static SharedPreferences sPref;

    public static void initSharedReferences(Context context) {
        if (sPref == null) {
            sPref = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        }
    }

    public static float getLocationPosX() {
        return sPref.getFloat(LOCATION_POS_X, 0f);
    }

    public static void setLocationPosX(float number) {
        Editor ed = sPref.edit();
        ed.putFloat(LOCATION_POS_X, number);
        ed.commit();
    }

    public static float getLocationPosY() {
        return sPref.getFloat(LOCATION_POS_Y, 0f);
    }

    public static void setLocationPosY(float number) {
        Editor ed = sPref.edit();
        ed.putFloat(LOCATION_POS_Y, number);
        ed.commit();
    }

    public static float getLocationPosZoom() {
        return sPref.getFloat(LOCATION_POS_ZOOM, 1f);
    }

    public static void setLocationPosZoom(float number) {
        Editor ed = sPref.edit();
        ed.putFloat(LOCATION_POS_ZOOM, number);
        ed.commit();
    }
}
