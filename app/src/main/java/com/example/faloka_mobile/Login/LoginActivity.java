package com.example.faloka_mobile.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.databinding.ActivityLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener,AuthListener{

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot() );

        binding.btnLogLogin.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        if(view.getId() == binding.btnLogLogin.getId()){
            loginViewModel = new LoginViewModel(binding);
        }

    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String massage) {

    }

}