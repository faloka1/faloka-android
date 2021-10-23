package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.R;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>{
    private List<OrderUser> orderUserList;

    public OrderProductAdapter(List<OrderUser> orderUserList){
        this.orderUserList = orderUserList;
    }

    @NonNull
    @Override
    public OrderProductAdapter.OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_profile_product_order,parent,false);
        return new OrderProductAdapter.OrderProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductAdapter.OrderProductViewHolder holder, int position) {
        OrderUser orderUser = orderUserList.get(position);
        if(orderUser.getOrderDetailList().isEmpty()){
            return;
        }
        if(orderUser.getOrderDetailList().get(0).getProduct() == null){
            return;
        }
        OrderDetail orderDetail = orderUser.getOrderDetailList().get(0);
        holder.tvOrderBrand.setText("GAPAHAM");
//        holder.tvOrderProductName.setText(orderDetail.getProduct().getName());
//        holder.tvOrderPrice.setText(String.valueOf(orderDetail.getProduct().getPrice()));
//        Glide.with(holder.imgOrderProduct.getContext())
//                .load(ApiConfig.BASE_IMAGE_URL + orderDetail.getProduct().getProductImageURL())
//                .into(holder.imgBtnDetailOrder);
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), orderDetail.getOrderID(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return orderUserList.size();
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView tvOrderBrand;
        TextView tvOrderProductName;
        TextView tvOrderPrice;
        ImageButton imgBtnDetailOrder;
        ImageView imgOrderProduct;
        View view;

        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            linearLayout = itemView.findViewById(R.id.linear_layout_detail);
            tvOrderBrand = itemView.findViewById(R.id.tv_order_brand);
            tvOrderProductName = itemView.findViewById(R.id.tv_order_name);
            tvOrderPrice = itemView.findViewById(R.id.tv_order_price);
            imgOrderProduct = itemView.findViewById(R.id.img_order_product);
            imgBtnDetailOrder = itemView.findViewById(R.id.img_btn_detail_order);
        }
    }
}
