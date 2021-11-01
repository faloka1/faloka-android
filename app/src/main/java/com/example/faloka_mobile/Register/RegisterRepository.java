package com.example.faloka_mobile.Register;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.UserRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {

    public static final  void userRegister(View view, UserRegister userRegister, RegisterListener registerValidListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));

        Call<RegisterResponse> callRegister = ApiConfig.getApiService(tokenManager).addUser(userRegister);
        callRegister.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    RegisterResponse registerResponse = response.body();
                    registerValidListener.onRegister(registerResponse.getUser());
                    Toast.makeText(view.getContext(), registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    RegisterErrorResponse error = RegisterErrorUtils.parseError(response);
                    registerValidListener.onError(error.getError());

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
