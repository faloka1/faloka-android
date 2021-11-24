package com.example.faloka_mobile;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.faloka_mobile.Account.AuthFlagListener;
import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Cart.CartCountItemListener;
import com.example.faloka_mobile.Cart.CartEmptyActivity;
import com.example.faloka_mobile.Cart.CartRepository;
import com.example.faloka_mobile.Login.TokenManager;

import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class BaseFragment extends Fragment implements CartCountItemListener, AuthFlagListener {

    private ImageBadgeView badgeView;
    private int count = 0;
    private static boolean flagAuth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(!flagAuth) {
            CartRepository.getCountCarts(getContext(), this::onItemCount, this::onUnauthorized);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume(){
        TokenManager tokenManager = TokenManager.getInstance(getContext().getSharedPreferences("Token",0));
        if(!tokenManager.isLogin()){
            super.onResume();
            return;
        }
        if(!flagAuth) {
            CartRepository.getCountCarts(getContext(), this::onItemCount, this::onUnauthorized);
        }
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        TokenManager tokenManager = TokenManager.getInstance(getContext().getSharedPreferences("Token",0));
        if(!tokenManager.isLogin()){
            super.onCreateOptionsMenu(menu, inflater);
            return;
        }
        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
//            case R.id.top_menu_wishlist:
//                Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), "WISHLIST", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.top_menu_cart:
//                Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        TokenManager tokenManager = TokenManager.getInstance(getContext().getSharedPreferences("Token",0));
        if(!tokenManager.isLogin()){
            super.onPrepareOptionsMenu(menu);
            return;
        }

        MenuItem menuItem = menu.findItem(R.id.top_menu_cart);
        badgeView = menuItem.getActionView().findViewById(R.id.cart_badge);
        badgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count <= 0){
//                    Toast.makeText(((AppCompatActivity) getActivity()).getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), CartEmptyActivity.class);
                    startActivity(intent);
                }
                else {
//                    Toast.makeText(((AppCompatActivity) getActivity()).getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), CartActivity.class);
                    startActivity(intent);
                }
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemCount(int count) {
        this.count = count;
        System.out.println(this.count);
        if(badgeView != null) {
            badgeView.setBadgeValue(this.count)
                    .setBadgeOvalAfterFirst(true)
                    .setBadgeTextSize(8)
                    .setMaxBadgeValue(999)
                    .setBadgePosition(BadgePosition.TOP_RIGHT)
                    .setBadgeTextStyle(Typeface.NORMAL)
                    .setShowCounter(true)
                    .setBadgePadding(4);
        }
    }

    @Override
    public void onUnauthorized(boolean flag) {
        this.flagAuth = flag;
    }
}
