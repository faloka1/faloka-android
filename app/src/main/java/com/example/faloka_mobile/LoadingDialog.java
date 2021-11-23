package com.example.faloka_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;

import com.example.faloka_mobile.Login.TokenManager;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_loading_dialog, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissLoadingDialog();
            }
        }, 4000L);
    }

    public void dismissLoadingDialog(){
        alertDialog.dismiss();
    }
}
