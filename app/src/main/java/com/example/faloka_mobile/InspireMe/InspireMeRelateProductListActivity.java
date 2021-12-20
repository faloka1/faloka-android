package com.example.faloka_mobile.InspireMe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.BaseActivity;
import com.example.faloka_mobile.Cart.CartActivity;
import com.example.faloka_mobile.MixAndMatch.MixMatchActivity;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Variant;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityInspireMeRelateProductBinding;

import java.util.ArrayList;
import java.util.List;

public class InspireMeRelateProductListActivity extends BaseActivity {

    ActivityInspireMeRelateProductBinding binding;
    InspireMeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInspireMeRelateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(this));
        viewModel = new ViewModelProvider(this, factory).get(InspireMeViewModel.class);
        setToolbar();
        setHeader();
        setProductList();
        onButtonMixMatchClicked();
    }

    private void onButtonMixMatchClicked(){
        ArrayList<Product> productList = this.getIntent().getExtras().getParcelableArrayList("relateProducts");
        binding.btnInpsoMixMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productList != null) {
                    Intent intent = new Intent(binding.getRoot().getContext(), MixMatchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Product.EXTRA_PRODUCT, (ArrayList) getCartFromProduct(productList));
                    intent.putExtras(bundle);
                    binding.getRoot().getContext().startActivity(intent);
                }
            }
        });
    }

    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setHeader(){
        String image = this.getIntent().getExtras().getString("image");
        Glide.with(binding.ivInspiremeProductList)
                .load(ApiConfig.BASE_IMAGE_URL + image)
                .into(binding.ivInspiremeProductList);
    }
    private void setProductList(){
        ArrayList<Product> products = this.getIntent().getExtras().getParcelableArrayList("relateProducts");
        RecyclerView rv = binding.rvInspiremeProductList;
        ProductAdapter adapter = new ProductAdapter(products);
        rv.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        rv.setAdapter(adapter);
    }

    public static final List<Cart> getCartFromProduct(List<Product> productList){
        List<Cart> cartList = new ArrayList<>();
        for(Product product : productList){
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setProductID(product.getId());
            cart.setQuantity(1);
            cart.setVariant(product.getVariantList().get(0));
            cart.setVariantID(product.getVariantList().get(0).getId());
            cartList.add(cart);
        }
        return cartList;
    }

}