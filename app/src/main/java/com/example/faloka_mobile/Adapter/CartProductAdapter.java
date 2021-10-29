package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>{
    List<Product> productList;
    private boolean isBrandChecked;

    public CartProductAdapter(List<Product> productList){
        this.productList = productList;
    }

    public void setBrandChecked(boolean isBrandChecked){
        this.isBrandChecked = isBrandChecked;
    }

    @NonNull
    @Override
    public CartProductAdapter.CartProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cart_many_product,parent,false);
        return new CartProductAdapter.CartProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.CartProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvCartProductName.setText(product.getName());
        holder.cbxCartProduct.setChecked(isBrandChecked);
        Glide.with(holder.imgCartProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+product.getImageMixMatchURL() )
                .into(holder.imgCartProduct);
        holder.tvCartProductSize.setText(product.getSizeProduct());
        holder.tvCartProductPrice.setText(getFormatRupiah(product.getPrice()));

    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbxCartProduct;
        ImageView imgCartProduct;
        TextView tvCartProductName;
        TextView tvCartProductSize;
        TextView tvCartQty;
        TextView tvCartProductPrice;

        public CartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cbxCartProduct = itemView.findViewById(R.id. cbx_cart_product);
            imgCartProduct = itemView.findViewById(R.id.img_cart_product);
            tvCartProductName = itemView.findViewById(R.id.tv_cart_product_name);
            tvCartProductSize = itemView.findViewById(R.id.tv_cart_product_size);
            tvCartQty = itemView.findViewById(R.id.tv_cart_qty);
            tvCartProductPrice = itemView.findViewById(R.id.tv_cart_product_price);
        }
    }
}
