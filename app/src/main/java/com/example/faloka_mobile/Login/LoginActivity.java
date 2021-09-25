package com.example.faloka_mobile.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.Retrofit.BackendAPI;
import com.example.faloka_mobile.databinding.ActivityLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private BackendAPI backendAPI;
    private Call<List<User>> callUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot() );

        backendAPI = new BackendAPI();

        callbackUsers();

    }

    void callbackUsers(){ // try get data user
        callUsers = backendAPI.getAPI().getUsers();
        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                for(User user : users) {
                    binding.edtLogEmail.setText(user.getEmail());
                    binding.edtLogPassword.setText(user.getPassword());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}