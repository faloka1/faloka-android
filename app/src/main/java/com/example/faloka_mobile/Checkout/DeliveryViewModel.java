package com.example.faloka_mobile.Checkout;

import androidx.lifecycle.ViewModel;

public class DeliveryViewModel extends ViewModel {

    private CheckoutRepository mCheckoutRepository;
    public DeliveryViewModel(CheckoutRepository checkoutRepository){
        mCheckoutRepository = checkoutRepository;
    }
}
