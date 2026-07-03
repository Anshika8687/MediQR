package com.anshi.mediqr;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.OutputStream;

public class GenerateQRActivity extends AppCompatActivity {

    ImageView qrImage;
    Button btnDownload;
    Bitmap qrBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qractivity);

        qrImage = findViewById(R.id.qrImage);
        btnDownload = findViewById(R.id.btnDownload);

        generateQRCode();

        btnDownload.setOnClickListener(v -> {

            if (qrBitmap != null) {
                saveQRCode();
            } else {

                Toast.makeText(
                        this,
                        "QR not generated",
                        Toast.LENGTH_SHORT
                ).show();
            }

        });
    }

    private void generateQRCode() {

        try {

            SharedPreferences sp =
                    getSharedPreferences("MediQR", MODE_PRIVATE);

            String qrData =
                    "Name=" + sp.getString("name", "") +
                            ";Age=" + sp.getString("age", "") +
                            ";Blood=" + sp.getString("bloodGroup", "") +
                            ";Allergy=" + sp.getString("allergies", "") +
                            ";Medicine=" + sp.getString("medicine", "") +
                            ";Emergency=" + sp.getString("emergency", "");

            BitMatrix matrix =
                    new MultiFormatWriter().encode(
                            qrData,
                            BarcodeFormat.QR_CODE,
                            600,
                            600
                    );

            qrBitmap = Bitmap.createBitmap(
                    600,
                    600,
                    Bitmap.Config.RGB_565
            );

            for (int x = 0; x < 600; x++) {

                for (int y = 0; y < 600; y++) {

                    qrBitmap.setPixel(
                            x,
                            y,
                            matrix.get(x, y)
                                    ? 0xFF000000
                                    : 0xFFFFFFFF
                    );
                }
            }

            qrImage.setImageBitmap(qrBitmap);

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Error generating QR",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void saveQRCode() {

        try {

            ContentValues values =
                    new ContentValues();

            values.put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "MediQR_" +
                            System.currentTimeMillis() +
                            ".png"
            );

            values.put(
                    MediaStore.Images.Media.MIME_TYPE,
                    "image/png"
            );

            android.net.Uri uri =
                    getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                    );

            OutputStream outputStream =
                    getContentResolver()
                            .openOutputStream(uri);

            qrBitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    outputStream
            );

            outputStream.close();

            Toast.makeText(
                    this,
                    "QR Downloaded Successfully",
                    Toast.LENGTH_LONG
            ).show();

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Download Failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}