package com.example.faloka_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.faloka_mobile.Account.AccountFragment;
import com.example.faloka_mobile.Home.HomeFragment;
import com.example.faloka_mobile.InspireMe.InspireMeFragment;
import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.MixAndMatch.MixMatchActivity;
import com.example.faloka_mobile.Search.SearchFragment;
import com.example.faloka_mobile.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Fragment fragment;

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
                        fragment = new InspireMeFragment();
                        break;
                    case R.id.bottom_menu_mixmatch:
//                        if(!LoginRepository.isValidationLogin(MainActivity.this)){
//                            item.setCheckable(false);
//                            return true;
//                        }
                        item.setCheckable(false);
                        Toast.makeText(getApplicationContext(), "MIX AND MATCH", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MixMatchActivity.class);
                        startActivity(intent);
                        return true;
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
//        Fragment fragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();


    }
}