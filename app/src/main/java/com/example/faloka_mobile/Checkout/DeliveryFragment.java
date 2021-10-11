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

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.AddressAdapter;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Profile;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentDeliveryBinding;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryFragment extends Fragment implements View.OnClickListener{

    public static final int REQUEST_CHOOSE_DELIVERY = 99;
    public static final int RESULT_CHOOSE_DELIVERY = 88;
    public static final String EXTRA_CHOOSE_DELIVERY = "EXTRA_CHOOSE_DELIVERY";

    Product product;
    FragmentDeliveryBinding binding;
    View view;
    Button btnEdit;
    Button btnNext;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_delivery, container, false);
        setAddressSection();
        setFooterDelivery();
        return view;
    }
    private void setFooterDelivery(){
        btnNext = view.findViewById(R.id.btn_checkout_next);
        btnNext.setEnabled(true);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.findViewById(R.id.btn_edit) == btnEdit){
            Intent intent = new Intent(getContext(),ActionAddressActivity.class);
            startActivity(intent);
        }
        else if(view.findViewById(R.id.btn_checkout_next) == btnNext){
            final FragmentManager fragmentManager = getFragmentManager();
          
            Fragment fragmentPayment = CheckoutFragmentUtil.getFragmentByTagName(fragmentManager, "Fragment Payment");

            if(fragmentPayment==null)
            {
                fragmentPayment = new PaymentFragment();
            }
          
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container_payment, fragmentPayment, "Fragment Payment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            CheckoutFragmentUtil.printActivityFragmentList(fragmentManager);
          
        }
    }

    private void setExpedition(){
        binding.tvDeliveryEkspedition.setText("HAHAHA");
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
            String result = data.getStringExtra(DeliveryFragment.EXTRA_CHOOSE_DELIVERY);
            Toast.makeText(getContext(), "HAHA"+result, Toast.LENGTH_SHORT).show();
            binding.tvDeliveryEkspedition.setText("HMMM");
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

    }

    private String getFormatRupiah(int price) {
        Integer.parseInt(String.valueOf(product.getPrice()));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(price);
    }


    private void setAddressSection(){
        TokenManager tokenManager = TokenManager.getInstance(getContext().getSharedPreferences("Token",0));
        Call<Profile> callProfile = ApiConfig.getApiService(tokenManager).getProfile("Bearer "+tokenManager.getToken());

        callProfile.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile profile = response.body();
                    AddressAdapter addressAdapter;

                    addressAdapter = new AddressAdapter(profile.getAddressList());
                    binding.rvAddresses.setAdapter(addressAdapter);
                    binding.rvAddresses.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Toast.makeText(getContext(), "FAIL", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }
}