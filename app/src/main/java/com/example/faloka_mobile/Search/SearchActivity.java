package com.example.faloka_mobile.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.faloka_mobile.Adapter.ProductAdapter;
import com.example.faloka_mobile.Adapter.SearchAdapter;
import com.example.faloka_mobile.Checkout.CheckoutRepository;
import com.example.faloka_mobile.Checkout.ConfirmCheckoutActivity;
import com.example.faloka_mobile.Checkout.FileUtils;
import com.example.faloka_mobile.InspireMe.InpireMeUploadActivity;
import com.example.faloka_mobile.InspireMe.InspireMeTagProductActivity;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivitySearchBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchProductListener {

    public final int CAMERA_PERM_CODE = 101;
    public final int GALLERY_PERM_CODE = 102;
    private final int REQUEST_OPEN_GALLERY = 201;
    private final int REQUEST_OPEN_CAMERA = 202;

    private ActivitySearchBinding binding;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        setToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.top_menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari outfitmu");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length()>=3 || s.length()==0){
                    SearchRepository.getSearchProducts(view, s, SearchActivity.this::onSearchProduct);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.top_menu_visual_search:
                selectImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectImage(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
        builder.setTitle("Tambahkan foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    askPermissionCamera();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    dialog.dismiss();
                    askPermissionGallery();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void askPermissionGallery(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PERM_CODE);
        }
        else{
            openGallery();
        }
    }
    private void askPermissionCamera(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else{
            openCamera();
        }
    }
    private void openGallery(){
        Intent intent = new  Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_OPEN_GALLERY);
    }
    private void openCamera(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, REQUEST_OPEN_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PERM_CODE){
            if(grantResults.length<0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        showMessageOKCancel("You need to allow access permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            ActivityCompat.requestPermissions(getParent(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
                                        }
                                    }
                                });
                    }
                }
            }
        }
        if(requestCode==GALLERY_PERM_CODE){
            if(grantResults.length<0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SearchActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_OPEN_GALLERY){
            Uri uri = data.getData();
            confirmImageSearch(uri);
        }
        else if(requestCode==REQUEST_OPEN_CAMERA){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
            Uri uri = Uri.parse(path);
            confirmImageSearch(uri);
        }

    }

    public void confirmImageSearch(Uri uri){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(binding.getRoot().getContext());
        alertBuilder.setMessage("Apakah kamu yakin mencari outfit ini?");
        alertBuilder.setCancelable(true);
        ImageView imageView = new ImageView(binding.getRoot().getContext());
        Glide.with(binding.getRoot().getContext())
                .load(uri)
                .apply(new RequestOptions().override(500, 500))
                .into(imageView);
        alertBuilder.setView(imageView);
        alertBuilder.setPositiveButton(
                "Cari",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(uri != null) {
                            Intent intent = new Intent(SearchActivity.this, SearchListProductActivity.class);
                            intent.putExtra("IMAGE_URI", uri.toString());
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "You must choose the image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        alertBuilder.setNegativeButton(
                "Batal",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void setToolbar(){
        setSupportActionBar(binding.searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @Override
    public void onSearchProduct(ProductListResponse productListResponse, String text) {
        List<Product> productList = new ArrayList<>();
        if(productListResponse.getProductList().size() == 0){
            Product product = new Product();
            productList.add(product);
        }
        else {
            productList = productListResponse.getProductList();
        }
        SearchAdapter searchAdapter = new SearchAdapter(productList, text);
        binding.rvSearchProduct.setAdapter(searchAdapter);
        binding.rvSearchProduct.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}