package com.example.faloka_mobile.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.faloka_mobile.Adapter.SubCategoryAdapter;
import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.Model.Image;
import com.example.faloka_mobile.Model.SubCategory;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentContentHomeBinding;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

public class ContentHomeFragment extends Fragment {

    Category category;
    RecyclerView rvSubCategory;
    SubCategoryAdapter subCategoryAdapter;
    CarouselView homeCarousel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_home, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            category = getArguments().getParcelable("id");
        }

        homeCarousel = view.findViewById(R.id.home_carousel);
        homeCarousel.setPageCount(category.getImages().size());
        homeCarousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(category.getImages().get(position).getPosition() );
            }
        });
        homeCarousel.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), category.getImages().get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        rvSubCategory = view.findViewById(R.id.rv_subcategory);
        subCategoryAdapter = new SubCategoryAdapter(getContext(), category.getSubCategories());
        System.out.println(category.getSubCategories().size());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() , 4, GridLayoutManager.VERTICAL, false);
        rvSubCategory.setLayoutManager(gridLayoutManager);
        rvSubCategory.setAdapter(subCategoryAdapter);

        return view;
    }
}