package com.example.faloka_mobile;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.faloka_mobile.Adapter.ProductImageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class DetailProductActivity extends AppCompatActivity {

    List<Integer> productImage = new ArrayList<>();
    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.product_image_pager);
        viewPagerIndicator = findViewById(R.id.pager_image_indicator);

        productImage.add(R.drawable.product_image);
        productImage.add(R.drawable.product_image);
        productImage.add(R.drawable.product_image);
        productImage.add(R.drawable.product_image);

        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImage);
        productImageViewPager.setAdapter(productImageAdapter);
        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        ListProductFagment listProductFagment = new ListProductFagment();
        Fragment fragment = mFragmentManager.findFragmentByTag(ListProductFagment.class.getSimpleName());

        if (!(fragment instanceof ListProductFagment)) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + ListProductFagment.class.getSimpleName());
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.product_relate_list_wrapper, listProductFagment, ListProductFagment.class.getSimpleName())
                    .commit();
        }
    }
}