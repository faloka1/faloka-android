package com.example.faloka_mobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Account.AccountFragment;
import com.example.faloka_mobile.Account.OrderActivity;
import com.example.faloka_mobile.Account.PostActivity;
import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAccountAdapter extends RecyclerView.Adapter<MenuAccountAdapter.MenuViewHolder> {

    List<String> names;
    List<Integer> icons;

    public MenuAccountAdapter(List<String> names, List<Integer> icons) {
        this.names = names;
        this.icons = icons;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_menu_account,parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        String name = names.get(position);
        Integer icon = icons.get(position);
        holder.bind(name, icon);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder {
        Button btnMenu;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            btnMenu = itemView.findViewById(R.id.btn_account_menu);
        }
        public void bind(String name, Integer icon){
            btnMenu.setText(name);
            btnMenu.setCompoundDrawablePadding(20);
            btnMenu.setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0);
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
                    if(name.equals("Masuk / Buat Akun")){
                        view.getContext().startActivity(new Intent(view.getContext(),LoginActivity.class));
                    }
                    else if(name.equals("Pesanan Saya")){
                        if(tokenManager.getToken()==null){
                            view.getContext().startActivity(new Intent(view.getContext(),LoginActivity.class));
                        }
                        else {
                            view.getContext().startActivity(new Intent(view.getContext(), OrderActivity.class));
                        }
                    }
                    else if(name.equals("Keranjang Saya")){
                        if(tokenManager.getToken()==null){
                            view.getContext().startActivity(new Intent(view.getContext(),LoginActivity.class));
                        }
                        else {
                            view.getContext().startActivity(new Intent(view.getContext(), CartActivity.class));
                        }
                    }
                    else if(name.equals("Postingan Saya")){
                        if(tokenManager.getToken()==null){
                            view.getContext().startActivity(new Intent(view.getContext(),LoginActivity.class));
                        }
                        else {
                            view.getContext().startActivity(new Intent(view.getContext(), PostActivity.class));
                        }
                    }
                    else if(name.equals("Keluar")){

                        tokenManager.deleteToken();

                        Fragment fragment = new AccountFragment();
                        ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, fragment)
                                .commit();

                    }
                }
            });
        }
    }
}
