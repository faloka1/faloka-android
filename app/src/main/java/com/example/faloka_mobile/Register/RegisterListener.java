package com.example.faloka_mobile.Register;

import com.example.faloka_mobile.Model.RegisterError;
import com.example.faloka_mobile.Model.User;

public interface RegisterListener {
    public void onRegister(User user);
    public void onError(RegisterError error);
}
