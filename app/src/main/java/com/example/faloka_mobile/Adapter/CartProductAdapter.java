package com.example.faloka_mobile.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Cart.CartBrandToProductDelete;
import com.example.faloka_mobile.Cart.CartBrandToProductListener;
import com.example.faloka_mobile.Cart.CartCheckedProductListener;
import com.example.faloka_mobile.Cart.CartRepository;
import com.example.faloka_mobile.Cart.CartUpdateQtyListener;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>{
    List<Cart> cartList;
    private boolean isBrandChecked;
    private CartCheckedProductListener cartCheckedProductListener;
    private CartUpdateQtyListener cartUpdateQtyListener;
    private CartBrandToProductListener cartBrandToProductListener;
    private CartBrandToProductDelete cartBrandDelete;
    public CartProductAdapter(List<Cart> cartList, CartCheckedProductListener cartCheckedProductListener,
                              CartUpdateQtyListener cartUpdateQtyListener,
                              CartBrandToProductListener cartBrandToProductListener,
                              CartBrandToProductDelete cartBrandDelete){
        this.cartList = cartList;
        this.cartUpdateQtyListener = cartUpdateQtyListener;
        this.cartCheckedProductListener = cartCheckedProductListener;
        this.cartBrandToProductListener = cartBrandToProductListener;
        this.cartBrandDelete = cartBrandDelete;
    }

    public void setBrandChecked(boolean isBrandChecked){
        this.isBrandChecked = isBrandChecked;
    }

    @NonNull
    @Override
    public CartProductAdapter.CartProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cart_many_product,parent,false);
        return new CartProductAdapter.CartProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.CartProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = cartList.get(position);
        holder.tvCartProductName.setText(cart.getProduct().getName());

        if(!isBrandChecked){
            holder.cbxCartProduct.setChecked(false);
            cartCheckedProductListener.onCartProductChecked(cartList, false, cartList.get(position).getId() );
            holder.btnCartQtyPlus.setEnabled(false);
            holder.btnCartQtyMinus.setEnabled(false);
            cartBrandToProductListener.onCartBrandToProduct(cart.getId(), false);
        }
        else {
            holder.cbxCartProduct.setChecked(true);
            List<Cart> cartListTemp = new ArrayList<>();
            cartListTemp.add(cartList.get(position));
            cartCheckedProductListener.onCartProductChecked(cartListTemp, true, 0);
            holder.btnCartQtyPlus.setEnabled(true);
            holder.btnCartQtyMinus.setEnabled(true);
            cartBrandToProductListener.onCartBrandToProduct(cart.getId(), true);
        }

        Glide.with(holder.imgCartProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+cart.getVariant().getVariantImageList().get(0).getImageURL() )
                .into(holder.imgCartProduct);
        holder.tvCartProductSize.setText(cart.getVariant().getName());
        holder.tvCartProductPrice.setText(getFormatRupiah(cart.getProduct().getPrice()));
        holder.tvCartQty.setText(String.valueOf(cart.getQuantity()));
        holder.btnCartQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.getQuantity() > 1){
                    int qty = cart.getQuantity()-1;
                    cart.setQuantity(qty);
                    CartRepository.editCartQuantity(view, cart.getId(), qty);
                    cartUpdateQtyListener.onUpdateQtyCart(cart, qty);
                }
                holder.tvCartQty.setText(String.valueOf(cart.getQuantity() ));

            }
        });
        holder.btnCartQtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = cart.getQuantity()+1;
                cart.setQuantity(qty);
                CartRepository.editCartQuantity(view, cart.getId(), qty);
                cartUpdateQtyListener.onUpdateQtyCart(cart, qty);
                holder.tvCartQty.setText(String.valueOf(cart.getQuantity()));
            }
        });
        holder.btnCartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Apakah kamu yakin untuk menghapus?")
                        .setPositiveButton("Hapus",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CartRepository.deleteCart(view,cart.getId());
                                cartList.remove(cart);
                                notifyDataSetChanged();
                                if(cartList.size()==0){
                                    cartBrandDelete.onDelete();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
            }
        });
        holder.cbxCartProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    List<Cart> cartListTemp = new ArrayList<>();
                    cartListTemp.add(cartList.get(position));
                    cartCheckedProductListener.onCartProductChecked(cartListTemp, true, 0);
                    holder.btnCartQtyPlus.setEnabled(true);
                    holder.btnCartQtyMinus.setEnabled(true);
                    cartBrandToProductListener.onCartBrandToProduct(cart.getId(), true);
                }
                else {
                    cartCheckedProductListener.onCartProductChecked(cartList, false, cartList.get(position).getId() );
                    holder.btnCartQtyPlus.setEnabled(false);
                    holder.btnCartQtyMinus.setEnabled(false);
                    cartBrandToProductListener.onCartBrandToProduct(cart.getId(), false);
                }
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
        return cartList.size();
    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbxCartProduct;
        ImageView imgCartProduct;
        TextView tvCartProductName;
        TextView tvCartProductSize;
        TextView tvCartQty;
        TextView tvCartProductPrice;
        ImageButton btnCartQtyMinus;
        ImageButton btnCartQtyPlus;
        ImageButton btnCartDelete;

        public CartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cbxCartProduct = itemView.findViewById(R.id. cbx_cart_product);
            imgCartProduct = itemView.findViewById(R.id.img_cart_product);
            tvCartProductName = itemView.findViewById(R.id.tv_cart_product_name);
            tvCartProductSize = itemView.findViewById(R.id.tv_cart_product_size);
            tvCartQty = itemView.findViewById(R.id.tv_cart_qty);
            tvCartProductPrice = itemView.findViewById(R.id.tv_cart_product_price);
            btnCartQtyMinus = itemView.findViewById(R.id.btn_cart_qty_minus);
            btnCartQtyPlus = itemView.findViewById(R.id.btn_cart_qty_plus);
            btnCartDelete = itemView.findViewById(R.id.btn_cart_delete);
        }
    }
}
