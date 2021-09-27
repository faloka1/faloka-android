package com.example.faloka_mobile.API;

import com.example.faloka_mobile.Login.AccountResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    Call<AccountResponse> getUser(@Field("email") String email, @Field("password") String password);
}

