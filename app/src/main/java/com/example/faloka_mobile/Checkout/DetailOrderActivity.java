package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.CheckoutBrandAdapter;
import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityDetailOrderBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailOrderActivity extends AppCompatActivity {

    ActivityDetailOrderBinding binding;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.detailOrderToolbar);
        getSupportActionBar().setTitle("Detail Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent() != null){
            Bundle bundle = getIntent().getBundleExtra("DATA_ORDER");
            order = bundle.getParcelable(Order.EXTRA_ORDER);
        }
        setDetailPrice();
        setDetailProduct();
        setDetailExpedition();
        setShowPayment();
    }

    private void setDetailPrice(){
        int total = 0;
        total = PaymentFragment.getTotal(order.getCartBrandList()) +2000;
        binding.tvDeliveryValueTotalProductPrice.setText(getFormatRupiah(PaymentFragment.getTotalProductPrice(order.getCartBrandList())));
        binding.tvDeliveryValueTotalDeliveryPrice.setText(getFormatRupiah(PaymentFragment.getTotalExpeditionPrice(order.getCartBrandList())));
        binding.tvOrderDetailValueServicePrice.setText(getFormatRupiah(2000));
        binding.tvOrderDetailValueTotalPrice.setText(getFormatRupiah(total));
    }

    private void setDetailProduct(){
        CheckoutBrandAdapter checkoutBrandAdapter = new CheckoutBrandAdapter(order.getCartBrandList());
        checkoutBrandAdapter.setOpenDialog(false);
        binding.rvDetailBrandProduct.setAdapter(checkoutBrandAdapter);
        binding.rvDetailBrandProduct.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
    }

    private void setDetailExpedition(){
        String address = order.getAddress().getLocation()+", "
                +order.getAddress().getProvince().getName()+", "
                +order.getAddress().getDistrict().getName()+", "
                +order.getAddress().getSubDistrict();
        binding.tvDeliveryName.setText(address);
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    public void setShowPayment(){
        if(order.getImagePaymentURL() != null) {
            binding.btnOrderShowPayment.setVisibility(View.VISIBLE);
            binding.btnOrderShowPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (order.getImagePaymentURL() != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setMessage("Bukti pembayaran kamu");

                        ImageView imageView = new ImageView(view.getContext());
                        Glide.with(view.getContext())
                                .load(ApiConfig.BASE_IMAGE_URL + order.getImagePaymentURL())
                                .apply(new RequestOptions().override(500, 500))
                                .into(imageView);
                        builder.setView(imageView);
                        builder.setNegativeButton(" ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create();
                        builder.show();
                    }
                }
            });
        }
        else {
            binding.btnOrderShowPayment.setVisibility(View.GONE);
        }
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