package com.anshi.mediqr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MedicalCardActivity extends AppCompatActivity {

    TextView txtName, txtAge, txtBlood,
            txtAllergies, txtMedicine,
            txtEmergency;

    Button btnGenerateQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_card);

        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        txtBlood = findViewById(R.id.txtBlood);
        txtAllergies = findViewById(R.id.txtAllergies);
        txtMedicine = findViewById(R.id.txtMedicine);
        txtEmergency = findViewById(R.id.txtEmergency);

        btnGenerateQR = findViewById(R.id.btnGenerateQR);

        String qrData = getIntent().getStringExtra("qrData");

        if (qrData != null) {

            String[] fields = qrData.split(";");

            String name = "";
            String age = "";
            String blood = "";
            String allergy = "";
            String medicine = "";
            String emergency = "";

            for (String field : fields) {

                if (field.startsWith("Name="))
                    name = field.replace("Name=", "");

                else if (field.startsWith("Age="))
                    age = field.replace("Age=", "");

                else if (field.startsWith("Blood="))
                    blood = field.replace("Blood=", "");

                else if (field.startsWith("Allergy="))
                    allergy = field.replace("Allergy=", "");

                else if (field.startsWith("Medicine="))
                    medicine = field.replace("Medicine=", "");

                else if (field.startsWith("Emergency="))
                    emergency = field.replace("Emergency=", "");
            }

            txtName.setText(name);
            txtAge.setText("Age : " + age);
            txtBlood.setText("Blood Group : " + blood);
            txtAllergies.setText("Allergies : " + allergy);
            txtMedicine.setText("Medicine : " + medicine);
            txtEmergency.setText("Emergency Contact : " + emergency);

        } else {

            SharedPreferences sp =
                    getSharedPreferences("MediQR", MODE_PRIVATE);

            txtName.setText(
                    sp.getString("name", "")
            );

            txtAge.setText(
                    "Age : " +
                            sp.getString("age", "")
            );

            txtBlood.setText(
                    "Blood Group : " +
                            sp.getString("bloodGroup", "")
            );

            txtAllergies.setText(
                    "Allergies : " +
                            sp.getString("allergies", "")
            );

            txtMedicine.setText(
                    "Medicine : " +
                            sp.getString("medicine", "")
            );

            txtEmergency.setText(
                    "Emergency Contact : " +
                            sp.getString("emergency", "")
            );
        }

        btnGenerateQR.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MedicalCardActivity.this,
                    GenerateQRActivity.class
            );

            startActivity(intent);

        });
    }
}