package com.example.faloka_mobile.MixAndMatch;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;

import java.util.Random;

public class MixMatchActivityEx extends AppCompatActivity implements View.OnTouchListener{
    private ActivityMixMatchBinding binding;
    private View view;
    private static int id = 0;
    private static int X = 100;
    private float x,y;
    private float dx, dy;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMixMatchBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = new ImageView(MixMatchActivityEx.this);
                imageView.setImageResource(R.drawable.img_blouse);
                addView(imageView, 100, 100);
                colorrandom(imageView);
            }
        });
//        MixMatchViewModel mixMatchViewModel = new MixMatchViewModel(binding, this);
    }

    public void colorrandom(ImageView imageView) {

        // Initialising the Random();
        Random random = new Random();

        // adding the random background color
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

        // setting the background color
        imageView.setBackgroundColor(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            x = motionEvent.getX();
            y = motionEvent.getY();
        }
        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            dx = motionEvent.getX() - x;
            dy = motionEvent.getY() - y;
            imageView.setX(imageView.getX() + dx);
            imageView.setY(imageView.getY() + dy);
            x = motionEvent.getX();
            y = motionEvent.getY();
        }
        return super.onTouchEvent(motionEvent);
    }

    private void addView(ImageView imageView, int width, int height) {
        RelativeLayout.LayoutParams imParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setId(id++);
        float posX = 0;
        imageView.setX(X+=200);
        imageView.setLayoutParams(imParams);
        binding.relativeLayoutMixMatch.addView(imageView);
        imageView.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        imageView = (ImageView) view;
        return super.onTouchEvent(motionEvent);
    }


}