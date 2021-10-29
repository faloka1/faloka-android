package com.example.faloka_mobile.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Model.SubCategory;
import com.example.faloka_mobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends BaseActivity {

    private SubCategory subCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = findViewById(R.id.toolbar_product_list);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        subCategory = intent.getParcelableExtra(SubCategory.EXTRA_SUBCATEGORY);
        toolbar.setTitle(subCategory.getName());

        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        Call<ProductListResponse> callProduct;
        callProduct = ApiConfig.getApiService(tokenManager).
                getProductSubCategories(subCategory.getSlugCategory(), subCategory.getSlug());
        callProduct.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                ProductListResponse productListResponses = response.body();
                List<Product> respProduct = productListResponses.getProductList();
                RecyclerView rvProductList = findViewById(R.id.rv_product_list);
                ProductAdapter productAdapter = new ProductAdapter(respProduct);
                rvProductList.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL, false));
                rvProductList.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {

            }
        });

    }

}