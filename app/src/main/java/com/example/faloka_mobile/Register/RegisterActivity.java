package com.example.faloka_mobile.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.Login.LoginValidListener;
import com.example.faloka_mobile.Login.LoginViewModel;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.Model.Login;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements RegisterValidListener, View.OnClickListener, LoginValidListener {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnRegRegister.setOnClickListener(this);
    }

    @Override
    public void onRegister(User user) {
        Login login = new Login();
        login.setEmail(user.getEmail());
        login.setPassword(binding.edtRegPassword.getText().toString().trim());
        LoginRepository.userLogin(login, this, this::onLogin);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.btnRegRegister.getId()){
            RegisterViewModel registerViewModel = new RegisterViewModel(binding, this::onRegister);
            registerViewModel.register(view);
        }
        else if(view.getId() == binding.tvRegLogin.getId()){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onLogin(boolean valid) {
        if(valid){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}