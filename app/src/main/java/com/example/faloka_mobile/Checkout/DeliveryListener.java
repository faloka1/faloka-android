package com.example.faloka_mobile.Checkout;

import android.os.Parcelable;

import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;

import java.io.Serializable;


public interface DeliveryListener{
    public void onDelivery(Courier courier, CourierService courierService);
}
