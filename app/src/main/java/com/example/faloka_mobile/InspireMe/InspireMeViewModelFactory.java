package com.example.faloka_mobile.InspireMe;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class InspireMeViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile InspireMeViewModelFactory INSTANCE=null;
    private final InspireMeRepositry mInpireMeRepository;

    public InspireMeViewModelFactory(InspireMeRepositry checkoutRepository){
        mInpireMeRepository = checkoutRepository;
    }

    public static InspireMeViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (InspireMeViewModelFactory.class) {
                INSTANCE = new InspireMeViewModelFactory(InspireMeRepositry.getINSTANCE(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InspireMeViewModel.class)) {
            return (T) new InspireMeViewModel(mInpireMeRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
