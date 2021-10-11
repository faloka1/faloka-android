package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Profile;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityActionAdressBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionAddressActivity extends AppCompatActivity {

    ActivityActionAdressBinding binding;
    Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActionAdressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Edit Alamat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        address = getIntent().getParcelableExtra(Address.EXTRA_ADDRESS);

        binding.etAddressName.setText(address.getName());
        binding.etAddressPhoneNumber.setText(address.getPhone());
        binding.etAddressProvince.setText(address.getProvince());
        binding.etAddressCity.setText(address.getDistrict());
        binding.etAddressSubdistrict.setText(address.getSubDistrict());
        binding.etAddressPostalCode.setText(String.valueOf(address.getPostalCode()));
        binding.etAddressComplete.setText(address.getLocation());

        binding.btnSubmitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Address newAddress = new Address();
                newAddress.setName(binding.etAddressName.getText().toString().trim());
                newAddress.setPhone(binding.etAddressPhoneNumber.getText().toString().trim());
                newAddress.setProvince(binding.etAddressProvince.getText().toString().trim());
                newAddress.setDistrict(binding.etAddressCity.getText().toString().trim());
                newAddress.setSubDistrict(binding.etAddressSubdistrict.getText().toString().trim());
                newAddress.setPostalCode(Integer.parseInt(binding.etAddressPostalCode.getText().toString().trim()));
                newAddress.setLocation(binding.etAddressComplete.getText().toString().trim());

                TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
                Call<Message> callAddress = ApiConfig.getApiService(tokenManager).putAddress(address.getId(), newAddress);
                callAddress.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
//                        if(!response.isSuccessful()){
//                            Toast.makeText(view.getContext(), response.code() + " GAGAL", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                        Message message = response.body();
//                        Toast.makeText(view.getContext(), message.getMessage() + " BERHASIL", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ActionAddressActivity.this, CheckoutActivity.class));
//                        finish();
//                        Intent intent = new Intent();
//                        intent.putExtra(DeliveryFragment.EXTRA_CHOOSE_DELIVERY, "Testing passing data back to ActivityOne");

                        setResult(Address.RESULT_EDIT_ADDRESS); // You can also send result without any data using setResult(int resultCode)
                        finish();

                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.getMessage() + " GAGAL", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}