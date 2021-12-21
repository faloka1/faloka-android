package com.example.faloka_mobile.Checkout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Account.AccountFragment;
import com.example.faloka_mobile.Cart.CartRepository;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderResponse;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.PaymentUploadResponse;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityConfrimCheckoutBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCheckoutActivity extends AppCompatActivity implements View.OnClickListener ,UploadFileListener {
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

        if(getIntent().getBundleExtra("DATA_ORDER") != null){
            bundle = getIntent().getBundleExtra("DATA_ORDER");
            order = bundle.getParcelable(Order.EXTRA_ORDER);
            Snackbar snackbar = Snackbar.make(binding.coordinatorLayoutTopConfirmCheckout, "Pesananmu dalam proses, silahkan upload bukti pembayaran", Snackbar.LENGTH_LONG);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.primary_dark));
            snackbar.setTextColor(getResources().getColor(R.color.primary_dark));
            snackbar.setBackgroundTint(getResources().getColor(R.color.semantic_success));
            snackbar.show();
        }
        if(getIntent().getBundleExtra("DATA_ORDER_ACCOUNT") != null){
            bundle = getIntent().getBundleExtra("DATA_ORDER_ACCOUNT");
            order = bundle.getParcelable(Order.EXTRA_ORDER);
        }

        binding.tvConfirmValueMethodPayment.setText(order.getPayment().getPaymentName());
        binding.tvConfirmValuePaymentCode.setText(order.getPayment().getAccountNumber()
                + " (" + order.getPayment().getAccountName() + ")");
        int total = PaymentFragment.getTotal(order.getCartBrandList()) + 2000;
        binding.tvConfirmValueTotalPayment.setText(getFormatRupiah(total));
        binding.btnDetail.setOnClickListener(this);
        binding.btnUpload.setOnClickListener(this);
        binding.btnShopping.setOnClickListener(this);
        binding.btnProfile.setOnClickListener(this);
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
        else if(view.findViewById(R.id.btn_shopping) == binding.btnShopping){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(view.findViewById(R.id.btn_upload) == binding.btnUpload){
            choosePhoto();
        }
        else if(view.getId() == binding.btnProfile.getId()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(AccountFragment.EXTRA_FRAGMENT_ACCOUNT, AccountFragment.INDEX_FRAGMENT_ACCOUNT);
            startActivity(intent);
            finish();
        }
    }

    public void confirmUpload(Uri uri){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(binding.getRoot().getContext());
        alertBuilder.setMessage("Apakah kamu yakin upload bukti pembayaran?");
        alertBuilder.setCancelable(true);
        ImageView imageView = new ImageView(binding.getRoot().getContext());
        Glide.with(binding.getRoot().getContext())
                .load(uri)
                .apply(new RequestOptions().override(500, 500))
                .into(imageView);
        alertBuilder.setView(imageView);
        alertBuilder.setPositiveButton(
                "Upload",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(uri != null) {
                            File file = FileUtils.getFile(getApplicationContext(), uri);
                            CheckoutRepository.uploadMultipart(binding.getRoot(), file,order, ConfirmCheckoutActivity.this::onUpload);
                        }else{
                            Snackbar snackbar = Snackbar.make(binding.coordinatorLayoutTopConfirmCheckout, "Pilih gambar bukti pembayaran dahulu", Snackbar.LENGTH_LONG);
                            snackbar.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            snackbar.setActionTextColor(getResources().getColor(R.color.primary_dark));
                            snackbar.setTextColor(getResources().getColor(R.color.primary_dark));
                            snackbar.setBackgroundTint(getResources().getColor(R.color.semantic_error));
                            snackbar.show();
                        }
                    }
                }
        );
        alertBuilder.setNegativeButton(
                "Batal",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if(data != null) {
                uri = data.getData();
                System.out.println(uri);
                confirmUpload(uri);
            }
            else {
                Snackbar snackbar = Snackbar.make(binding.coordinatorLayoutTopConfirmCheckout, "Pilih gambar bukti pembayaran dahulu", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                snackbar.setActionTextColor(getResources().getColor(R.color.primary_dark));
                snackbar.setTextColor(getResources().getColor(R.color.primary_dark));
                snackbar.setBackgroundTint(getResources().getColor(R.color.semantic_error));
                snackbar.show();
            }
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

    @Override
    public void onUpload(PaymentUploadResponse paymentUploadResponse) {
        order.setImagePaymentURL(paymentUploadResponse.getImagePaymentURL());
        Snackbar snackbar = Snackbar.make(binding.coordinatorLayoutTopConfirmCheckout, "Bukti pembayaran berhasil di update", Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.primary_dark));
        snackbar.setTextColor(getResources().getColor(R.color.primary_dark));
        snackbar.setBackgroundTint(getResources().getColor(R.color.semantic_success));
        snackbar.show();
        binding.btnUpload.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {

    }
}