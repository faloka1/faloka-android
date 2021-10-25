package com.example.faloka_mobile.InspireMe;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faloka_mobile.Checkout.CheckoutRepository;
import com.example.faloka_mobile.Home.HomeRepository;
import com.example.faloka_mobile.Model.InspireMe;

import java.util.ArrayList;
import java.util.List;

public class InspireMeRepositry {
    private volatile static InspireMeRepositry INSTANCE = null;
    private Context context;

    public InspireMeRepositry(Context context){
        this.context = context;
    }

    public static InspireMeRepositry getINSTANCE(Context context){
        if(INSTANCE!=null){
            synchronized (InspireMeRepositry.class){
                INSTANCE = new InspireMeRepositry(context);
            }
        }
        return INSTANCE;
    }
    public LiveData<List<InspireMe>> getPost(){
        MutableLiveData<List<InspireMe>> data = new MutableLiveData<>();
        List<InspireMe> inspireMeList = new ArrayList<>();

        inspireMeList.add(new InspireMe(1, "elaamr7", "Miror selfie check! lagi seneng banget pakek baju ini"));
        inspireMeList.add(new InspireMe(2, "elaamr8", "Miror selfie check! lagi seneng banget pakek baju ini"));
        inspireMeList.add(new InspireMe(3, "elaamr9", "Miror selfie check! lagi seneng banget pakek baju ini"));
        inspireMeList.add(new InspireMe(4, "elaamr10", "Miror selfie check! lagi seneng banget pakek baju ini"));

        data.setValue(inspireMeList);
        return data;
    }
}
