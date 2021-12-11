package com.example.faloka_mobile.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MixAndMatch.MixMatchCartVarSizeListener;
import com.example.faloka_mobile.MixAndMatch.MixMatchRepository;
import com.example.faloka_mobile.MixAndMatch.MixMatchVariantSizeListener;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.Model.VariantSize;
import com.example.faloka_mobile.Product.ProductListener;
import com.example.faloka_mobile.Product.ProductRepository;
import com.example.faloka_mobile.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MixMatchCartAdapter extends RecyclerView.Adapter<MixMatchCartAdapter.MixMatchCartViewHolder> {
    private View view;
    private List<Cart> cartList;
    private List<Cart> resultCartList;
    private MixMatchVariantSizeListener mixMatchVariantSizeListener;

    public MixMatchCartAdapter(List<Cart> cartList, MixMatchVariantSizeListener mixMatchVariantSizeListener){
        this.cartList = cartList;
        this.mixMatchVariantSizeListener = mixMatchVariantSizeListener;
        resultCartList = new ArrayList<>(cartList);
    }

    public List<Cart> getCartList(){
        return this.resultCartList;
    }


    @NonNull
    @Override
    public MixMatchCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_mix_match_cart,parent,false);
        return new MixMatchCartAdapter.MixMatchCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MixMatchCartViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        holder.tvMixMatchCartName.setText(cart.getProduct().getName());
        holder.tvMixMatchCartPrice.setText(getFormatRupiah(cart.getProduct().getPrice()));
        Glide.with(holder.imgMixMatchCart.getContext())
                .load(ApiConfig.BASE_IMAGE_URL+cart.getVariant().getVariantImageList().get(0).getImageURL() )
                .into(holder.imgMixMatchCart);
        for(VariantSize variantSize : cart.getVariant().getVariantSizes()){
            System.out.print(variantSize.getName()+"=> ");
        }
        System.out.println("\n");
        MixMatchRepository.getMixMatchCartVariantSize(view, cart, holder::onMixMatchCartVarSize);
    }

    public String getFormatRupiah(int price){
        Double tempPrice = Double.parseDouble(String.valueOf(price));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return  formatRupiah.format(tempPrice);
    }

    @Override
    public int getItemCount() {
        return this.cartList.size();
    }

    public class MixMatchCartViewHolder extends RecyclerView.ViewHolder implements MixMatchCartVarSizeListener {

        TextView tvMixMatchCartName;
        TextView tvMixMatchCartPrice;
        CardView cvMixMatchCart;
        ImageView imgMixMatchCart;
        ChipGroup cgMixMatchCart;
        VariantSize selectedVariantSize;

        public MixMatchCartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMixMatchCartName = itemView.findViewById(R.id.tv_mix_match_cart_name);
            tvMixMatchCartPrice = itemView.findViewById(R.id.tv_mix_match_cart_price);
            cvMixMatchCart = itemView.findViewById(R.id.cv_mix_match_cart);
            imgMixMatchCart = itemView.findViewById(R.id.img_mix_match_cart);
            cgMixMatchCart = itemView.findViewById(R.id.cg_mix_match_cart);
            selectedVariantSize = new VariantSize();
        }

        @Override
        public void onMixMatchCartVarSize(Cart cart, Product product) {
            List<VariantSize> variantSizeList = product.getVariantList().get(0).getVariantSizes();
            for(int i=0; i<variantSizeList.size(); i++){
                Chip mChip = new Chip(view.getContext());
                mChip.setText(variantSizeList.get(i).getName());
                mChip.setCheckable(true);
                int finalI = i;
                mChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mChip.setChecked(true);
                        selectedVariantSize = variantSizeList.get(finalI);
                        mixMatchVariantSizeListener.onMixMatchVariantSize(getAdapterPosition(), selectedVariantSize);
                    }
                });
                cgMixMatchCart.addView(mChip);
            }
            if(cart.getVariant().getVariantSizes().size() == 1){
                for(int i=0; i<cgMixMatchCart.getChildCount(); i++){
                    Chip chip = (Chip) cgMixMatchCart.getChildAt(i);

                    if(chip.getText().toString().equalsIgnoreCase(cart.getVariant().getVariantSizes().get(0).getName())){
                        mixMatchVariantSizeListener.onMixMatchVariantSize(getAdapterPosition(), cart.getVariant().getVariantSizes().get(0));
                        chip.setChecked(true);
                    }
                }
            }
        }
    }
}
