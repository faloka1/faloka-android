package com.example.faloka_mobile.MixAndMatch;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.LoadingDialog;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Model.ProductMixMatch;
import com.example.faloka_mobile.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MixMatchRepository {
    public final static void getProductsMixMatch(View view, ProductMxMatchListener productMxMatchListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        tokenManager.setLoadingDialog(new LoadingDialog((Activity) view.getContext()));
        tokenManager.getLoadingDialog().startLoadingDialog();
        Call<List<ProductMixMatch>> callProducts = ApiConfig.getApiService(tokenManager).getProductsMixMatch();

        callProducts.enqueue(new Callback<List<ProductMixMatch>>() {
            @Override
            public void onResponse(Call<List<ProductMixMatch>> call, Response<List<ProductMixMatch>> response) {
                if(response.isSuccessful()){
                    List<ProductMixMatch> productMixMatchList = response.body();
                    productMxMatchListener.onProduct(productMixMatchList);
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductMixMatch>> call, Throwable t) {
                Toast.makeText(view.getContext(), "FAIL API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static final void getMixMatchCartVariantSize(View view, Cart cart, MixMatchCartVarSizeListener mixMatchCartVarSizeListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<Product> callProductSlug = ApiConfig.getApiService(tokenManager).getProductSlug(cart.getProduct().getSlug());
        callProductSlug.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    mixMatchCartVarSizeListener.onMixMatchCartVarSize(cart, product);
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
