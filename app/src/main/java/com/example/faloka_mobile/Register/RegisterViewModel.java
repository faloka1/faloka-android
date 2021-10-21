package com.example.faloka_mobile.Register;

import android.content.Context;
import android.util.Patterns;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.Model.UserRegister;
import com.example.faloka_mobile.databinding.ActivityRegisterBinding;

import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {
    private ActivityRegisterBinding binding;
    private UserRegister userRegister;
    private RegisterValidListener registerValidListener;

    public RegisterViewModel(ActivityRegisterBinding binding, RegisterValidListener registerValidListener){
        this.binding = binding;
        this.registerValidListener = registerValidListener;
    }

    public void init(){
        userRegister = new UserRegister();
        userRegister.setName(binding.edtRegName.getText().toString().trim());
        userRegister.setEmail(binding.edtRegEmail.getText().toString().trim());
        userRegister.setPhone(binding.edtRegPhone.getText().toString().trim());
        userRegister.setPassword(binding.edtRegPassword.getText().toString().trim());
        userRegister.setPasswordConfirmation(binding.edtRegConfirmPass.getText().toString().trim());
        userRegister.setGender(binding.spinnerGender.getSelectedItem().toString().trim());
    }

    public boolean isValidInput(){
        boolean valid = true;
        if(userRegister.getName().isEmpty()){
            binding.edtRegName.setError("Field name must be filled");
            valid = false;
        }
        if(userRegister.getEmail().isEmpty()){
            binding.edtRegEmail.setError("Field email must be filled");
            valid = false;
        }
        if(userRegister.getPhone().isEmpty()){
            binding.edtRegPhone.setError("Field phone must be filled");
            valid = false;
        }
        if(userRegister.getPassword().isEmpty()){
            binding.edtRegPassword.setError("Field password must be filled");
            valid = false;
        }
        if(userRegister.getPasswordConfirmation().isEmpty()){
            binding.edtRegConfirmPass.setError("Field register must be filled");
            valid = false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userRegister.getEmail()).matches()){
            binding.edtRegEmail.setError("Email must be adjusted format");
            valid = false;
        }
        return valid;
    }

    public void register(View view){
        init();
        if(!isValidInput()){
            return;
        }
        RegisterRepository.userRegister(view, userRegister, registerValidListener);
    }

}
