package com.example.faloka_mobile.Checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.District;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Province;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityActionAdressBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionAddressActivity extends AppCompatActivity {

    ActivityActionAdressBinding binding;
    Address address;
    Province province;
    District district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActionAdressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Tambah Alamat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setProvince();
        if(getIntent().hasExtra(Address.EXTRA_ADDRESS)) {
            address = getIntent().getParcelableExtra(Address.EXTRA_ADDRESS);
            getSupportActionBar().setTitle("Edit Alamat");
            binding.etAddressName.setText(address.getName());
            binding.etAddressPhoneNumber.setText(address.getPhone());
            binding.etAddressSubdistrict.setText(address.getSubDistrict());
            binding.etAddressPostalCode.setText(String.valueOf(address.getPostalCode()));
            binding.etAddressComplete.setText(address.getLocation());
//            binding.spinnerProvince.setSelection(address.);
        }
        binding.btnSubmitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isValidInput(binding)){
                    return;
                }

                Address newAddress = new Address();
                newAddress.setName(binding.etAddressName.getText().toString().trim());
                newAddress.setPhone(binding.etAddressPhoneNumber.getText().toString().trim());
                newAddress.setProvince(province.getName());
                newAddress.setDistrict(district.getName());
                newAddress.setSubDistrict(binding.etAddressSubdistrict.getText().toString().trim());
                newAddress.setPostalCode(Integer.parseInt(binding.etAddressPostalCode.getText().toString().trim()));
//                newAddress.setPostalCode(district.getPostalCode());
                newAddress.setLocation(binding.etAddressComplete.getText().toString().trim());

                if(getIntent().hasExtra(Address.EXTRA_ADDRESS)) {
                    editAddress(view.getContext(), newAddress);
                }
                else{
                    addAddress(view.getContext(), newAddress);
                }

            }
        });
    }

    private boolean isValidInput(ActivityActionAdressBinding binding){
        boolean valid = true;
        if(binding.etAddressName.getText().toString().isEmpty()){
            binding.etAddressName.setError("Fill address name");
            valid = false;
        }
        if(binding.etAddressPhoneNumber.getText().toString().isEmpty()){
            binding.etAddressPhoneNumber.setError("Fill phone number");
            valid = false;
        }
        if(binding.etAddressPostalCode.getText().toString().isEmpty()){
            binding.etAddressPostalCode.setError("Fill postal code");
            valid = false;
        }
        if(binding.etAddressSubdistrict.getText().toString().isEmpty()){
            binding.etAddressSubdistrict.setError("Fill address subdistrict");
            valid = false;
        }
        if(binding.etAddressComplete.getText().toString().isEmpty()){
            binding.etAddressComplete.setError("Fill location");
            valid = false;
        }
        return valid;
    }

    private void setProvince(){
        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
        Call<List<Province>> callAddress = ApiConfig.getApiService(tokenManager).getAllProvince();
        callAddress.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                List<Province> provinceList = response.body();
                ArrayAdapter<Province> provinceArrayAdapter = new ArrayAdapter<Province>(getApplicationContext(), R.layout.spinner_item, provinceList);
                binding.spinnerProvince.setPrompt("Select province");
                provinceArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                binding.spinnerProvince.setAdapter(provinceArrayAdapter);
                binding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        province = (Province) adapterView.getSelectedItem();
                        setDistrict(province.getId());
                        Toast.makeText(view.getContext(), province.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {

            }
        });
    }

    private void setDistrict(int provinceID){
        if(province != null){
            TokenManager tokenManager = TokenManager.getInstance(getApplicationContext().getSharedPreferences("Token",0));
            Call<List<District>> callDistrict = ApiConfig.getApiService(tokenManager).getDistrictByProvince(province.getId()) ;
            callDistrict.enqueue(new Callback<List<District>>() {
                @Override
                public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                    List<District> districtList = response.body();

                    ArrayAdapter<District> districtArrayAdapter = new ArrayAdapter<District>(getApplicationContext(), R.layout.spinner_item, districtList);
//                    binding.spinnerProvince.setPrompt("Select province");
                    districtArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    binding.spinnerDistrict.setAdapter(districtArrayAdapter);
                    binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            district = (District) adapterView.getSelectedItem();
                            binding.etAddressPostalCode.setText(String.valueOf(district.getPostalCode()));
                            Toast.makeText(view.getContext(), district.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<List<District>> call, Throwable t) {

                }
            });
        }
    }

    private void editAddress(Context context, Address newAddress){
        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("Token",0));
        Call<Message> callAddress = ApiConfig.getApiService(tokenManager).putAddress(tokenManager.getTypeToken()+" "+tokenManager.getToken(), address.getId(), newAddress);
        callAddress.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
                setResult(Address.RESULT_EDIT_ADDRESS); // You can also send result without any data using setResult(int resultCode)
                finish();

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + " GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAddress(Context context, Address newAddress){
        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("Token",0));
        Call<Message> callAddress = ApiConfig.getApiService(tokenManager).addAddress(tokenManager.getTypeToken()+" "+tokenManager.getToken(),newAddress);
        callAddress.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
                setResult(Address.RESULT_ADD_ADDRESS); // You can also send result without any data using setResult(int resultCode)
                finish();

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + " GAGAL", Toast.LENGTH_SHORT).show();
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