package com.example.faloka_mobile.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.Login.LoginValidListener;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.Model.Login;
import com.example.faloka_mobile.Model.RegisterError;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity implements RegisterListener, View.OnClickListener, LoginValidListener {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnRegRegister.setOnClickListener(this);
        setAutoCompleteSpinnerGender();
        setAfterError();
    }

    private void setAutoCompleteSpinnerGender(){
        String[] option = {"L", "P"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.layout_option_regist_gender,option);
        binding.autoCompleteSpinnerGender.setAdapter(arrayAdapter);
    }

    private void setAfterError(){
        binding.edtRegEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtRegEmail.getError()==null){
                    binding.layoutEdtRegEmail.setError("");
                }
            }
        });
        binding.edtRegPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtRegPassword.getError()==null){
                    binding.layoutEdtRegPassword.setError("");
                }
            }
        });
        binding.edtRegConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtRegConfirmPassword.getError()==null){
                    binding.layoutEdtRegConfirmPassword.setError("");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.btnRegRegister.getId()){
            RegisterViewModel registerViewModel = new RegisterViewModel(binding, this);
            registerViewModel.register(view);
        }
        else if(view.getId() == binding.tvRegLogin.getId()){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRegister(User user) {
        Login login = new Login();
        login.setEmail(user.getEmail());
        login.setPassword(binding.edtRegPassword.getText().toString().trim());
        LoginRepository.userLogin(login, this, this::onLogin);
    }

    @Override
    public void onError(RegisterError error) {
        if(error.getEmail()!=null){
            binding.layoutEdtRegEmail.setError(error.getEmail().get(0));
        }
        else if(error.getEmail()==null){
            binding.layoutEdtRegEmail.setError("");
        }

        if(error.getPassword()!=null){
            binding.layoutEdtRegPassword.setError(error.getPassword().get(0));
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