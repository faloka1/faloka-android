package com.example.faloka_mobile.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.faloka_mobile.Adapter.CartBrandAdapter;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCartBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartViewModel implements CartItemListener, CartCheckedProductListener{

    private AppCompatActivity activity;
    private ActivityCartBinding binding;
    private View view;
    private List<Cart> checkedCartProduct;

    public CartViewModel(ActivityCartBinding binding, AppCompatActivity activity){
        this.activity = activity;
        this.binding = binding;
        view = binding.getRoot();
        checkedCartProduct = new ArrayList<>();
        CartRepository.getCarts(binding.getRoot(), this::onCart);
    }

    public void setToolbar(){
        activity.setSupportActionBar(binding.cartToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Tas");
    }

    public void setTotalProduct(List<Cart> cartList){
        int total = CartActivity.getTotal(cartList);
        binding.tvCartTotalProduct.setText(getFormatRupiah(total));
        binding.tvCartSubTotal.setText(getFormatRupiah(total));

    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    @Override
    public void onCart(List<Cart> cartList) {
        List<CartBrand> cartBrandList = CartActivity.brandClassification(cartList);
        CartBrandAdapter cartBrandAdapter = new CartBrandAdapter(cartBrandList, this::onCartProductChecked);
        cartBrandAdapter.setAllChecked(true);
        binding.rvCartBrandProduct.setAdapter(cartBrandAdapter);
        binding.rvCartBrandProduct.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.cbxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    cartBrandAdapter.setAllChecked(true);
                }
                else {
                    cartBrandAdapter.setAllChecked(false);
                }
                binding.rvCartBrandProduct.setAdapter(cartBrandAdapter);
                binding.rvCartBrandProduct.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
        });
    }

    @Override
    public void onCartProductChecked(List<Cart> productList, boolean mode, int cartID) {
        if(mode){
            for(Cart cart : productList){
                this.checkedCartProduct.add(cart);
            }
        }
        else {
            for(int i=0; i<this.checkedCartProduct.size(); i++){
                Cart cartChecked = checkedCartProduct.get(i);
                if(cartChecked.getId() == cartID){
                    checkedCartProduct.remove(i);
                }
//                Product productChecked = checkedCartProduct.get(i);
//                if(productChecked.getSlug().equals(slug)){
//                    checkedCartProduct.remove(i);
//                    System.out.println("REMOVe: "+productChecked.getSlug());
//                }
            }
        }
        for(Cart cart : this.checkedCartProduct){
            System.out.println("\n"+mode+" CHECKED: "+cart.getProduct().getName()+" "+cart.getId());
        }
        setTotalProduct(checkedCartProduct);
        setFooterCart(checkedCartProduct);
    }

    public void setFooterCart(List<Cart> cartList){
        int total = CartActivity.getTotal(cartList);
        binding.footerCartCheckout.btnCheckoutNext.setEnabled(true);
        binding.footerCartCheckout.btnCheckoutNext.setBackgroundResource(R.color.netral_900);
        binding.footerCartCheckout.btnCheckoutNext.setTextColor(activity.getResources().getColor(R.color.white));
        binding.footerCartCheckout.tvCartTotalCart.setText(getFormatRupiah(total));
        binding.footerCartCheckout.btnCheckoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(binding.getRoot().getContext(), CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList)checkedCartProduct);
                intent.putExtras(bundle);
                binding.getRoot().getContext().startActivity(intent);
            }
        });
    }

}
