package com.example.faloka_mobile.Account.OrderStatus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faloka_mobile.Account.AccountRepository;
import com.example.faloka_mobile.Account.OrderUserListener;
import com.example.faloka_mobile.Adapter.OrderProductAdapter;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentNotYetPaidBinding;

import java.util.List;

public class WaitConfirmationFragment extends Fragment implements OrderUserListener {

    FragmentNotYetPaidBinding binding;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotYetPaidBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        AccountRepository.getOrders("pending", view, this::onOrder);
        return view;
    }

    @Override
    public void onOrder(List<OrderUser> orderUserList) {
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(orderUserList);
        binding.rvOrderNotYetPaid.setAdapter(orderProductAdapter);
        binding.rvOrderNotYetPaid.setLayoutManager(new LinearLayoutManager(getContext()));
//        debugData(orderUserList);
    }

//    public void debugData(List<OrderUser> orderUserList){
//        for(OrderUser orderUser : orderUserList){
//            System.out.println("saas: "+orderUser.getCreateDate().toString());
//
//        }
//    }
}