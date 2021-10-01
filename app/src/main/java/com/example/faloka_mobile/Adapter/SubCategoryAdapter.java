package com.example.faloka_mobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Model.SubCategory;
import com.example.faloka_mobile.Product_List.ProductListActivity;
import com.example.faloka_mobile.R;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    List<SubCategory> subCategories;
    LayoutInflater inflater;
    Context context;

    public SubCategoryAdapter(Context context, List<SubCategory> subCategories){
        this.context = context;
        this.subCategories = subCategories;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_subcategory , parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);
//        holder.imgSubCategory.setImageResource(subCategory.getImage().getPosition() );
        holder.tvSubCategory.setText(subCategory.getName());
        holder.cvSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), holder.tvSubCategory.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ProductListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSubCategory;
        TextView tvSubCategory;
        CardView cvSubCategory;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cvSubCategory = itemView.findViewById(R.id.cv_subcategory);
            imgSubCategory = itemView.findViewById(R.id.img_subcategory);
            tvSubCategory = itemView.findViewById(R.id.tv_subcategory_name);

        }
    }
}
