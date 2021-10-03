package com.example.faloka_mobile.Home;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.Model.SubCategory;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private AppCompatActivity activity;
    private FragmentHomeBinding binding;
    private List<Category> categories;
//    private List<SubCategory> subCategories;
//    private List<Image> images;
//    private List<Image> images2;
    private FragmentTabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = ((AppCompatActivity)getActivity());
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        tabHost = view.findViewById(R.id.tab_host);
        initDataCategory();

        return view;
    }

    void initTabHost(List<Category> categories){
        int i=0;
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.home_content);
        tabHost.setCurrentTab(0);
        for(Category category : categories){

            System.out.println(category.getName());
            Bundle bundle = new Bundle();
            bundle.putParcelable("category", category);
            tabHost.addTab(tabHost.newTabSpec(category.getName()).setIndicator(category.getName()), ContentHomeFragment.class, bundle);
            TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setAllCaps(false);
            tv.setTextSize(13);
            if(tabHost.getTabWidget().getChildAt(i).isSelected()){
                tv.setTextColor(getResources().getColor(R.color.faloka_accent_green));
            }
            else{
                tv.setTextColor(getResources().getColor(R.color.white_faloka));
            }

            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.khula_semibold);
            tv.setTypeface(typeface);

            i++;
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                updateTabs();
            }
        });
    }

    void updateTabs() {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            if (tabHost.getTabWidget().getChildAt(i).isSelected()) {
                tv.setTextColor(getResources().getColor(R.color.faloka_accent_green));
            }
            else{
                tv.setTextColor(getResources().getColor(R.color.white_faloka));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        activity.getMenuInflater().inflate(R.menu.top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.top_menu_wishlist:
                Toast.makeText(activity.getApplicationContext(), "WISHLIST", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_menu_cart:
                Toast.makeText(activity.getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void initDataCategory(){
//        images = new ArrayList<>();
//        images2 = new ArrayList<>();
//        Image blouseImage = new Image("Blouse", R.drawable.blouse_collection);
//        Image hijabImage = new Image("Hijab", R.drawable.hijab_collection);
//        images.add(blouseImage);
//        images.add(hijabImage);
//        images.add(blouseImage);
//        images.add(hijabImage);
//
//        images2.add(hijabImage);
//        images2.add(hijabImage);
//        images2.add(hijabImage);
//
//        subCategories = new ArrayList<>();
//        subCategories.add(new SubCategory("Blouse", new Image("Gambar Blouse", R.drawable.img_blouse)));
//        subCategories.add(new SubCategory("Jacket", new Image("Gambar Blouse", R.drawable.img_jaket)));
//        subCategories.add(new SubCategory("Vest", new Image("Gambar Blouse", R.drawable.img_vest)));
//        subCategories.add(new SubCategory("Sweeter", new Image("Gambar Blouse", R.drawable.img_sweeter)));
//        subCategories.add(new SubCategory("Rompi", new Image("Gambar Blouse", R.drawable.img_rompi)));
//
//        List<SubCategory> subCategories2 = new ArrayList<>();
//        subCategories2.add(new SubCategory("Vest", new Image("Gambar Blouse", R.drawable.img_vest)));
//        subCategories2.add(new SubCategory("Blouse", new Image("Gambar Blouse", R.drawable.img_blouse)));
//        subCategories2.add(new SubCategory("Jacket", new Image("Gambar Blouse", R.drawable.img_jaket)));
//        subCategories2.add(new SubCategory("Rompi", new Image("Gambar Blouse", R.drawable.img_rompi)));
//        subCategories2.add(new SubCategory("Sweeter", new Image("Gambar Blouse", R.drawable.img_sweeter)));
//        subCategories2.add(new SubCategory("Rok", new Image("Gambar Blouse", R.drawable.img_rok)));
//        subCategories2.add(new SubCategory("Rok", new Image("Gambar Blouse", R.drawable.img_rok)));
//        subCategories2.add(new SubCategory("Rok", new Image("Gambar Blouse", R.drawable.img_rok)));

//        categories.add(new Category("Cowok", images, subCategories));
//        categories.add(new Category("Cewek", images2, subCategories2));
//        categories.add(new Category("Atasan", images, subCategories));
//        categories.add(new Category("Bawahan", images2, subCategories2));


        categories = new ArrayList<>();
        TokenManager tokenManager = TokenManager.getInstance(activity.getApplicationContext().getSharedPreferences("Token",0));
        Call<List<Category>> callCategories;
        callCategories = ApiConfig.getApiService(tokenManager).getCategories();
        callCategories.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                if(response.isSuccessful()){
                    List<Category> respCategory;
                    respCategory = response.body();
                    initTabHost(respCategory);
                }
                else{

                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

        System.out.println(categories);

    }

}