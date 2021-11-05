package com.example.faloka_mobile.Checkout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import com.example.faloka_mobile.Adapter.PaymentAdapter;
import com.example.faloka_mobile.Cart.CartRepository;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.BodyCheckout;
import com.example.faloka_mobile.Model.BodyOrderBrand;
import com.example.faloka_mobile.Model.BodyOrderItem;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderResponse;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.PaymentMethod;
import com.example.faloka_mobile.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment implements PaymentMethodSelectedListener, PaymentSelectedListener {

    public static final int PAYMENT_STEP = 1;

    StepViewSelectedListener stepViewSelectedListener;
    PaymentViewModel viewModel;
    View view;
//    Checkout checkout;
    Order order;
//    OrderUser orderUser;

    public PaymentFragment(StepViewSelectedListener stepViewSelectedListener){
        this.stepViewSelectedListener = stepViewSelectedListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = new Order();
//        orderUser = new OrderUser();
        if(getArguments() != null){
            order = getArguments().getParcelable(Order.EXTRA_ORDER);
//            orderUser = getArguments().getParcelable(OrderUser.EXTRA_ORDER_USER);
//            Toast.makeText(getContext(), "HAHA"+order.getCheckout().getTotalPrice(), Toast.LENGTH_SHORT).show();
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
        int total = 0;
//        total = orderUser.getShippingPrice() + orderUser.getOrderDetailList().get(0).getProduct().getPrice();
        total = getTotal(order.getCartBrandList());
        TextView tvSubTotal = view.findViewById(R.id.tv_payment_value_subtotal);
//        tvSubTotal.setText(String.valueOf(order.getTotalOrder()));
        tvSubTotal.setText(String.valueOf(total));
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

    public static final int getTotalOrderUserProduct(OrderUser orderUser){
        int total = 0;
        List<OrderDetail> orderDetailList = orderUser.getOrderDetailList();
        for(OrderDetail orderDetail : orderDetailList){
            int sub = orderDetail.getProduct().getPrice() * orderDetail.getQuantity();
            total += sub;
        }
        return total;
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
//            order.setTotalOrder(Integer.parseInt(total));
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
        buttonNext.setTextColor(getResources().getColor(R.color.white));
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
                getActivity().finish();
            }
        });

    }

    @Override
    public void onPaymentSelected(Payment paymentMethod) {
        Button buttonNext = view.findViewById(R.id.btn_checkout_next);
        TextView tvPriceService = view.findViewById(R.id.tv_payment_value_total_service);
        tvPriceService.setText(String.valueOf(paymentMethod.getPriceService()));
        buttonNext.setEnabled(true);
        buttonNext.setTextColor(getResources().getColor(R.color.white));
        buttonNext.setBackgroundResource(R.color.netral_900);
//        order.getCheckout().setPaymentID(paymentMethod.getId());
//        orderUser.setPaymentID(paymentMethod.getId());
        order.setPayment(paymentMethod);
//        Toast.makeText(view.getContext(), String.valueOf(order.getPayment().getId()), Toast.LENGTH_SHORT).show();
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

//                getTotal().observe(getActivity(),total->{
//                    order.getCheckout().setTotalPrice(Integer.parseInt(total));
//                });


                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                alertBuilder.setMessage("Apakah kamu yakin memesan ini?");
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton(
                        "Pesan",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TokenManager tokenManager = TokenManager.getInstance(getContext().getSharedPreferences("Token",0));
                                Call<OrderResponse> callCheckout = ApiConfig.getApiService(tokenManager).isCheckout(
                                        tokenManager.getTypeToken()+" "+tokenManager.getToken(),
                                            getBodyCheckout(order)
                                );
                                callCheckout.enqueue(new Callback<OrderResponse>() {
                                    @Override
                                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                        if(response.isSuccessful()){
                                            OrderResponse orderResponse = response.body();
                                            CartRepository.deleteAllCart(view);
                                            Bundle bundlePayment = new Bundle();
                                            Intent intent = new Intent(getActivity(), ConfirmCheckoutActivity.class);
                                            order.setId(orderResponse.getOrderID());
                                            bundlePayment.putParcelable(Order.EXTRA_ORDER, order);
                                            intent.putExtra("DATA_ORDER",bundlePayment);
                                            startActivity(intent);
                                        }
                                        else {
                                            Snackbar.make(view, "Maaf, gagal order"+ response.code(), Snackbar.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderResponse> call, Throwable t) {

                                    }
                                });
                            }
                        }
                );
                alertBuilder.setNegativeButton(
                        "Batal",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );

                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

            }
        });
        setFooterPayment();
    }

    private BodyCheckout getBodyCheckout(Order order){
        BodyCheckout bodyCheckout = new BodyCheckout();
        bodyCheckout.setAddressID(order.getAddress().getId());
        bodyCheckout.setPaymentID(order.getPayment().getId());
        List<BodyOrderBrand> bodyOrderBrandList = new ArrayList<>();
        for(CartBrand cartBrand : order.getCartBrandList()){
            BodyOrderBrand bodyOrderBrand = new BodyOrderBrand();
            bodyOrderBrand.setBrandID(cartBrand.getBrand().getId());
            bodyOrderBrand.setShippingPrice(cartBrand.getCourierService().getCost().get(0).getValue());
            bodyOrderBrand.setExpeditionName(cartBrand.getCourier().getName());
            bodyOrderBrand.setService(cartBrand.getCourierService().getName());
            List<BodyOrderItem> bodyOrderItemList = new ArrayList<>();
            for(Cart cart : cartBrand.getCartList()){
                BodyOrderItem bodyOrderItem = new BodyOrderItem();
                bodyOrderItem.setProductID(cart.getProductID());
                bodyOrderItem.setVariantID(cart.getVariantID());
                bodyOrderItem.setQuantity(cart.getQuantity());
                bodyOrderItemList.add(bodyOrderItem);
            }
            bodyOrderBrand.setBodyOrderItemList(bodyOrderItemList);
            bodyOrderBrandList.add(bodyOrderBrand);
        }
        bodyCheckout.setBodyOrderBrandList(bodyOrderBrandList);
//        System.out.println("\npayment_id: "+bodyCheckout.getPaymentID());
//        System.out.println("address_id: "+bodyCheckout.getAddressID());
//        for (BodyOrderBrand bodyOrderBrand : bodyCheckout.getBodyOrderBrandList()){
//            System.out.println("   payment_id: "+bodyOrderBrand.getBrandID());
//            System.out.println("   shipping_price: "+bodyOrderBrand.getShippingPrice());
//            System.out.println("   expedition_name: "+bodyOrderBrand.getExpeditionName());
//            System.out.println("   service: "+bodyOrderBrand.getService());
//            for(BodyOrderItem bodyOrderItem : bodyOrderBrand.getBodyOrderItemList()){
//                System.out.println("      quantity: "+bodyOrderItem.getQuantity());
//                System.out.println("      variant_id: "+bodyOrderItem.getVariantID());
//                System.out.println("      product_id: "+bodyOrderItem.getProductID());
//            }
//        }

        return bodyCheckout;
    }

    private LiveData<String> getTotal(){

        TextView tvSubTotal = view.findViewById(R.id.tv_payment_value_subtotal);
        TextView tvServicePrice = view.findViewById(R.id.tv_payment_value_total_service);

        return viewModel.getTotalPrice(tvSubTotal.getText().toString(),tvServicePrice.getText().toString());
    }

    public static final int getTotal(List<CartBrand> cartBrandList){
        int sumTotalProduct = 0;
        int sumTotalExpedition = 0;
        for(CartBrand cartBrand : cartBrandList){
            sumTotalExpedition += cartBrand.getCourierService().getCost().get(0).getValue();
            for(Cart cart : cartBrand.getCartList()){
                sumTotalProduct += cart.getProduct().getPrice() * cart.getQuantity();
            }
        }
        return sumTotalExpedition + sumTotalProduct;
    }

}