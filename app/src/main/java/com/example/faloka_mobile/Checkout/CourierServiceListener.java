package com.example.faloka_mobile.Checkout;

import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;

import java.util.List;

public interface CourierServiceListener {
    public void onCourierService(Courier courier , List<CourierService> courierServiceList);
}
