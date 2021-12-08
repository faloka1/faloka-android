package com.example.faloka_mobile.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.faloka_mobile.Adapter.CartBrandAdapter;
import com.example.faloka_mobile.Adapter.OrderProductAdapter;
import com.example.faloka_mobile.Model.Brand;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCartBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class CartActivity extends AppCompatActivity{
    private ActivityCartBinding binding;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        CartViewModel cartViewModel = new CartViewModel(binding, this);
        cartViewModel.setToolbar();
        cartViewModel.onButtonMixMatchClicked();
        cartViewModel.onDeleteAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem item = menu.findItem(R.id.top_menu_cart);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
//            case R.id.top_menu_wishlist:
//                Toast.makeText(getApplicationContext(), "WISHLIST", Toast.LENGTH_SHORT).show();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static final List<CartBrand> brandClassification(List<Cart> cartList){
        List<CartBrand> cartBrandList = new ArrayList<>();
        HashSet<Brand> brandNames = new HashSet<>();

        for(Cart cart : cartList){
            brandNames.add(cart.getProduct().getBrand());
        }
        Iterator it = brandNames.iterator();
        while (it.hasNext()){
            Brand brand = (Brand) it.next();
            CartBrand cartBrand = new CartBrand();
            List<Cart> cartListTemp = new ArrayList<>();
            cartBrand.setBrand(brand);
            for(Cart cart : cartList){
                if(brand.getName().equals(cart.getProduct().getBrand().getName())){
                    cartListTemp.add(cart);
                }
            }
            cartBrand.setCartList(cartListTemp);
            cartBrandList.add(cartBrand);
        }
        return cartBrandList;
    }

    public static final int getTotal(List<Cart> cartList){
        int total = 0;
        for(Cart cart : cartList){
            int sub = cart.getQuantity() * cart.getProduct().getPrice();
            total += sub;
        }
        return total;
    }

}