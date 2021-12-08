package com.example.faloka_mobile.Product;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Account.AuthFlagListener;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Cart.CartAddItemListener;
import com.example.faloka_mobile.Cart.CartItemListener;
import com.example.faloka_mobile.Cart.CartRepository;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Decoration.ListProductPaddingDecoration;
import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MixAndMatch.MixMatchActivity;
import com.example.faloka_mobile.Model.BodyCart;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.Model.VariantSize;
import com.example.faloka_mobile.databinding.ActivityProductDetailBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.synnapps.carouselview.ImageListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity implements CartAddItemListener, AuthFlagListener, ProductListener, CartItemListener {

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
                Cart cart = new Cart();
                Variant variant = product.getVariantList().get(0);
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
                CartRepository.getCarts(view, ProductDetailActivity.this::onCart);
            }
        });
    }

    public void onClickButtonMixMatch(){
        binding.btnMixMatch.setOnClickListener(new View.OnClickListener() {
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

        List<VariantSize> variantSizes = product.getVariantList().get(0).getVariantSizes();
        for(int i=0; i<variantSizes.size(); i++){
            Chip mChip = new Chip(this);
            mChip.setText(variantSizes.get(i).getName());
            mChip.setCheckable(true);
            if(i==0){
                mChip.setChecked(true);
            }
            mChip.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   mChip.setChecked(true);
               }
           });
            binding.cgDetailProduct.addView(mChip);
        }
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
                ListProductPaddingDecoration decoration = new ListProductPaddingDecoration(getApplicationContext());
                rvProductRelate.setAdapter(productAdapter);
                rvProductRelate.addItemDecoration(decoration);
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

    @Override
    public void onCart(List<Cart> cartList) {
        Cart cartSelected = null;
        boolean isMultiple = false;
        Variant variant = product.getVariantList().get(0);
        BodyCart bodyCart = new BodyCart();
        bodyCart.setProductID(product.getId());
        bodyCart.setVariantID(variant.getId());
        for(int i=0; i<binding.cgDetailProduct.getChildCount(); i++){
            Chip chip = (Chip) binding.cgDetailProduct.getChildAt(i);
            if(chip.isChecked()){
                Log.d("######", String.valueOf(product.getVariantList().get(0).getVariantSizes().get(i).getId()));
               bodyCart.setVariantSizeID(product.getVariantList().get(0).getVariantSizes().get(i).getId());
            }
        }
        bodyCart.setQuantity(1);
        for(Cart cart : cartList){
            if(cart.getProduct().getId() == bodyCart.getProductID()){
                if(cart.getVariant().getVariantSizes().get(0).getId() == bodyCart.getVariantSizeID()) {
                    cartSelected = cart;
                    isMultiple = true;
                    break;
                }
            }
        }

        if(!isMultiple){
            CartRepository.addCart(binding.getRoot(), bodyCart, ProductDetailActivity.this::onAddToCart);
        }
        else {
            CartRepository.editCartQuantity(binding.getRoot(), cartSelected.getId(), cartSelected.getQuantity()+1);
            Snackbar.make(binding.getRoot(), "Tas berhasil diperbaharui", Snackbar.LENGTH_SHORT).show();
        }

    }
}