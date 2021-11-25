package com.example.faloka_mobile.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Product.ProductListListener;
import com.example.faloka_mobile.Product.ProductRepository;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivitySearchListProductBinding;

public class SearchListProductActivity extends BaseActivity implements ProductListListener {

    ActivitySearchListProductBinding binding;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchListProductBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        Uri uri = null;
        if(getIntent() != null){
            uri = Uri.parse(getIntent().getExtras().getString("IMAGE_URI"));
            binding.imgVisSearchResult.setImageURI(uri);
        }
        setToolbar();
        ProductRepository.getProducts(view, this::onListProduct);
    }

    public void setToolbar(){
        setSupportActionBar(binding.toolbarListSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hasil pencarian gambar");
    }

    @Override
    public void onListProduct(ProductListResponse productListResponse) {
        ProductAdapter productAdapter = new ProductAdapter(productListResponse.getProductList());
        binding.rvSearchListProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL, false));
        binding.rvSearchListProduct.setAdapter(productAdapter);
    }
}