package com.example.faloka_mobile.MixAndMatch;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Adapter.ProductMixMatchAdapter;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;

import java.util.ArrayList;
import java.util.List;

public class MixMatchViewModel extends ViewModel implements View.OnTouchListener, ProductMxMatchListener, SelectedImageListener, View.OnClickListener {

    private ActivityMixMatchBinding binding;
    private AppCompatActivity activity;
    private ImageToLayoutListener imageToLayoutListener;
    private static int imageID = 0;
    private List<ImageView> imageViewList;

    public MixMatchViewModel(ActivityMixMatchBinding binding, AppCompatActivity activity, ImageToLayoutListener imageToLayoutListener){
        this.binding = binding;
        this.activity = activity;
        this.imageToLayoutListener = imageToLayoutListener;
        this.imageViewList = new ArrayList<>();
        binding.btnMixMatchDelete.setOnClickListener(this);
        MixMatchRepository.getMixMatchProducts(binding.getRoot(), this::onProduct);
        setToolbar();
    }

    private void setToolbar(){
        activity.setSupportActionBar(binding.mixMatchToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Mix and match");
    }

    private void addImageView(ImageView imageView, int width, int height) {
        RelativeLayout.LayoutParams imParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setId(imageID++);
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
            if(imageView.getId() != img.getId()){
                img.setBackgroundResource(R.drawable.mix_match_product_default);
            }
        }
        imageView.setBackgroundResource(R.drawable.mix_match_product_border);
        imageToLayoutListener.onLayout(imageView);
        return activity.onTouchEvent(motionEvent);
    }

    @Override
    public void onProduct(List<Product> productList) {
        ProductMixMatchAdapter productMixMatchAdapter = new ProductMixMatchAdapter(productList, this::onSelected);
        binding.rvMixMatchProduct.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(),3, GridLayoutManager.VERTICAL, false));
        binding.rvMixMatchProduct.setAdapter(productMixMatchAdapter);
    }

    @Override
    public void onSelected(Product product) {
        ImageView imageView = new ImageView(activity);
        Glide.with(imageView.getContext())
                .load(ApiConfig.BASE_IMAGE_URL + product.getProductImageURL())
                .into(imageView);
        addImageView(imageView, 350, 350);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.btnMixMatchDelete.getId()){
            binding.relativeLayoutMixMatch.removeAllViews();
            imageViewList.clear();
            MixMatchRepository.getMixMatchProducts(binding.getRoot(), this::onProduct);
        }
    }
}
