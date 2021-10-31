package com.example.faloka_mobile.Cart;

import com.example.faloka_mobile.Model.Product;

import java.util.List;

public interface CartCheckedProductListener {
    public void onCartProductChecked(List<Product> productList, boolean mode, String slug);
}
