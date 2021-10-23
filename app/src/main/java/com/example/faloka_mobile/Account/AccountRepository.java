package com.example.faloka_mobile.Account;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.AddressAdapter;
import com.example.faloka_mobile.Adapter.AddressAddAdapter;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {

    public static final void logoutUser(View view, AppCompatActivity activity){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<Message> callLogout = ApiConfig.getApiService(tokenManager).getLogoutMessage(tokenManager.getTypeToken()+" "+tokenManager.getToken());
        callLogout.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()) {
                    Message logout = response.body();
                    tokenManager.deleteToken();

                    activity.startActivity(new Intent(view.getContext(), LoginActivity.class));
                    Toast.makeText(view.getContext(), logout.getMessage(), Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                else {
                    Toast.makeText(view.getContext(), "Logout Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    public static final void setUserProfile(View view, UserProfileListener userProfileListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<User> callUser = ApiConfig.getApiService(tokenManager).getUser("Bearer "+tokenManager.getToken());

        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    userProfileListener.onUserProfile(user);
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public static final void getOrders(String status, View view, OrderUserListener orderUserListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<List<OrderUser>> callOrderUser = ApiConfig.getApiService(tokenManager).getOrders("Bearer "+tokenManager.getToken(), status);

        callOrderUser.enqueue(new Callback<List<OrderUser>>() {
            @Override
            public void onResponse(Call<List<OrderUser>> call, Response<List<OrderUser>> response) {
                if(response.isSuccessful()){
                    List<OrderUser> orderUserList = response.body();
                    orderUserListener.onOrder(orderUserList);
                }
                else {
                    Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderUser>> call, Throwable t) {
                Toast.makeText(view.getContext(), "FAIL API "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
