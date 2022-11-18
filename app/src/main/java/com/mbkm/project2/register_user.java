package com.mbkm.project2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class register_user extends AppCompatActivity {
    private Button register;
    private EditText name, email, password;
    private TextView textLogin;
    private ProgressDialog loadbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        // Hide ActionBar          //untuk menyembunyikan action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        name = findViewById(R.id.input_name);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        register = findViewById(R.id.btn_register);
        textLogin = findViewById(R.id.text_login_2);
        loadbar = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_user.this, login.class));
            }
        });
    }


    private void Register() {
        String getname = name.getText().toString();
        String getemail = email.getText().toString();
        String getpass = password.getText().toString();

        if (TextUtils.isEmpty(getname)) {
            Toast.makeText(this, "Can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(getemail)) {
            Toast.makeText(this, "Can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(getpass)) {
            Toast.makeText(this, "Can not be empty", Toast.LENGTH_SHORT).show();
        } else {
            loadbar.setTitle("Create Account");
            loadbar.setTitle("Please wait...");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();

            Validateemail(getname, getemail, getpass);
        }
    }

    private void Validateemail(String getname, String getemail, String getpass) {
        final DatabaseReference Ref;
        Ref = FirebaseDatabase.getInstance().getReference();

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(getname).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", getname);
                    userdataMap.put("email", getemail);
                    userdataMap.put("password", getpass);

                    Ref.child("Users").child(getname).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(register_user.this, "Register Success, your account has been created.",Toast.LENGTH_SHORT).show();
                                loadbar.dismiss();

                                startActivity(new Intent(register_user.this, login.class));
                            }
                            else{
                                Toast.makeText(register_user.this, "Error: Please try again.",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else {
                    Toast.makeText(register_user.this, "This " + getemail + " already exist.", Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                    Toast.makeText(register_user.this, "Please try again using another email!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}