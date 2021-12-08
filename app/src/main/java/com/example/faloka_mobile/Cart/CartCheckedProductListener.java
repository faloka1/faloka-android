package com.example.faloka_mobile.Cart;

import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;

import java.util.List;

public interface CartCheckedProductListener {
    public void onCartProductChecked(List<Cart> productList, boolean mode, int cartID);
}
