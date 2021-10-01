package com.example.faloka_mobile.Product_List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.faloka_mobile.Home.HomeFragment;
import com.example.faloka_mobile.R;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

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
}