package com.example.faloka_mobile.Cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.faloka_mobile.Adapter.CartBrandAdapter;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.MixAndMatch.MixMatchActivity;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCartBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CartViewModel implements
        CartItemListener,
        CartCheckedProductListener,
        CartProductsRelated,
        CartUpdateQtyListener,
        CartAllToBrandListener, CountPriceListener{

    private AppCompatActivity activity;
    private ActivityCartBinding binding;
    private View view;
    private List<Cart> checkedCartProduct;
    private HashMap<Integer, Boolean> cartBooleanHashMap = new HashMap<>();
    private CartBrandAdapter cartBrandAdapter;
    private List<CartBrand> cartBrandList;

    public CartViewModel(ActivityCartBinding binding, AppCompatActivity activity){
        this.activity = activity;
        this.binding = binding;
        view = binding.getRoot();
        checkedCartProduct = new ArrayList<>();
        binding.cbxSelectAll.setChecked(true);
        CartRepository.getCarts(binding.getRoot(), this::onCart);
        CartRepository.getProductsRelated(binding.getRoot(), this::onProductsRelated);
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

    public void onButtonMixMatchClicked(){
        binding.btnCartMixMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkedCartProduct.isEmpty() && checkedCartProduct != null) {
                    Intent intent = new Intent(binding.getRoot().getContext(), MixMatchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList) checkedCartProduct);
                    intent.putExtras(bundle);
                    binding.getRoot().getContext().startActivity(intent);
                }
                else {
                    Snackbar snackbar = Snackbar.make(binding.coordinatorLayoutTopCart, "Pilih outfitnya dulu ya", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    snackbar.setActionTextColor(activity.getResources().getColor(R.color.primary_dark));
                    snackbar.setTextColor(activity.getResources().getColor(R.color.primary_dark));
                    snackbar.setBackgroundTint(activity.getResources().getColor(R.color.semantic_warning));
                    snackbar.show();

                }
            }
        });
    }
    public void onDeleteAll(){
        binding.btnCartDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkedCartProduct.isEmpty() && checkedCartProduct != null) {
                    new AlertDialog.Builder(activity)
                            .setMessage("Apakah yakin ingin menghapus item?")
                            .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    for(Cart cart : checkedCartProduct){
                                        CartRepository.deleteCart(view, cart.getId());
                                    }
                                    CartRepository.getCarts(view, new CartItemListener() {
                                        @Override
                                        public void onCart(List<Cart> cartList) {
                                            if(cartList.size()==0){
                                                emptyCart(true);
                                            }
                                            else {
                                                CartViewModel cartViewModel = new CartViewModel(binding, activity);
                                                cartViewModel.setToolbar();
                                                cartViewModel.onButtonMixMatchClicked();
                                                cartViewModel.onDeleteAll();
                                            }
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
                else {
                    Snackbar snackbar = Snackbar.make(binding.coordinatorLayoutTopCart, "Pilih outfitnya dulu ya", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    snackbar.setActionTextColor(activity.getResources().getColor(R.color.primary_dark));
                    snackbar.setTextColor(activity.getResources().getColor(R.color.primary_dark));
                    snackbar.setBackgroundTint(activity.getResources().getColor(R.color.semantic_warning));
                    snackbar.show();

                }
            }
        });
    }
    public void emptyCart(boolean empty){
        if(empty){
            view.getContext().startActivity(new Intent(activity,CartEmptyActivity.class));
        }
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    @Override
    public void onCart(List<Cart> cartList) {
        cartBrandList = CartActivity.brandClassification(cartList);
        initCartBooleanHashMap(cartBrandList);
        cartBrandAdapter = new CartBrandAdapter(
                cartBrandList,
                this::onCartProductChecked, this::onUpdateQtyCart,
                this::onCartAllToBrand, this::onCount);
        cartBrandAdapter.setAllChecked(true);
        binding.rvCartBrandProduct.setAdapter(cartBrandAdapter);
        binding.rvCartBrandProduct.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.cbxSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.cbxSelectAll.isChecked()){
                    cartBrandAdapter.setAllChecked(true);
                }
                else {
                    cartBrandAdapter.setAllChecked(false);
                    checkedCartProduct.clear();
                }
                binding.rvCartBrandProduct.setAdapter(cartBrandAdapter);
                binding.rvCartBrandProduct.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
        });

    }

    public void initCartBooleanHashMap(List<CartBrand> cartBrandList){
        for(CartBrand cartBrand : cartBrandList){
            this.cartBooleanHashMap.put(cartBrand.getBrand().getId(), true);
        }
    }
    @Override
    public void onCartAllToBrand(int brandID, boolean checked) {
        boolean isAllChecked = true;
        Set set = cartBooleanHashMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry map = (Map.Entry) iterator.next();
            int tempBrandID = (int) map.getKey();
            if(tempBrandID == brandID){
                cartBooleanHashMap.put(brandID, checked);
                break;
            }
        }

        int counter = 0;
        Iterator it = set.iterator();
        while (it.hasNext()){
            Map.Entry map = (Map.Entry) it.next();
            boolean validBrandChecked = (boolean) map.getValue();
            if(validBrandChecked == true){
                counter++;
            }
        }
        if(counter == cartBooleanHashMap.size()){
            isAllChecked = true;
        }
        else {
            isAllChecked = false;
        }
        binding.cbxSelectAll.setChecked(isAllChecked);
    }

    @Override
    public void onCartProductChecked(List<Cart> productList, boolean mode, int cartID) {
        if(mode){
            for(Cart cart : productList){
                boolean valid = true;
                for(Cart cartChecked : checkedCartProduct){
                    if(cart.getId() == cartChecked.getId()){
                        valid = false;
                    }
                }
                if (valid){
                    this.checkedCartProduct.add(cart);
                }
            }
        }
        else {
            for(int i=0; i<this.checkedCartProduct.size(); i++){
                Cart cartChecked = checkedCartProduct.get(i);
                if(cartChecked.getId() == cartID){
                    checkedCartProduct.remove(i);
                }
            }
        }
        setTotalProduct(checkedCartProduct);
        setFooterCart(checkedCartProduct);
    }

    public void setFooterCart(List<Cart> cartList){
        int total = CartActivity.getTotal(cartList);
        binding.footerCartCheckout.btnCheckoutNext.setEnabled(true);
        binding.footerCartCheckout.btnCheckoutNext.setBackgroundColor(activity.getResources().getColor(R.color.netral_900));
        binding.footerCartCheckout.btnCheckoutNext.setTextColor(activity.getResources().getColor(R.color.white));
        binding.footerCartCheckout.tvCartTotalCart.setText(getFormatRupiah(total));
        binding.footerCartCheckout.btnCheckoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkedCartProduct!=null) {
                    Intent intent = new Intent(binding.getRoot().getContext(), CheckoutActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList) checkedCartProduct);
                    intent.putExtras(bundle);
                    binding.getRoot().getContext().startActivity(intent);
                }
                else {
                    Snackbar.make(binding.getRoot(), "Cart is empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onProductsRelated(List<Product> productList) {
        ProductAdapter productAdapter = new ProductAdapter(productList);
        binding.rvProductsRelated.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(),2, GridLayoutManager.VERTICAL, false));
        binding.rvProductsRelated.setAdapter(productAdapter);
    }

    @Override
    public void onUpdateQtyCart(Cart cart, int qty) {
        for(Cart checkedCart : checkedCartProduct){
            if(cart.getId() == checkedCart.getId()){
                checkedCart.setQuantity(qty);
            }
        }
        setTotalProduct(checkedCartProduct);
        setFooterCart(checkedCartProduct);
    }
    @Override
    public void onCount(Cart cart) {
        checkedCartProduct.remove(cart);
        setTotalProduct(checkedCartProduct);
        setFooterCart(checkedCartProduct);
    }
}
