package com.example.faloka_mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.faloka_mobile.Account.AuthFlagListener;
import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Cart.CartCountItemListener;
import com.example.faloka_mobile.Cart.CartEmptyActivity;
import com.example.faloka_mobile.Cart.CartRepository;
import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.Login.TokenManager;

import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class BaseActivity extends AppCompatActivity implements CartCountItemListener, AuthFlagListener {

    private ImageBadgeView badgeView;
    private int count;
    private static boolean flagAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(!flagAuth){
            CartRepository.getCountCarts(getApplicationContext(), this::onItemCount, this::onUnauthorized);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        if(!tokenManager.isLogin()){
            super.onResume();
            return;
        }
        CartRepository.getCountCarts(getApplicationContext(), this::onItemCount, this::onUnauthorized);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.top_menu_cart:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.top_menu_cart);
        badgeView = menuItem.getActionView().findViewById(R.id.cart_badge);
        badgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count <= 0){
                    if(!LoginRepository.isValidationLogin(BaseActivity.this)){
                        return;
                    }
                    Intent intent = new Intent(getApplicationContext(), CartEmptyActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                    startActivity(intent);
                }
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemCount(int count) {
        this.count = count;
        if(badgeView != null) {
            badgeView.setBadgeValue(count)
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
