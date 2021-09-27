package com.example.faloka_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.faloka_mobile.Account.AccountFragment;
import com.example.faloka_mobile.Home.HomeFragment;
import com.example.faloka_mobile.InspireMe.InpireMeFragment;
import com.example.faloka_mobile.Search.SearchFragment;
import com.example.faloka_mobile.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.buttomNavigation.setSelectedItemId(R.id.bottom_menu_home);
        binding.buttomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.bottom_menu_home:
                        Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
                        fragment = new HomeFragment();
                        break;
                    case R.id.bottom_menu_search:
                        Toast.makeText(getApplicationContext(), "SEARCH", Toast.LENGTH_SHORT).show();
                        fragment = new SearchFragment();
                        break;
                    case R.id.bottom_menu_inspire:
                        Toast.makeText(getApplicationContext(), "INSPIRE ME", Toast.LENGTH_SHORT).show();
                        fragment = new InpireMeFragment();
                        break;
                    case R.id.bottom_menu_account:
                        Toast.makeText(getApplicationContext(), "ACCOUNT", Toast.LENGTH_SHORT).show();
                        fragment = new AccountFragment();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .commit();
                return true;
            }
        });
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

}