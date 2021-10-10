package com.example.faloka_mobile.Checkout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.faloka_mobile.Adapter.PaymentMethodAdapter;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;

public class PaymentFragment extends Fragment implements PaymentMethodSelectedListener {

    PaymentViewModel viewModel;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckoutViewModelFactory factory = new CheckoutViewModelFactory(new CheckoutRepository(getActivity()));
        viewModel = new ViewModelProvider(getActivity(), factory).get(PaymentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_payment, container, false);
        setSummaryPrice();
        setPaymentMethod();
        setFooterPayment();

        return view;
    }
    private void setSummaryPrice(){

    }
    private void setPaymentMethod(){
        viewModel.getPaymentMethod().observe(getActivity(),paymentMethods -> {
            RecyclerView rvPaymentMethod = view.findViewById(R.id.rv_payment_method);
            PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(paymentMethods,this::onPaymentMethodSelected);
            rvPaymentMethod.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvPaymentMethod.setAdapter(paymentMethodAdapter);
        });
    }
    private void setFooterPayment(){
        TextView tvTotalPrice = view.findViewById(R.id.tv_total_price);
        Button buttonNext = view.findViewById(R.id.btn_checkout_next);

        buttonNext.setText("Konfirmasi");

        getTotal().observe(getActivity(),total->{
            tvTotalPrice.setText(total);
        });
    }

    @Override
    public void onPaymentMethodSelected(PaymentMethod paymentMethod) {
        Button buttonNext = view.findViewById(R.id.btn_checkout_next);
        buttonNext.setEnabled(true);
        buttonNext.setBackgroundResource(R.color.netral_900);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundlePayment = new Bundle();
                Intent intent = new Intent(getActivity(), ConfirmCheckoutActivity.class);

                getTotal().observe(getActivity(),total->{
                    bundlePayment.putString("TOTAL_PRICE", total);
                });

                bundlePayment.putParcelable("PAYMENT_METHOD",paymentMethod);
                intent.putExtra("DATA_CHECKOUT",bundlePayment);
                startActivity(intent);
            }
        });

    }
    private LiveData<String> getTotal(){

        TextView tvSubTotal = view.findViewById(R.id.tv_payment_value_subtotal);
        TextView tvServicePrice = view.findViewById(R.id.tv_payment_value_total_service);

        return viewModel.getTotalPrice(tvSubTotal.getText().toString(),tvServicePrice.getText().toString());
    }

}