package com.example.faloka_mobile.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityDetailOrderBinding;

public class DetailOrderActivity extends AppCompatActivity {

    ActivityDetailOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.detailOrderToolbar);
        getSupportActionBar().setTitle("Detail Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}