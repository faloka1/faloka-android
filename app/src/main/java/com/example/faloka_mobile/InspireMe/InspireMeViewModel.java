package com.example.faloka_mobile.InspireMe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Model.InspireMe;

import java.util.List;

public class InspireMeViewModel extends ViewModel {

    private final InspireMeRepositry inspireMeRepositry;

    public InspireMeViewModel(InspireMeRepositry inspireMeRepositry){
        this.inspireMeRepositry = inspireMeRepositry;
    }

    public LiveData<List<InspireMe>> getPost(){
        return inspireMeRepositry.getPost();
    }
}
