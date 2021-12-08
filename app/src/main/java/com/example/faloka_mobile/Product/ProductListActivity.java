package com.example.faloka_mobile.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Decoration.ListProductPaddingDecoration;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Model.SubCategory;
import com.example.faloka_mobile.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends BaseActivity {


    Intent intent;
    SubCategory subCategory;
    Button button;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        toolbar = findViewById(R.id.toolbar_product_list);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        intent = getIntent();
        subCategory = intent.getParcelableExtra(SubCategory.EXTRA_SUBCATEGORY);
        toolbar.setTitle(subCategory.getName());

        button = findViewById(R.id.button_category);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerSubCategory();
            }
        });

        setListProductBySubCategory();
    }
    public void setSpinnerSubCategory(){

        ListPopupWindow listPopupWindow = new ListPopupWindow(this,null);
        List<SubCategory> subCategories = intent.getParcelableArrayListExtra("subcategories");
        List<String>subcategoryName = new ArrayList<>();
        for(SubCategory subcategory: subCategories){
            subcategoryName.add(subcategory.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(ProductListActivity.this,
                R.layout.layout_popup_category,R.id.tv_element,subcategoryName);




        listPopupWindow.setAnchorView(button);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    subCategory = subCategories.get(i);
                    listPopupWindow.dismiss();
                    setListProductBySubCategory();
                    toolbar.setTitle(subCategory.getName());
                }
        });

        listPopupWindow.show();


    }
    private void setListProductBySubCategory(){
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
                ListProductPaddingDecoration decoration = new ListProductPaddingDecoration(getApplication());
                rvProductList.addItemDecoration(decoration);
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {

            }
        });
    }

}