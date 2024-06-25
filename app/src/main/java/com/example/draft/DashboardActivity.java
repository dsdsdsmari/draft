package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");

        // Set up bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.navigation_maps) {
                    selectedFragment = new MapsFragment();
                } else if (item.getItemId() == R.id.navigation_favorites) {
                    selectedFragment = new FavoritesFragment();
                } else if (item.getItemId() == R.id.navigation_messages) {
                    selectedFragment = new MessagesFragment();
                } else if (item.getItemId() == R.id.navigation_account) {
                    AccountFragment accountFragment = new AccountFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("fullName", fullName);
                    bundle.putString("email", email);
                    accountFragment.setArguments(bundle);
                    selectedFragment = accountFragment;
                }
                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                }
                return true;
            }
        });
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
