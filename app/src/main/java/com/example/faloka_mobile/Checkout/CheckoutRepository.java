package com.example.faloka_mobile.Checkout;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faloka_mobile.Home.HomeRepository;
import com.example.faloka_mobile.Model.PaymentMethod;

import java.util.ArrayList;
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

    public LiveData<List<PaymentMethod>> getPaymentMethod(){
        List<PaymentMethod> paymentMethodList = new ArrayList<>();
        MutableLiveData<List<PaymentMethod>> data = new MutableLiveData<>();
        paymentMethodList.add(new PaymentMethod("BNI", "Ela", "278182718"));
        paymentMethodList.add(new PaymentMethod("BRI", "Agus", "278182718"));

        data.setValue(paymentMethodList);

        return data;

    }

}
