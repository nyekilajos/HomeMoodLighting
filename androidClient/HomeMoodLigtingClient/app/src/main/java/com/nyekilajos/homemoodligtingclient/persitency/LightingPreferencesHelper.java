package com.nyekilajos.homemoodligtingclient.persitency;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;

/**
 * Created by Lajos_Nyeki on 12/1/2015.
 */
public class LightingPreferencesHelper {

    private static final String PREFERENCES_NAME = "lighingPreferences";
    private static final String ACTIVE_BEDROOM_PROGRAM = "activeBedroomProgram";

    private SharedPreferences sharedPreferences;

    @Inject
    public LightingPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setActiveBedroomProgramId(int programId) {
        sharedPreferences.edit().putInt(ACTIVE_BEDROOM_PROGRAM, programId).apply();
    }

    public int getActiveBedroomProgramId() {
        return sharedPreferences.getInt(ACTIVE_BEDROOM_PROGRAM, 0);
    }

}
