package com.example.faloka_mobile;

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

import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Cart.CartCountItemListener;
import com.example.faloka_mobile.Cart.CartRepository;

import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class BaseActivity extends AppCompatActivity implements CartCountItemListener {

    private ImageBadgeView badgeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        CartRepository.getCountCarts(getApplicationContext(), this::onItemCount);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        CartRepository.getCountCarts(getApplicationContext(), this::onItemCount);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.top_menu_wishlist:
                Toast.makeText(getApplicationContext(), "WISHLIST", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_menu_cart:
                Toast.makeText(getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemCount(int count) {
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