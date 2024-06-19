package com.example.draft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class CreatePetProfile extends AppCompatActivity {

    private EditText etName, etBreed, etAge, etAddress, etBehavior, etBio;
    private Spinner spinnerCategory, spinnerGender;
    private Button btnSave;
    private ImageView ivPetImage;
    private ProgressDialog progressDialog;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private FirebaseAuth auth;

    private Uri imageUri;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        etName = findViewById(R.id.pet_name);
        etBreed = findViewById(R.id.pet_breed);
        etAge = findViewById(R.id.pet_age);
        etAddress = findViewById(R.id.pet_address);
        etBehavior = findViewById(R.id.pet_behavior);
        etBio = findViewById(R.id.pet_BriefBio);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnSave = findViewById(R.id.btnCreate);
        ivPetImage = findViewById(R.id.btnUploadProfileImage);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving pet profile...");
        progressDialog.setCancelable(false);

        btnSave.setOnClickListener(v -> {
            savePetProfile();
        });

        ivPetImage.setOnClickListener(v -> {
            pickImage();
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1); // Use startActivityForResult for picking image
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivPetImage.setImageURI(imageUri);
        }
    }

    private void savePetProfile() {
        String name = etName.getText().toString().trim();
        String breed = etBreed.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String behavior = etBehavior.getText().toString().trim();
        String bio = etBio.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();

        if (name.isEmpty() || breed.isEmpty() || age.isEmpty() || address.isEmpty() || behavior.isEmpty() || bio.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String emailKey = currentUser.getEmail().replace(".", ",");
            DatabaseReference userPetsRef = databaseReference
                    .child("users")
                    .child(emailKey)
                    .child("pets")
                    .push();

            String petId = userPetsRef.getKey(); // Get the generated key

            // Upload image to Firebase Storage
            uploadProfileImage(currentUser.getUid(), petId);
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Users not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage(String userId, String petId) {
        StorageReference storageRef = storageReference.child("profile_images").child(petId + ".jpg");

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        imageUrl = uri.toString();
                        savePetProfileToDatabase(userId, petId);
                    });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed to upload profile image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void savePetProfileToDatabase(String userId, String petId) {
        String name = etName.getText().toString().trim();
        String breed = etBreed.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String behavior = etBehavior.getText().toString().trim();
        String bio = etBio.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();

        Map<String, Object> petProfile = new HashMap<>();
        petProfile.put("name", name);
        petProfile.put("breed", breed);
        petProfile.put("age", age);
        petProfile.put("address", address);
        petProfile.put("behavior", behavior);
        petProfile.put("bio", bio);
        petProfile.put("category", category);
        petProfile.put("gender", gender);
        petProfile.put("imageUrl", imageUrl);

        String emailKey = auth.getCurrentUser().getEmail().replace(".", ",");
        DatabaseReference userPetsRef = databaseReference.child("users").child(emailKey).child("pets").child(petId);

        userPetsRef.setValue(petProfile)
                .addOnSuccessListener(aVoid -> {

                    DatabaseReference sharedPetsRef = databaseReference.child("pets").child(petId);
                    sharedPetsRef.setValue(petProfile)
                            .addOnSuccessListener(aVoid1 -> {
                                progressDialog.dismiss();
                                Toast.makeText(CreatePetProfile.this, "Pet profile saved successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Finish activity after saving
                            })
                            .addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(this, "Failed to save pet profile to shared pets: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(CreatePetProfile.this, "Failed to save pet profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
