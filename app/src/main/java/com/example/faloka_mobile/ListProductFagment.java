package com.example.faloka_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductData;

import java.util.ArrayList;


public class ListProductFagment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Product> listProduct = new ArrayList<>();
    ProductAdapter productAdapter;

    public ListProductFagment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_product, container, false);

        recyclerView =view.findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        listProduct.addAll(ProductData.getListData());
        productAdapter = new ProductAdapter(listProduct);
        recyclerView.setAdapter(productAdapter);

        return view;

    }
}