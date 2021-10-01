package com.example.faloka_mobile.Product_List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.faloka_mobile.Home.HomeFragment;
import com.example.faloka_mobile.R;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = findViewById(R.id.toolbar_product_list);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("Blouse");

        ArrayList<ProductResponse> products = new ArrayList<>();
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        products.add(new ProductResponse("Blouse bagus",R.drawable.product_image,"Toko bagus",8000));
        RecyclerView rvStyleInspiration = findViewById(R.id.rv_product_list);
        ProductAdapter productAdapter = new ProductAdapter(products);
        rvStyleInspiration.setLayoutManager(new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false));
        rvStyleInspiration.setAdapter(productAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
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