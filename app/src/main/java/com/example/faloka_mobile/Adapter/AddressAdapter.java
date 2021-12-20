package com.example.faloka_mobile.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Checkout.ActionAddressActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.District;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    List<Address> addressList;
    Context context;
    AddressAdapter addressAdapter;


    public AddressAdapter(List<Address> addressList){
        this.addressList = addressList;
        this.addressAdapter = this;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_address,parent,false);
        context = parent.getContext();
        return new AddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        String deliveryAddress = address.getProvince().getName()+", "+address.getDistrict().getName()+", "+address.getSubDistrict();
        holder.tvTitleDelivery.setText("Alamat Pengiriman");
        holder.tvDeliveryCompleteAddress.setText(address.getLocation());
        holder.tvDeliveryAddress.setText(deliveryAddress);

        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("Token",0));
        Call<User> callUser = ApiConfig.getApiService(tokenManager).getUser(tokenManager.getTypeToken()+" "+tokenManager.getToken());
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                holder.tvDeliveryPhoneNumber.setText(user.getPhone());
                holder.tvDeliveryName.setText(user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActionAddressActivity.class);
                intent.putExtra(Address.EXTRA_ADDRESS, address);
                ((Activity) context).startActivityForResult(intent, Address.REQUEST_EDIT_ADDRESS);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
                Call<Message> callAddress = ApiConfig.getApiService(tokenManager).deleteAddress(tokenManager.getTypeToken()+" "+tokenManager.getToken(),address.getId());
                callAddress.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message message = response.body();
                        Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                        ((Activity) context).startActivity(((Activity) context).getIntent());
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.addressList.size();
    }


    public class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleDelivery;
        TextView tvDeliveryName;
        TextView tvDeliveryCompleteAddress;
        TextView tvDeliveryAddress;
        TextView tvDeliveryPhoneNumber;
        Button btnEdit;
        Button btnDelete;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleDelivery = itemView.findViewById(R.id.tv_title_delivery);
            tvDeliveryName = itemView.findViewById(R.id.tv_delivery_name);
            tvDeliveryCompleteAddress = itemView.findViewById(R.id.tv_delivery_complete_address);
            tvDeliveryAddress = itemView.findViewById(R.id.tv_delivery_address);
            tvDeliveryPhoneNumber = itemView.findViewById(R.id.tv_delivery_phone_number);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
