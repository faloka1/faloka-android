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
import com.example.faloka_mobile.Account.AuthFlagListener;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Cart.CartAddItemListener;
import com.example.faloka_mobile.Cart.CartRepository;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.MixAndMatch.MixMatchActivity;
import com.example.faloka_mobile.Model.BodyCart;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityProductDetailBinding;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity implements CartAddItemListener, AuthFlagListener, ProductListener {

    ActivityProductDetailBinding binding;
    Product product;
    private static boolean flagAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        flagAuth = false;
        setToolbar();

        getDetailProductFromList();
        if(product!=null){
            setHeader();
            setDescription();
            setProductRelate();
            onClickButtonBuy();
            onClickButtonCart();
            onClickButtonMixMatch();
        }
    }

    private void onClickButtonBuy(){
        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!LoginRepository.isValidationLogin(ProductDetailActivity.this)){
                    return;
                }
                List<Cart> cartList = new ArrayList<>();
//                List<Product> productList = new ArrayList<>();
//                productList.add(product);
                Cart cart = new Cart();
                Variant variant = product.getVariantList().get(0);
                cart.setProduct(product);
                cart.setVariant(variant);
                cart.setQuantity(1);
                cart.setProductID(product.getId());
                cart.setVariantID(product.getVariantList().get(0).getId());
                cartList.add(cart);
                Intent intent = new Intent(ProductDetailActivity.this, CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList)cartList);
//                intent.putExtra(Product.EXTRA_PRODUCT, product);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void onClickButtonCart(){
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!LoginRepository.isValidationLogin(ProductDetailActivity.this)){
                    return;
                }
                Variant variant = product.getVariantList().get(0);
                BodyCart bodyCart = new BodyCart();
                bodyCart.setProductID(product.getId());
                bodyCart.setVariantID(variant.getId());
                bodyCart.setQuantity(1);
                CartRepository.addCart(view, bodyCart, ProductDetailActivity.this::onAddToCart);
            }
        });
    }

    public void onClickButtonMixMatch(){
        binding.fabMixMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductRepository.getProductBySlug(view, product.getSlug(), ProductDetailActivity.this::onProductSlug);
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

    @Override
    public void onAddToCart() {
        if(!flagAuth) {
            CartRepository.getCountCarts(getApplicationContext(), ProductDetailActivity.super::onItemCount, this::onUnauthorized);
        }
    }

    @Override
    public void onUnauthorized(boolean flag) {
        this.flagAuth = flag;
        super.onUnauthorized(flag);
    }

    @Override
    public void onProductSlug(Product product) {
        Intent intent = new Intent(binding.getRoot().getContext(), MixMatchActivity.class);
        intent.putExtra(Product.EXTRA_PRODUCT, product);
        binding.getRoot().getContext().startActivity(intent);
    }
}