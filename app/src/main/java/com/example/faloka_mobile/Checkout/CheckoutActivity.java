package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    public ActivityCheckoutBinding binding;
    public ArrayList<String> label = new ArrayList<>();
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();
        setStepProgressBar();
        setContent();

    }

    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Checkout");
    }

    private void setStepProgressBar(){
        label.add("Pengiriman");
        label.add("Pembayaran");
        binding.stepView.setBackgroundColor(getResources().getColor(R.color.white));
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
        product = getIntent().getParcelableExtra(Product.EXTRA_PRODUCT);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Product.EXTRA_PRODUCT, product);
        DeliveryFragment deliveryFragment = new DeliveryFragment();
        deliveryFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.frame_container_2, deliveryFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Address.REQUEST_EDIT_ADDRESS && resultCode == Address.RESULT_EDIT_ADDRESS){
            Toast.makeText(getApplicationContext(), "HAHA", Toast.LENGTH_SHORT).show();
            setContent();
        }
    }

}