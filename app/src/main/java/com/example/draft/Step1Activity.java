package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Step1Activity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioPetOwner, radioPetAdopter;
    Button continueButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        radioGroup = findViewById(R.id.radioGroup);
        radioPetOwner = findViewById(R.id.radioPetOwner);
        radioPetAdopter = findViewById(R.id.radioPetAdopter);
        continueButton = findViewById(R.id.btn_continue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String userType = "";
                if (selectedId == radioPetOwner.getId()) {
                    userType = "Pet Owner";
                } else if (selectedId == radioPetAdopter.getId()) {
                    userType = "Pet Adopter";
                }

                saveUserType(email, userType);
                openStep2Activity(email);
            }
        });
    }

    private void saveUserType(String email, String userType) {
        reference.child(email.replace(".", ",")).child("userType").setValue(userType);
    }

    private void openStep2Activity(String email) {
        Intent intent = new Intent(this, Step2Activity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
