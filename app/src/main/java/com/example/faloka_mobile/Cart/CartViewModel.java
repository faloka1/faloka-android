package com.example.faloka_mobile.Cart;

import androidx.appcompat.app.AppCompatActivity;

import com.example.faloka_mobile.databinding.ActivityCartBinding;

public class CartViewModel {

    private AppCompatActivity activity;
    private ActivityCartBinding binding;

    public CartViewModel(ActivityCartBinding binding, AppCompatActivity activity){
        this.activity = activity;
        this.binding = binding;
    }

    public void setToolbar(){
        activity.setSupportActionBar(binding.cartToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Tas");
    }


}
