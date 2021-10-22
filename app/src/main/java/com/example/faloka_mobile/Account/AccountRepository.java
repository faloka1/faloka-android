package com.example.faloka_mobile.Account;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Message;

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

}
