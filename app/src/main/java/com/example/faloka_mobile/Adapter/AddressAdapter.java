package com.example.faloka_mobile.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    List<Address> addressList;

    public AddressAdapter(List<Address> addressList){
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_address,parent,false);
        return new AddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        String deliveryAddress = address.getProvince()+", "+address.getDistrict()+", "+address.getSubDistrict();
        holder.tvTitleDelivery.setText("Alamat Pengiriman");
        holder.tvDeliveryName.setText(address.getName());
        holder.tvDeliveryCompleteAddress.setText(address.getLocation());
        holder.tvDeliveryAddress.setText(deliveryAddress);
        holder.tvDeliveryPhoneNumber.setText(address.getPhone());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "EDIT"+ address.getLocation(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "DELETE"+ address.getLocation(), Toast.LENGTH_SHORT).show();
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
