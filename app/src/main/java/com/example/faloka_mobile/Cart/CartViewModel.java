package com.example.faloka_mobile.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Adapter.CartBrandAdapter;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Checkout.CheckoutActivity;
import com.example.faloka_mobile.Checkout.ChooseDeliveryDialog;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityCartBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartViewModel implements CartItemListener, CartCheckedProductListener, CartProductsRelated{

    private AppCompatActivity activity;
    private ActivityCartBinding binding;
    private View view;
    private List<Cart> checkedCartProduct;

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
                    List<Cart> tempCart = new ArrayList<>(checkedCartProduct);
                    for (int i=0; i<cartList.size(); i++){
                        if(!isOnCart(cartList.get(i))){
                            checkedCartProduct.add(cartList.get(i));
                            System.out.println("HAHA");
                        }
                    }
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

    boolean isOnCart(Cart cart){
        boolean isThere = false;
        List<Cart> tempCart = new ArrayList<>(checkedCartProduct);
        for(Cart cartItem : tempCart){
            if(cartItem.getId() == cart.getId()){
                isThere = true;
                break;
            }
        }
        return isThere;
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
        binding.footerCartCheckout.btnCheckoutNext.setBackgroundColor(activity.getResources().getColor(R.color.netral_900));
//        binding.footerCartCheckout.btnCheckoutNext.setBackgroundResource(R.color.netral_900);
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
//                    ChooseDeliveryDialog chooseDeliveryDialog = new ChooseDeliveryDialog();
//                    chooseDeliveryDialog.show(activity.getSupportFragmentManager(), null);
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

//    public void setProductsRelated()

//    public List<Product> initProductList(List<Cart> cartList){
//        List<Product> productList = new ArrayList<>();
//        for(Cart cart : cartList){
//            Product product = cart.getProduct();
//            product.setQuantity(cart.getQuantity());
//            List<Variant> variantList = new ArrayList<>();
//            variantList.add(cart.getVariant());
//            product.setVariantList(variantList);
//            productList.add(product);
//            System.out.println("PSPSP: "+product.getQuantity()+" VS "+cart.getQuantity());
//        }
////        for(Product product : productList){
////        }
//        return productList;
//    }

}
