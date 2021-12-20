package com.example.faloka_mobile.InspireMe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.LoadingDialog;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.Model.InspireMe;
import com.example.faloka_mobile.Model.InspireMeResponse;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.OrderResponseForInspireMe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspireMeRepositry {
    private volatile static InspireMeRepositry INSTANCE = null;
    private Context context;
    private TokenManager tokenManager;

    public InspireMeRepositry(Context context){
        this.context = context;
        tokenManager = TokenManager.
                getInstance(context.getSharedPreferences("Token",0));
    }

    public static InspireMeRepositry getINSTANCE(Context context){
        if(INSTANCE!=null){
            synchronized (InspireMeRepositry.class){
                INSTANCE = new InspireMeRepositry(context);
            }
        }
        return INSTANCE;
    }
    public LiveData<InspireMeResponse> getPost(int page){
        MutableLiveData<InspireMeResponse> inspireMeData = new MutableLiveData<>();
        Call<InspireMeResponse> response = ApiConfig.getApiService(tokenManager).getInspireMe(page);

        response.enqueue(new Callback<InspireMeResponse>() {
            @Override
            public void onResponse(Call<InspireMeResponse> call, Response<InspireMeResponse> response) {
                if(response.isSuccessful()){
                    InspireMeResponse inspireMeResponse = response.body();
                    inspireMeData.setValue(inspireMeResponse);
                }
            }

            @Override
            public void onFailure(Call<InspireMeResponse> call, Throwable t) {
                Log.e("####", String.valueOf(t));
            }

        });
        return inspireMeData;
    }
    public MutableLiveData<List<OrderResponseForInspireMe>> getOrder(){
        MutableLiveData<List<OrderResponseForInspireMe>> data = new MutableLiveData<>();

        Call<List<OrderResponseForInspireMe>> response = ApiConfig.getApiService(tokenManager).getOrderForProduct(tokenManager.getTypeToken()+" "+tokenManager.getToken());
        response.enqueue(new Callback<List<OrderResponseForInspireMe>>() {
            @Override
            public void onResponse(Call<List<OrderResponseForInspireMe>> call, Response<List<OrderResponseForInspireMe>> response) {
                if(response.isSuccessful()){
                    List<OrderResponseForInspireMe> orderProductsList = response.body();
                    data.setValue(orderProductsList);
                }
                else{
                    Log.e("gagal", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponseForInspireMe>> call, Throwable t) {
                Log.e("####", String.valueOf(t));
            }
        });
        return data;
    }
    public void add(MultipartBody body){
        Call<Message> response = ApiConfig.getApiService(tokenManager).addInspireMe(
                tokenManager.getTypeToken()+" "+tokenManager.getToken(),body);
        tokenManager.setLoadingDialog(new LoadingDialog((Activity) context));
        tokenManager.getLoadingDialog().startLoadingDialog();
        response.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Message message = response.body();
                    Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(InspireMeFragment.EXTRA_FRAGMENT_INSPO, InspireMeFragment.INDEX_FRAGMENT_ACCOUNT);
                    ((Activity) context).startActivity(intent);
                    ((Activity) context).finish();
                    Log.d("berhasil", response.message());
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                }
                else{
                    Log.e("gagal upload", response.message());
                    Toast.makeText(context, "GAGAL UPLOAD", Toast.LENGTH_SHORT).show();
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("gagal", String.valueOf(t));
                tokenManager.getLoadingDialog().dismissLoadingDialog();
            }
        });
    }
    public LiveData<List<InspireMe>> getInspireMeById(){
        MutableLiveData<List<InspireMe>> inspireMe = new MutableLiveData<>();

        Call<List<InspireMe>> response = ApiConfig.getApiService(tokenManager).getInspireMeById(
                tokenManager.getTypeToken()+tokenManager.getToken());
        response.enqueue(new Callback<List<InspireMe>>() {
            @Override
            public void onResponse(Call<List<InspireMe>> call, Response<List<InspireMe>> response) {
                if(response.isSuccessful()){
                    List<InspireMe> inspireMeList = response.body();
                    inspireMe.setValue(inspireMeList);
                }
                else{
                    Log.d("gagal response",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<InspireMe>> call, Throwable t) {
                Log.d("gagal",String.valueOf(t));
            }
        });
        return inspireMe;
    }

}
