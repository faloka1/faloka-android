package com.example.faloka_mobile.InspireMe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.faloka_mobile.Adapter.InspiremeUploadProductAdapter;
import com.example.faloka_mobile.Model.InspireMe;
import com.example.faloka_mobile.Model.InspireMeProductVariant;
import com.example.faloka_mobile.R;
import com.example.faloka_mobile.databinding.ActivityInpireMeUploadInspireMeBinding;

import java.util.ArrayList;
import java.util.List;

public class InpireMeUploadActivity extends AppCompatActivity {

    ActivityInpireMeUploadInspireMeBinding binding;

    ImageView imageView;
    Button buttonUploadPhoto;
    Button buttonRelateProduct;
    EditText etCaption;

    Uri uri;

    InspireMeViewModel viewModel;

    private final int CAMERA_PERM_CODE = 101;
    private final int GALLERY_PERM_CODE = 102;

    private final int REQUEST_OPEN_GALLERY = 201;
    private final int REQUEST_OPEN_CAMERA = 202;
    public static final int REQUEST_TAG_PRODUCT = 203;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInpireMeUploadInspireMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buttonUploadPhoto = binding.buttonUploadImage;
        buttonRelateProduct = binding.buttonAddProduct;
        imageView = binding.ivPost;
        etCaption= binding.uploadInspireEtCaption;



        InspireMeViewModelFactory factory = new InspireMeViewModelFactory(new InspireMeRepositry(this));
        viewModel = new ViewModelProvider(this, factory).get(InspireMeViewModel.class);

        viewModel.getProductVariants().clear();
        setToolbar();
        setUploadImage();
        setRelateProduct();
        setCTAButton();

    }
    private void setToolbar(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //===========================================================================================
    private void setUploadImage(){
        buttonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }
    private void selectImage(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(InpireMeUploadActivity.this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PERM_CODE){
            if(grantResults.length<0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
        }
        if(requestCode==GALLERY_PERM_CODE){
            if(grantResults.length<0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
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
    //===========================================================================================
    private void setRelateProduct(){
        RecyclerView rv = binding.rvInspireMeProduct;
        List<String> images = new ArrayList<>();
        for(InspireMeProductVariant productVariant : InspireMeViewModel.productVariants){
            images.add(productVariant.getVariant().getVariantImageList().get(0).getImageURL());
        }
        InspiremeUploadProductAdapter adapter = new InspiremeUploadProductAdapter(images);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(adapter);
        buttonRelateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InpireMeUploadActivity.this, InspireMeTagProductActivity.class);
                startActivityForResult(intent, REQUEST_TAG_PRODUCT);
            }
        });
    }
    //===========================================================================================
    private void setCTAButton() {
        Button buttonAdd =  binding.buttonAddInspireme;
        Button buttonReset = binding.buttonReset;
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.addInspireme(uri, etCaption.getText().toString(), getApplicationContext());
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new InspireMeFragment()).commit();
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InpireMeUploadActivity.this, InpireMeUploadActivity.class));
                viewModel.getProductVariants().clear();
            }
        });

    }
    //===========================================================================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_OPEN_GALLERY){
            uri = data.getData();
            imageView.setImageURI(uri);
            buttonUploadPhoto.setText("Ganti Gambar");
        }
        else if(requestCode==REQUEST_OPEN_CAMERA){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            buttonUploadPhoto.setText("Ganti Gambar");
        }
        else if(requestCode == REQUEST_TAG_PRODUCT){
            if(resultCode == InspireMeTagProductActivity.RESULT_CODE){
                setRelateProduct();
            }
        }
    }
}