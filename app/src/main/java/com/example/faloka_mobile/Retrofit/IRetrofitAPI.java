package com.example.faloka_mobile.Retrofit;

import com.example.faloka_mobile.Model.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetrofitAPI {

    @GET("users")
    Call<List<Profile>> getUsers();
}
