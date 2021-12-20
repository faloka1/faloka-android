package com.example.faloka_mobile.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.Register.RegisterActivity;
import com.example.faloka_mobile.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener,AuthListener, LoginValidListener{

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot() );
        setToolbar();
        setPassword();
        binding.btnLogLogin.setOnClickListener(this);
        binding.tvLogRegister.setOnClickListener(this);

    }
    public void setPassword(){
        TextInputLayout password = binding.layoutEdtRegPassword;

    }
    @Override
    public void onClick(View view) {
        if(view.getId() == binding.btnLogLogin.getId()){
            loginViewModel = new LoginViewModel(binding, this::onLogin);
            loginViewModel.login(view, view.getContext() );

        }
        else if(view.getId() == binding.tvLogRegister.getId()){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
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

    @Override
    public void onLogin(boolean valid) {
        if(valid){
            onSuccess();
        }
        else {
            Toast.makeText(binding.getRoot().getContext(), "Maaf, Login gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void setToolbar(){
        setSupportActionBar(binding.toolbarLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}