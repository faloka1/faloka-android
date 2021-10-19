package com.example.faloka_mobile.Checkout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Checkout;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderResponse;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.example.faloka_mobile.databinding.ActivityConfrimCheckoutBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    ActivityConfrimCheckoutBinding binding;
    Order order;
    Bundle bundle;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfrimCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        order = new Order();
        bundle = new Bundle();

        if(getIntent() != null){
            bundle = getIntent().getBundleExtra("DATA_ORDER");
            order = bundle.getParcelable(Order.EXTRA_ORDER);
        }

//        Bundle bundle = getIntent().getParcelableExtra("DATA_CHECKOUT");
//        PaymentMethod paymentMethod = bundle.getParcelable("PAYMENT_METHOD");
//        String total = bundle.getString("TOTAL_PRICE");

//        binding.tvConfirmValueMethodPayment.setText(paymentMethod.getBank());
//        binding.tvConfirmValuePaymentCode.setText(paymentMethod.getRekeningNumber()
//                + " (" + paymentMethod.getRekeningName() + ")");
//        binding.tvConfirmValueTotalPayment.setText(total);

        binding.tvConfirmValueMethodPayment.setText(order.getPayment().getPaymentName());
        binding.tvConfirmValuePaymentCode.setText(order.getPayment().getAccountNumber()
                + " (" + order.getPayment().getAccountName() + ")");
        binding.tvConfirmValueTotalPayment.setText(getFormatRupiah(order.getTotalOrder()));

        binding.btnDetail.setOnClickListener(this);
        binding.btnHowToPay.setOnClickListener(this);
        binding.btnUpload.setOnClickListener(this);

    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    @Override
    public void onClick(View view) {
        if(view.findViewById(R.id.btn_detail) == binding.btnDetail){
            Intent intent = new Intent(this, DetailOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Order.EXTRA_ORDER, order);
            intent.putExtra("DATA_ORDER", bundle);
            startActivity(intent);
        }
        else if(view.findViewById(R.id.btn_how_to_pay) == binding.btnHowToPay){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.layout_how_to_pay);

            final TextView tvPembayaran = dialog.findViewById(R.id.how_to_pay_title);
            Button button = dialog.findViewById(R.id.btn_how_to_pay_close);

            button.setOnClickListener((v)->{
                dialog.dismiss();
            });
            dialog.show();

        }
        else if(view.findViewById(R.id.btn_upload) == binding.btnUpload){


//            if(uri == null){
                choosePhoto();
//            }
//            else {
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext() );
//                alertBuilder.setMessage("Apakah kamu yakin upload ini?");
//                alertBuilder.setCancelable(true);
//                alertBuilder.setPositiveButton(
//                        "Upload",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if(uri != null) {
//                                    File file = FileUtils.getFile(view.getContext(), uri);
//                                    uploadMultipart(file);
//                                }else{
//                                    Toast.makeText(view.getContext(), "You must choose the image", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                );
//                alertBuilder.setNegativeButton(
//                        "Batal",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        }
//                );
//            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if(data != null) {
                uri = data.getData();
                System.out.println(uri);
                if(uri != null) {
                    File file = FileUtils.getFile(getApplicationContext(), uri);
                    uploadMultipart(file);
                }else{
                    Toast.makeText(getApplicationContext(), "You must choose the image", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "GAGAL UPLOAD", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "GAGAL TERIMA DATA", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                return;
            }
        }
    }

    private void uploadMultipart(File file) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
//
        RequestBody method = RequestBody.create(MediaType.parse("multipart/form-data"), "PATCH");
        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        Call<Message> callUploadPayment = ApiConfig.getApiService(tokenManager).uploadPhotoMultipart(method, photoPart, order.getOrderID());
        callUploadPayment.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()) {
                    Message message = response.body();
                    Toast.makeText(getApplicationContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
//        uploadService = new UploadService();
//        uploadService.uploadPhotoMultipart(action, photoPart, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                BaseResponse baseResponse = (BaseResponse) response.body();
//
//                if(baseResponse != null) {
//                    Toast.makeText(MainActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);

        }else{
            openGallery();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

}