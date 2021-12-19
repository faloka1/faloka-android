package com.example.faloka_mobile.Checkout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.faloka_mobile.Model.Cost;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ChooseDeliveryDialog extends AppCompatDialogFragment implements CourierListener, CourierServiceListener{
//    EditText test;
    DeliveryListener deliveryListener;
    RadioGroup radioGroupCourier;
    RadioGroup radioGroupCourierservice;
    Courier chooseCourier;
    CourierService chooseCourierService;
    View view;
    public ChooseDeliveryDialog(DeliveryListener deliveryListener){
        this.deliveryListener = deliveryListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_dialog_delivery, null);
        radioGroupCourier = view.findViewById(R.id.radio_group_courier);
        radioGroupCourierservice = view.findViewById(R.id.radio_group_courierservice);

        DeliveryRepository.getCouriers(view, this::onCourier );

        builder.setView(view)
                .setTitle("Pilih Ekspedisi")
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(chooseCourier != null && chooseCourierService != null){
                            deliveryListener.onDelivery(chooseCourier, chooseCourierService);
                        }
                        else {
                            CoordinatorLayout coordinatorLayout = getActivity().findViewById(R.id.coordinator_layout_top_checkout);
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Ekspedisi belum dipilih", Snackbar.LENGTH_LONG);
                            snackbar.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            snackbar.setActionTextColor(getActivity().getResources().getColor(R.color.primary_dark));
                            snackbar.setTextColor(getActivity().getResources().getColor(R.color.primary_dark));
                            snackbar.setBackgroundTint(getActivity().getResources().getColor(R.color.semantic_warning));
                            snackbar.show();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onCourier(List<Courier> courierList) {
        int i=0;
        for(Courier courier : courierList){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setText(courier.getName());
            radioButton.setTextColor(getResources().getColor(R.color.black_faloka));
            ColorStateList myColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{getResources().getColor(R.color.faloka_accent_green)}
                    },
                    new int[]{getResources().getColor(R.color.faloka_accent_green)}
            );
            radioButton.setButtonTintList(myColorStateList);
            radioGroupCourier.addView(radioButton);
            i++;
        }

        radioGroupCourier.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId() != -1){
                    Courier courier = courierList.get(i);
                    DeliveryRepository.getCourierServices(view, courier, ChooseDeliveryDialog.this::onCourierService);
                }
            }
        });
    }

    @Override
    public void onCourierService(Courier courier, List<CourierService> courierServiceList) {
        radioGroupCourierservice.removeAllViews();
        int i=0;
        for(CourierService courierService : courierServiceList){
            Cost cost = courierService.getCost().get(0);
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setText(courierService.getName()+" ("+getFormatRupiah(cost.getValue())+") - "+cost.getEtd() +" Hari");
            radioButton.setTextColor(getResources().getColor(R.color.black_faloka));
            ColorStateList myColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{getResources().getColor(R.color.faloka_accent_green)}
                    },
                    new int[]{getResources().getColor(R.color.faloka_accent_green)}
            );
            radioButton.setButtonTintList(myColorStateList);
            radioGroupCourierservice.addView(radioButton);
            i++;
        }
        radioGroupCourierservice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId() != -1){
                    CourierService courierService = courierServiceList.get(i);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Courier.EXTRA_COURIER, courier);
                    bundle.putParcelable(CourierService.EXTRA_COURIER_SERVICE, courierService);
                    System.out.println(courierService.getName() +" "+(radioGroup.getCheckedRadioButtonId()));
//                    deliveryListener.onDelivery(courier, courierService, null);
                    chooseCourier = courier;
                    chooseCourierService = courierService;
                }
            }
        });
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }
}
