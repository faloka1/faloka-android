package com.example.faloka_mobile.MixAndMatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Adapter.ProductMixMatchAdapter;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductMixMatch;
import com.example.faloka_mobile.Product.ProductListener;
import com.example.faloka_mobile.Product.ProductRepository;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;

import java.util.ArrayList;
import java.util.List;

public class MixMatchViewModel extends ViewModel implements View.OnTouchListener, ProductMxMatchListener, SelectedImageListener, View.OnClickListener, ProductListener, RemoveProductListener {

    private ActivityMixMatchBinding binding;
    private AppCompatActivity activity;
    private ImageToLayoutListener imageToLayoutListener;
//    private static int imageID = 0;
    private List<ImageView> imageViewList;
    private List<Cart> cartList;

    public MixMatchViewModel(ActivityMixMatchBinding binding, AppCompatActivity activity, ImageToLayoutListener imageToLayoutListener){
        this.binding = binding;
        this.activity = activity;
        this.imageToLayoutListener = imageToLayoutListener;
        this.imageViewList = new ArrayList<>();
        this.cartList = new ArrayList<>();
        binding.btnMixMatchDelete.setOnClickListener(this);
        binding.btnMixMatchCheckout.setOnClickListener(this);
//        MixMatchRepository.getMixMatchProducts(binding.getRoot(), this::onProduct);
        MixMatchRepository.getProductsMixMatch(binding.getRoot(), this::onProduct);
        setToolbar();
    }

    private void setToolbar(){
        activity.setSupportActionBar(binding.mixMatchToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Mix and match");
    }

    private void addImageView(int imageID, ImageView imageView, int width, int height) {
        RelativeLayout.LayoutParams imParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setId(imageID);
        imageView.setLayoutParams(imParams);
        imageView.getLayoutParams().height = height;
        imageView.getLayoutParams().width = width;
        imageView.setY((float)(binding.rvMixMatchProduct.getMinimumWidth()));
        imageView.setX((float)(binding.rvMixMatchProduct.getHeight()/2));
        binding.relativeLayoutMixMatch.addView(imageView);
        imageViewList.add(imageView);
        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ImageView imageView = (ImageView) view;
        for (ImageView img : imageViewList){
            if(imageView.getId() == img.getId()){
                imageView.setBackgroundResource(R.drawable.mix_match_product_border);
            }else {
                img.setBackgroundResource(R.drawable.mix_match_product_default);
            }
        }
        imageToLayoutListener.onLayout(imageView);
        return activity.onTouchEvent(motionEvent);
    }

    @Override
    public void onProduct(List<ProductMixMatch> productMixMatchList) {
        ProductMixMatchAdapter productMixMatchAdapter = new ProductMixMatchAdapter(productMixMatchList, this::onSelected, this::onRemoveProduct);
        binding.rvMixMatchProduct.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(),3, GridLayoutManager.VERTICAL, false));
        binding.rvMixMatchProduct.setAdapter(productMixMatchAdapter);
    }

    @Override
    public void onSelected(ProductMixMatch product) {
        ProductRepository.getProductBySlug(binding.getRoot(), product.getSlug(), this::onProduct);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.btnMixMatchDelete.getId()){
            binding.relativeLayoutMixMatch.removeAllViews();
            imageViewList.clear();
            cartList.clear();
            MixMatchRepository.getProductsMixMatch(binding.getRoot(), this::onProduct);
        }
        else if(view.getId() == binding.btnMixMatchCheckout.getId()){
            if(!cartList.isEmpty()) {
                Intent intent = new Intent(binding.getRoot().getContext(), CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList) cartList);
                intent.putExtras(bundle);
                binding.getRoot().getContext().startActivity(intent);
            }
            else {
                Toast.makeText(binding.getRoot().getContext(), "Pilih outfitmu dahulu!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onProduct(Product product) {
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setVariant(product.getVariantList().get(0));
        cart.setQuantity(1);
        cart.setProductID(product.getId());
        cart.setVariantID(product.getVariantList().get(0).getId());
        this.cartList.add(cart);
        ImageView imageView = new ImageView(activity);
        Glide.with(imageView.getContext())
                .load(ApiConfig.BASE_IMAGE_URL + product.getImageMixMatchURL())
                .into(imageView);
        addImageView(product.getId(), imageView, 350, 350);
    }

    @Override
    public void onRemoveProduct(ProductMixMatch productMixMatch) {
        Product product = null;
        ImageView imageView = null;
        int i=0;
        for(Cart cart : cartList){
            if(cart.getProduct().getSlug().equals(productMixMatch.getSlug())){
                product = cart.getProduct();
                cartList.remove(i);
            }
            i++;
        }
        i=0;
        for(ImageView img : imageViewList){
            if(img.getId() == product.getId()){
                imageView = img;
                imageViewList.remove(i);
            }
            i++;
        }
        binding.relativeLayoutMixMatch.removeViewInLayout(imageView);
    }
}
