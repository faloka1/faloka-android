package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.MixAndMatch.SelectedImageListener;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;

import java.util.List;

public class ProductMixMatchAdapter extends RecyclerView.Adapter<ProductMixMatchAdapter.ProductMixMatchViewHolder>{
    List<Product> productList;
    SelectedImageListener selectedImageListener;

    public ProductMixMatchAdapter(List<Product> productList, SelectedImageListener selectedImageListener){
        this.productList = productList;
        this.selectedImageListener = selectedImageListener;
    }

    @NonNull
    @Override
    public ProductMixMatchAdapter.ProductMixMatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_mix_match,parent,false);
        return new ProductMixMatchAdapter.ProductMixMatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMixMatchAdapter.ProductMixMatchViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imgSelected.setVisibility(View.GONE);
        Glide.with(holder.imgMixMatchProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+product.getProductImageURL() )
                .into(holder.imgMixMatchProduct);
        holder.btnMixMatchBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra(Product.EXTRA_PRODUCT, product);
                view.getContext().startActivity(intent);
            }
        });
        holder.cvMixMatchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgSelected.setVisibility(View.VISIBLE);
                selectedImageListener.onSelected(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public class ProductMixMatchViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMixMatchProduct;
        Button btnMixMatchBuy;
        CardView cvMixMatchProduct;
        ImageView imgSelected;
        public ProductMixMatchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMixMatchProduct = itemView.findViewById(R.id.img_mix_match_product);
            btnMixMatchBuy = itemView.findViewById(R.id.btn_mix_match_buy);
            cvMixMatchProduct = itemView.findViewById(R.id.cv_mix_match_product);
            imgSelected = itemView.findViewById(R.id.img_selected);
        }
    }
}
