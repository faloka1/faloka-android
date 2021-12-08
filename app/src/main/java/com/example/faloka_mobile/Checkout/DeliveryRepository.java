package com.example.faloka_mobile.Checkout;

import android.view.View;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryRepository {

    public static final void getCouriers(View view, CourierListener courierListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<List<Courier>> callCouriers = ApiConfig.getApiService(tokenManager).getExpeditions();

        callCouriers.enqueue(new Callback<List<Courier>>() {
            @Override
            public void onResponse(Call<List<Courier>> call, Response<List<Courier>> response) {
                if(response.isSuccessful()){
                    List<Courier> courierList = response.body();
                    courierListener.onCourier(courierList);
                }
            }

            @Override
            public void onFailure(Call<List<Courier>> call, Throwable t) {

            }
        });
    }

    public static final void getCourierServices(View view, Courier courier, CourierServiceListener courierServiceListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<List<Courier>> callCostCouriers = ApiConfig.getApiService(tokenManager).getCostExpeditions(501, 114, 1000, courier.getCode() );
        callCostCouriers.enqueue(new Callback<List<Courier>>() {
            @Override
            public void onResponse(Call<List<Courier>> call, Response<List<Courier>> response) {
                if(response.isSuccessful()){
                    List<Courier> courierList = response.body();
                    List<CourierService> courierServiceList = courierList.get(0).getCourierServiceList();
                    courierServiceListener.onCourierService(courier, courierServiceList);
                }
            }

            @Override
            public void onFailure(Call<List<Courier>> call, Throwable t) {

            }
        });
    }

}
