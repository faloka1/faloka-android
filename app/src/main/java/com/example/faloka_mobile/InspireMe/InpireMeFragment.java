package com.example.faloka_mobile.InspireMe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faloka_mobile.Adapter.InspiremeAdapter;
import com.example.faloka_mobile.R;

public class InpireMeFragment extends Fragment {

    InspireMeViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(getContext()));
        viewModel = new ViewModelProvider(this,factory).get(InspireMeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inpire_me, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_inspire_me_list);
        viewModel.getPost().observe(getActivity(),posts->{
            InspiremeAdapter adapter = new InspiremeAdapter(posts);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        });
        return view;
    }
}