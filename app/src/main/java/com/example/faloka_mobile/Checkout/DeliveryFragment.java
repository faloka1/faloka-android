package com.example.faloka_mobile.Checkout;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.AddressAdapter;
import com.example.faloka_mobile.Adapter.AddressAddAdapter;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Checkout;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Profile;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentDeliveryBinding;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryFragment extends Fragment{

    public static final int REQUEST_CHOOSE_DELIVERY = 99;
    public static final int RESULT_CHOOSE_DELIVERY = 88;
    public static final String EXTRA_CHOOSE_DELIVERY = "EXTRA_CHOOSE_DELIVERY";
    public static final String EXTRA_CODE_EXPEDITION = "EXTRA_CODE_EXPEDITION";
    public static final String EXTRA_PRICE_EXPEDITION = "EXTRA_PRICE_EXPEDITION";

    Product product;
    Checkout checkout;
    CourierService courierService;
    Courier courier;
    Address address;
    int totalOrder;

    FragmentDeliveryBinding binding;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        checkout = new Checkout();
        setAddressSection();
        setContentProduct();
        setExpedition();
        setFooterDelivery();

        return view;
    }

    private void setContentProduct(){
        if(getArguments() != null){
            product = getArguments().getParcelable(Product.EXTRA_PRODUCT);
            setProductOrder();
            binding.tvDeliveryBrand.setText(product.getBrand().getName());
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
        binding.tvDeliveryEkspedition.setText("Pilih Ekspedisimu");
        Button button = view.findViewById(R.id.btn_checkout_next);
        button.setEnabled(false);
        binding.tvDeliveryEkspedition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeliveryChooseActivity.class);
                startActivityForResult(intent, DeliveryFragment.REQUEST_CHOOSE_DELIVERY);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DeliveryFragment.REQUEST_CHOOSE_DELIVERY && resultCode == RESULT_CHOOSE_DELIVERY){
//            String result = data.getStringExtra(DeliveryFragment.EXTRA_CHOOSE_DELIVERY);

            Bundle bundle = data.getBundleExtra(DeliveryFragment.EXTRA_CHOOSE_DELIVERY);
            courier = bundle.getParcelable(Courier.EXTRA_COURIER);
            courierService = bundle.getParcelable(CourierService.EXTRA_COURIER_SERVICE);
            totalOrder = product.getPrice()+courierService.getCost().get(0).getValue();

//            String codeCourier = data.getStringExtra(DeliveryFragment.EXTRA_CODE_EXPEDITION);
//            int priceCourier = data.getIntExtra(DeliveryFragment.EXTRA_PRICE_EXPEDITION, 0);
//            int subTotal = product.getPrice() + priceCourier;
//            Toast.makeText(getContext(), "HAHA"+result, Toast.LENGTH_SHORT).show();
            binding.tvDeliveryEkspedition.setText(courier.getName()+" "+courierService.getName());
            binding.tvDeliveryExpeditionPrice.setText(getFormatRupiah(courierService.getCost().get(0).getValue()));
            binding.tvDeliverySubtotalValue.setText(getFormatRupiah(totalOrder));
            TextView tvTotal = view.findViewById(R.id.tv_total_price);
            tvTotal.setText(getFormatRupiah(totalOrder));
            Button button = view.findViewById(R.id.btn_checkout_next);
            button.setEnabled(true);
            button.setBackgroundColor(getResources().getColor(R.color.black_faloka));
            checkout.setExpeditionName(courier.getCode());
            checkout.setQuantity(1);
            checkout.setShippingPrice(courierService.getCost().get(0).getValue());
            checkout.setServiceExpedition(courierService.getName());
        }
    }

    private void setProductOrder(){
        TextView tvOrderProductName = view.findViewById(R.id.tv_order_product_name);
        TextView tvOrderProductPrice = view.findViewById(R.id.tv_order_product_price);
        ImageView imgOrderProduct = view.findViewById(R.id.image_order_product);
        TextView tvOrderProductSize = view.findViewById(R.id.tv_order_product_size_value);

        tvOrderProductSize.setText(product.getSizeProduct());
        tvOrderProductPrice.setText(String.valueOf(getFormatRupiah(product.getPrice())));
        tvOrderProductName.setText(product.getName());

        Glide.with(imgOrderProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+product.getProductImageURL())
                .into(imgOrderProduct);
        checkout.setVariantID(product.getVariantList().get(0).getId());
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
                        checkout.setAddressID(user.getAddressList().get(0).getId());
                        address = user.getAddressList().get(0);
                    }
                    else{
                        Toast.makeText(getContext(), "KOSONG", Toast.LENGTH_SHORT).show();
                        binding.rvAddresses.setAdapter(new AddressAddAdapter());
                        binding.rvAddresses.setLayoutManager(new LinearLayoutManager(getContext()));
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

    private void setFooterDelivery(){
//        Button button = binding.footerCheckout.btnCheckoutNext;
        Button button = view.findViewById(R.id.btn_checkout_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Order order = new Order();
                order.setCheckout(checkout);
                order.setProduct(product);
                order.setAddress(address);
                order.setCourier(courier);
                order.setCourierService(courierService);
                order.setTotalOrder(totalOrder);
                Fragment fragment = new PaymentFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Order.EXTRA_ORDER, order);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.frame_container_checkout, new PaymentFragment());
                ft.replace(R.id.frame_container_checkout, fragment );
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}