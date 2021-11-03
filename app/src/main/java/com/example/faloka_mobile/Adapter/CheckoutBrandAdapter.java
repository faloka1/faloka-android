package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class CheckoutBrandAdapter extends RecyclerView.Adapter<CheckoutBrandAdapter.OrderBrandViewHolder>{

    private List<CartBrand> cartBrandList;

    public CheckoutBrandAdapter(List<CartBrand> cartBrandList){
        this.cartBrandList = cartBrandList;
    }

    @NonNull
    @Override
    public CheckoutBrandAdapter.OrderBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_checkout_many_brand,parent,false);
        return new CheckoutBrandAdapter.OrderBrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutBrandAdapter.OrderBrandViewHolder holder, int position) {
        CartBrand cartBrand = cartBrandList.get(position);
        holder.tvCheckoutBrandName.setText(cartBrand.getBrand().getName());
        CheckoutProductAdapter checkoutProductAdapter = new CheckoutProductAdapter(cartBrand.getCartList());
        holder.rvCheckoutProduct.setAdapter(checkoutProductAdapter);
        holder.rvCheckoutProduct.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
    }

//    public List<Product> initProductList(List<Cart> cartList){
//        List<Product> productList = new ArrayList<>();
//        for(Cart cart : cartList){
//            productList.add(cart.getProduct());
//        }
//        return productList;
//    }

    @Override
    public int getItemCount() {
        return this.cartBrandList.size();
    }

    public class OrderBrandViewHolder extends RecyclerView.ViewHolder {

        TextView tvCheckoutBrandName;
        RecyclerView rvCheckoutProduct;

        public OrderBrandViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCheckoutBrandName = itemView.findViewById(R.id.tv_checkout_brand_name);
            rvCheckoutProduct = itemView.findViewById(R.id.rv_checkout_product);

        }
    }
}
