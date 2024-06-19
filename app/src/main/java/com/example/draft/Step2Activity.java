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

public class Step2Activity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioDog, radioCat;
    Button continueButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step2);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        radioGroup = findViewById(R.id.radioGroup);
        radioDog = findViewById(R.id.radioDog);
        radioCat = findViewById(R.id.radioCat);
        continueButton = findViewById(R.id.btn_continue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String animalType = "";
                if (selectedId == radioDog.getId()) {
                    animalType = "Dog";
                    openDogStep3Activity(email);
                } else if (selectedId == radioCat.getId()) {
                    animalType = "Cat";
                    openCatStep3Activity(email);
                }

                saveAnimalType(email, animalType);
            }
        });
    }

    private void saveAnimalType(String email, String animalType) {
        reference.child(email.replace(".", ",")).child("animalType").setValue(animalType);
    }

    private void openDogStep3Activity(String email) {
        Intent intent = new Intent(this, DogStep3.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    private void openCatStep3Activity(String email) {
        Intent intent = new Intent(this, CatStep3.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

}
