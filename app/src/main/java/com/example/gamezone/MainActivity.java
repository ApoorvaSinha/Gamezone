package com.example.gamezone;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private NavController navController;

    private AppBarConfiguration appBarConfiguration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.frag_home);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        Log.i(TAG, "onCreate: ");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView = (NavigationView) findViewById(R.id.sidebar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setupNavigation();

        }

    private void setupNavigation() {
        Log.i(TAG, "setupNavigation: ");
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()) //Pass the ids of fragments from nav_graph which you dont want to show back button in toolbar
                        .setDrawerLayout(drawerLayout)
                        .build();


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); //Setup toolbar with back button and drawer icon according to appBarConfiguration
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        /*
         ** Listener for bottomNavigation must be called after been setupWithNavController
         ** This command will override NavigationUI.setupWithNavController(bottomNavigationView, navController)
         ** and the automatic transaction between fragments is lost
         * */
        //bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: ");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            Log.i(TAG, "onBackPressed: DRAWER IS OPEN -  CLOSING IT");
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i(TAG, "onSupportNavigateUp: ");
        // replace navigation up button with nav drawer button when on start destination
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i(TAG, "onNavigationItemSelected: SIDE BAR");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return false;
    }

       /* @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(mToggle.onOptionsItemSelected(item)){
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }*/
}
