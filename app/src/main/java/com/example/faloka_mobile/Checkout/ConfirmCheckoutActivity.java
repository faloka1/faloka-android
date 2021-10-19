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

import com.example.faloka_mobile.Model.Checkout;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.example.faloka_mobile.databinding.ActivityConfrimCheckoutBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class ConfirmCheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityConfrimCheckoutBinding binding;
    Checkout checkout;
    Payment payment;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfrimCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkout = new Checkout();
        bundle = new Bundle();
        payment = new Payment();

        if(getIntent() != null){
            bundle = getIntent().getBundleExtra("DATA_CHECKOUT");
            checkout = bundle.getParcelable(Checkout.EXTRA_CHECKOUT);
            payment = bundle.getParcelable(Payment.EXTRA_PAYMENT);
        }

//        Bundle bundle = getIntent().getParcelableExtra("DATA_CHECKOUT");
//        PaymentMethod paymentMethod = bundle.getParcelable("PAYMENT_METHOD");
//        String total = bundle.getString("TOTAL_PRICE");

//        binding.tvConfirmValueMethodPayment.setText(paymentMethod.getBank());
//        binding.tvConfirmValuePaymentCode.setText(paymentMethod.getRekeningNumber()
//                + " (" + paymentMethod.getRekeningName() + ")");
//        binding.tvConfirmValueTotalPayment.setText(total);

        binding.tvConfirmValueMethodPayment.setText(payment.getPaymentName());
        binding.tvConfirmValuePaymentCode.setText(payment.getAccountNumber()
                + " (" + payment.getAccountName() + ")");
        binding.tvConfirmValueTotalPayment.setText(getFormatRupiah(checkout.getTotalPrice()));

        binding.btnDetail.setOnClickListener(this);
        binding.btnHowToPay.setOnClickListener(this);

    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
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