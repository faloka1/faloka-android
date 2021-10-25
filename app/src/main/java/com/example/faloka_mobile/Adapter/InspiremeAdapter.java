package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faloka_mobile.Model.InspireMe;
import com.example.faloka_mobile.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InspiremeAdapter extends RecyclerView.Adapter<InspiremeAdapter.InspiremeViewHolder> {


    List<InspireMe> inspireMeList;
    public InspiremeAdapter(List<InspireMe> inspireMeList){
        this.inspireMeList = inspireMeList;
    }

    @NonNull
    @Override
    public InspiremeAdapter.InspiremeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_inspire_me,parent,false);
        return new InspiremeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspiremeAdapter.InspiremeViewHolder holder, int position) {
        InspireMe inspireMe = inspireMeList.get(position);
        holder.bind(inspireMe);
    }

    @Override
    public int getItemCount() {
        return inspireMeList.size();
    }

    public class InspiremeViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CircleImageView photoProfile;
        TextView username;
        TextView caption;
        Button button;

        public InspiremeViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.inspire_image);
            photoProfile = itemView.findViewById(R.id.inspire_photo_profile);
            username = itemView.findViewById(R.id.inspire_name);
            caption = itemView.findViewById(R.id.inspire_caption);
            button = itemView.findViewById(R.id.inspire_button_relate_product);
        }
        void bind(InspireMe inspireMe){
            username.setText(inspireMe.getUsername());
            caption.setText(inspireMe.getCaption());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
