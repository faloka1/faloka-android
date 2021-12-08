package com.example.faloka_mobile.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Checkout.PaymentFragment;
import com.example.faloka_mobile.Checkout.PaymentMethodSelectedListener;
import com.example.faloka_mobile.Checkout.PaymentSelectedListener;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>{

    List<Payment> paymentList;
    View view;
    int lastSelectedPosition = -1;
    PaymentSelectedListener selectedListener;
    public PaymentAdapter(List<Payment> paymentList, PaymentSelectedListener selectedListener){
        this.paymentList = paymentList;
        this.selectedListener = selectedListener;
        int price = 2000;
        for(Payment payment : paymentList){
            payment.setPriceService(price);
        }
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_payment_method,parent,false);
        return new PaymentAdapter.PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        holder.bind(payment, position);

    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }


    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;
        TextView tvBankName;
        TextView tvAccountName;
        TextView tvAccountNumber;
        Payment payment;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.rb_payment_method);
            tvBankName = itemView.findViewById(R.id.tv_bank_name);
            tvAccountName = itemView.findViewById(R.id.tv_rekening_name);
            tvAccountNumber = itemView.findViewById(R.id.tv_rekening_number);
        }

        public void bind(Payment payment, int position){
            this.payment = payment;
            tvBankName.setText(payment.getPaymentName());
            tvAccountName.setText(payment.getAccountName());
            tvAccountNumber.setText(String.valueOf(payment.getAccountNumber()));
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    selectedListener.onPaymentSelected(payment);
//                    Toast.makeText(view.getContext(),
//                            "selected offer is " + tvBankName.getText(),
//                            Toast.LENGTH_LONG).show();
                }
            });
            radioButton.setChecked(lastSelectedPosition==position);
        }
    }
}
