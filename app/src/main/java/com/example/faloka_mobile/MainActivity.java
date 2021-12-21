package com.example.faloka_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.faloka_mobile.Account.AccountFragment;
import com.example.faloka_mobile.Home.HomeFragment;
import com.example.faloka_mobile.InspireMe.InspireMeFragment;
import com.example.faloka_mobile.MixAndMatch.MixMatchActivity;
import com.example.faloka_mobile.Search.SearchActivity;
import com.example.faloka_mobile.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Fragment fragment;
    private static int currentMenuID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentMenuID = R.id.bottom_menu_home;
        binding.buttomNavigation.setSelectedItemId(R.id.bottom_menu_home);
        binding.buttomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.bottom_menu_home:
                        currentMenuID = R.id.bottom_menu_home;
                        fragment = new HomeFragment();
                        break;
                    case R.id.bottom_menu_search:
                        Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intentSearch);
                        return true;
                    case R.id.bottom_menu_inspire:
                        currentMenuID = R.id.bottom_menu_inspire;
                        fragment = new InspireMeFragment();
                        break;
                    case R.id.bottom_menu_mixmatch:
                        Intent intent = new Intent(MainActivity.this, MixMatchActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.bottom_menu_account:
                        currentMenuID = R.id.bottom_menu_account;
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
        if(this.fragment == null){
            this.fragment = new HomeFragment();
        }
        if(getIntent().getIntExtra(AccountFragment.EXTRA_FRAGMENT_ACCOUNT, 0) == AccountFragment.INDEX_FRAGMENT_ACCOUNT){
            this.fragment = new AccountFragment();
            binding.buttomNavigation.setSelectedItemId(R.id.bottom_menu_account);
        }
        if(getIntent().getIntExtra(InspireMeFragment.EXTRA_FRAGMENT_INSPO, 0) == InspireMeFragment.INDEX_FRAGMENT_ACCOUNT){
            this.fragment = new InspireMeFragment();
            binding.buttomNavigation.setSelectedItemId(R.id.bottom_menu_inspire);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();


    }

    @Override
    protected void onResume() {
        binding.buttomNavigation.getMenu().findItem(currentMenuID).setChecked(true);
        super.onResume();
    }
}