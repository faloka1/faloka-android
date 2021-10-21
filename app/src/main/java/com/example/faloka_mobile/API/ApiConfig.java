package com.example.faloka_mobile.API;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.faloka_mobile.Login.TokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiConfig {

    public static final String BASE_URL = "http://13.59.13.137:80/api/";
    public static final String BASE_IMAGE_URL = "http://13.59.13.137:80";
    static OkHttpClient client;
    public static ApiService getApiService(TokenManager tokenManager) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        if(tokenManager.isLogin()){
             client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @NonNull
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("authorization",tokenManager.getToken()).build();
                            return chain.proceed(newRequest);
                        }
                    }).build();
        }
        else {
            client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(ApiService.class);
    }
}
