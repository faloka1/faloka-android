package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Checkout.ConfirmCheckoutActivity;
import com.example.faloka_mobile.Checkout.DetailOrderActivity;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
        if(orderUser.getStatus().equals("pending")){
            holder.btnUploadPayment.setVisibility(View.GONE);
        }
        OrderDetail orderDetail = orderUser.getOrderDetailList().get(0);
        holder.tvOrderBrand.setText(orderDetail.getProduct().getBrand().getName());
        holder.tvOrderProductName.setText(orderDetail.getProduct().getName());
        int total = orderDetail.getProduct().getPrice() + orderUser.getShippingPrice() + 2000;
        holder.tvOrderPrice.setText(getFormatRupiah(total));
        Variant variant = orderDetail.getVariant();
        Glide.with(holder.imgOrderProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL + variant.getVariantImageList().get(0).getImageURL() )
                .into(holder.imgOrderProduct);
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), orderDetail.getOrderID(), Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.imgBtnDetailOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(OrderUser.EXTRA_ORDER_USER, orderUser);
                intent.putExtra("DATA_ORDER", bundle);
                view.getContext().startActivity(intent);
            }
        });

        holder.btnUploadPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ConfirmCheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(OrderUser.EXTRA_ORDER_USER, orderUser);
                intent.putExtra("DATA_ORDER",bundle);
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
        return orderUserList.size();
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView tvOrderBrand;
        TextView tvOrderProductName;
        TextView tvOrderPrice;
        ImageButton imgBtnDetailOrder;
        ImageView imgOrderProduct;
        Button btnUploadPayment;
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
            btnUploadPayment = itemView.findViewById(R.id.btn_upload_payment);
        }
    }
}
