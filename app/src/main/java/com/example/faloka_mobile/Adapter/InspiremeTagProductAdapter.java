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
import com.example.faloka_mobile.Model.InspireMeProductVariant;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InspiremeTagProductAdapter extends RecyclerView.Adapter<InspiremeTagProductAdapter.viewHolder> {

    List<Product> products;
    List<Variant> variants;
    public ArrayList<InspireMeProductVariant> productsChecked = new ArrayList<>();

    public InspiremeTagProductAdapter(List<Product> products,
                                      List<Variant>variants,
                                      ArrayList<InspireMeProductVariant> productChecked){
        this.products = products;
        this.variants = variants;
        this.productsChecked = productChecked;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_upload_relate_product,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Product product = products.get(position);
        Variant variant = variants.get(position);
        holder.bind(product,variant);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvPrice;
        CheckBox checkBox;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_inspireme_upload_relate_product);
            checkBox = itemView.findViewById(R.id.cb_inspireme_upload_relate_product);
            tvName = itemView.findViewById(R.id.tv_inspireme_tag_product_name);
            tvPrice = itemView.findViewById(R.id.tv_inspireme_tag_product_price);

        }
        public void bind(Product product, Variant variant){
            Glide.with(imageView.getContext())
                    .load(ApiConfig.BASE_IMAGE_URL + variant.getVariantImageList().get(0).getImageURL())
                    .into(imageView);
            tvName.setText(product.getName());

            Double price = Double.parseDouble(String.valueOf(product.getPrice()));
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            tvPrice.setText(String.valueOf(formatRupiah.format(price)));

            InspireMeProductVariant productVariant = new InspireMeProductVariant(product,variant);
            if(productsChecked.size()!=0){
                for(InspireMeProductVariant pv : productsChecked){
                    if(product.getId()==pv.getProduct().getId()){
                        checkBox.setChecked(true);
                    }
                }
            }
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked()){
                        productsChecked.add(productVariant);
                    }
                    else if(!checkBox.isChecked()){
                        for(int i=0; i<productsChecked.size(); i++){
                            if(productsChecked.get(i).getProduct().getId() == product.getId()){
                                productsChecked.remove(i);
                            }
                        }
                    }
                }
            });


        }
    }
}
