package com.example.faloka_mobile.Account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.faloka_mobile.BaseFragment;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.FragmentAccountBinding;

import java.util.ArrayList;

public class AccountFragment extends BaseFragment implements DrawerOptionListener {

    public static final String EXTRA_FRAGMENT_ACCOUNT = "EXTRA_FRAGMENT_ACCOUNT";
    public static final int INDEX_FRAGMENT_ACCOUNT = 4;

    private FragmentAccountBinding binding;
    private AppCompatActivity activity;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private AccountViewModel accountViewModel;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        activity = ((AppCompatActivity)getActivity());
        accountViewModel = new AccountViewModel(binding, activity, this::onOptionDrawer);
        view = binding.getRoot();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        accountViewModel.initUser();
        accountViewModel.initStateUser(accountViewModel.getLabelsName(), accountViewModel.getIconsName());

        super.onResume();
    }

    @Override
    public void onOptionDrawer(ActionBarDrawerToggle actionBarDrawerToggle) {
        this.actionBarDrawerToggle  = actionBarDrawerToggle;
    }
}