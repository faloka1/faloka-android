package com.example.faloka_mobile.InspireMe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.faloka_mobile.Adapter.InspiremeTagProductAdapter;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.databinding.ActivityInspireMeTagProductBinding;

import java.util.ArrayList;
import java.util.List;

public class InspireMeTagProductActivity extends AppCompatActivity{

    ActivityInspireMeTagProductBinding binding;

    public static final int RESULT_CODE=204;

    InspireMeViewModel viewModel;
    InspiremeTagProductAdapter adapter;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInspireMeTagProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(this));
        viewModel = new ViewModelProvider(this, factory).get(InspireMeViewModel.class);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setOrderProduct();
    }
    private void setOrderProduct(){
        RecyclerView rvProduct = binding.rvUploadInspiremeProduct;
        button = binding.buttonChooseInspiremeProduct;

        viewModel.getOrder().observe(this, orders->{
            List<Product> products = new ArrayList<>();
            List<Variant> variants = new ArrayList<>();
            for(int i=0; i<orders.size(); i++){
                if(products.size()==0){
                    products.add(orders.get(i).getProducts());
                    variants.add(orders.get(i).getVariants());
                }
                else{
                    int sum=0;
                    for(int j=0; j<products.size(); j++){
                        if((orders.get(i).getProducts().getName().equals(products.get(j).getName()))){
                            sum++;
                            break;
                        }
                    }
                    if(sum==0){
                        products.add(orders.get(i).getProducts());
                        variants.add(orders.get(i).getVariants());
                    }
                }
            }
            adapter = new InspiremeTagProductAdapter(products, variants, InspireMeViewModel.productVariants);
            rvProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
            rvProduct.setAdapter(adapter);
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InspireMeTagProductActivity.this, InpireMeUploadActivity.class);
                InspireMeViewModel.productVariants = adapter.productsChecked;
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}