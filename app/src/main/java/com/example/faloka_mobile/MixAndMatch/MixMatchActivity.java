package com.example.faloka_mobile.MixAndMatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class MixMatchActivity extends AppCompatActivity implements ImageToLayoutListener{
    private ActivityMixMatchBinding binding;
    private View view;
    private ImageView imageView;
    private float x,y;
    private float dx, dy;
    private ImageBadgeView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMixMatchBinding.inflate(getLayoutInflater());
        view = binding.getRoot();

        setContentView(view);
        MixMatchViewModel mixMatchViewModel = new MixMatchViewModel(binding, this, this::onLayout);
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            x = motionEvent.getX();
            y = motionEvent.getY();
//            imageView.setBackgroundResource(R.drawable.mix_match_product_border);
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
//            imageView.setBackgroundResource(R.drawable.mix_match_product_default);
        }
        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            dx = motionEvent.getX() - x;
            dy = motionEvent.getY() - y;
            if(imageView != null) {
                imageView.setX(imageView.getX() + dx);
                imageView.setY(imageView.getY() + dy);
            }
            x = motionEvent.getX();
            y = motionEvent.getY();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void onLayout(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        badgeView.setBadgeValue(27)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(8)
                .setMaxBadgeValue(999)
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);
        return super.onPrepareOptionsMenu(menu);
    }
}