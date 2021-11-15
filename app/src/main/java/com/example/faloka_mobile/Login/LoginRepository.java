package com.example.faloka_mobile.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.API.ApiService;
import com.example.faloka_mobile.Model.Login;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository{

    public static final void userLogin(Login login, Context context, LoginValidListener loginValidListener){

        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("Token",0));

        Call<LoginResponse> client = ApiConfig.getApiService(tokenManager).getSession(login);
        client.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(tokenManager.getToken()==null){
                            tokenManager.saveToken(response.body());
                            loginValidListener.onLogin(true);
                        }
                    }
                }
                else{
                    loginValidListener.onLogin(false);
                    if(response.body()!=null){}
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("failure", "onFailure: " + t.getMessage());
            }

        });
    }

    public static final boolean isValidationLogin(Activity activity){
        boolean isValid = true;
        TokenManager tokenManager = TokenManager.getInstance(activity.getApplication().getSharedPreferences("Token",0));
        if(!tokenManager.isLogin()){
            activity.startActivity(new Intent(activity, LoginActivity.class));
//            activity.finish();
            isValid = false;
        }
        return isValid;
    }

}
