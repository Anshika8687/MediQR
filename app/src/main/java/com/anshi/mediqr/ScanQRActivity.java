package com.anshi.mediqr;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan MediQR");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);

        barcodeLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(
                    new ScanContract(),
                    result -> {

                        if (result.getContents() != null) {

                            Intent intent =
                                    new Intent(
                                            ScanQRActivity.this,
                                            MedicalCardActivity.class
                                    );

                            intent.putExtra(
                                    "qrData",
                                    result.getContents()
                            );

                            startActivity(intent);
                            finish();
                        }
                    });
}