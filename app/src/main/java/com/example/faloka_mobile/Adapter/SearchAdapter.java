package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    List<Product> productList;
    View view;
    String text;

    public SearchAdapter(List<Product> productList, String text){
        this.productList = productList;
        this.text = text;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_search_product,parent,false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product.getName() == null){
            holder.tvSearchProductName.setText("Produk \""+text+"\" tidak ditemukan");
        }
        else {
            holder.tvSearchProductName.setText(product.getName());
        }
        if(product.getPrice() == 0){
            holder.tvSearchProductPrice.setText("Mungkin produk belum ada, yuk cari kata lain");
        }
        else {
            holder.tvSearchProductPrice.setText(getFormatRupiah(product.getPrice()));
        }
        if (product.getVariantList() == null){
            holder.imgSearchProduct.setImageResource(R.drawable.ic_svg_unkown);
        }
        else {
            Glide.with(holder.imgSearchProduct.getContext())
                    .load(ApiConfig.BASE_IMAGE_URL+product.getProductImageURL() )
                    .into(holder.imgSearchProduct);
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra(Product.EXTRA_PRODUCT, product);
                view.getContext().startActivity(intent);
            }
        });
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

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView tvSearchProductName;
        TextView tvSearchProductPrice;
        ImageView imgSearchProduct;
        ConstraintLayout constraintLayout;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchProductName = itemView.findViewById(R.id.tv_search_product_name);
            tvSearchProductPrice = itemView.findViewById(R.id.tv_search_product_price);
            imgSearchProduct = itemView.findViewById(R.id.img_search_product);
            constraintLayout = itemView.findViewById(R.id.constraint_layout_grid_search);
        }
    }
}
