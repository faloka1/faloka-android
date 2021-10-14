package com.example.faloka_mobile.Checkout;

import static com.example.faloka_mobile.R.color.netral_400;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Model.PaymentMethod;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PaymentViewModel extends ViewModel {

    CheckoutRepository checkoutRepository;

    public PaymentViewModel(CheckoutRepository checkoutRepository){
        this.checkoutRepository = checkoutRepository;
    }

    public LiveData<List<PaymentMethod>> getPaymentMethod(){
        return checkoutRepository.getPaymentMethod();
    }

    public LiveData<String> getTotalPrice(String subtotalParam, String servicePriceParam){
        double subtotal = Double.parseDouble(subtotalParam);
        double servicePrice = Double.parseDouble(servicePriceParam);

        MutableLiveData<String> total = new MutableLiveData<>();

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        total.setValue(formatRupiah.format(subtotal + servicePrice));
        return total;
    }

}
