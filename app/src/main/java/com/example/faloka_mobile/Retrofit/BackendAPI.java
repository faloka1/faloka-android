package com.example.faloka_mobile.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendAPI {

    private Retrofit retrofit;
    private String baseURL;
    private IRetrofitAPI iRetrofitAPI;

    public BackendAPI(String baseURL){
        this.baseURL = baseURL;
        this.build();
    }

    public BackendAPI(){
        this.baseURL = "https://jsonplaceholder.typicode.com/";
        this.build();
    }

    public void build(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public IRetrofitAPI getAPI(){
        return this.retrofit.create(IRetrofitAPI.class);
    }

}
