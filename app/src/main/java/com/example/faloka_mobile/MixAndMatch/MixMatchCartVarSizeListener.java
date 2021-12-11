package com.example.faloka_mobile.MixAndMatch;

import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.VariantSize;

import java.util.List;

public interface MixMatchCartVarSizeListener {
    public void onMixMatchCartVarSize(Cart cart, Product product);
}
