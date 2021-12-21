package com.example.faloka_mobile.Product;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.LoadingDialog;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private volatile static ProductRepository INSTANCE = null;
    private Context context;

    public ProductRepository(Context context){
        this.context = context;
    }

    public static ProductRepository getINSTANCE(Context context){
        if(INSTANCE!=null){
            synchronized (ProductRepository.class){
                INSTANCE = new ProductRepository(context);
            }
        }
        return INSTANCE;
    }

    public static final void getProductBySlug(View view, String slug, ProductListener productListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<Product> callProductSlug = ApiConfig.getApiService(tokenManager).getProductSlug(slug);
        callProductSlug.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    productListener.onProductSlug(product);
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(view.getContext(), "FAIL API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
