package com.example.faloka_mobile.Login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Model.Login;
import com.example.faloka_mobile.databinding.ActivityLoginBinding;

import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {

    ActivityLoginBinding binding;
    private Login login;
    public LoginValidListener loginValidListener;

    public LoginViewModel(ActivityLoginBinding binding, LoginValidListener loginValidListener){
        this.binding = binding;
        this.loginValidListener = loginValidListener;
    }
    private void init(){
        login = new Login();
        login.setEmail(binding.edtLogEmail.getText().toString().trim());
        login.setPassword(binding.edtLogPassword.getText().toString().trim());
    }

    public void login(View view, Context context) {
        init();
        MutableLiveData<Boolean> validation = new MutableLiveData<>();
        if (!isValidEmail() | !isValidPassword()) {
            return;
        }
        LoginRepository.userLogin(login,context, loginValidListener);
    }

    public void onKeyEnter(View view){
        binding.edtLogPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    init();
                    if (!isValidEmail() | !isValidPassword()) {
                        return false;
                    }
                    LoginRepository.userLogin(login,view.getContext(), loginValidListener);
                    return true;
                }
                return false;
            }
        });
    }

    private Boolean isValidEmail(){
        if(login.getEmail().isEmpty()){
            binding.edtLogEmail.setError("Email masih kosong");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(login.getEmail()).matches()){
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
        if(login.getPassword().isEmpty()){
            binding.edtLogPassword.setError("Password masih kosong");
            return false;
        }
        else {
            binding.edtLogPassword.setError(null);
            return true;
        }
    }


}
