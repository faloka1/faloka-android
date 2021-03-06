package com.example.faloka_mobile.Cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Account.AccountRepository;
import com.example.faloka_mobile.Account.AuthFlagListener;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.Model.BodyCart;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {

    public static final void getCarts(View view, CartItemListener cartItemListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<List<Cart>> callCarts = ApiConfig.getApiService(tokenManager).getCarts(tokenManager.getTypeToken()+" "+tokenManager.getToken() );

        callCarts.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if(response.isSuccessful()){
                    List<Cart> cartList = response.body();
                    cartItemListener.onCart(cartList);
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.e("####","gagal api" + String.valueOf(t));
            }
        });
    }

    public static final void deleteCart(View view, int id){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<Message> callDelete = ApiConfig.getApiService(tokenManager).deleteCart(tokenManager.getTypeToken()+" "+tokenManager.getToken(),id);
        callDelete.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Message message = response.body();
                }
                else{
                    Log.e("gagal", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("gagal tersambung", t.getMessage());
            }
        });
    }
    public static final void deleteAllCart(View view){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<Message> callDeleteAllCart = ApiConfig.getApiService(tokenManager).deleteAllCart(tokenManager.getTypeToken()+" "+tokenManager.getToken() );

        callDeleteAllCart.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Message message = response.body();
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(view.getContext(), "FAIL API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static final void getCountCarts(Context context, CartCountItemListener cartCountItemListener, AuthFlagListener authFlagListener){
        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("Token",0));
        Call<List<Cart>> callCarts = ApiConfig.getApiService(tokenManager).getCarts(tokenManager.getTypeToken()+" "+tokenManager.getToken() );

        callCarts.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if(response.isSuccessful()){
                    List<Cart> cartList = response.body();
                    int count = 0;
                    for(Cart cart : cartList){
                        count += cart.getQuantity();
                    }
                    cartCountItemListener.onItemCount(count);
                    authFlagListener.onUnauthorized(false);
                }
                if(response.code() == 401){
                    AccountRepository.logoutUser(context);
                    authFlagListener.onUnauthorized(true);
                }
                else {
//                    Toast.makeText(context, "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
//                Toast.makeText(context, "FAIL API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static final void addCart(View view, BodyCart bodyCart, CartAddItemListener cartAddItemListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<Message> callCarts = ApiConfig.getApiService(tokenManager).addCart(tokenManager.getTypeToken()+" "+tokenManager.getToken(), bodyCart );

        callCarts.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Message message = response.body();
                    cartAddItemListener.onAddToCart();
                }
                else {
                    Log.e("####", "gagal response" + String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("####","gagal api" + String.valueOf(t));
            }
        });
    }

    public static final void editCartQuantity(View view, int cartID, int quantity){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<Message> callEditCartQuantity = ApiConfig.getApiService(tokenManager).editCartQuantity(tokenManager.getTypeToken()+" "+tokenManager.getToken(), cartID, quantity );

        callEditCartQuantity.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Message message = response.body();
//                    Snackbar.make(view, message.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("####","gagal api" + String.valueOf(t));
            }
        });
    }

    public static final void getProductsRelated(View view, CartProductsRelated cartProductsRelated){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<List<Product>> callCartProductsRelated = ApiConfig.getApiService(tokenManager).getCartProductsRelated(tokenManager.getTypeToken()+" "+tokenManager.getToken());

        callCartProductsRelated.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> productList = response.body();
                    cartProductsRelated.onProductsRelated(productList);
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(view.getContext(), "FAIL API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
