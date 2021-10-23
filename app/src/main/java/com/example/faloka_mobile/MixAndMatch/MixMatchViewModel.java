package com.example.faloka_mobile.MixAndMatch;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityMixMatchBinding;

public class MixMatchViewModel extends ViewModel implements View.OnTouchListener {

    private ActivityMixMatchBinding binding;
    private AppCompatActivity activity;
    private ImageToLayoutListener imageToLayoutListener;
    private static int imageID = 0;

    public MixMatchViewModel(ActivityMixMatchBinding binding, AppCompatActivity activity, ImageToLayoutListener imageToLayoutListener){
        this.binding = binding;
        this.activity = activity;
        this.imageToLayoutListener = imageToLayoutListener;

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(binding.relativeLayoutMixMatch.getMinimumWidth()+" "+ view.getMinimumHeight());
                System.out.println(binding.relativeLayoutMixMatch.getWidth()+" "+ view.getHeight());
                ImageView imageView = new ImageView(activity);
                imageView.setImageResource(R.drawable.img_blouse);
                addImageView(imageView, 100, 100);
            }
        });


    }

    private void addImageView(ImageView imageView, int width, int height) {
        RelativeLayout.LayoutParams imParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setId(imageID++);
        imageView.setLayoutParams(imParams);
        binding.relativeLayoutMixMatch.addView(imageView);
        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ImageView imageView = (ImageView) view;
        imageToLayoutListener.onLayout(imageView);
        return activity.onTouchEvent(motionEvent);
    }
}
