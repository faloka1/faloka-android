package com.example.faloka_mobile.InspireMe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.faloka_mobile.Adapter.InspiremeAdapter;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentInpireMeBinding;

public class InspireMeFragment extends Fragment {

    InspireMeViewModel viewModel;
    private FragmentInpireMeBinding binding;
    private AppCompatActivity activity;
    private View view;
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
        viewModel.getPost().observe(getActivity(),posts->{
            InspiremeAdapter adapter = new InspiremeAdapter(posts);
            binding.rvInspireMeList.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rvInspireMeList.setAdapter(adapter);
        });
        setToolbar();
        return view;
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
                Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, InspireMeUploadInspireMe.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}