package com.example.faloka_mobile.Account;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentAccountBinding;
import com.example.faloka_mobile.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment implements DrawerOptionListener {

    public static final String EXTRA_FRAGMENT_ACCOUNT = "EXTRA_FRAGMENT_ACCOUNT";
    public static final int INDEX_FRAGMENT_ACCOUNT = 3;

    private FragmentAccountBinding binding;
    private AppCompatActivity activity;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        activity = ((AppCompatActivity)getActivity());
        AccountViewModel accountViewModel = new AccountViewModel(binding, activity, this::onOptionDrawer);
        view = binding.getRoot();
        return view;
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

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionDrawer(ActionBarDrawerToggle actionBarDrawerToggle) {
        this.actionBarDrawerToggle  = actionBarDrawerToggle;
    }
}