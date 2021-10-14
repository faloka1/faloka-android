package com.example.faloka_mobile.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.example.faloka_mobile.databinding.ActivityConfrimCheckoutBinding;

public class ConfirmCheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityConfrimCheckoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfrimCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getParcelableExtra("DATA_CHECKOUT");
        PaymentMethod paymentMethod = bundle.getParcelable("PAYMENT_METHOD");
        String total = bundle.getString("TOTAL_PRICE");

        binding.tvConfirmValueMethodPayment.setText(paymentMethod.getBank());
        binding.tvConfirmValuePaymentCode.setText(paymentMethod.getRekeningNumber()
                + " (" + paymentMethod.getRekeningName() + ")");
        binding.tvConfirmValueTotalPayment.setText(total);

        binding.btnDetail.setOnClickListener(this);
        binding.btnHowToPay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.findViewById(R.id.btn_detail) == binding.btnDetail){
            Intent intent = new Intent(this, DetailOrderActivity.class);
            startActivity(intent);
        }
        else if(view.findViewById(R.id.btn_how_to_pay) == binding.btnHowToPay){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.layout_how_to_pay);

            final TextView tvPembayaran = dialog.findViewById(R.id.how_to_pay_title);
            Button button = dialog.findViewById(R.id.btn_how_to_pay_close);

            button.setOnClickListener((v)->{
                dialog.dismiss();
            });
            dialog.show();

        }
    }
}