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
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.Product.ProductListener;
import com.example.faloka_mobile.Product.ProductRepository;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MixMatchViewModel extends ViewModel implements View.OnTouchListener, ProductMxMatchListener, SelectedImageListener, View.OnClickListener, ProductListener, RemoveProductListener {

    private ActivityMixMatchBinding binding;
    private AppCompatActivity activity;
    private ImageToLayoutListener imageToLayoutListener;
    private ImageViewUnselectedListener imageViewUnselectedListener;
//    private static int imageID = 0;
    private LinkedList<ImageView> imageViewList;
    private List<Cart> cartList;
    private ImageView currentImageView;
    private int currentIndex;

    public MixMatchViewModel(ActivityMixMatchBinding binding, AppCompatActivity activity, ImageToLayoutListener imageToLayoutListener, ImageViewUnselectedListener imageViewUnselectedListener){
        this.binding = binding;
        this.activity = activity;
        this.imageToLayoutListener = imageToLayoutListener;
        this.imageViewUnselectedListener = imageViewUnselectedListener;
        this.imageViewList = new LinkedList<>();
        this.cartList = new ArrayList<>();
        binding.btnMixMatchDelete.setOnClickListener(this);
        binding.btnMixMatchCheckout.setOnClickListener(this);
//        MixMatchRepository.getMixMatchProducts(binding.getRoot(), this::onProduct);
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
//        print();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            currentImageView = imageView;
//            imageView.bringToFront();
            int i =0;
            for (ImageView img : imageViewList) {
                if (imageView.getId() == img.getId()) {
                    imageView.setBackgroundResource(R.drawable.mix_match_product_border);
//                    currentIndex = i;
                } else {
                    img.setBackgroundResource(R.drawable.mix_match_product_default);
                }
                i++;
            }
            imageViewUnselectedListener.onUnselectedImageView(false);
            imageToLayoutListener.onLayout(imageView);
            updateCurrentIndex();
//            print();
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

//        int i =0;
        for(int i=0; i<binding.relativeLayoutMixMatch.getChildCount(); i++){
            System.out.println("IN: "+binding.relativeLayoutMixMatch.getChildAt(i).getId());
//            System.out.print(binding.relativeLayoutMixMatch.getChildAt(i).getId()+" <> ");
        }
//        for (ImageView imageView : imageViewList){
//            i++;
//        }

//        System.out.println("IN");
//        ViewGroup myViewGroup = ((ViewGroup) imageViewList.get(0).getParent());
//        int index = myViewGroup.getChildCount();
////        System.out.println("aSA: "+index);
//        for(int i = 0; i<index; i++)
//        {
//            System.out.print(myViewGroup.getChildAt(i).getId()+" <> ");
//        }
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
//                    System.out.println("HAHAHAHHA: "+currentIndex+" "+currentImageView.getId());
                    imageViewList.remove(currentIndex);
                    imageViewList.addLast(currentImageView);
                    currentImageView.bringToFront();
                    currentImageView.invalidate();
                    updateCurrentIndex();
//                    print();
                }
                else {
//                    Toast.makeText(activity.getApplicationContext(), "GABISA BRO", Toast.LENGTH_SHORT).show();
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
//                    System.out.println("INDEX REMOVE: "+currentIndex);
                    imageViewList.remove(currentIndex);
                    imageViewList.addFirst(currentImageView);
                    updateCurrentIndex();
//                    print();
                }
                else {
//                    Toast.makeText(activity.getApplicationContext(), "GABISA BRO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onProduct(List<ProductMixMatch> productMixMatchList) {
        ProductMixMatchAdapter productMixMatchAdapter = new ProductMixMatchAdapter(productMixMatchList, this::onSelected, this::onRemoveProduct);
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
                Toast.makeText(binding.getRoot().getContext(), "Pilih outfitmu dahulu!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onProductSlug(Product product) {
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
        float posX = (float)(binding.rvMixMatchProduct.getMinimumWidth());
        float posY = (float)(binding.rvMixMatchProduct.getHeight()/2);

        for(Category category : product.getCategoryList()){
            if(category.getSlug().equalsIgnoreCase("atasan")){
                posX = (float)(binding.rvMixMatchProduct.getMinimumWidth());
                posY = (float)(binding.rvMixMatchProduct.getHeight()/2);
                break;
            }
            if(category.getSlug().equalsIgnoreCase("bawahan")){
                posX = posX+380;
                posY = posY;
                break;
            }
        }

        addImageView(product.getId(), imageView, 410, 410, posX, posY);
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
            }
            i++;
        }

        binding.relativeLayoutMixMatch.removeViewInLayout(imageView);
    }
}
