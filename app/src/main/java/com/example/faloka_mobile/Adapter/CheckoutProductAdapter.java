package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.CheckoutProductViewHolder>{

    List<Product> productList;

    public CheckoutProductAdapter(List<Product> productList){
        this.productList = productList;
    }

    @NonNull
    @Override
    public CheckoutProductAdapter.CheckoutProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_order,parent,false);
        return new CheckoutProductAdapter.CheckoutProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutProductAdapter.CheckoutProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(holder.imageOrderProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+product.getProductImageURL() )
                .into(holder.imageOrderProduct);
        holder.tvOrderProductName.setText(product.getName());
        holder.tvOrderProductPrice.setText(getFormatRupiah(product.getPrice()));
        holder.tvOrderProductSizeValue.setText(product.getSizeProduct());
        holder.tvCheckoutProductQty.setText(String.valueOf(product.getQuantity())+"x");
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public class CheckoutProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageOrderProduct;
        TextView tvOrderProductName;
        TextView tvOrderProductSizeValue;
        TextView tvOrderProductPrice;
        TextView tvCheckoutProductQty;

        public CheckoutProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOrderProduct = itemView.findViewById(R.id.image_order_product);
            tvOrderProductName = itemView.findViewById(R.id.tv_order_product_name);
            tvOrderProductSizeValue = itemView.findViewById(R.id.tv_order_product_size_value);
            tvOrderProductPrice = itemView.findViewById(R.id.tv_order_product_price);
            tvCheckoutProductQty = itemView.findViewById(R.id.tv_checkout_product_qty);
        }
    }
}
