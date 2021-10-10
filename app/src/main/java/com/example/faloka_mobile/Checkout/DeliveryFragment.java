package com.example.faloka_mobile.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    Button btnEdit;
    Button btnNext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_delivery, container, false);
        setAddressSection();
        setFooterDelivery();
        return view;
    }
    private void setAddressSection(){
        btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);
    }
    private void setFooterDelivery(){
        btnNext = view.findViewById(R.id.btn_checkout_next);
        btnNext.setEnabled(true);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.findViewById(R.id.btn_edit) == btnEdit){
            Intent intent = new Intent(getContext(),ActionAddressActivity.class);
            startActivity(intent);
        }
        else if(view.findViewById(R.id.btn_checkout_next) == btnNext){
            final FragmentManager fragmentManager = getFragmentManager();

            Fragment fragmentPayment = CheckoutFragmentUtil.getFragmentByTagName(fragmentManager, "Fragment Payment");

            if(fragmentPayment==null)
            {
                fragmentPayment = new PaymentFragment();
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container_payment, fragmentPayment, "Fragment Payment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            CheckoutFragmentUtil.printActivityFragmentList(fragmentManager);
        }

    }
}