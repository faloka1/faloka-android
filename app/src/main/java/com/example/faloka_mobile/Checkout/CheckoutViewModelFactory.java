package com.example.faloka_mobile.Checkout;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.faloka_mobile.Home.HomeViewModel;


public class CheckoutViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile CheckoutViewModelFactory INSTANCE=null;
    private final CheckoutRepository mCheckoutRepository;

    public CheckoutViewModelFactory(CheckoutRepository checkoutRepository){
        mCheckoutRepository = checkoutRepository;
    }

    public static CheckoutViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CheckoutViewModelFactory.class) {
                INSTANCE = new CheckoutViewModelFactory(CheckoutRepository.getINSTANCE(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeliveryViewModel.class)) {
            return (T) new DeliveryViewModel(mCheckoutRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
