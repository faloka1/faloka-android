package com.example.faloka_mobile.Product;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Model.Product;

import java.util.List;

public class ProductViewModel extends ViewModel {

    private final ProductRepository mProductRepository;

    public ProductViewModel(ProductRepository productRepository){
        mProductRepository = productRepository;
    }

}
