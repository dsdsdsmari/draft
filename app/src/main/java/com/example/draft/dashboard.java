package com.example.draft;

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

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Apply window insets to adjust padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.navigation_maps) {
                    loadFragment(new MapsFragment());
                    return true;
                } else if (item.getItemId() == R.id.navigation_favorites) {
                    loadFragment(new FavoritesFragment());
                    return true;
                } else if (item.getItemId() == R.id.navigation_messages) {
                    loadFragment(new MessagesFragment());
                    return true;
                } else if (item.getItemId() == R.id.navigation_account) {
                    loadFragment(new AccountFragment());
                    return true;
                }
                return false;
            }
        });



        // Load the initial fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    // Method to load a fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
