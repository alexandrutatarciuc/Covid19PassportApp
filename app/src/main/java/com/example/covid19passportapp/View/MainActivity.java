package com.example.covid19passportapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.covid19passportapp.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);*/

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        //Allows navigation to destinations when clicking menu items in the navigation drawer
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}