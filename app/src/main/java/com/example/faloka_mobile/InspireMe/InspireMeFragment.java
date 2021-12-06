package com.example.faloka_mobile.InspireMe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.faloka_mobile.Adapter.InspiremeAdapter;
import com.example.faloka_mobile.Login.LoginRepository;
import com.example.faloka_mobile.Model.InspireMe;
import com.example.faloka_mobile.Product.ProductDetailActivity;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentInpireMeBinding;

import java.util.ArrayList;
import java.util.List;

public class InspireMeFragment extends Fragment {

    public static final String EXTRA_FRAGMENT_INSPO = "EXTRA_FRAGMENT_INSPO";
    public static final int INDEX_FRAGMENT_ACCOUNT = 2;

    InspireMeViewModel viewModel;
    private FragmentInpireMeBinding binding;
    private AppCompatActivity activity;
    private View view;
    private int page;
    private InspiremeAdapter adapter;
    private int totalItemCount, previousTotal=0;
    private boolean isLoading = true;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(getContext()));
        viewModel = new ViewModelProvider(this,factory).get(InspireMeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInpireMeBinding.inflate(getLayoutInflater());
        activity = ((AppCompatActivity)getActivity());
        view = binding.getRoot();
        page = 1;
        layoutManager = new LinearLayoutManager(getActivity());
        binding.rvInspireMeList.setLayoutManager(layoutManager);
        adapter = new InspiremeAdapter();
        binding.rvInspireMeList.setAdapter(adapter);
        getPost(page);
        setToolbar();
        setScroll();
        return view;
    }

    public void setScroll(){
        binding.rvInspireMeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                if(dy > 0){
                    if(isLoading){
                        if(totalItemCount>previousTotal){
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if(!isLoading){
                        page++;
                        getPost(page);
                        isLoading = true;
                    }
                }
            }
        });
    }

    public void getPost(int page){
        binding.progressBarInspireMe.setVisibility(View.VISIBLE);
        viewModel.getPost(page).observe(getActivity(),posts->{
            adapter.addInspireMe(posts.getInspireMeList());
            binding.progressBarInspireMe.setVisibility(View.GONE);
        });
    }

    private void setToolbar(){
        activity.setSupportActionBar(binding.toolbar);
        activity.getSupportActionBar().setTitle("Inspire Me");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.inspo_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.inspo_add_outfit:
                if(!LoginRepository.isValidationLogin(getActivity())){
                    return super.onOptionsItemSelected(item);
                }
//                Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, InpireMeUploadActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}