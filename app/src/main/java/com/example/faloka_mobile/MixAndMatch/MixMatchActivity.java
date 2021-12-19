package com.example.faloka_mobile.MixAndMatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class MixMatchActivity extends BaseActivity implements ImageToLayoutListener, ImageViewUnselectedListener{
    private ActivityMixMatchBinding binding;
    private View view;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private float factor = 1.0f;
    private float dx, dy;
    private float lastX, lastY;
    private int pointerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMixMatchBinding.inflate(getLayoutInflater());
        view = binding.getRoot();

        setContentView(view);
        MixMatchViewModel mixMatchViewModel = new MixMatchViewModel(binding, this, this::onLayout, this::onUnselectedImageView);
        mixMatchViewModel.addProductFromDetail();
        mixMatchViewModel.addProductFromCart();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            final int pointerIndex = MotionEventCompat.getActionIndex(motionEvent);
            final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
            final float y = MotionEventCompat.getY(motionEvent, pointerIndex);
            lastX = x;
            lastY = y;
            pointerID = MotionEventCompat.getPointerId(motionEvent, 0);
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            final int pointerIndex = MotionEventCompat.findPointerIndex(motionEvent, pointerID);
            final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
            final float y = MotionEventCompat.getY(motionEvent, pointerIndex);
            dx = x - lastX;
            dy = y - lastY;
            if(imageView != null) {
                imageView.setX(imageView.getX() + dx);
                imageView.setY(imageView.getY() + dy);
            }
            lastX = x;
            lastY = y;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            pointerID = MotionEvent.INVALID_POINTER_ID;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL){
            pointerID = MotionEvent.INVALID_POINTER_ID;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP){
            final int pointerIndex = MotionEventCompat.getActionIndex(motionEvent);
            final int pointerId = MotionEventCompat.getPointerId(motionEvent, pointerIndex);

            if (pointerId == pointerID) {
                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                lastX = MotionEventCompat.getX(motionEvent, newPointerIndex);
                lastY = MotionEventCompat.getY(motionEvent, newPointerIndex);
                pointerID = MotionEventCompat.getPointerId(motionEvent, newPointerIndex);
            }
        }
        scaleGestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void onLayout(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onUnselectedImageView(boolean flag) {
        if(flag == true){
            this.imageView = null;
        }
    }

    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            factor *= scaleGestureDetector.getScaleFactor();
            factor = Math.max(0.1f, Math.min(factor,10.f));
            if(imageView != null) {
                imageView.setScaleX(factor);
                imageView.setScaleY(factor);
            }
            return true;
        }

    }
}