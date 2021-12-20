package com.example.faloka_mobile.Account;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.faloka_mobile.Adapter.MenuAccountAdapter;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.Register.RegisterActivity;
import com.example.faloka_mobile.databinding.FragmentAccountBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountViewModel extends ViewModel implements UserProfileListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public View view;
    private DrawerOptionListener drawerOptionListener;



    private FragmentAccountBinding binding;
    private AppCompatActivity activity;

    public  AccountViewModel(){

    }

    public AccountViewModel(FragmentAccountBinding binding, AppCompatActivity activity, DrawerOptionListener drawerOptionListener) {
        this.binding = binding;
        this.activity = activity;
        this.drawerOptionListener = drawerOptionListener;
        view = binding.getRoot();
        init();
    }
    public void init(){
        initTab();
        setToolbar();
        createDrawer(view);
        initStateUser();
    }

    public void setToolbar(){
        activity.setSupportActionBar(binding.toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(" ");
    }

    public void initTab(){
        tabLayout = view.findViewById(R.id.account_main_tab);
        viewPager = view.findViewById(R.id.account_content_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Pesanan Saya"));
        tabLayout.addTab(tabLayout.newTab().setText("Postingan"));

        MyAdapter adapter = new MyAdapter(view.getContext(),activity.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        createTab(view);
    }

    public void createTab(View view){
        viewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

        if(tokenManager.getToken() == null){
            names.add(0, "Masuk / Buat Akun");
            icons.add(0, R.drawable.ic_ant_design_login_outlined);
            names.remove(names.size()-1);
            icons.remove(icons.size()-1);
        }
    }

    public void createDrawer(View view){
        drawerLayout = view.findViewById(R.id.account_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(activity.getResources().getColor(R.color.black_faloka));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        drawerOptionListener.onOptionDrawer(actionBarDrawerToggle);
    }

    @Override
    public void onUserProfile(User user) {
        binding.account.tvName.setText(user.getName());
    }
          
    public class MyAdapter extends FragmentStatePagerAdapter {

        private Context myContext;
        int totalTabs;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new OrderFragment();
                case 1:
                    return new PostFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return totalTabs;
        }
    }

    public void initStateUser(){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        View navViewHeaderView = binding.profileNavView.getHeaderView(0);
        Button btnLogin = navViewHeaderView.findViewById(R.id.btn_profile_login);
        Button btnRegister = navViewHeaderView.findViewById(R.id.btn_profile_register);

        if(tokenManager.getToken() != null){
            btnRegister.setVisibility(View.GONE);
            btnLogin.setText("Logout");
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AccountRepository.logoutUser(view.getContext());
                }
            });
        }
        else{
            btnLogin.setText("Login");
            btnRegister.setText("Register");
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, RegisterActivity.class));
                }
            });
        }
        AccountRepository.setUserProfile(view, this::onUserProfile);
    }

}
