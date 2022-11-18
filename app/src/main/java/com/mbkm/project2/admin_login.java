package com.mbkm.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mbkm.project2.Model.Admin;
import com.mbkm.project2.Model.Users;

public class admin_login extends AppCompatActivity {
    private EditText input_name, inputpassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog loadbar;

    private String DB = "Admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        mAuth = FirebaseAuth.getInstance();
        input_name = findViewById(R.id.admin_input_name);
        inputpassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_login_page);
        loadbar = new ProgressDialog(this);


        // Hide ActionBar          //untuk menyembunyikan action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login(){
        String name = input_name.getText().toString();
        String password = inputpassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password can not be empty", Toast.LENGTH_SHORT).show();
        } else {
            loadbar.setTitle("Login");
            loadbar.setTitle("Please wait...");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();

            Success(name,password);
        }
    }

    private void Success(String name,String password){
        final DatabaseReference Ref;
        Ref = FirebaseDatabase.getInstance().getReference();

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(DB).child(name).exists()){
                    Admin Data = dataSnapshot.child(DB).child(name).getValue(Admin.class);

                    if(Data.getName().equals(name)){
                        if(Data.getPassword().equals(password)){
                            Toast.makeText(admin_login.this,"Login Success", Toast.LENGTH_SHORT).show();
                            loadbar.dismiss();

//                            startActivity(new Intent(admin_login.this, CategoryActivty.class));
                            Intent intent = new Intent(admin_login.this,CategoryActivty.class);
                            startActivity(intent);
                        }
                    }

                }
                else{
                    Toast.makeText(admin_login.this, "Account with this " + name + " ID does not exists.",Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
