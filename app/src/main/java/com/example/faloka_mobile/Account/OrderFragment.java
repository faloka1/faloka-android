package com.example.faloka_mobile.Account;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faloka_mobile.Account.OrderStatus.NotYetPaidFragment;
import com.example.faloka_mobile.Account.OrderStatus.WaitConfirmationFragment;
import com.example.faloka_mobile.R;
import com.google.android.material.tabs.TabLayout;


public class OrderFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    public OrderFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        createTab(view);
        return view;
    }

    private void createTab(View view){
        tabLayout = view.findViewById(R.id.order_tab);
        viewPager = view.findViewById(R.id.order_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Belum dibayar"));
        tabLayout.addTab(tabLayout.newTab().setText("Tunggu Konfirmasi"));

        tabLayout.setInlineLabel(false);

        final MyAdapter adapter = new MyAdapter(getContext(),getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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
    public class MyAdapter extends FragmentPagerAdapter {

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
}