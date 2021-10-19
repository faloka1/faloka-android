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
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.AddressAdapter;
import com.example.faloka_mobile.Adapter.PaymentAdapter;
import com.example.faloka_mobile.Adapter.PaymentMethodAdapter;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Checkout;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment implements PaymentMethodSelectedListener, PaymentSelectedListener {

    PaymentViewModel viewModel;
    View view;
    Checkout checkout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkout = new Checkout();
        if(getArguments() != null){
            checkout = getArguments().getParcelable(Checkout.EXTRA_CHECKOUT);
            Toast.makeText(getContext(), "HAHA"+checkout.getTotalPrice(), Toast.LENGTH_SHORT).show();
        }
        CheckoutViewModelFactory factory = new CheckoutViewModelFactory(new CheckoutRepository(getActivity()));
        viewModel = new ViewModelProvider(getActivity(),factory).get(PaymentViewModel.class);
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
        TextView tvSubTotal = view.findViewById(R.id.tv_payment_value_subtotal);
        tvSubTotal.setText(String.valueOf(checkout.getTotalPrice()));
    }
    private void setPaymentMethod(){
//        viewModel.getPaymentMethod().observe(getActivity(),paymentMethods -> {
//            RecyclerView rvPaymentMethod = view.findViewById(R.id.rv_payment_method);
//            PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(paymentMethods,this::onPaymentMethodSelected);
//            rvPaymentMethod.setLayoutManager(new LinearLayoutManager(getActivity()));
//            rvPaymentMethod.setAdapter(paymentMethodAdapter);
//        });
        TokenManager tokenManager = TokenManager.getInstance(getContext().getSharedPreferences("Token",0));
        Call<List<Payment>> callPayments = ApiConfig.getApiService(tokenManager).getPayments();

        callPayments.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                List<Payment> paymentList = response.body();
                setRecycleView(paymentList);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
    }

    private void setRecycleView(List<Payment> paymentList){
        PaymentAdapter paymentAdapter = new PaymentAdapter(paymentList, this::onPaymentSelected);
        RecyclerView rvPayment = view.findViewById(R.id.rv_payment_method);
        rvPayment.setAdapter(paymentAdapter);
        rvPayment.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setFooterPayment(){
        TextView tvTotalPrice = view.findViewById(R.id.tv_total_price);
        Button buttonNext = view.findViewById(R.id.btn_checkout_next);

        buttonNext.setText("Konfirmasi");

        getTotal().observe(getActivity(),total->{
            tvTotalPrice.setText(getFormatRupiah(Integer.parseInt(total)));
        });
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
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

    @Override
    public void onPaymentSelected(Payment paymentMethod) {
        Button buttonNext = view.findViewById(R.id.btn_checkout_next);
        TextView tvPriceService = view.findViewById(R.id.tv_payment_value_total_service);
        tvPriceService.setText(String.valueOf(paymentMethod.getPriceService()));
        buttonNext.setEnabled(true);
        buttonNext.setBackgroundResource(R.color.netral_900);
        checkout.setPaymentID(paymentMethod.getId());
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundlePayment = new Bundle();
//                Intent intent = new Intent(getActivity(), ConfirmCheckoutActivity.class);
//
//                getTotal().observe(getActivity(),total->{
//                    bundlePayment.putString("TOTAL_PRICE", total);
//                });
//
//                bundlePayment.putParcelable("PAYMENT_METHOD",paymentMethod);
//                intent.putExtra("DATA_CHECKOUT",bundlePayment);
//                startActivity(intent);
                Bundle bundlePayment = new Bundle();
                Intent intent = new Intent(getActivity(), ConfirmCheckoutActivity.class);

                getTotal().observe(getActivity(),total->{
                    checkout.setTotalPrice(Integer.parseInt(total));
                });

                bundlePayment.putParcelable(Checkout.EXTRA_CHECKOUT, checkout);
                bundlePayment.putParcelable(Payment.EXTRA_PAYMENT, paymentMethod);
                intent.putExtra("DATA_CHECKOUT",bundlePayment);
                startActivity(intent);
            }
        });
        setFooterPayment();
    }
    private LiveData<String> getTotal(){

        TextView tvSubTotal = view.findViewById(R.id.tv_payment_value_subtotal);
        TextView tvServicePrice = view.findViewById(R.id.tv_payment_value_total_service);

        return viewModel.getTotalPrice(tvSubTotal.getText().toString(),tvServicePrice.getText().toString());
    }

}