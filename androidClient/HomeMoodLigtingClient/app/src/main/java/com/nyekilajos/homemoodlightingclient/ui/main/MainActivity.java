package com.nyekilajos.homemoodlightingclient.ui.main;

import roboguice.activity.RoboActionBarActivity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nyekilajos.homemoodlightingclient.R;
import com.nyekilajos.homemoodlightingclient.ui.about.AboutFragment;
import com.nyekilajos.homemoodlightingclient.ui.bedroom.BedroomFragment;
import com.nyekilajos.homemoodlightingclient.ui.bedroom.SendSingleDataFragment;
import com.nyekilajos.homemoodlightingclient.ui.livingroom.LivingroomFragment;
import com.nyekilajos.homemoodlightingclient.ui.settings.SettingsFragment;

public class MainActivity extends RoboActionBarActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swapFragment(new BedroomFragment());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment opening;
        int id = item.getItemId();

        if (id == R.id.nav_bedroom) {
            opening = new BedroomFragment();
        } else if (id == R.id.nav_livingroom) {
            opening = new LivingroomFragment();
        } else if (id == R.id.nav_send_single_data) {
            opening = new SendSingleDataFragment();
        } else if (id == R.id.nav_settings) {
            opening = new SettingsFragment();
        } else if (id == R.id.nav_about) {
            opening = new AboutFragment();
        } else {
            throw new AssertionError("No such navigation menu item exists.");
        }

        swapFragment(opening);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
