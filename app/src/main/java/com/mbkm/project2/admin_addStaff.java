package com.mbkm.project2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class admin_addStaff extends AppCompatActivity {
    private Button addStaff;
    private EditText inputname, inputid, inputphone, inputaddress, inputemail, inputloginpass;
    private ProgressDialog loadbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);

        // Hide ActionBar          //untuk menyembunyikan action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        inputname = findViewById(R.id.input_name);
        inputid = findViewById(R.id.input_ID);
        inputphone = findViewById(R.id.input_phone);
        inputaddress = findViewById(R.id.input_address);
        inputemail = findViewById(R.id.input_email_staff);
        inputloginpass = findViewById(R.id.input_loginpass);
        addStaff = findViewById(R.id.btn_add_staff);
        loadbar = new ProgressDialog(this);

        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStaff();
            }
        });

    }

    private void addStaff() {
        String name = inputname.getText().toString();
        String ID = inputid.getText().toString();
        String phone = inputphone.getText().toString();
        String address = inputaddress.getText().toString();
        String email = inputemail.getText().toString();
        String loginpass = inputloginpass.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(ID)) {
            Toast.makeText(this, "ID can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Phone can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Address can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email can not be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(loginpass)) {
            Toast.makeText(this, "Login Pass can not be empty", Toast.LENGTH_SHORT).show();
        } else {
            loadbar.setTitle("Add Staff");
            loadbar.setTitle("Please wait...");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();

            ValidateStaff(name, ID, phone, address, email, loginpass);
        }
    }
    private void ValidateStaff(String name, String ID, String phone, String address, String email, String loginpass){
        final DatabaseReference Staff;
        Staff = FirebaseDatabase.getInstance().getReference();

        Staff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Staff").child(name).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("ID", ID);
                    userdataMap.put("phone", phone);
                    userdataMap.put("address", address);
                    userdataMap.put("email", email);
                    userdataMap.put("loginpass", loginpass);

                    Staff.child("Staff").child(name).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(admin_addStaff.this, "Add Staff Success.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(admin_addStaff.this,CategoryActivty.class);
                                startActivity(intent);
                                loadbar.dismiss();

                            }
                            else{
                                Toast.makeText(admin_addStaff.this, "Error: Please try again.",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(admin_addStaff.this, "This " + name + " already exist.", Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                    Toast.makeText(admin_addStaff.this, "Please try again using another name!", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
