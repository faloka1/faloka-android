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

public class AccountViewModel extends ViewModel implements UserProfileListener, OrderUserListener{

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
        setAccountMenu();
        createDrawer(view);
        AccountRepository.setUserProfile(view, this::onUserProfile);
//        AccountRepository.getOrders(view, this::onOrder);
    }
    private void setAccountMenu(){
        ArrayList<String> names = new ArrayList<>();
        names.add("Pesanan Saya");
        names.add("Keranjang Saya");
        names.add("Postingan Saya");
        names.add("Keluar");
        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_carbon_delivery);
        icons.add(R.drawable.ic_bx_bx_shopping_bag);
        icons.add(R.drawable.ic_carbon_drop_photo);
        icons.add(R.drawable.ic_ant_design_logout_outlined);
        initStateUser(names, icons);
        MenuAccountAdapter menuAdapter = new MenuAccountAdapter(names, icons);
        RecyclerView menus = view.findViewById(R.id.rv_account_menu);
        menus.setLayoutManager(new LinearLayoutManager(view.getContext()));
        menus.setAdapter(menuAdapter);
    }

    private void initStateUser(ArrayList<String> names, ArrayList<Integer> icons){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));

        if(tokenManager.getToken() == null){
            names.add(0, "Masuk / Buat Akun");
            icons.add(0, R.drawable.ic_ant_design_login_outlined);
            names.remove(names.size()-1);
            icons.remove(icons.size()-1);
        }
    }

    private void createDrawer(View view){
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

    @Override
    public void onOrder(List<OrderUser> orderUserList) {
//        for (OrderUser orderUser : orderUserList){
//            System.out.println(orderUser.getId());
//
//        }
//        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(orderUserList);
//        binding.rvAddresses.setAdapter(addressAdapter);
//        binding.rvAddresses.setLayoutManager(new LinearLayoutManager(getContext()));
//        Toast.makeText(view.getContext(), "HMMMMMMMMMMM", Toast.LENGTH_SHORT).show();
    }



}
