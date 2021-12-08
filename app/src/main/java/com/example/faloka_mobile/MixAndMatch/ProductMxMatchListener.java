package com.example.faloka_mobile.MixAndMatch;

import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductMixMatch;

import java.util.List;

public interface ProductMxMatchListener {
    public void onProduct(List<ProductMixMatch> productList);
}
