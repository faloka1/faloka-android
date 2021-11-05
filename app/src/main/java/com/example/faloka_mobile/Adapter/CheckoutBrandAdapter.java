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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
                chooseDeliveryDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), null);
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
        public void onDelivery(Courier courier, CourierService courierService) {
            tvCheckoutDelivery.setText(courier.getName()+" "+courierService.getName());
            tvCheckoutDeliveryPrice.setText(getFormatRupiah(courierService.getCost().get(0).getValue()));
        }
    }
    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

}
