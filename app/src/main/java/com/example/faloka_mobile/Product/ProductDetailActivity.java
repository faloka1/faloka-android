package com.example.faloka_mobile.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.databinding.ActivityProductDetailBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityProductDetailBinding binding;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();

        getDetailProductFromList();
        if(product!=null){
            setHeader();
            setDescription();
            binding.btnBuyNow.setOnClickListener(this);
        }
    }

    private void getDetailProductFromList(){
        product = getIntent().getParcelableExtra("product");
    }
    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Produk");
    }

    private void setHeader(){
        Glide.with(this)
                .load("http://192.168.100.7:8000"+product.getProductImageURL())
                .into(binding.detailImage);
        binding.tvDetailName.setText(product.getName());
        binding.tvDetailBrand.setText(product.getBrand().getName());
        Double price = Double.parseDouble(String.valueOf(product.getPrice()));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        binding.tvDetailPrice.setText(String.valueOf(formatRupiah.format(price)));
    }

    private void setDescription(){
        binding.tvDetailDescription.setText(product.getDescription());
    }

    private void setProductRelate(List<Product> products) {
        ProductAdapter productAdapter = new ProductAdapter(products);
        RecyclerView rvProductRelate = binding.rvProductRelate;
        rvProductRelate.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL, false));
        rvProductRelate.setAdapter(productAdapter);

    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CheckoutActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}