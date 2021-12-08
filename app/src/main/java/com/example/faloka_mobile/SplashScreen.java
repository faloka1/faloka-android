package com.example.faloka_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Register.RegisterActivity;

public class SplashScreen extends AppCompatActivity {
    //DONE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TokenManager tokenManager = TokenManager.getInstance(getSharedPreferences("Token",0));
//                if(tokenManager.isLogin()){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
//                }
//                else{
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                    finish();
//                }
            }
        }, 2000L);
    }
}