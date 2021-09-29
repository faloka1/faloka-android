package com.example.faloka_mobile.Login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.databinding.ActivityLoginBinding;

import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {

    ActivityLoginBinding binding;
    private String email = null;
    private String password = null;
    public AuthListener authListener;

    public LoginViewModel(ActivityLoginBinding binding){
        this.binding = binding;
    }
    private void init(){
        email = binding.edtLogEmail.getText().toString().trim();
        password = binding.edtLogPassword.getText().toString().trim();
    }

    public boolean login(View view, Context context) {
        init();
        MutableLiveData<Boolean> validation = new MutableLiveData<>();
        if (!isValidEmail() | !isValidPassword()) {
            return false;
        }
        LoginRepository.userLogin(email,password,context);
        return true;
    }
    private Boolean isValidEmail(){
        if(email.isEmpty()){
            binding.edtLogEmail.setError("Email masih kosong");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtLogEmail.setError("Sesuaikan dengan format email");
            return false;
        }
        else {
            binding.edtLogEmail.setError(null);
            return true;
        }
    }
    public static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    );
    private boolean isValidPassword(){
        if(email.isEmpty()){
            binding.edtLogPassword.setError("Password masih kosong");
            return false;
        }
        else {
            binding.edtLogEmail.setError(null);
            return true;
        }
    }


}
