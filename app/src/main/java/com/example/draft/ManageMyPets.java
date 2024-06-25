package com.example.draft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ManageMyPets extends AppCompatActivity {

    private ImageView petImageView;
    private EditText petNameEditText, petBreedEditText, petAgeEditText, petAddressEditText, petBehaviorEditText, petBriefBioEditText;
    private Spinner categorySpinner, genderSpinner;
    private Button updateButton, adoptedButton, removeButton;

    private String petId;
    private DatabaseReference petRef;
    private StorageReference storageRef;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_my_pets);

        petImageView = findViewById(R.id.pet_image);
        petNameEditText = findViewById(R.id.pet_name);
        petBreedEditText = findViewById(R.id.pet_breed);
        petAgeEditText = findViewById(R.id.pet_age);
        petAddressEditText = findViewById(R.id.pet_address);
        petBehaviorEditText = findViewById(R.id.pet_behavior);
        petBriefBioEditText = findViewById(R.id.pet_BriefBio);
        categorySpinner = findViewById(R.id.spinnerCategory);
        genderSpinner = findViewById(R.id.spinnerGender);
        updateButton = findViewById(R.id.btnUpdate);
        adoptedButton = findViewById(R.id.btnAdopted);
        removeButton = findViewById(R.id.btnRemove);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        Intent intent = getIntent();
        petId = intent.getStringExtra("PET_ID");

        if (petId == null || petId.isEmpty()) {
            Toast.makeText(this, "Pet ID is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        petRef = FirebaseDatabase.getInstance().getReference().child("pets").child(petId);

        loadPetInfo();

        updateButton.setOnClickListener(view -> updatePetInfo());
        adoptedButton.setOnClickListener(view -> markAsAdopted());
        removeButton.setOnClickListener(view -> removePet());

        petImageView.setOnClickListener(v -> chooseImage());

    }

    private void loadPetInfo() {
        petRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PetProfile petProfile = snapshot.getValue(PetProfile.class);
                if (petProfile != null) {
                    petNameEditText.setText(petProfile.getName());
                    petBreedEditText.setText(petProfile.getBreed());
                    petAgeEditText.setText(petProfile.getAge());
                    petAddressEditText.setText(petProfile.getAddress());
                    petBehaviorEditText.setText(petProfile.getBehavior());
                    petBriefBioEditText.setText(petProfile.getBio());

                    categorySpinner.setSelection(getIndex(categorySpinner, petProfile.getCategory()));
                    genderSpinner.setSelection(getIndex(genderSpinner, petProfile.getGender()));

                    Glide.with(ManageMyPets.this).load(petProfile.getImageUrl()).into(petImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ManageMyPetActivity", "Error fetching pet data: " + error.getMessage());
            }
        });
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    private void updatePetInfo() {
        String name = petNameEditText.getText().toString();
        String breed = petBreedEditText.getText().toString();
        String age = petAgeEditText.getText().toString();
        String address = petAddressEditText.getText().toString();
        String behavior = petBehaviorEditText.getText().toString();
        String briefBio = petBriefBioEditText.getText().toString();

        // Get selected values from spinners
        String category = categorySpinner.getSelectedItem().toString();
        String gender = genderSpinner.getSelectedItem().toString();

        // Show progress dialog while updating
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating pet information...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Update pet information in the 'pets' node
        petRef.child("name").setValue(name);
        petRef.child("breed").setValue(breed);
        petRef.child("age").setValue(age);
        petRef.child("address").setValue(address);
        petRef.child("behavior").setValue(behavior);
        petRef.child("briefBio").setValue(briefBio);
        petRef.child("category").setValue(category);
        petRef.child("gender").setValue(gender);

        // Check if there's an image to upload
        if (imageUri != null) {
            uploadImageToStorage(progressDialog);
        } else {
            updatePetUnderUsers(progressDialog); // Update under each user's 'pets' node
        }
    }

    private void uploadImageToStorage(ProgressDialog progressDialog) {
        StorageReference fileRef = storageRef.child(petId + ".jpg");

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageData = baos.toByteArray();

            UploadTask uploadTask = fileRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    petRef.child("imageUrl").setValue(uri.toString())
                            .addOnSuccessListener(aVoid -> {
                                updatePetUnderUsers(progressDialog); // Update under each user's 'pets' node
                            })
                            .addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(ManageMyPets.this, "Failed to update pet info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(ManageMyPets.this, "Failed to retrieve download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                progressDialog.dismiss();
                Toast.makeText(ManageMyPets.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } catch (IOException e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePetUnderUsers(ProgressDialog progressDialog) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        petRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PetProfile updatedPet = snapshot.getValue(PetProfile.class);
                if (updatedPet != null && updatedPet.getUserId() != null) {
                    usersRef.child(updatedPet.getUserId()).child("pets").child(petId).setValue(updatedPet)
                            .addOnSuccessListener(aVoid -> {
                                progressDialog.dismiss();
                                Toast.makeText(ManageMyPets.this, "Pet info updated successfully", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(ManageMyPets.this, "Failed to update pet info under user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ManageMyPets.this, "Failed to retrieve user ID for pet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(ManageMyPets.this, "Error updating pet info: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> imageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    imageUri = data.getData();
                    petImageView.setImageURI(imageUri);
                }
            });

    private void markAsAdopted() {
        petRef.child("adopted").setValue(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Your pet is successfully adopted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to mark as adopted", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void removePet() {
        // Remove pet from 'pets' node under its own ID
        petRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Pet removed", Toast.LENGTH_SHORT).show();
                // Remove pet from 'pets' node under 'users' node
                removePetFromUsersNode();
                finish(); // Optional: Close the activity after removal
            } else {
                Toast.makeText(this, "Failed to remove pet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removePetFromUsersNode() {
        // Get current user's email/key
        String emailKey = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ",");

        // Remove pet from 'pets' node under 'users' node
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(emailKey).child("pets").child(petId);
        usersRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("RemovePet", "Pet removed from user's node");
            } else {
                Log.e("RemovePet", "Failed to remove pet from user's node: " + task.getException().getMessage());
            }
        });
    }

}