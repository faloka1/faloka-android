package com.example.faloka_mobile.Product;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ProductViewModelFactory INSTANCE=null;
    private final ProductRepository mProductRepository;

    public ProductViewModelFactory(ProductRepository productRepository){
        mProductRepository = productRepository;
    }

    public static ProductViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ProductViewModelFactory.class) {
                INSTANCE = new ProductViewModelFactory(ProductRepository.getINSTANCE(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            //noinspection unchecked
            return (T) new ProductViewModel(mProductRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
