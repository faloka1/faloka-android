package com.example.faloka_mobile.Home;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.LoadingDialog;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private volatile static HomeRepository INSTANCE = null;
    private Context context;

    public HomeRepository(Context context){
        this.context = context;
    }

    public static HomeRepository getINSTANCE(Context context){
        if(INSTANCE!=null){
            synchronized (HomeRepository.class){
                INSTANCE = new HomeRepository(context);
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Category>> getCategory(){

        MutableLiveData<List<Category>> categoryResult = new MutableLiveData<>();

        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("Token",0));
        tokenManager.setLoadingDialog(new LoadingDialog((Activity) context));
        tokenManager.getLoadingDialog().startLoadingDialog();
        Call<List<Category>> callCategories;
        callCategories = ApiConfig.getApiService(tokenManager).getCategories();
        callCategories.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoryResult.postValue(response.body());
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                    Log.e("berhasil", "onResponse: ");
                }
                else{}

            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("gagal", "onFailure: " + t.getMessage());
            }
        });
        return categoryResult;
    }
}
