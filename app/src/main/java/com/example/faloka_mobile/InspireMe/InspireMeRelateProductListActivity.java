package com.example.faloka_mobile.InspireMe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityInspireMeRelateProductBinding;

import java.util.ArrayList;

public class InspireMeRelateProductListActivity extends AppCompatActivity {

    ActivityInspireMeRelateProductBinding binding;
    InspireMeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInspireMeRelateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(this));
        viewModel = new ViewModelProvider(this, factory).get(InspireMeViewModel.class);

        setToolbar();
        setHeader();
        setProductList();
    }
    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setHeader(){
        String image = this.getIntent().getExtras().getString("image");
        Glide.with(binding.ivInspiremeProductList)
                .load(ApiConfig.BASE_IMAGE_URL + image)
                .into(binding.ivInspiremeProductList);
    }
    private void setProductList(){
        ArrayList<Product> products = this.getIntent().getExtras().getParcelableArrayList("relateProducts");
        RecyclerView rv = binding.rvInspiremeProductList;
        ProductAdapter adapter = new ProductAdapter(products);
        rv.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.cart_menu_item:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.wishlist_menu_item:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}