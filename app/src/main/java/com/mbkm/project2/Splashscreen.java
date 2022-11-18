package com.mbkm.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class Splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        // inisialisasi view
        View decorView = getWindow().getDecorView();
        // Hide the status bar.          //untuk menampilkan full screen
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        // Hide ActionBar                //untuk menyembunyikan action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        Toast.makeText(this, "Wait for the process", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splashscreen.this, MainActivity.class));
                finish();
            }
        }, 5500);   // timer
    }
}
