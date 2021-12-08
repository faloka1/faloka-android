package com.example.faloka_mobile.Account;

import com.example.faloka_mobile.Model.OrderUser;

import java.util.List;

public interface OrderUserListener {
    public void onOrder(List<OrderUser> orderUserList);
}
