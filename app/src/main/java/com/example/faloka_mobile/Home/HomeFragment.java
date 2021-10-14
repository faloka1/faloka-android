package com.example.faloka_mobile.Home;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private AppCompatActivity activity;
    private FragmentHomeBinding binding;
    private List<Category> categories;
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
        activity.setSupportActionBar(binding.toolbar);
        tabHost = view.findViewById(R.id.tab_host);
        initDataCategory();

        return view;
    }

    void initDataCategory(){

        HomeViewModelFactory homeViewModelFactory = new HomeViewModelFactory(new HomeRepository(getActivity()));
        HomeViewModel homeViewModel = new ViewModelProvider(getActivity(), homeViewModelFactory).get(HomeViewModel.class);
        homeViewModel.getCategories().observe(getActivity(), this::initTabHost);

    }
    void initTabHost(List<Category> categories){
        int i=0;
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.home_content);
        tabHost.setCurrentTab(0);
        for(Category category : categories){

            Bundle bundle = new Bundle();
            bundle.putParcelable(Category.EXTRA_CATEGORY, category);

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


}