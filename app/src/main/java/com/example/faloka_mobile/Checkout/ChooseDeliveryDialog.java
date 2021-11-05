package com.example.faloka_mobile.Checkout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.faloka_mobile.R;

public class ChooseDeliveryDialog extends AppCompatDialogFragment {
    EditText test;
    DeliveryListener deliveryListener;
    public ChooseDeliveryDialog(DeliveryListener deliveryListener){
        this.deliveryListener = deliveryListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_delivery, null);

        builder.setView(view)
                .setTitle("Pilih ekspedisi")
                .setNegativeButton("batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("pilih", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = test.getText().toString().trim();
                        deliveryListener.onDelivery(null, null, text);
                    }
                });
        test = view.findViewById(R.id.edt_test);

        return builder.create();
    }
}
