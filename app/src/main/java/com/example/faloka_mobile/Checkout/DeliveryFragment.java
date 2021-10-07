package com.example.faloka_mobile.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.example.faloka_mobile.databinding.FragmentDeliveryBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class DeliveryFragment extends Fragment implements View.OnClickListener{

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_delivery, container, false);
        setAddressSection();
        return view;
    }
    private void setAddressSection(){

        Button btnEdit;
        btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(),ActionAddressActivity.class);
        startActivity(intent);
    }
}