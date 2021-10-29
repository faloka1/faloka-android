package com.example.faloka_mobile.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityProductDetailBinding;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity {

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
            setProductRelate();
            onClickButtonBuy();
        }
    }

    private void onClickButtonBuy(){
        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, CheckoutActivity.class);
                intent.putExtra(Product.EXTRA_PRODUCT, product);
                startActivity(intent);
            }
        });
    }
    private void getDetailProductFromList(){
        product = getIntent().getParcelableExtra(Product.EXTRA_PRODUCT);
    }
    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Produk");
    }

    private void setHeader(){

        binding.carouselDetailImage.setPageCount(product.getProductCarouselImageURL().size());
        binding.carouselDetailImage.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(imageView.getContext())
                        .load(ApiConfig.BASE_IMAGE_URL+product.getProductCarouselImageURL()
                                .get(position).getImageURL())
                        .into(imageView);
            }
        });

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

    private void setProductRelate() {
        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        Call<List<Product>> callRelatedProducts;
        callRelatedProducts = ApiConfig.getApiService(tokenManager).getRelatedProducts(product.getSlug());
        callRelatedProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> respProduct;
                respProduct = response.body();
                System.out.println(respProduct);
                ProductAdapter productAdapter = new ProductAdapter(respProduct);
                RecyclerView rvProductRelate = binding.rvProductRelate;
                rvProductRelate.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL, false));
                rvProductRelate.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });



    }

}