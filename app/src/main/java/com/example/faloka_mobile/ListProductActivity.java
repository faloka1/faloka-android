package com.example.faloka_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ListProductActivity extends AppCompatActivity {
    private ListProductFagment listProductFagment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        listProductFagment = new ListProductFagment();
        Fragment fragment = mFragmentManager.findFragmentByTag(ListProductFagment.class.getSimpleName());

        if (!(fragment instanceof ListProductFagment)) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + ListProductFagment.class.getSimpleName());
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.wrapper, listProductFagment, ListProductFagment.class.getSimpleName())
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
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