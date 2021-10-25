package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MixAndMatch.SelectedImageListener;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductMixMatch;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductMixMatchAdapter extends RecyclerView.Adapter<ProductMixMatchAdapter.ProductMixMatchViewHolder>{
    List<ProductMixMatch> productMixMatchList;
    SelectedImageListener selectedImageListener;
    View view;

    public ProductMixMatchAdapter(List<ProductMixMatch> productMixMatchList, SelectedImageListener selectedImageListener){
//        this.productList = productList;
        this.productMixMatchList = productMixMatchList;
        this.selectedImageListener = selectedImageListener;
    }

    @NonNull
    @Override
    public ProductMixMatchAdapter.ProductMixMatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_mix_match,parent,false);
        return new ProductMixMatchAdapter.ProductMixMatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMixMatchAdapter.ProductMixMatchViewHolder holder, int position) {
//        Product product = productList.get(position);
        ProductMixMatch productMixMatch = productMixMatchList.get(position);
        holder.imgSelected.setVisibility(View.GONE);
        Glide.with(holder.imgMixMatchProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+productMixMatch.getImageURL() )
                .into(holder.imgMixMatchProduct);
        holder.btnMixMatchBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
                Call<Product> callProductSlug = ApiConfig.getApiService(tokenManager).getProductSlug(productMixMatch.getSlug());
                callProductSlug.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful()){
                            Product product = response.body();
//                            Toast.makeText(view.getContext(), product.getProductImageURL(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                            intent.putExtra(Product.EXTRA_PRODUCT, product);
                            view.getContext().startActivity(intent);
                        }
                        else {
                            Toast.makeText(view.getContext(), "FAIL RESPONSE", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(view.getContext(), "FAIL API", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.cvMixMatchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgSelected.setVisibility(View.VISIBLE);
                selectedImageListener.onSelected(productMixMatch);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.productMixMatchList.size();
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
