package com.example.faloka_mobile.Login;

import android.content.SharedPreferences;

public class TokenManager {
    static private SharedPreferences prefs;
    static private SharedPreferences.Editor editor;

    public static TokenManager INSTANCE = null;

    private TokenManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized TokenManager getInstance(SharedPreferences prefs){
        if(INSTANCE == null){
            INSTANCE = new TokenManager(prefs);
        }
        return INSTANCE;
    }

    public void saveToken(LoginResponse token){
        editor.putString("TYPE_TOKEN", token.getTokenType()).commit();
        editor.putString("ACCESS_TOKEN", token.getAccessToken()).commit();
        editor.putInt("EXPIRED_TIME", token.getExpiresIn()).commit();
        editor.apply();
    }
    public boolean isLogin(){
        return (INSTANCE.getToken()!=null);
    }
    public void deleteToken(){
        editor.remove("TYPE_TOKEN").commit();
        editor.remove("ACCESS_TOKEN").commit();
        editor.remove("EXPIRED_TIME").commit();
        editor.apply();
    }

    public String getTypeToken(){
        return prefs.getString("TYPE_TOKEN", null);
    }

    public String getToken(){

        return prefs.getString("ACCESS_TOKEN",null);
    }

}
