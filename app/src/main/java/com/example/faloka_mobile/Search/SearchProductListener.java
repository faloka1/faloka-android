package com.example.faloka_mobile.Search;

import com.example.faloka_mobile.Model.ProductListResponse;

public interface SearchProductListener {
    public void onSearchProduct(ProductListResponse productListResponse, String text);
}
