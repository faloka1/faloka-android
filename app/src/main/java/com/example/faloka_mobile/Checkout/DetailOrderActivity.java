package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityDetailOrderBinding;

import java.text.NumberFormat;
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
    }

    private void setDetailPrice(){
        binding.tvDeliveryValueTotalProductPrice.setText(getFormatRupiah(order.getProduct().getPrice()));
        binding.tvDeliveryValueTotalDeliveryPrice.setText(getFormatRupiah(order.getCourierService().getCost().get(0).getValue()));
        binding.tvOrderDetailValueServicePrice.setText(getFormatRupiah(order.getPayment().getPriceService()));
        binding.tvOrderDetailValueTotalPrice.setText(getFormatRupiah(order.getTotalOrder()));
    }

    private void setDetailProduct(){
        binding.tvDeliveryBrand.setText(order.getProduct().getBrand().getName());
        binding.gridDetailProduct.tvOrderProductSizeValue.setText(order.getProduct().getSizeProduct());
        binding.gridDetailProduct.tvOrderProductName.setText(order.getProduct().getName());
        binding.gridDetailProduct.tvOrderProductPrice.setText(getFormatRupiah(order.getProduct().getPrice()));
        Glide.with(binding.gridDetailProduct.imageOrderProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+order.getProduct().getProductImageURL() )
                .into(binding.gridDetailProduct.imageOrderProduct);
    }

    private void setDetailExpedition(){
        String expedition = order.getCourier().getName()+" "+order.getCourierService().getName();
        binding.tvDeliveryEkspedition.setText(expedition);
        binding.tvDeliveryExpeditionPrice.setText(getFormatRupiah(order.getCourierService().getCost().get(0).getValue()));
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