package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.GridviewHolder>{

    ArrayList<Product> listProduct;

    public ProductAdapter(ArrayList<Product> listProduct){

        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public GridviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product, parent, false);
        return new GridviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridviewHolder holder, int position) {
        Product product = listProduct.get(position);
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .apply(new RequestOptions().override(157,245))
                .into(holder.iVProduct);
        holder.tvProductName.setText(product.getName());
        holder.tvBrand.setText(product.getBrand());
        holder.tvPrice.setText(product.getPrice());

    }

    @Override
    public int getItemCount() {

        return listProduct.size();
    }

    public class GridviewHolder extends RecyclerView.ViewHolder {
        ImageView iVProduct;
        TextView tvProductName, tvBrand, tvPrice;

        public GridviewHolder(View itemView) {
            super((itemView));
            iVProduct = itemView.findViewById(R.id.img_item_photo);
            tvProductName = itemView.findViewById(R.id.product_name);
            tvBrand = itemView.findViewById(R.id.brand);
            tvPrice = itemView.findViewById(R.id.price);
        }
    }
}
