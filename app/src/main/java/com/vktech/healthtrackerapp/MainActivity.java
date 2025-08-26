package com.vktech.healthtrackerapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new HomeFragment())
                    .commit(); // Or commitAllowingStateLoss()
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                selected = new HomeFragment();
            } else if (id == R.id.nav_log) {
                selected = new LogFragment();
            } else if (id == R.id.nav_profile) {
                selected = new ProfileFragment();
            }
            if (selected != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, selected)
                        .commitAllowingStateLoss(); // Use this to fix the crash
                return true;
            }
            return false;
        });
    }
}