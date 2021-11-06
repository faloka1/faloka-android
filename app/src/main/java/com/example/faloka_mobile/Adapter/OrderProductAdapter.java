package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Account.AccountRepository;
import com.example.faloka_mobile.Checkout.ConfirmCheckoutActivity;
import com.example.faloka_mobile.Checkout.DetailOrderActivity;
import com.example.faloka_mobile.Checkout.PaymentFragment;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderBrand;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        if(orderUser.getOrderBrandList().isEmpty()){
            return;
        }
        if(orderUser.getOrderBrandList().get(0).getOrderDetailList() == null){
            return;
        }
        if(orderUser.getStatus().equalsIgnoreCase("pending")){
            holder.btnUploadPayment.setVisibility(View.GONE);
        }
        OrderBrand orderBrand = orderUser.getOrderBrandList().get(0);
        int sumBrands = orderUser.getOrderBrandList().size();
        int sumProducts = orderBrand.getOrderDetailList().size();

        if(!orderBrand.getOrderDetailList().isEmpty()){
            OrderDetail orderDetail = orderBrand.getOrderDetailList().get(0);
            Product product = orderDetail.getProduct();
            Variant variant = orderDetail.getVariant();
            Glide.with(holder.imgProductOrderProduct.getContext())
                    .load(ApiConfig.BASE_IMAGE_URL + variant.getVariantImageList().get(0).getImageURL() )
                    .into(holder.imgProductOrderProduct);
            holder.tvProductOrderProductName.setText(product.getName());
            holder.tvProductOrderPrice.setText(getFormatRupiah(getTotalOrderPrice(orderUser)));
        }

        holder.tvProductOrderBrand.setText(orderBrand.getBrand().getName());
        String dateOrder = convertDate(orderUser.getCreateDate(), "dd MMMM yyyy", null);
        holder.tvProductOrderDate.setText(dateOrder);

        if(sumProducts > 1){
            holder.tvProductOrderAnotherProduct.setText("+"+String.valueOf(sumProducts-1)+" outfit lagi");
        }
        else {
            holder.tvProductOrderAnotherProduct.setVisibility(View.GONE);
        }

        if(sumBrands > 1){
            holder.tvProductOrderAnotherBrand.setText("+"+String.valueOf(sumBrands-1)+" brand lagi");
        }
        else {
            holder.tvProductOrderAnotherBrand.setVisibility(View.GONE);
        }


        holder.llProductOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Order.EXTRA_ORDER, AccountRepository.getOrder(orderUser));
                intent.putExtra("DATA_ORDER", bundle);
                view.getContext().startActivity(intent);
            }
        });

//        holder.imgBtnDetailOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), DetailOrderActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(OrderUser.EXTRA_ORDER_USER, orderUser);
//                intent.putExtra("DATA_ORDER", bundle);
//                view.getContext().startActivity(intent);
//            }
//        });

        holder.btnUploadPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ConfirmCheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Order.EXTRA_ORDER, AccountRepository.getOrder(orderUser));
                intent.putExtra("DATA_ORDER",bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    protected static String convertDate(Date date, String pattern, Locale locale) {
        String result = null;
        SimpleDateFormat formatter = null;
        if (locale == null) {
            formatter = new SimpleDateFormat(pattern);
        } else {
            formatter = new SimpleDateFormat(pattern, locale);
        }

        result = formatter.format(date);
        return result;
    }

    public int getTotalOrderPrice(OrderUser orderUser){
        int sumExpeditionPrice = 0;
        int sumProductPrice = 0;
        for(OrderBrand orderBrand : orderUser.getOrderBrandList()){
            sumExpeditionPrice += orderBrand.getOrderShipping().getShippingPrice();
            for (OrderDetail orderDetail : orderBrand.getOrderDetailList()){
                sumProductPrice += orderDetail.getProduct().getPrice();
            }
        }
        return (sumExpeditionPrice + sumProductPrice + 2000);
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

        LinearLayout llProductOrderDetail;
        TextView tvProductOrderBrand;
        TextView tvProductOrderProductName;
        TextView tvProductOrderPrice;
        ImageView imgProductOrderProduct;
        TextView tvProductOrderDate;
        TextView tvProductOrderAnotherProduct;
        TextView tvProductOrderAnotherBrand;
        Button btnUploadPayment;
        ConstraintLayout clProductOrderAnotherBrand;
        View view;

        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            llProductOrderDetail = itemView.findViewById(R.id.ll_product_order_detail);
            tvProductOrderBrand = itemView.findViewById(R.id.tv_product_order_brand);
            tvProductOrderProductName = itemView.findViewById(R.id.tv_product_order_name);
            tvProductOrderPrice = itemView.findViewById(R.id.tv_product_order_price);
            imgProductOrderProduct = itemView.findViewById(R.id.img_product_order_product);
            btnUploadPayment = itemView.findViewById(R.id.btn_upload_payment);
            tvProductOrderDate = itemView.findViewById(R.id.tv_product_order_date);
            tvProductOrderAnotherProduct = itemView.findViewById(R.id.tv_product_order_another_product);
            tvProductOrderAnotherBrand = itemView.findViewById(R.id.tv_product_order_another_brand);
            clProductOrderAnotherBrand = itemView.findViewById(R.id.cl_product_order_another_brand);
        }
    }
}
