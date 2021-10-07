package com.example.faloka_mobile.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityActionAdressBinding;

public class ActionAddressActivity extends AppCompatActivity {

    ActivityActionAdressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActionAdressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Edit Alamat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}