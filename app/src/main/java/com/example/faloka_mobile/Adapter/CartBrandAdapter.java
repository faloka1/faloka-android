package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.R;

import java.util.List;

public class CartBrandAdapter extends RecyclerView.Adapter<CartBrandAdapter.CartBrandViewHolder>{

    private List<CartBrand> cartBrandList;
    private boolean isAllChecked;

    public CartBrandAdapter(List<CartBrand> cartBrandList){
        this.cartBrandList = cartBrandList;
    }

    public void setAllChecked(boolean isAllChecked){
        this.isAllChecked = isAllChecked;
    }

    @NonNull
    @Override
    public CartBrandAdapter.CartBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cart_many_brand,parent,false);
        return new CartBrandAdapter.CartBrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartBrandAdapter.CartBrandViewHolder holder, int position) {
        CartBrand cartBrand = cartBrandList.get(position);
        holder.cbxCartBrandName.setText(cartBrand.getBrand().getName());
        CartProductAdapter cartProductAdapter = new CartProductAdapter(cartBrand.getProductList());
        cartProductAdapter.setBrandChecked(isAllChecked);
        holder.rvCartBrand.setAdapter(cartProductAdapter);
        holder.rvCartBrand.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.cbxCartBrandName.setChecked(isAllChecked);

        holder.cbxCartBrandName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    cartProductAdapter.setBrandChecked(true);
                }
                else {
                    cartProductAdapter.setBrandChecked(false);
                }
                holder.rvCartBrand.setAdapter(cartProductAdapter);
                holder.rvCartBrand.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.cartBrandList.size();
    }

    public class CartBrandViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rvCartBrand;
        CheckBox cbxCartBrandName;

        public CartBrandViewHolder(@NonNull View itemView) {
            super(itemView);
            rvCartBrand = itemView.findViewById(R.id.rv_cart_brand);
            cbxCartBrandName = itemView.findViewById(R.id.cbx_cart_brand_name);
        }
    }
}
