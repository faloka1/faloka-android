package com.example.faloka_mobile.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.faloka_mobile.Account.OrderStatus.NotYetPaidFragment;
import com.example.faloka_mobile.Account.OrderStatus.WaitConfirmationFragment;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityOrderBinding;
import com.google.android.material.tabs.TabLayout;

public class OrderActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setToolbar();
        createTab(binding.getRoot());
    }
    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Pesanan Saya");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void createTab(View view){
        tabLayout = view.findViewById(R.id.order_tab);
        viewPager = view.findViewById(R.id.order_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Belum dibayar"));
        tabLayout.addTab(tabLayout.newTab().setText("Tunggu Konfirmasi"));

        tabLayout.setInlineLabel(true);

        final MyAdapter adapter = new MyAdapter(getApplicationContext(),
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public class MyAdapter extends FragmentStatePagerAdapter {

        private Context myContext;
        int totalTabs;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NotYetPaidFragment();
                case 1:
                    return new WaitConfirmationFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return totalTabs;
        }
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