package com.mbkm.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CategoryActivty extends AppCompatActivity {

    private ImageView laptop, phone;
    private ImageView console, tshirts;
    private Button btnStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_activty);
        btnStaff = findViewById(R.id.btn_add_staff);
        laptop = findViewById(R.id.laptop);
        phone = findViewById(R.id.phone);
        console = findViewById(R.id.konsol);
        tshirts = findViewById(R.id.tshirt);

        btnStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivty.this, admin_addStaff.class));
            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivty.this, AddNewProduct.class);
                intent.putExtra("category", "laptop");
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivty.this, AddNewProduct.class);
                intent.putExtra("category", "phone");
                startActivity(intent);
            }
        });

        console.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivty.this, AddNewProduct.class);
                intent.putExtra("category", "console");
                startActivity(intent);
            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivty.this, AddNewProduct.class);
                intent.putExtra("category", "tshirts");
                startActivity(intent);
            }
        });
    }
}