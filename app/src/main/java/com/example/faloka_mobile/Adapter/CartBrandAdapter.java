package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Cart.CartAllToBrandListener;
import com.example.faloka_mobile.Cart.CartBrandToProductDelete;
import com.example.faloka_mobile.Cart.CartBrandToProductListener;
import com.example.faloka_mobile.Cart.CartCheckedProductListener;
import com.example.faloka_mobile.Cart.CartCountItemListener;
import com.example.faloka_mobile.Cart.CartDeleteListener;
import com.example.faloka_mobile.Cart.CartEmptyActivity;
import com.example.faloka_mobile.Cart.CartUpdateQtyListener;
import com.example.faloka_mobile.Cart.CountPriceListener;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CartBrandAdapter extends RecyclerView.Adapter<CartBrandAdapter.CartBrandViewHolder>{

    private List<CartBrand> cartBrandList;
    private boolean isAllChecked;
    private CartCheckedProductListener cartCheckedProductListener;
    private CartUpdateQtyListener cartUpdateQtyListener;
    private CartAllToBrandListener cartAllToBrandListener;
    private CountPriceListener countPriceListener;

    public CartBrandAdapter(List<CartBrand> cartBrandList,
                            CartCheckedProductListener cartCheckedProductListener,
                            CartUpdateQtyListener cartUpdateQtyListener,
                            CartAllToBrandListener cartAllToBrandListener,
                            CountPriceListener countPriceListener){
        this.cartBrandList = cartBrandList;
        this.cartCheckedProductListener = cartCheckedProductListener;
        this.cartUpdateQtyListener = cartUpdateQtyListener;
        this.cartAllToBrandListener = cartAllToBrandListener;
        this.countPriceListener = countPriceListener;

    }

    public void setAllChecked(boolean isAllChecked){
        this.isAllChecked = isAllChecked;
    }

    @NonNull
    @Override
    public CartBrandAdapter.CartBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cart_many_brand,parent,false);
        return new CartBrandAdapter.CartBrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartBrandAdapter.CartBrandViewHolder holder, int position) {
        CartBrand cartBrand = cartBrandList.get(position);
        holder.initCartBrand(cartBrand);
        holder.initCartBooleanHashMap();
        holder.cbxCartBrandName.setText(cartBrand.getBrand().getName());
        CartProductAdapter cartProductAdapter = new CartProductAdapter(
                cartBrand.getCartList(), cartCheckedProductListener,
                cartUpdateQtyListener,holder::onCartBrandToProduct,holder::onDelete);
        cartProductAdapter.setBrandChecked(isAllChecked);
        holder.rvCartBrand.setAdapter(cartProductAdapter);
        holder.rvCartBrand.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.cbxCartBrandName.setChecked(isAllChecked);
        if(cartAllToBrandListener!=null) {
            if (isAllChecked) {
                cartAllToBrandListener.onCartAllToBrand(cartBrand.getBrand().getId(), true);
            } else {
                cartAllToBrandListener.onCartAllToBrand(cartBrand.getBrand().getId(), false);
            }
        }
        holder.cbxCartBrandName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    cartAllToBrandListener.onCartAllToBrand(cartBrand.getBrand().getId(), true);
                }
                else {
                    cartAllToBrandListener.onCartAllToBrand(cartBrand.getBrand().getId(), false);
                }
            }
        });
        holder.cbxCartBrandName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cbxCartBrandName.isChecked()){
                    cartProductAdapter.setBrandChecked(true);
                    cartAllToBrandListener.onCartAllToBrand(cartBrand.getBrand().getId(), true);
                }
                else {
                    cartProductAdapter.setBrandChecked(false);
                    cartAllToBrandListener.onCartAllToBrand(cartBrand.getBrand().getId(), false);
                }
                holder.rvCartBrand.setAdapter(cartProductAdapter);
                holder.rvCartBrand.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.cartBrandList.size();
    }


    public class CartBrandViewHolder extends RecyclerView.ViewHolder
            implements CartBrandToProductListener, CartBrandToProductDelete {

        RecyclerView rvCartBrand;
        CheckBox cbxCartBrandName;
        boolean isBrandChecked;
        private HashMap<Integer, Boolean> cartBooleanHashMap = new HashMap<>();
        CartBrand cartBrand;


        public CartBrandViewHolder(@NonNull View itemView) {
            super(itemView);
            rvCartBrand = itemView.findViewById(R.id.rv_cart_brand);
            cbxCartBrandName = itemView.findViewById(R.id.cbx_cart_brand_name);
            cbxCartBrandName.setChecked(true);
            isBrandChecked = true;
        }
        public void  initCartBrand(CartBrand cartBrand){
            this.cartBrand = cartBrand;
        }
        public void initCartBooleanHashMap(){
            for (Cart cart : cartBrand.getCartList()){
                cartBooleanHashMap.put(cart.getId(), true);
            }
        }
        @Override
        public void onDelete() {
            cartBrandList.remove(cartBrand);
            if(cartBrandList.size()==0){
                itemView.getContext().startActivity(new Intent(itemView.getContext(), CartEmptyActivity.class));
            }
            else{
                notifyDataSetChanged();
            }
        }
        @Override
        public void onCartBrandToProduct(int cartID, boolean checked) {
            Set set = cartBooleanHashMap.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                Map.Entry map = (Map.Entry) iterator.next();
                int tempCartID = (int) map.getKey();
                if(tempCartID == cartID){
                    cartBooleanHashMap.put(cartID, checked);
                    break;
                }
            }

            int counter = 0;
            Iterator it = set.iterator();
            while (it.hasNext()){
                Map.Entry map = (Map.Entry) it.next();
                boolean validCartChecked = (boolean) map.getValue();
                if(validCartChecked == true){
                    counter++;
                }
            }
            if(counter == cartBooleanHashMap.size()){
                isBrandChecked = true;
            }
            else {
                isBrandChecked = false;
            }
            cbxCartBrandName.setChecked(isBrandChecked);
        }


    }
}
