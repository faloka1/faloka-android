package com.example.faloka_mobile.Product_List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.faloka_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<ProductResponse> productList;

    public ProductAdapter(ArrayList<ProductResponse> productList){
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product,parent,false);
        ViewGroup.LayoutParams params = new  RecyclerView.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        params.width = parent.getWidth()/2;
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        ProductResponse product = productList.get(position);
        holder.img.setImageResource(product.getImage());
        holder.name.setText(product.getName());
        holder.brand.setText(product.getBrand());
        //holder.price.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView brand;
        TextView name;
        TextView price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.product_image);
            brand = itemView.findViewById(R.id.product_brand);
            name = itemView.findViewById(R.id.product_name);
            //price = itemView.findViewById(R.id.product_price);
        }
    }
}
