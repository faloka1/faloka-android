package com.example.faloka_mobile.Checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    public ActivityCheckoutBinding binding;
    public ArrayList<String> label = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setStepProgressBar();
        setContent();

    }
    private void setStepProgressBar(){
        label.add("Pengiriman");
        label.add("Pembayaran");
        binding.stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .steps(label)
                .stepsNumber(2)
                .nextStepCircleEnabled(true)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .typeface(ResourcesCompat.getFont(this, R.font.khula))
                .commit();
    }
    private void setContent(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_container_2, new DeliveryFragment());
        ft.commit();
    }

}