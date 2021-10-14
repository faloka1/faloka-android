package com.example.faloka_mobile.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Checkout.ActionAddressActivity;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.R;

public class AddressAddAdapter extends RecyclerView.Adapter<AddressAddAdapter.AddressAddViewHolder>{
    Context context;
    @NonNull
    @Override
    public AddressAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_add_address,parent,false);
        context = parent.getContext();
        return new AddressAddAdapter.AddressAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAddViewHolder holder, int position) {
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "HAHA", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ActionAddressActivity.class);
                ((Activity) context).startActivityForResult(intent, Address.REQUEST_ADD_ADDRESS);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class AddressAddViewHolder extends RecyclerView.ViewHolder {
        Button btnAdd;

        public AddressAddViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAdd = itemView.findViewById(R.id.btn_add);
        }
    }
}
