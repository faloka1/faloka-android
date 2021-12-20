package com.example.faloka_mobile.Register;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Model.UserRegister;
import com.example.faloka_mobile.databinding.ActivityRegisterBinding;

public class RegisterViewModel extends ViewModel {
    private ActivityRegisterBinding binding;
    private UserRegister userRegister;
    private RegisterListener registerValidListener;

    public RegisterViewModel(ActivityRegisterBinding binding, RegisterListener registerValidListener){
        this.binding = binding;
        this.registerValidListener = registerValidListener;
    }

    public void init(){
        userRegister = new UserRegister();
        userRegister.setName(binding.edtRegName.getText().toString().trim());
        userRegister.setEmail(binding.edtRegEmail.getText().toString().trim());
        userRegister.setPhone(binding.edtRegPhone.getText().toString().trim());
        userRegister.setPassword(binding.edtRegPassword.getText().toString().trim());
        userRegister.setPasswordConfirmation(binding.edtRegConfirmPassword.getText().toString().trim());
        userRegister.setGender(binding.autoCompleteSpinnerGender.getText().toString().trim());
    }

    public boolean isValidInput(){
        boolean valid = true;
        if(userRegister.getName().isEmpty()){
            binding.layoutEdtRegName.setError("Field name must be filled");
            valid = false;
        }
        if(userRegister.getEmail().isEmpty()){
            binding.layoutEdtRegEmail.setError("Field email must be filled");
            valid = false;
        }
        if(userRegister.getPhone().isEmpty()){
            binding.layoutEdtRegPhone.setError("Field phone must be filled");
            valid = false;
        }
        if(userRegister.getPassword().isEmpty()){
            binding.layoutEdtRegPassword.setError("Field password must be filled");
            valid = false;
        }
        if(userRegister.getPasswordConfirmation().isEmpty()){
            binding.layoutEdtRegConfirmPassword.setError("Field register must be filled");
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
