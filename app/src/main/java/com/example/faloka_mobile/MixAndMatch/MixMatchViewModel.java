package com.example.faloka_mobile.MixAndMatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Adapter.ProductMixMatchAdapter;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductMixMatch;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.Product.ProductListener;
import com.example.faloka_mobile.Product.ProductRepository;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MixMatchViewModel extends ViewModel implements View.OnTouchListener, ProductMxMatchListener, SelectedImageListener, View.OnClickListener, ProductListener, RemoveProductListener {

    private ActivityMixMatchBinding binding;
    private AppCompatActivity activity;
    private ImageToLayoutListener imageToLayoutListener;
    private ImageViewUnselectedListener imageViewUnselectedListener;
    private LinkedList<ImageView> imageViewList;
    private List<Cart> cartList;
    private ImageView currentImageView;
    private int currentIndex;
    private static int imageID;

    public MixMatchViewModel(ActivityMixMatchBinding binding, AppCompatActivity activity, ImageToLayoutListener imageToLayoutListener, ImageViewUnselectedListener imageViewUnselectedListener){
        this.binding = binding;
        this.activity = activity;
        this.imageToLayoutListener = imageToLayoutListener;
        this.imageViewUnselectedListener = imageViewUnselectedListener;
        this.imageViewList = new LinkedList<>();
        this.cartList = new ArrayList<>();
        imageID = 1;
        binding.btnMixMatchDelete.setOnClickListener(this);
        binding.btnMixMatchCheckout.setOnClickListener(this);
        MixMatchRepository.getProductsMixMatch(binding.getRoot(), this::onProduct);
        setToolbar();
        setButtonFlip();
        binding.constrantLayoutMain.setOnTouchListener(this);
    }

    private void setToolbar(){
        activity.setSupportActionBar(binding.mixMatchToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Mix and match");
    }

    private void addImageView(int imageID, ImageView imageView, int width, int height, float posX, float posY) {
        RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setId(imageID);
        imageView.setLayoutParams(imParams);
        imageView.getLayoutParams().height = height;
        imageView.getLayoutParams().width = width;
        imageView.setY(posX);
        imageView.setX(posY);
        imageView.setOnTouchListener(this);
        imageViewList.add(imageView);
        binding.relativeLayoutMixMatch.addView(imageView);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            currentImageView = imageView;
            int i =0;
            for (ImageView img : imageViewList) {
                if (imageView.getId() == img.getId()) {
                    imageView.setBackgroundResource(R.drawable.mix_match_product_border);
                } else {
                    img.setBackgroundResource(R.drawable.mix_match_product_default);
                }
                i++;
            }
            imageViewUnselectedListener.onUnselectedImageView(false);
            imageToLayoutListener.onLayout(imageView);
            updateCurrentIndex();
        }
        if (view instanceof ConstraintLayout){
            for (ImageView img : imageViewList) {
                img.setBackgroundResource(R.drawable.mix_match_product_default);
            }
            imageViewUnselectedListener.onUnselectedImageView(true);
        }
        return activity.onTouchEvent(motionEvent);
    }

    public void print(){
        System.out.println("\n");
        for (ImageView imageView : imageViewList){
            System.out.println("OUT: "+imageView.getId());
        }
        System.out.println("\n");

        for(int i=0; i<binding.relativeLayoutMixMatch.getChildCount(); i++){
            System.out.println("IN: "+binding.relativeLayoutMixMatch.getChildAt(i).getId());
        }
    }

    private void updateCurrentIndex(){
        int i=0;
        for(ImageView imageView : imageViewList){
            if(imageView.getId() == currentImageView.getId()){
                currentIndex = i;
            }
            i++;
        }
    }

    private void setButtonFlip(){
        binding.btnBringToFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentImageView != null){
                    imageViewList.remove(currentIndex);
                    imageViewList.addLast(currentImageView);
                    currentImageView.bringToFront();
                    currentImageView.invalidate();
                    updateCurrentIndex();
                }
            }
        });
        binding.btnBringToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentImageView != null){
                    int i=0;
                    for(ImageView imageView : imageViewList){
                        if(imageView.getId() == currentImageView.getId()){
                            currentIndex = i;
                        }
                        if(imageView.getId() != currentImageView.getId()){
                            imageView.bringToFront();
                            imageView.invalidate();
                        }
                        i++;
                    }
                    imageViewList.remove(currentIndex);
                    imageViewList.addFirst(currentImageView);
                    updateCurrentIndex();
                }
            }
        });
    }

    @Override
    public void onProduct(List<ProductMixMatch> productMixMatchList) {
        List<ProductMixMatch> productMixMatches = new ArrayList<>(productMixMatchList);
        if(activity.getIntent() != null){
            List<Cart> cartList = activity.getIntent().getParcelableArrayListExtra(Product.EXTRA_PRODUCT);
            if(cartList != null){
                for(Cart cart : cartList){
                    int i=0;
                    for(ProductMixMatch productMixMatch : productMixMatches){
                        if(cart.getProduct().getSlug().equalsIgnoreCase(productMixMatch.getSlug())){
                            productMixMatches.remove(i);
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        ProductMixMatchAdapter productMixMatchAdapter = new ProductMixMatchAdapter(productMixMatches, this::onSelected, this::onRemoveProduct);
        binding.rvMixMatchProduct.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(),3, GridLayoutManager.VERTICAL, false));
        binding.rvMixMatchProduct.setAdapter(productMixMatchAdapter);
    }

    @Override
    public void onSelected(ProductMixMatch product) {
        ProductRepository.getProductBySlug(binding.getRoot(), product.getSlug(), this::onProductSlug);
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
            if(!LoginRepository.isValidationLogin(activity)){
                return;
            }
            if(!cartList.isEmpty()) {
                Intent intent = new Intent(binding.getRoot().getContext(), CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList) cartList);
                intent.putExtras(bundle);
                binding.getRoot().getContext().startActivity(intent);
            }
            else {
                Snackbar snackbar = Snackbar.make(binding.coordinatorLayoutTopMixMatch, "Pilih outfitnya dulu ya", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                snackbar.setActionTextColor(activity.getResources().getColor(R.color.primary_dark));
                snackbar.setTextColor(activity.getResources().getColor(R.color.primary_dark));
                snackbar.setBackgroundTint(activity.getResources().getColor(R.color.semantic_warning));
                snackbar.show();
            }
        }
    }

    @Override
    public void onProductSlug(Product product) {
        addProduct(product);
    }

    public void addProduct(Product product){
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
        float posY = 16;
        float posX = 311;

        for(Category category : product.getCategoryList()){
            if(category.getSlug().equalsIgnoreCase("atasan")){
                posY = (float)(binding.relativeLayoutMixMatch.getMeasuredHeight()/50);
                posX = (float)(binding.relativeLayoutMixMatch.getMeasuredWidth()/2 - 205);
                break;
            }
            if(category.getSlug().equalsIgnoreCase("bawahan")){
                posY = (float)(binding.relativeLayoutMixMatch.getMeasuredHeight()/50)+380;
                posX = (float)(binding.relativeLayoutMixMatch.getMeasuredWidth()/2 - 205);
                break;
            }
        }

        addImageView(imageID, imageView, 410, 410, posY, posX);
        imageID++;
    }

    public void addProductFromDetail(){
        if(activity.getIntent() != null){
            Product product = activity.getIntent().getParcelableExtra(Product.EXTRA_PRODUCT);
            if(product != null){
                ProductRepository.getProductBySlug(binding.getRoot(), product.getSlug(), this::onProductSlug);
            }
        }
    }

    public void addProductFromCart(){
        if(activity.getIntent() != null){
            List<Cart> cartList = activity.getIntent().getParcelableArrayListExtra(Product.EXTRA_PRODUCT);
            if(cartList != null){
                for(Cart cart : cartList){
                    ProductRepository.getProductBySlug(binding.getRoot(), cart.getProduct().getSlug(), this::onProductSlug);
                }
            }
        }
    }

    @Override
    public void onRemoveProduct(ProductMixMatch productMixMatch) {
        List<Cart> cartListTemp = new ArrayList<>(cartList);
        ImageView imageView = null;
        int i=0;
        for (Cart cart : cartListTemp) {
            if (cart.getProduct().getSlug().equalsIgnoreCase(productMixMatch.getSlug())) {
                cartList.remove(i);
                imageView = imageViewList.get(i);
                imageViewList.remove(i);
                break;
            }
            i++;
        }

        binding.relativeLayoutMixMatch.removeViewInLayout(imageView);
    }
}
