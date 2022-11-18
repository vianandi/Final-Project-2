package com.mbkm.project2;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mbkm.project2.Model.Staff;

import java.util.ArrayList;

public class staff extends AppCompatActivity {

    ImageView imgLogo;
    TextView nmStore;
    TextView deskStore;

    DatabaseReference database;
    RecyclerView recyclerView_Staff;
    RecyclerViewAdapter_Staff myAdapter;
    ArrayList<Staff> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_page);

        isiJudul();

        recyclerView_Staff = findViewById(R.id.recyclerView_Staff);
        database = FirebaseDatabase.getInstance().getReference("Staff");

        recyclerView_Staff.setHasFixedSize(true);
        recyclerView_Staff.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new RecyclerViewAdapter_Staff(list, this);
        recyclerView_Staff.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Staff staff = dataSnapshot.getValue(Staff.class);
                    list.add(staff);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void isiJudul(){
        imgLogo = findViewById(R.id.imgLogo);
        nmStore = findViewById(R.id.nmStore);
        deskStore = findViewById(R.id.deskStore);

        imgLogo.setImageResource(R.drawable.icon1);
        nmStore.setText("Blaclist D Store");
        deskStore.setText("Selamat datang di Blacklist D Store\n" +
                "\n" +
                "Blacklist D Store adalah Toko Online yang menyediakan berbagai macam barang mulai dari Laptop, SmartPhone, Clothing, dan berbagai macam konsol game.\n" +
                "Semua barang yang dijual disini sudah pasti sangat berkualitas dan keren tentunya.\n" +
                "\n" +
                "Jadi tunggu apalagi...\n" +
                "\n" +
                "Selamat Berbelanja!!!\n");
    }


}
