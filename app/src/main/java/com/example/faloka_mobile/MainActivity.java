package com.example.faloka_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                switch (item.getItemId()){
                    case R.id.bottom_menu_home:
                        Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bottom_menu_search:
                        Toast.makeText(getApplicationContext(), "SEARCH", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bottom_menu_inspire:
                        Toast.makeText(getApplicationContext(), "INSPIRE ME", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bottom_menu_account:
                        Toast.makeText(getApplicationContext(), "ACCOUNT", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        binding.button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.top_menu_wishlist:
                Toast.makeText(getApplicationContext(), "WISHLIST", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_menu_cart:
                Toast.makeText(getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}