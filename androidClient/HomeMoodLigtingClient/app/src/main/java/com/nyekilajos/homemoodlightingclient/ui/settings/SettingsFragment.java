package com.nyekilajos.homemoodlightingclient.ui.settings;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.inject.Inject;
import com.nyekilajos.homemoodlightingclient.R;
import com.nyekilajos.homemoodlightingclient.persitency.LightingPreferencesHelper;

/**
 * Fragment for settings about lighting controllers.
 * 
 * @author Lajos_Nyeki
 */
public class SettingsFragment extends RoboFragment {

    private static final int MIN_LED_NUM = 1;
    private static final int MAX_LED_NUM = 1500;
    private static final int DEFAULT_LED_NUM = 300;

    @InjectView(R.id.bedroom_controller_ip)
    private TextView bedroomControllerIp;
    @InjectView(R.id.bedroom_controller_port)
    private TextView bedroomControllerPort;
    @InjectView(R.id.livingroom_controller_ip)
    private TextView livingroomControllerIp;
    @InjectView(R.id.livingroom_controller_port)
    private TextView livingroomControllerPort;
    @InjectView(R.id.led_num_picker)
    private NumberPicker ledNumPicker;

    @Inject
    private LightingPreferencesHelper lightingPreferencesHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ledNumPicker.setMinValue(MIN_LED_NUM);
        ledNumPicker.setMaxValue(MAX_LED_NUM);
        ledNumPicker.setValue(DEFAULT_LED_NUM);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadValuesFromPreferences();
    }

    private void loadValuesFromPreferences() {
        bedroomControllerIp.setText(lightingPreferencesHelper.getBedroomControllerIpAddress());

        int bedroomPort = lightingPreferencesHelper.getBedroomControllerPort();
        bedroomControllerPort.setText(bedroomPort != -1 ? Integer.toString(bedroomPort) : null);

        livingroomControllerIp.setText(lightingPreferencesHelper.getLivingroomControllerIpAddress());

        int livingroomPort = lightingPreferencesHelper.getLivingroomControllerPort();
        livingroomControllerPort.setText(livingroomPort != -1 ? Integer.toString(lightingPreferencesHelper.getLivingroomControllerPort()) : null);

        int ledCount = lightingPreferencesHelper.getLivingroomLedCount();
        ledNumPicker.setValue(ledCount != -1 ? lightingPreferencesHelper.getLivingroomLedCount() : DEFAULT_LED_NUM);
    }

    @Override
    public void onPause() {
        super.onPause();
        storeValuesToPreferences();
    }

    private void storeValuesToPreferences() {
        if (bedroomControllerIp.getText().length() != 0) {
            lightingPreferencesHelper.setBedroomControllerIpAddress(bedroomControllerIp.getText().toString());
        }
        if (bedroomControllerPort.length() != 0) {
            lightingPreferencesHelper.setBedroomControllerPort(Integer.parseInt(bedroomControllerPort.getText().toString()));
        }
        if (livingroomControllerIp.length() != 0) {
            lightingPreferencesHelper.setLivingroomControllerIpAddress(livingroomControllerIp.getText().toString());
        }
        if (livingroomControllerPort.length() != 0) {
            lightingPreferencesHelper.setLivingroomControllerPort(Integer.parseInt(livingroomControllerPort.getText().toString()));
        }

        lightingPreferencesHelper.setLivingroomLedCount(ledNumPicker.getValue());
    }
}
