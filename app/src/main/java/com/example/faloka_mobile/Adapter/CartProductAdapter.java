package com.example.faloka_mobile.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Cart.CartCheckedProductListener;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>{
    List<Product> productList;
    List<Product> checkedProductList;
//    List<Integer> quantityList;
    private boolean isBrandChecked;
    private CartCheckedProductListener cartCheckedProductListener;
//    private static int qty = 1;

    public CartProductAdapter(List<Cart> cartList, CartCheckedProductListener cartCheckedProductListener){
//        this.productList = productList;
        initProductList(cartList);
//        this.quantityList = quantityList;
        this.cartCheckedProductListener = cartCheckedProductListener;
        this.checkedProductList = new ArrayList<>(productList);
        cartCheckedProductListener.onCartProductChecked(checkedProductList, true, "");
    }

    public void initProductList(List<Cart> cartList){
        productList = new ArrayList<>();
        for(Cart cart : cartList){
            Product product = cart.getProduct();
            List<Variant> variantList = new ArrayList<>();
            Variant variant = cart.getVariant();
            variantList.add(variant);
            product.setVariantList(variantList);
            product.setQuantity(cart.getQuantity());
            this.productList.add(product);
        }
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
        Product product = productList.get(position);
        holder.tvCartProductName.setText(product.getName());
        holder.cbxCartProduct.setChecked(isBrandChecked);
        Glide.with(holder.imgCartProduct.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+product.getProductImageURL() )
                .into(holder.imgCartProduct);
        holder.tvCartProductSize.setText(product.getSizeProduct());
        holder.tvCartProductPrice.setText(getFormatRupiah(product.getPrice()));
//        holder.tvCartQty.setText(String.valueOf(quantityList.get(position)));
        holder.tvCartQty.setText(String.valueOf(product.getQuantity()));
        //        cartCheckedProductListener.onCartProductChecked(checkedProductList);
        holder.btnCartQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getQuantity() > 1){
                    product.setQuantity(product.getQuantity()-1);
//                    quantityList.set(position, quantityList.get(position)-1);
                }
                holder.tvCartQty.setText(String.valueOf(product.getQuantity() ));
                Toast.makeText(holder.itemView.getContext(), "MINUS", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnCartQtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                quantityList.set(position, quantityList.get(position)+1);
                product.setQuantity(product.getQuantity()+1);
                holder.tvCartQty.setText(String.valueOf(product.getQuantity()));
                Toast.makeText(holder.itemView.getContext(), "PLUS", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cbxCartProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    List<Product> productListTemp = new ArrayList<>();
                    productListTemp.add(productList.get(position));
                    cartCheckedProductListener.onCartProductChecked(productListTemp, true, "");
                }
                else {
                    cartCheckedProductListener.onCartProductChecked(productList, false, productList.get(position).getSlug());
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
        return productList.size();
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
        }
    }
}
