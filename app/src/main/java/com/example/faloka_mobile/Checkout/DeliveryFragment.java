package com.example.faloka_mobile.Checkout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentDeliveryBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class DeliveryFragment extends Fragment{

    Product product;
    FragmentDeliveryBinding binding;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        setAddressSection();
        setContentProduct();
        setExpedition();
        setFooterDelivery();

        return view;
    }

    private void setContentProduct(){
        if(getArguments() != null){
            product = getArguments().getParcelable(Product.EXTRA_PRODUCT);
            setProductOrder();

            binding.tvDeliveryBrand.setText(product.getBrand().getName());
            binding.tvDeliverySubtotalValue.setText(String.valueOf(getFormatRupiah(0 ) ));
        }
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    private void setExpedition(){

    }

    private void setProductOrder(){
        TextView tvOrderProductName = view.findViewById(R.id.tv_order_product_name);
        TextView tvOrderProductPrice = view.findViewById(R.id.tv_order_product_price);
        ImageView imgOrderProduct = view.findViewById(R.id.image_order_product);

        tvOrderProductPrice.setText(String.valueOf(getFormatRupiah(product.getPrice())));
        tvOrderProductName.setText(product.getName());

        Glide.with(imgOrderProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+product.getProductImageURL())
                .into(imgOrderProduct);
    }

    private void setAddressSection(){
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ActionAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setFooterDelivery(){
        binding.tvTotalPrice.setText(String.valueOf(getFormatRupiah(0)));
        binding.btnNextPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}