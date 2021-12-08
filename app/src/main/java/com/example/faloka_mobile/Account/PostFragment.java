package com.example.faloka_mobile.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faloka_mobile.Adapter.AccountPostAdapter;
import com.example.faloka_mobile.InspireMe.InspireMeRepositry;
import com.example.faloka_mobile.InspireMe.InspireMeViewModel;
import com.example.faloka_mobile.InspireMe.InspireMeViewModelFactory;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentInpireMeBinding;
import com.example.faloka_mobile.databinding.FragmentPostBinding;

public class PostFragment extends Fragment {

    InspireMeViewModel viewModel;
    private FragmentPostBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(getContext()));
        viewModel = new ViewModelProvider(this,factory).get(InspireMeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        binding = FragmentPostBinding.inflate(getLayoutInflater());

        view = binding.getRoot();
        RecyclerView rv = binding.rvAccountPost;

        viewModel.getInspireMeById().observe(getActivity(), inspireMeList -> {
            AccountPostAdapter adapter = new AccountPostAdapter(inspireMeList);
            rv.setLayoutManager(new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false));
            rv.setAdapter(adapter);
        });

        return view;
    }
}