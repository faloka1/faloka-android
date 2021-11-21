package com.example.faloka_mobile.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Adapter.SearchAdapter;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchProductListener {

    private ActivitySearchBinding binding;
    private View view;
    private boolean flagEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        setToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.top_menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari outfitmu");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length()>=3 || s.length()==0){
                    SearchRepository.getSearchProducts(view, s, SearchActivity.this::onSearchProduct);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//            case R.id.top_menu_search:
//                Toast.makeText(view.getContext(), "BISA NIH", Toast.LENGTH_SHORT).show();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.top_menu_search);
//        SearchView searchView = menuItem.getActionView().findViewById(R.id.cart_badge);
//        switch (menuItem.getItemId()){
//            case R.id.top_menu_search:
//                Toast.makeText(view.getContext(), "BISA NIH", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void setToolbar(){
        setSupportActionBar(binding.searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @Override
    public void onSearchProduct(ProductListResponse productListResponse, String text) {
        List<Product> productList = new ArrayList<>();
        if(productListResponse.getProductList().size() == 0){
            Product product = new Product();
            productList.add(product);
        }
        else {
            productList = productListResponse.getProductList();
        }
        SearchAdapter searchAdapter = new SearchAdapter(productList, text);
        binding.rvSearchProduct.setAdapter(searchAdapter);
        binding.rvSearchProduct.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}