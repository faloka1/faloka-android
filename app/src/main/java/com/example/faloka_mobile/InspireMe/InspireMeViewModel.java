package com.example.faloka_mobile.InspireMe;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faloka_mobile.Checkout.FileUtils;
import com.example.faloka_mobile.Model.InspireMe;
import com.example.faloka_mobile.Model.InspireMeProductVariant;
import com.example.faloka_mobile.Model.InspireMeResponse;
import com.example.faloka_mobile.Model.OrderResponseForInspireMe;
import com.example.faloka_mobile.Model.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InspireMeViewModel extends ViewModel {

    private final InspireMeRepositry inspireMeRepositry;
    public static ArrayList<InspireMeProductVariant> productVariants = new ArrayList<>();


    public InspireMeViewModel(InspireMeRepositry inspireMeRepositry) {
        this.inspireMeRepositry = inspireMeRepositry;
    }

    public LiveData<InspireMeResponse> getPost(int page) {
        return inspireMeRepositry.getPost(page);
    }

    public LiveData<List<OrderResponseForInspireMe>> getOrder() {
        return inspireMeRepositry.getOrder();
    }

    public void addInspireme(Uri uri, String captionParam, Context context){
        File file = FileUtils.getFile(context,uri);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("caption",captionParam);
        for(int i=0; i<productVariants.size(); i++){
            builder.addFormDataPart("products[" + i +"][product_id]",
                    String.valueOf(productVariants.get(i).getProduct().getId()));
            builder.addFormDataPart("products[" + i +"][variant_id]",
                    String.valueOf(productVariants.get(i).getVariant().getId()));
        }
        builder.addFormDataPart("image",file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"),file));
        inspireMeRepositry.add(builder.build());
    }

    public LiveData<List<InspireMe>> getInspireMeById(){
       return inspireMeRepositry.getInspireMeById();
    }

    public ArrayList<InspireMeProductVariant> getProductVariants(){
        return productVariants;
    }



}
