package com.nyekilajos.homemoodlightingclient.persitency;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;

/**
 * Helper class for storing and loading preferences.
 * 
 * @author Lajos_Nyeki
 */
public class LightingPreferencesHelper {

    private static final String PROGRAM_PREFERENCES_NAME = "programPreferences";
    private static final String ACTIVE_BEDROOM_PROGRAM = "activeBedroomProgram";
    private static final String ACTIVE_LIVINGROOM_PROGRAM = "activeLivingroomProgram";

    private static final String CONTROLLER_SETUP_PREFERENCES = "controllerSetupPreferences";
    private static final String BEDROOM_IP_ADDRESS = "bedroomIpAddress";
    private static final String BEDROOM_PORT = "bedroomPort";
    private static final String LIVINGROOM_IP_ADDRESS = "livingroomIpAddress";
    private static final String LIVINGROOM_PORT = "port";
    private static final String LIVINGROOM_LED_COUNT = "livingroomLedCount";

    private final SharedPreferences programPreferences;
    private final SharedPreferences controllerPreferences;

    @Inject
    public LightingPreferencesHelper(Context context) {
        programPreferences = context.getSharedPreferences(PROGRAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        controllerPreferences = context.getSharedPreferences(CONTROLLER_SETUP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setActiveBedroomProgramId(int programId) {
        programPreferences.edit().putInt(ACTIVE_BEDROOM_PROGRAM, programId).apply();
    }

    public int getActiveBedroomProgramId() {
        return programPreferences.getInt(ACTIVE_BEDROOM_PROGRAM, 0);
    }

    public void setActiveLivingroomProgramId(int programId) {
        programPreferences.edit().putInt(ACTIVE_LIVINGROOM_PROGRAM, programId).apply();
    }

    public int getActiveLivingroomProgramId() {
        return programPreferences.getInt(ACTIVE_LIVINGROOM_PROGRAM, 0);
    }

    public void setBedroomControllerIpAddress(String controllerIpAddress) {
        controllerPreferences.edit().putString(BEDROOM_IP_ADDRESS, controllerIpAddress).apply();
    }

    public String getBedroomControllerIpAddress() {
        return controllerPreferences.getString(BEDROOM_IP_ADDRESS, null);
    }

    public void setBedroomControllerPort(int port) {
        controllerPreferences.edit().putInt(BEDROOM_PORT, port).apply();
    }

    public int getBedroomControllerPort() {
        return controllerPreferences.getInt(BEDROOM_PORT, -1);
    }

    public void setLivingroomControllerIpAddress(String controllerIpAddress) {
        controllerPreferences.edit().putString(LIVINGROOM_IP_ADDRESS, controllerIpAddress).apply();
    }

    public String getLivingroomControllerIpAddress() {
        return controllerPreferences.getString(LIVINGROOM_IP_ADDRESS, null);
    }

    public void setLivingroomControllerPort(int port) {
        controllerPreferences.edit().putInt(LIVINGROOM_PORT, port).apply();
    }

    public int getLivingroomControllerPort() {
        return controllerPreferences.getInt(LIVINGROOM_PORT, -1);
    }

    public void setLivingroomLedCount(int ledCount) {
        controllerPreferences.edit().putInt(LIVINGROOM_LED_COUNT, ledCount).apply();
    }

    public int getLivingroomLedCount() {
        return controllerPreferences.getInt(LIVINGROOM_LED_COUNT, -1);
    }

}
