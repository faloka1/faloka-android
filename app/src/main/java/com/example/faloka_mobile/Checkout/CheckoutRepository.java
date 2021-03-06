package com.example.faloka_mobile.Checkout;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Home.HomeRepository;
import com.example.faloka_mobile.LoadingDialog;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.Model.PaymentUploadResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutRepository {
    private volatile static CheckoutRepository INSTANCE = null;
    private Context context;

    public CheckoutRepository(Context context){
        this.context = context;
    }

    public static CheckoutRepository getINSTANCE(Context context){
        if(INSTANCE!=null){
            synchronized (HomeRepository.class){
                INSTANCE = new CheckoutRepository(context);
            }
        }
        return INSTANCE;
    }

    public LiveData<List<PaymentMethod>> getPaymentMethod(){
        List<PaymentMethod> paymentMethodList = new ArrayList<>();
        MutableLiveData<List<PaymentMethod>> data = new MutableLiveData<>();
        paymentMethodList.add(new PaymentMethod("BNI", "Ela", "278182718"));
        paymentMethodList.add(new PaymentMethod("BRI", "Agus", "278182718"));

        data.setValue(paymentMethodList);

        return data;

    }

    public static final void uploadMultipart(View view, File file, Order order, UploadFileListener uploadFileListener) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody method = RequestBody.create(MediaType.parse("multipart/form-data"), "PATCH");
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        tokenManager.setLoadingDialog(new LoadingDialog((Activity) view.getContext()));
        tokenManager.getLoadingDialog().startLoadingDialog();
        Call<PaymentUploadResponse> callUploadPayment = ApiConfig.getApiService(tokenManager).uploadPhotoMultipart(method, photoPart, order.getId() );
        callUploadPayment.enqueue(new Callback<PaymentUploadResponse>() {
            @Override
            public void onResponse(Call<PaymentUploadResponse> call, Response<PaymentUploadResponse> response) {
                if(response.isSuccessful()) {
                    PaymentUploadResponse paymentUploadResponse = response.body();
                    uploadFileListener.onUpload(paymentUploadResponse);
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                }
                else {
                    tokenManager.getLoadingDialog().dismissLoadingDialog();
                    Toast.makeText(view.getContext(),"GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentUploadResponse> call, Throwable t) {
                tokenManager.getLoadingDialog().dismissLoadingDialog();
                Toast.makeText(view.getContext(), "FAIL API"+ call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
