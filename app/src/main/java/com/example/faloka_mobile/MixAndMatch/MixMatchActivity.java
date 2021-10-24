package com.example.faloka_mobile.MixAndMatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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

public class MixMatchActivity extends AppCompatActivity implements ImageToLayoutListener{
    private ActivityMixMatchBinding binding;
    private View view;
    private ImageView imageView;
    private float x,y;
    private float dx, dy;

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
}