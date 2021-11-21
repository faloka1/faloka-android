package com.example.faloka_mobile.Search;

import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Model.ProductMixMatch;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    public static void getSearchProducts(View view, String text, SearchProductListener searchProductListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<ProductListResponse> callSearchProduct = ApiConfig.getApiService(tokenManager).getSearchProducts(text);

        callSearchProduct.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if(response.isSuccessful()){
                    ProductListResponse productListResponse = response.body();
                    searchProductListener.onSearchProduct(productListResponse, text);
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), "FAIL API: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
