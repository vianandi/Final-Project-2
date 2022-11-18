package com.mbkm.project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.mbkm.project2.databinding.ActivityMainBinding;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AddNewProduct extends AppCompatActivity {

    private String categoryname, description, pid, quantity, price, pname, savedate, savetime;
    private Button AddNewProduct;
    private Button intentBtn;
    private EditText productname,id_product, descriptionproduct, quantityproduct, priceproduct;
    private ImageView inputimage,photo;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String ProductKey, ImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private ActivityMainBinding binding;
    FirebaseStorage storage;

//    private ActivityResultLauncher activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        AddNewProduct = findViewById(R.id.btn_add_product);
        productname = findViewById(R.id.product_name);
        id_product = findViewById(R.id.product_id);
        descriptionproduct = findViewById(R.id.description);
        quantityproduct = findViewById(R.id.quantity);
        priceproduct = findViewById(R.id.price);
        photo = findViewById(R.id.photo);
        loadingBar = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();



        categoryname = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        //coba kategori
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname);
        Toast.makeText(this, categoryname,Toast.LENGTH_SHORT).show();


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        AddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        photo.setImageURI(result);
                        ImageUri = result;
                    }
                }
            });

    private void ValidateProductData(){
        pid = id_product.getText().toString();
        description = descriptionproduct.getText().toString();
        quantity = quantityproduct.getText().toString();
        price = priceproduct.getText().toString();
        pname = productname.getText().toString();

        if(ImageUri == null){
            Toast.makeText(this,"Product image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pid)){
            Toast.makeText(this,"Product ID don't be empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this,"Product description don't be empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(quantity)){
            Toast.makeText(this,"Product quantity don't be empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price)){
            Toast.makeText(this,"Price product don't be empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pname)){
            Toast.makeText(this,"Product name don't be empty", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        savedate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        savetime = currentTime.format(calendar.getTime());

        ProductKey = savedate + savetime;

        final StorageReference storageRef = ProductImagesRef.child(ImageUri.getLastPathSegment()+ pid + ".jpg");
        final UploadTask uploadTask = storageRef.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddNewProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddNewProduct.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        ImageUrl = storageRef.getDownloadUrl().toString();
                        return storageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            ImageUrl = task.getResult().toString();

                            Toast.makeText(AddNewProduct.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", pid);
        productMap.put("date", savedate);
        productMap.put("time", savetime);
        productMap.put("description", description);
        productMap.put("image", ImageUrl);
        productMap.put("category", categoryname);
        productMap.put("quantity",quantity);
        productMap.put("price", price);
        productMap.put("pname", pname);

        ProductsRef.child(pid).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AddNewProduct.this, CategoryActivty.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddNewProduct.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddNewProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}