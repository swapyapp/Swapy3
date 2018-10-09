package com.app.muhammadgamal.swapy;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private TextView receivedSwapRequests, sentSwapRequests, acceptedSwapRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //to handle the menu item clicks in the navigation drawer
        //the activity must implement OnNavigationItemSelectedListener
        //then override onNavigationItemSelected method
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //handle the toggle animation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //avoid recreating the fragment when the device is rotated
        if (savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container,
                            new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.home);
        }


        receivedSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_receivedSwapRequests));
        sentSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_sentSwapRequests));
        acceptedSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_acceptedSwapRequests));


        initializeCountDrawer();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //fragment container depends on the case result and will contain the specified fragment
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new HomeFragment())
                        .commit();
                break;
            case R.id.nav_account:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new AccountFragment())
                        .commit();
                break;
            case R.id.nav_receivedSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new ReceivedSwapFragment())
                        .commit();
                break;
            case R.id.nav_sentSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new SentSwapFragment())
                        .commit();
                break;
            case R.id.nav_acceptedSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new AcceptedSwapFragment())
                        .commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new SettingsFragment())
                        .commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //This method will initialize the count value in the menu in the navigation drawer
    private void initializeCountDrawer() {
        //Gravity property aligns the text
        receivedSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        receivedSwapRequests.setTypeface(null, Typeface.BOLD);
        receivedSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
        receivedSwapRequests.setText("99+");
        sentSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        sentSwapRequests.setTypeface(null, Typeface.BOLD);
        sentSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
        sentSwapRequests.setText("20");
        acceptedSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        acceptedSwapRequests.setTypeface(null, Typeface.BOLD);
        acceptedSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
        acceptedSwapRequests.setText("7");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        // when updating the value from an API call or SQLite database
        initializeCountDrawer();
        super.onStart();
    }

    @Override
    protected void onResume() {
        // when updating the value from an API call or SQLite database
        initializeCountDrawer();
        super.onResume();
    }
}
