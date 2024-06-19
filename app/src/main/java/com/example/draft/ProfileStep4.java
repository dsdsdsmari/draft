package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileStep4 extends AppCompatActivity {

    EditText fullName, phoneNumber;
    RadioGroup radioGroup;
    RadioButton radioButtonMale, radioButtonFemale;
    Button finishButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_step4);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        finishButton = findViewById(R.id.btn_finish);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                int selectedGenderId = radioGroup.getCheckedRadioButtonId();
                String gender = "";

                if (selectedGenderId == radioButtonMale.getId()) {
                    gender = "Male";
                } else if (selectedGenderId == radioButtonFemale.getId()) {
                    gender = "Female";
                }

                if (name.isEmpty() || phone.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(ProfileStep4.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference userRef = reference.child(email.replace(".", ","));
                userRef.child("fullName").setValue(name);
                userRef.child("phoneNumber").setValue(phone);
                userRef.child("gender").setValue(gender);
                openLoginActivity();
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show();

    }
}
