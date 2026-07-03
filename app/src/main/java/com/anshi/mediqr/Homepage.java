package com.anshi.mediqr;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Homepage extends AppCompatActivity {

    CardView cardProfile;
    CardView cardScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        cardScan = findViewById(R.id.cardScan);
        cardScan.setOnClickListener(v -> {


                Intent intent =
                        new Intent(
                                Homepage.this,
                                ScanQRActivity.class);

                startActivity(intent);

            });

        cardProfile = findViewById(R.id.cardProfile);

        cardProfile.setOnClickListener(v -> {

            Intent intent =
                    new Intent(Homepage.this,
                            Profile.class);

            startActivity(intent);

        });
    }
}