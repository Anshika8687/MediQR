package com.anshi.mediqr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    EditText etName, etAge, etBloodGroup,
            etAllergies, etMedicine, etEmergency;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etBloodGroup = findViewById(R.id.etBloodGroup);
        etAllergies = findViewById(R.id.etAllergies);
        etMedicine = findViewById(R.id.etMedicine);
        etEmergency = findViewById(R.id.etEmergency);

        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String bloodGroup = etBloodGroup.getText().toString().trim();
            String allergies = etAllergies.getText().toString().trim();
            String medicine = etMedicine.getText().toString().trim();
            String emergency = etEmergency.getText().toString().trim();

            if (name.isEmpty() || age.isEmpty() || bloodGroup.isEmpty()) {

                Toast.makeText(
                        Profile.this,
                        "Please fill all required fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            SharedPreferences sp =
                    getSharedPreferences("MediQR", MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();

            editor.putString("name", name);
            editor.putString("age", age);
            editor.putString("bloodGroup", bloodGroup);
            editor.putString("allergies", allergies);
            editor.putString("medicine", medicine);
            editor.putString("emergency", emergency);

            editor.apply();

            Toast.makeText(
                    Profile.this,
                    "Profile Saved Successfully",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent =
                    new Intent(Profile.this,
                            MedicalCardActivity.class);

            startActivity(intent);
            finish();

        });
    }
}