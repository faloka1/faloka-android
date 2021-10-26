package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.CourierAdapter;
import com.example.faloka_mobile.Adapter.SubCategoryAdapter;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Cost;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Profile;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityDeliveryChooseBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryChooseActivity extends AppCompatActivity {

    ActivityDeliveryChooseBinding binding;
    Intent intent;
//    List<Courier> courierList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = new Intent();
        setToolbar();
        binding.btnChoose.setEnabled(false);
        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        Call<List<Courier>> callCouriers = ApiConfig.getApiService(tokenManager).getExpeditions();

        callCouriers.enqueue(new Callback<List<Courier>>() {
            @Override
            public void onResponse(Call<List<Courier>> call, Response<List<Courier>> response) {
                List<Courier> courierList = response.body();
//                CourierAdapter courierAdapter;
//                courierAdapter = new CourierAdapter(courierList);
//                binding.rvCourier.setAdapter(courierAdapter);
//                binding.rvCourier.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                int i=0;
                for(Courier courier : courierList){
                    RadioButton radioButton = new RadioButton(getApplicationContext());
                    radioButton.setId(i);
                    radioButton.setText(courier.getName());
                    radioButton.setTextColor(getResources().getColor(R.color.black_faloka));
                    ColorStateList myColorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{getResources().getColor(R.color.faloka_accent_green)}
                            },
                            new int[]{getResources().getColor(R.color.faloka_accent_green)}
                    );
                    radioButton.setButtonTintList(myColorStateList);
                    binding.radioGroupCourier.addView(radioButton);
                    i++;
                }

                binding.radioGroupCourier.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(radioGroup.getCheckedRadioButtonId() != -1){
                            Courier courier = courierList.get(i);
                            setService(courier);

                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Courier>> call, Throwable t) {

            }
        });

        binding.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(DeliveryFragment.RESULT_CHOOSE_DELIVERY, intent);
                finish();
            }
        });
    }

    void setService(Courier courier){
        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        Call<List<Courier>> callCostCouriers = ApiConfig.getApiService(tokenManager).getCostExpeditions(501, 114, 1000, courier.getCode() );
//        Toast.makeText(getApplicationContext(), courier.getCode(), Toast.LENGTH_SHORT).show();

        callCostCouriers.enqueue(new Callback<List<Courier>>() {
            @Override
            public void onResponse(Call<List<Courier>> call, Response<List<Courier>> response) {
//                Toast.makeText(getApplicationContext(), "HMM", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()) {
                    List<Courier> courierList = response.body();
                    List<CourierService> courierServiceList = courierList.get(0).getCourierServiceList();
                    binding.radioGroupCourierservice.removeAllViews();
                    int i=0;
                    for(CourierService courierService : courierServiceList){
                        Cost cost = courierService.getCost().get(0);
                        RadioButton radioButton = new RadioButton(getApplicationContext());
                        radioButton.setId(i);
                        radioButton.setText(courierService.getName()+" ("+getFormatRupiah(cost.getValue())+") - "+cost.getEtd() +" Hari");
                        radioButton.setTextColor(getResources().getColor(R.color.black_faloka));
                        ColorStateList myColorStateList = new ColorStateList(
                                new int[][]{
                                        new int[]{getResources().getColor(R.color.faloka_accent_green)}
                                },
                                new int[]{getResources().getColor(R.color.faloka_accent_green)}
                        );
                        radioButton.setButtonTintList(myColorStateList);
                        binding.radioGroupCourierservice.addView(radioButton);
                        i++;
                    }
                    binding.radioGroupCourierservice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            if(radioGroup.getCheckedRadioButtonId() != -1){
                                CourierService courierService = courierServiceList.get(i);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(Courier.EXTRA_COURIER, courier);
                                bundle.putParcelable(CourierService.EXTRA_COURIER_SERVICE, courierService);
                                intent.putExtra(DeliveryFragment.EXTRA_CHOOSE_DELIVERY, bundle);
//                                intent.putExtra(DeliveryFragment.EXTRA_CODE_EXPEDITION, courier.getCode());
//                                intent.putExtra(DeliveryFragment.EXTRA_PRICE_EXPEDITION, courierService.getCost().get(0).getValue());
                                System.out.println(courierService.getName() +" "+(radioGroup.getCheckedRadioButtonId()));
                                binding.btnChoose.setEnabled(true);
                                binding.btnChoose.setTextColor(getResources().getColor(R.color.white));
                                binding.btnChoose.setBackgroundColor(getResources().getColor(R.color.black_faloka));
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(), "GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Courier>> call, Throwable t) {

            }
        });
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pilih Ekspedisi");
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
}