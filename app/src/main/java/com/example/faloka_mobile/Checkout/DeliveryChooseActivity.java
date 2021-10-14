package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.CourierAdapter;
import com.example.faloka_mobile.Adapter.SubCategoryAdapter;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Profile;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityDeliveryChooseBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryChooseActivity extends AppCompatActivity {

    ActivityDeliveryChooseBinding binding;
//    List<Courier> courierList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();

        binding.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
            }
        });
//        courierList = new ArrayList<>();
//        for(int i=0; i<3; i++){
//            Courier courier = new Courier("JNE");
//            List<CourierService> courierServiceList = new ArrayList<>();
//            for(int j=0; j<3; j++){
//                CourierService courierService = new CourierService("Reguler", 10000);
//                courierServiceList.add(courierService);
//            }
//            courier.setCourierServiceList(courierServiceList);
//            courierList.add(courier);
//        }

        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        Call<List<Courier>> callCouriers = ApiConfig.getApiService(tokenManager).getExpeditions();

        callCouriers.enqueue(new Callback<List<Courier>>() {
            @Override
            public void onResponse(Call<List<Courier>> call, Response<List<Courier>> response) {
                List<Courier> courierList = response.body();
                CourierAdapter courierAdapter;
                courierAdapter = new CourierAdapter(courierList);
                binding.rvCourier.setAdapter(courierAdapter);
                binding.rvCourier.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Courier>> call, Throwable t) {

            }
        });



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