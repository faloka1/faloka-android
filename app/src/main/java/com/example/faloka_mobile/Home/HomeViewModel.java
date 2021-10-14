package com.example.faloka_mobile.Home;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Model.Category;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private HomeRepository mHomeRepository;

    public HomeViewModel(HomeRepository homeRepository){
        mHomeRepository = homeRepository;
    }

    public LiveData<List<Category>> getCategories(){
        return mHomeRepository.getCategory();
    }
}
