package com.example.faloka_mobile.Checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.AddressAdapter;
import com.example.faloka_mobile.Adapter.AddressAddAdapter;
import com.example.faloka_mobile.Adapter.CheckoutBrandAdapter;
import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentDeliveryBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryFragment extends Fragment implements CheckoutBrandListener {

    public static final int REQUEST_CHOOSE_DELIVERY = 99;
    public static final int RESULT_CHOOSE_DELIVERY = 88;
    public static final String EXTRA_CHOOSE_DELIVERY = "EXTRA_CHOOSE_DELIVERY";
    public static final String EXTRA_CODE_EXPEDITION = "EXTRA_CODE_EXPEDITION";
    public static final String EXTRA_PRICE_EXPEDITION = "EXTRA_PRICE_EXPEDITION";
    public static final int DELIVERY_STEP = 0;

    List<Cart> cartList;
//    CourierService courierService;
//    Courier courier;
    Address address;
    StepViewSelectedListener stepViewSelectedListener;
    CheckoutBrandAdapter checkoutBrandAdapter;
    FragmentDeliveryBinding binding;
    View view;

    public DeliveryFragment(StepViewSelectedListener stepViewSelectedListener){
        this.stepViewSelectedListener = stepViewSelectedListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        view = binding.getRoot();
//        checkout = new Checkout();
        setAddressSection();
        setContentProduct();
        setExpedition();
//        setFooterDelivery();

        return view;
    }

    private void setContentProduct(){
        if(getArguments() != null){
            cartList = getArguments().getParcelableArrayList(Product.EXTRA_PRODUCT);
            setProductOrder(cartList);
            binding.tvDeliverySubtotalValue.setText(String.valueOf(getFormatRupiah(0 ) ));
        }
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    private void setExpedition(){
//        binding.tvDeliveryEkspedition.setText("Pilih Ekspedisimu");
//        Button button = view.findViewById(R.id.btn_checkout_next);
//        button.setEnabled(false);
//        binding.tvDeliveryEkspedition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), DeliveryChooseActivity.class);
//                startActivityForResult(intent, DeliveryFragment.REQUEST_CHOOSE_DELIVERY);
//            }
//        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("MASUK INI");
//        checkoutBrandAdapter.onActivityResult(requestCode, resultCode, data);
//        binding.rvCheckoutBrand.setAdapter(checkoutBrandAdapter);
//        if(requestCode == DeliveryFragment.REQUEST_CHOOSE_DELIVERY && resultCode == RESULT_CHOOSE_DELIVERY){
//            int totalOrder = 0;
//            Bundle bundle = data.getBundleExtra(DeliveryFragment.EXTRA_CHOOSE_DELIVERY);
//            courier = bundle.getParcelable(Courier.EXTRA_COURIER);
//            courierService = bundle.getParcelable(CourierService.EXTRA_COURIER_SERVICE);
//            totalOrder = CartActivity.getTotal(cartList) +courierService.getCost().get(0).getValue();
//
//            binding.tvDeliveryEkspedition.setText(courier.getName()+" "+courierService.getName());
//            binding.tvDeliveryExpeditionPrice.setText(getFormatRupiah(courierService.getCost().get(0).getValue()));
//            binding.tvDeliverySubtotalValue.setText(getFormatRupiah(totalOrder));
//            TextView tvTotal = view.findViewById(R.id.tv_total_price);
//            tvTotal.setText(getFormatRupiah(totalOrder));
//
//            if(address != null){
//                binding.footerCheckout.btnCheckoutNext.setEnabled(true);
//                binding.footerCheckout.btnCheckoutNext.setTextColor(getResources().getColor(R.color.white));
//                binding.footerCheckout.btnCheckoutNext.setBackgroundColor(getResources().getColor(R.color.black_faloka));
//            }
//            else {
//                binding.footerCheckout.btnCheckoutNext.setEnabled(false);
//                binding.footerCheckout.btnCheckoutNext.setBackgroundColor(getResources().getColor(R.color.white_faloka));
//            }
//        }
//    }

    private void setProductOrder(List<Cart> cartList){
        List<CartBrand> cartBrandList = CartActivity.brandClassification(cartList);

        int sumTotalProduct = 0;
        for(CartBrand cartBrand : cartBrandList){
            for(Cart cart : cartBrand.getCartList()){
                sumTotalProduct += cart.getProduct().getPrice() * cart.getQuantity();
            }
        }
        binding.tvDeliveryTotalProductPrice.setText(getFormatRupiah(sumTotalProduct));
        binding.footerCheckout.tvTotalPrice.setText(getFormatRupiah(sumTotalProduct));

        checkoutBrandAdapter = new CheckoutBrandAdapter(cartBrandList);
        checkoutBrandAdapter.setCheckoutBrandListener(this::onCartBrand);
        binding.rvCheckoutBrand.setAdapter(checkoutBrandAdapter);
        binding.rvCheckoutBrand.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void setAddressSection(){
        TokenManager tokenManager = TokenManager.getInstance(getContext().getSharedPreferences("Token",0));
        Call<User> callUser = ApiConfig.getApiService(tokenManager).getUser("Bearer "+tokenManager.getToken());

        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    AddressAdapter addressAdapter;
                    if(user.getAddressList().size() != 0){
                        addressAdapter = new AddressAdapter(user.getAddressList());
                        binding.rvAddresses.setAdapter(addressAdapter);
                        binding.rvAddresses.setLayoutManager(new LinearLayoutManager(getContext()));
//                        checkout.setAddressID(user.getAddressList().get(0).getId());
                        address = user.getAddressList().get(0);
                    }
                    else{
//                        Toast.makeText(getContext(), "KOSONG", Toast.LENGTH_SHORT).show();
                        binding.rvAddresses.setAdapter(new AddressAddAdapter());
                        binding.rvAddresses.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.footerCheckout.btnCheckoutNext.setEnabled(false);
                        binding.footerCheckout.btnCheckoutNext.setBackgroundColor(getResources().getColor(R.color.white_faloka));
                    }
                }
                else {
                    Toast.makeText(getContext(), "FAIL", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void setFooterDelivery(List<CartBrand> cartBrandList){
//        Button button = binding.footerCheckout.btnCheckoutNext;
        Button button = view.findViewById(R.id.btn_checkout_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OrderUser orderUser = new OrderUser();
//                orderUser.setShippingPrice(courierService.getCost().get(0).getValue());
//                orderUser.setService(courierService.getName());
//                orderUser.setExpeditionName(courier.getName());
//                orderUser.setAddressID(address.getId());

//                List<OrderDetail> orderDetailList = new ArrayList<>();
//                for(Cart cart : cartList){
//                    OrderDetail orderDetail = new OrderDetail();
//                    orderDetail.setQuantity(cart.getQuantity());
//                    orderDetail.setProductID(cart.getProductID());
//                    orderDetail.setVariantID(cart.getVariantID());
//                    orderDetail.setVariant(cart.getVariant());
//                    orderDetail.setProduct(cart.getProduct());
//                    orderDetailList.add(orderDetail);
//                }
//
////                orderDetail.setProduct(product);
////                orderDetail.setVariant(product.getVariantList().get(0));
////                orderDetail.setQuantity(1);
////                orderDetailList.add(orderDetail);
//                orderUser.setOrderDetailList(orderDetailList);
//                orderUser.setAddress(address);

                Order order = new Order();
                order.setAddress(address);
                order.setCartBrandList(cartBrandList);


                Fragment fragment = new PaymentFragment(stepViewSelectedListener);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Order.EXTRA_ORDER, order);
//                bundle.putParcelable(OrderUser.EXTRA_ORDER_USER, orderUser);
//                bundle.putParcelable(CartBrand.EXTRA_CART_BRAND, cartBrandList);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.frame_container_checkout, new PaymentFragment());
                ft.replace(R.id.frame_container_checkout, fragment );
                ft.addToBackStack(null);
                ft.commit();
                stepViewSelectedListener.onStep(PaymentFragment.PAYMENT_STEP);
            }
        });
    }

    @Override
    public void onCartBrand(List<CartBrand> cartBrandList) {
        int sumTotalExpedition = 0;
        int sumTotalProduct = 0;
        for(CartBrand cartBrand : cartBrandList){
            if(cartBrand.getCourier() != null){
                sumTotalExpedition += cartBrand.getCourierService().getCost().get(0).getValue();
                binding.footerCheckout.btnCheckoutNext.setEnabled(true);
                binding.footerCheckout.btnCheckoutNext.setTextColor(getResources().getColor(R.color.white));
                binding.footerCheckout.btnCheckoutNext.setBackgroundColor(getResources().getColor(R.color.black_faloka));
            }
            else {
                binding.footerCheckout.btnCheckoutNext.setEnabled(false);
                binding.footerCheckout.btnCheckoutNext.setBackgroundColor(getResources().getColor(R.color.white_faloka));
            }
            for(Cart cart : cartBrand.getCartList()){
                sumTotalProduct += cart.getProduct().getPrice() * cart.getQuantity();
            }
        }
        binding.tvDeliveryTotalExpeditionPrice.setText(getFormatRupiah(sumTotalExpedition));
        binding.tvDeliverySubtotalValue.setText(getFormatRupiah(sumTotalProduct+sumTotalExpedition));
        binding.footerCheckout.tvTotalPrice.setText(getFormatRupiah(sumTotalProduct+sumTotalExpedition));
        setFooterDelivery(cartBrandList);
    }
}