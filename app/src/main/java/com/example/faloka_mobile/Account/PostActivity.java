package com.example.faloka_mobile.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.faloka_mobile.Adapter.AccountPostAdapter;
import com.example.faloka_mobile.InspireMe.InspireMeRepositry;
import com.example.faloka_mobile.InspireMe.InspireMeViewModel;
import com.example.faloka_mobile.InspireMe.InspireMeViewModelFactory;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {

    ActivityPostBinding binding;
    InspireMeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        setToolbar();
        setContent();

    }
    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Postingan Saya");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setContent(){
        RecyclerView rv = binding.rvAccountPost;
        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(getApplicationContext()));
        viewModel = new ViewModelProvider(this,factory).get(InspireMeViewModel.class);
        viewModel.getInspireMeById().observe(this, inspireMeList -> {
            AccountPostAdapter adapter = new AccountPostAdapter(inspireMeList);
            rv.setLayoutManager(new GridLayoutManager(getApplicationContext(),3,GridLayoutManager.VERTICAL,false));
            rv.setAdapter(adapter);
        });
    }
}