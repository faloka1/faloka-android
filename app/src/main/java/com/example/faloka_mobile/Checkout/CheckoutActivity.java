package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements StepViewSelectedListener, StepView.OnStepClickListener {

    public ActivityCheckoutBinding binding;
    public ArrayList<String> label = new ArrayList<>();
//    private Product product;
    private List<Product> productList;

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
        binding.stepView.setOnStepClickListener(this);
    }
    private void setContent(){
        productList = getIntent().getParcelableArrayListExtra(Product.EXTRA_PRODUCT);

//        product = getIntent().getParcelableExtra(Product.EXTRA_PRODUCT);
        Bundle bundle = new Bundle();
//        bundle.putParcelable(Product.EXTRA_PRODUCT, product);
        bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList)productList);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DeliveryFragment deliveryFragment = new DeliveryFragment(this::onStep);
        deliveryFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.frame_container_checkout, deliveryFragment);
        ft.addToBackStack("HAHA");
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
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container_checkout);
                if(f instanceof DeliveryFragment){
                    finish();
                }
                else if(f instanceof PaymentFragment) {
                    this.getSupportFragmentManager().popBackStack();
                    onStep(DeliveryFragment.DELIVERY_STEP);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Address.REQUEST_EDIT_ADDRESS && resultCode == Address.RESULT_EDIT_ADDRESS){
            setContent();
        }
        if(requestCode == Address.REQUEST_ADD_ADDRESS && resultCode == Address.RESULT_ADD_ADDRESS){
            setContent();
        }
    }

    @Override
    public void onStep(int step) {
        binding.stepView.go(step, true);
    }

    @Override
    public void onStepClick(int step) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container_checkout);
        if(f instanceof DeliveryFragment){
            return;
        }
        else if(f instanceof PaymentFragment) {
            this.getSupportFragmentManager().popBackStack();
        }
        onStep(step);
    }
}