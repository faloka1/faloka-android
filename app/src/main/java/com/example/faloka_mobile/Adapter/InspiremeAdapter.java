package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.InspireMe.InspireMeRelateProductListActivity;
import com.example.faloka_mobile.Model.InspireMe;
import com.example.faloka_mobile.Model.InspiremeProducts;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InspiremeAdapter extends RecyclerView.Adapter<InspiremeAdapter.InspiremeViewHolder> {


    List<InspireMe> inspireMeList;
    View view;
    public InspiremeAdapter(){
        this.inspireMeList = new ArrayList<>();
    }

    public void addInspireMe(List<InspireMe> inspireMeList){
        for(InspireMe inspireMe : inspireMeList){
            this.inspireMeList.add(inspireMe);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InspiremeAdapter.InspiremeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_inspire_me,parent,false);
        return new InspiremeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspiremeAdapter.InspiremeViewHolder holder, int position) {
        InspireMe inspireMe = inspireMeList.get(position);
        holder.bind(inspireMe);
    }

    @Override
    public int getItemCount() {
        return inspireMeList.size();
    }

    public class InspiremeViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CircleImageView photoProfile;
        TextView username;
        TextView caption;
        Button button;

        public InspiremeViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.inspire_image);
            photoProfile = itemView.findViewById(R.id.inspire_photo_profile);
            username = itemView.findViewById(R.id.inspire_name);
            caption = itemView.findViewById(R.id.inspire_caption);
            button = itemView.findViewById(R.id.inspire_button_relate_product);
        }
        void bind(InspireMe inspireMe){
            Glide.with(photoProfile.getContext())
                    .load(ApiConfig.BASE_IMAGE_URL+inspireMe.getUser().getImageProfile())
                    .into(photoProfile);

            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(view.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(image.getContext())
                    .load(ApiConfig.BASE_IMAGE_URL+inspireMe.getImageUrl())
                    .placeholder(circularProgressDrawable)
                    .into(image);
            username.setText(inspireMe.getUser().getName());
            caption.setText(inspireMe.getCaption());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Product> products= new ArrayList<>();

                    for(InspiremeProducts product : inspireMe.getInspiremeproducts()){
                        ArrayList<Variant> variants= new ArrayList<>();
                        variants.add(product.getVariants());
                        product.getProduct().setVariantList(variants);
                        products.add(product.getProduct());
                    }
                    Intent intent = new Intent(itemView.getContext(), InspireMeRelateProductListActivity.class);
                    intent.putParcelableArrayListExtra("relateProducts",products);
                    intent.putExtra("image", inspireMe.getImageUrl());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
