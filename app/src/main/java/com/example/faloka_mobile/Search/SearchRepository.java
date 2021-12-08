package com.example.faloka_mobile.Search;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.LoadingDialog;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Product.ProductListListener;
import com.example.faloka_mobile.Product.ProductListResponseListener;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public static void getVisualSearchProducts(View view, File file, ProductListListener productListListener){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        RequestBody method = RequestBody.create(MediaType.parse("multipart/form-data"), "PATCH");
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        tokenManager.setLoadingDialog(new LoadingDialog((Activity) view.getContext()));
        tokenManager.getLoadingDialog().startLoadingDialog();
        Call<List<Product>> callVisualSearchProducts = ApiConfig.getApiService(tokenManager).getVisualSearchProducts(
                tokenManager.getTypeToken()+" "+tokenManager.getToken(),
                photoPart);
        callVisualSearchProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()) {
                    List<Product> productList = response.body();
                    productListListener.onProductList(productList);
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                }
                else {
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                    Toast.makeText(view.getContext(),"GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                tokenManager.getLoadingDialog().dismissLoadingDialog();
                Toast.makeText(view.getContext(), "FAIL API"+ call.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("FAIL API"+ call.toString()+" <> "+t.getMessage());
            }
        });
    }

}
