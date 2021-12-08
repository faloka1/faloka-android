package com.example.faloka_mobile.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Checkout.PaymentMethodSelectedListener;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder> {

    List<PaymentMethod> paymentMethods;
    PaymentMethodSelectedListener selectedListener;
    int lastSelectedPosition = -1;

    public PaymentMethodAdapter(List<PaymentMethod> paymentMethods, PaymentMethodSelectedListener selectedListener){
        this.paymentMethods = paymentMethods;
        this.selectedListener = selectedListener;
    }
    @NonNull
    @Override
    public PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_payment_method,parent,false);
        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodViewHolder holder, int position) {
        PaymentMethod paymentMethod = paymentMethods.get(position);
        holder.bind(paymentMethod,position);
    }

    @Override
    public int getItemCount() {
        return paymentMethods.size();
    }

    public class PaymentMethodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        RadioButton rbPayment;
        TextView tvBank;
        TextView tvRekeningNumber;
        TextView tvRekeingName;
        Bundle bundle = new Bundle();
        PaymentMethod paymentMethod;

        public PaymentMethodViewHolder(@NonNull View itemView) {
            super(itemView);

            rbPayment= itemView.findViewById(R.id.rb_payment_method);
            tvBank = itemView.findViewById(R.id.tv_bank_name);
            tvRekeningNumber = itemView.findViewById(R.id.tv_rekening_number);
            tvRekeingName = itemView.findViewById(R.id.tv_rekening_name);
            rbPayment = itemView.findViewById(R.id.rb_payment_method);

        }

        public void bind(PaymentMethod paymentMethod, int position){
            this.paymentMethod = paymentMethod;
            tvBank.setText(this.paymentMethod.getBank());
            tvRekeningNumber.setText(this.paymentMethod.getRekeningNumber());
            tvRekeingName.setText(" (" + this.paymentMethod.getRekeningName() + ")");
            rbPayment.setOnClickListener(this);
            rbPayment.setChecked(lastSelectedPosition==position);

        }

        @Override
        public void onClick(View view) {
            lastSelectedPosition = getAdapterPosition();
            notifyDataSetChanged();
            selectedListener.onPaymentMethodSelected(this.paymentMethod);
            Toast.makeText(view.getContext(),
                    "selected offer is " + tvBank.getText(),
                    Toast.LENGTH_LONG).show();

        }
    }
}
