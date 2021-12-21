package com.example.faloka_mobile.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCartBinding;
import com.example.faloka_mobile.databinding.ActivityCartEmptyBinding;

import java.util.List;

public class CartEmptyActivity extends AppCompatActivity implements CartProductsRelated{

    private ActivityCartEmptyBinding binding;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartEmptyBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        CartRepository.getProductsRelated(view, this::onProductsRelated);
        setToolbar();
        setButtonBuy();
    }

    public void setToolbar(){
        setSupportActionBar(binding.cartToolbarEmpty);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tas");
    }

    public void setButtonBuy(){
        binding.btnCartGoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartEmptyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void setRecycleView(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem item = menu.findItem(R.id.top_menu_cart);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProductsRelated(List<Product> productList) {
        ProductAdapter productAdapter = new ProductAdapter(productList);
        binding.rvProductsRelatedEmpty.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(),2, GridLayoutManager.VERTICAL, false));
        binding.rvProductsRelatedEmpty.setAdapter(productAdapter);
    }
}