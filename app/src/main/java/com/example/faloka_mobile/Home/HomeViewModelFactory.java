package com.example.faloka_mobile.Home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile HomeViewModelFactory INSTANCE=null;
    private final HomeRepository mHomeRepository;

    public HomeViewModelFactory(HomeRepository homeRepository){
        mHomeRepository = homeRepository;
    }

    public static HomeViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (HomeViewModelFactory.class) {
                INSTANCE = new HomeViewModelFactory(HomeRepository.getINSTANCE(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mHomeRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
