package com.example.faloka_mobile.Checkout;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Home.HomeRepository;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Category;

import java.util.List;

public class CheckoutRepository {
    private volatile static CheckoutRepository INSTANCE = null;
    private Context context;

    public CheckoutRepository(Context context){
        this.context = context;
    }

    public static CheckoutRepository getINSTANCE(Context context){
        if(INSTANCE!=null){
            synchronized (HomeRepository.class){
                INSTANCE = new CheckoutRepository(context);
            }
        }
        return INSTANCE;
    }

}
