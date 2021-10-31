package com.example.faloka_mobile;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Cart.CartCountItemListener;
import com.example.faloka_mobile.Cart.CartRepository;

import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class BaseFragment extends Fragment implements CartCountItemListener {

    private ImageBadgeView badgeView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        CartRepository.getCountCarts(getContext() , this::onItemCount);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume(){
        CartRepository.getCountCarts(getContext(), this::onItemCount);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.top_menu_wishlist:
                Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), "WISHLIST", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_menu_cart:
                Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {

        MenuItem menuItem = menu.findItem(R.id.top_menu_cart);
        badgeView = menuItem.getActionView().findViewById(R.id.cart_badge);
        badgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), "CART", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemCount(int count) {
        badgeView.setBadgeValue(count)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(8)
                .setMaxBadgeValue(999)
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);
    }
}
