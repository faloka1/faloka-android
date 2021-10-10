package com.example.faloka_mobile.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.R;

import java.util.List;

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.CourierViewHolder>{

    List<Courier> courierList;

    public CourierAdapter(List<Courier> courierList){
        this.courierList = courierList;
    }

    @NonNull
    @Override
    public CourierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_courier,parent,false);
        return new CourierAdapter.CourierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourierViewHolder holder, int position) {

        Courier courier = courierList.get(position);
        List<CourierService> courierServiceList = courier.getCourierServiceList();
        for(CourierService courierService : courierServiceList){
            RadioButton radioButton = new RadioButton(holder.itemView.getContext());
            radioButton.setText(courierService.getName());
            holder.radioGroupCourier.addView(radioButton);
        }

    }

    @Override
    public int getItemCount() {
        return courierList.size();
    }


    public class CourierViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourier;
        RadioGroup radioGroupCourier;
        public CourierViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourier = itemView.findViewById(R.id.tv_courier);
            radioGroupCourier = itemView.findViewById(R.id.rg_courier);
        }
    }
}
