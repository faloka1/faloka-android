package com.example.faloka_mobile.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Adapter.SubCategoryAdapter;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class ContentHomeFragment extends Fragment {

    Category category;
    RecyclerView rvSubCategory;
    Button btnLogout;
    SubCategoryAdapter subCategoryAdapter;
    CarouselView homeCarousel;
    RecyclerView rvStyleInspiration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_home, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            category = getArguments().getParcelable("category");
            String slugCategory = category.getSlug();
            for(int i=0; i<category.getSubCategoryList().size(); i++){
                category.getSubCategoryList().get(i).setSlugCategory(slugCategory);
            }
        }



        homeCarousel = view.findViewById(R.id.home_carousel);
        homeCarousel.setPageCount(category.getCarouselList().size());
        homeCarousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(getContext())
                        .load("http://192.168.100.7:8000"+category.getCarouselList().get(position).getImageURL())
                        .into(imageView);
            }
        });
        homeCarousel.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
//                Toast.makeText(getContext(), category.getImages().get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        rvSubCategory = view.findViewById(R.id.rv_subcategory);
        subCategoryAdapter = new SubCategoryAdapter(getContext(), category.getSubCategoryList() );
        System.out.println(category.getSubCategoryList().size());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() , 4, GridLayoutManager.VERTICAL, false);
        rvSubCategory.setLayoutManager(gridLayoutManager);
        rvSubCategory.setAdapter(subCategoryAdapter);

        List<Product> products = category.getProductList();
        RecyclerView rvStyleInspiration = view.findViewById(R.id.rv_product_list);
        ProductAdapter productAdapter = new ProductAdapter(products);
        rvStyleInspiration.setLayoutManager(new GridLayoutManager(getContext() ,2, GridLayoutManager.VERTICAL, false));
        rvStyleInspiration.setAdapter(productAdapter);
        return view;
    }

}