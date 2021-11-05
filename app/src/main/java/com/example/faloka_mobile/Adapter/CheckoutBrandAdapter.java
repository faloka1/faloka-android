package com.example.faloka_mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Checkout.ChooseDeliveryDialog;
import com.example.faloka_mobile.Checkout.DeliveryListener;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.R;

import java.util.List;

public class CheckoutBrandAdapter extends RecyclerView.Adapter<CheckoutBrandAdapter.CheckoutBrandViewHolder>{

    private List<CartBrand> cartBrandList;
    private Context context;
    public CheckoutBrandAdapter(List<CartBrand> cartBrandList){
        this.cartBrandList = cartBrandList;
    }

    @NonNull
    @Override
    public CheckoutBrandAdapter.CheckoutBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_checkout_many_brand,parent,false);
        context = view.getContext();

        final CheckoutBrandViewHolder checkoutBrandViewHolder = new CheckoutBrandViewHolder(view);
        checkoutBrandViewHolder.tvCheckoutDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDeliveryDialog chooseDeliveryDialog = new ChooseDeliveryDialog(checkoutBrandViewHolder::onDelivery);
                chooseDeliveryDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "haha");
                Toast.makeText(context, "TEST: "+String.valueOf(checkoutBrandViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        });
        return checkoutBrandViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutBrandAdapter.CheckoutBrandViewHolder holder, int position) {
        CartBrand cartBrand = cartBrandList.get(position);
        holder.tvCheckoutBrandName.setText(cartBrand.getBrand().getName());
        CheckoutProductAdapter checkoutProductAdapter = new CheckoutProductAdapter(cartBrand.getCartList());
        holder.rvCheckoutProduct.setAdapter(checkoutProductAdapter);
        holder.rvCheckoutProduct.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
//        holder.tvCheckoutDelivery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(holder.itemView.getContext(), "BISA DONG", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, DeliveryChooseActivity.class);
//                intent.putExtra("EXTRA_I_DELIVERY", new DeliveryListener() {
//                    @Override
//                    public void onDelivery(Courier courier, CourierService courierService) {
//                        Toast.makeText(view.getContext(), "BISA NIHHHH!!!!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                ModelDeliveryListener modelDeliveryListener = new ModelDeliveryListener(new DeliveryListener() {
//                    @Override
//                    public void onDelivery(Courier courier, CourierService courierService) {
//
//                    }
//                });
//                intent.putExtra("EXTRA_I_DELIVERY", new ModelDeliveryListener(){
//                    @Override
//                    public void onDelivery(Courier courier, CourierService courierService) {
//                        Toast.makeText(view.getContext(), "BISA NIHHHH!!!!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                ModelDeliveryListener modelDeliveryListener = new ModelDeliveryListener();
//                modelDeliveryListener.setDeliveryListener(this::);
//                view.getContext().startActivity(intent);
//                ((Activity)context).startActivityForResult(intent, DeliveryFragment.REQUEST_CHOOSE_DELIVERY);
//            }
//        });
//        holder.tvCheckoutDelivery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ChooseDeliveryDialog chooseDeliveryDialog = new ChooseDeliveryDialog();
//                chooseDeliveryDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "haha");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return this.cartBrandList.size();
    }

    public class CheckoutBrandViewHolder extends RecyclerView.ViewHolder implements DeliveryListener{

        TextView tvCheckoutBrandName;
        RecyclerView rvCheckoutProduct;
        TextView tvCheckoutDeliveryPrice;
        TextView tvCheckoutDelivery;
        public CheckoutBrandViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCheckoutBrandName = itemView.findViewById(R.id.tv_checkout_brand_name);
            rvCheckoutProduct = itemView.findViewById(R.id.rv_checkout_product);
            tvCheckoutDeliveryPrice = itemView.findViewById(R.id.tv_checkout_delivery_price);
            tvCheckoutDelivery = itemView.findViewById(R.id.tv_checkout_delivery);
        }

        @Override
        public void onDelivery(Courier courier, CourierService courierService, String text) {
            Toast.makeText(context, "MASUK DUNG!!!!!!!!!!!!!!!!!!!!!!!!!"+ text, Toast.LENGTH_SHORT).show();
            tvCheckoutBrandName.setText(text);
        }
    }

}
