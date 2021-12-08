package com.example.faloka_mobile.Login;

import androidx.lifecycle.LiveData;

public interface AuthListener {
    void onSuccess();
    void onFailure(String massage);
}
