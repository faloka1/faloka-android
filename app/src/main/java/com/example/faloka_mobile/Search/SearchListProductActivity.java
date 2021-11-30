package com.example.faloka_mobile.Search;

import androidx.recyclerview.widget.GridLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Checkout.FileUtils;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Product.ProductListListener;
import com.example.faloka_mobile.Product.ProductListResponseListener;
import com.example.faloka_mobile.Product.ProductRepository;
import com.example.faloka_mobile.databinding.ActivitySearchListProductBinding;

import java.io.File;
import java.util.List;

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
            File file = FileUtils.getFile(view.getContext(), uri);
            SearchRepository.getVisualSearchProducts(view, file, this::onProductList);
        }
        setToolbar();

    }

    public void setToolbar(){
        setSupportActionBar(binding.toolbarListSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hasil pencarian gambar");
    }

    @Override
    public void onProductList(List<Product> productList) {
        ProductAdapter productAdapter = new ProductAdapter(productList);
        binding.rvSearchListProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL, false));
        binding.rvSearchListProduct.setAdapter(productAdapter);
    }
}