package com.example.faloka_mobile.Cart;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.faloka_mobile.Adapter.CartBrandAdapter;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Product;
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
    private List<Product> checkedCartProduct;

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

    public void setTotalProduct(List<Product> productList){
        int total = CartActivity.getTotal(productList);
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
    public void onCartProductChecked(List<Product> productList, boolean mode, String slug) {
        if(mode){
            for(Product product : productList){
                this.checkedCartProduct.add(product);
            }
        }
        else {
            for(int i=0; i<this.checkedCartProduct.size(); i++){
                Product productChecked = checkedCartProduct.get(i);
                if(productChecked.getSlug().equals(slug)){
                    checkedCartProduct.remove(i);
                    System.out.println("REMOVe: "+productChecked.getSlug());
                }
            }
        }
        for(Product product : this.checkedCartProduct){
            System.out.println("\n"+mode+" CHECKED: "+product.getName()+" "+product.getId());
        }
        setTotalProduct(checkedCartProduct);
        setFooterCart(checkedCartProduct);
    }

    public void setFooterCart(List<Product> productList){
        int total = CartActivity.getTotal(productList);
        binding.footerCartCheckout.btnCheckoutNext.setEnabled(true);
        binding.footerCartCheckout.btnCheckoutNext.setBackgroundResource(R.color.netral_900);
        binding.footerCartCheckout.btnCheckoutNext.setTextColor(activity.getResources().getColor(R.color.white));
        binding.footerCartCheckout.tvCartTotalCart.setText(getFormatRupiah(total));
        binding.footerCartCheckout.btnCheckoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
