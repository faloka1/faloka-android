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
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCheckoutBinding;
import com.example.faloka_mobile.databinding.ActivityConfrimCheckoutBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class ConfirmCheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityConfrimCheckoutBinding binding;
    Order order;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfrimCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        order = new Order();
        bundle = new Bundle();

        if(getIntent() != null){
            bundle = getIntent().getBundleExtra("DATA_ORDER");
            order = bundle.getParcelable(Order.EXTRA_ORDER);
        }

//        Bundle bundle = getIntent().getParcelableExtra("DATA_CHECKOUT");
//        PaymentMethod paymentMethod = bundle.getParcelable("PAYMENT_METHOD");
//        String total = bundle.getString("TOTAL_PRICE");

//        binding.tvConfirmValueMethodPayment.setText(paymentMethod.getBank());
//        binding.tvConfirmValuePaymentCode.setText(paymentMethod.getRekeningNumber()
//                + " (" + paymentMethod.getRekeningName() + ")");
//        binding.tvConfirmValueTotalPayment.setText(total);

        binding.tvConfirmValueMethodPayment.setText(order.getPayment().getPaymentName());
        binding.tvConfirmValuePaymentCode.setText(order.getPayment().getAccountNumber()
                + " (" + order.getPayment().getAccountName() + ")");
        binding.tvConfirmValueTotalPayment.setText(getFormatRupiah(order.getTotalOrder()));

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
            Bundle bundle = new Bundle();
            bundle.putParcelable(Order.EXTRA_ORDER, order);
            intent.putExtra("DATA_ORDER", bundle);
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