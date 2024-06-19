package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CatStep3 extends AppCompatActivity {

    CheckBox checkBoxBreed1, checkBoxBreed2, checkBoxBreed3, checkBoxBreed4;
    Button continueButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cat_step3);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        checkBoxBreed1 = findViewById(R.id.checkBoxPersian);
        checkBoxBreed2 = findViewById(R.id.checkBoxRagdoll);
        checkBoxBreed3 = findViewById(R.id.checkBoxScottishFold);
        checkBoxBreed4 = findViewById(R.id.checkBoxSiamese);
        continueButton = findViewById(R.id.btn_continue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedBreeds = new ArrayList<>();
                if (checkBoxBreed1.isChecked()) {
                    selectedBreeds.add(checkBoxBreed1.getText().toString());
                }
                if (checkBoxBreed2.isChecked()) {
                    selectedBreeds.add(checkBoxBreed2.getText().toString());
                }
                if (checkBoxBreed3.isChecked()) {
                    selectedBreeds.add(checkBoxBreed3.getText().toString());
                }
                if (checkBoxBreed4.isChecked()) {
                    selectedBreeds.add(checkBoxBreed4.getText().toString());
                }

                if (selectedBreeds.isEmpty()) {
                    Toast.makeText(CatStep3.this, "Please select at least one breed", Toast.LENGTH_SHORT).show();
                } else {
                    saveSelectedBreeds(email, selectedBreeds);
                    openProfileStep4(email);
                }
            }
        });
    }

    private void saveSelectedBreeds(String email, List<String> selectedBreeds) {
        reference.child(email.replace(".", ",")).child("selectedBreeds").setValue(selectedBreeds);
    }

    private void openProfileStep4(String email) {
        Intent intent = new Intent(this, ProfileStep4.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
