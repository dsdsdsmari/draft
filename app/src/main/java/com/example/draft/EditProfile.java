package com.example.draft;

import static com.example.draft.MapActivity.REQUEST_CODE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    EditText editFullName, editEmail, editPhoneNumber;
    CircleImageView profileImageView;
    Button updateButton;
    Uri imageUri;
    FirebaseUser currentUser;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        profileImageView = findViewById(R.id.profile_image);
        editFullName = findViewById(R.id.editFullName);
        editEmail = findViewById(R.id.editEmail);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        updateButton = findViewById(R.id.btnUpdate);

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        editEmail.setFocusable(false);
        editEmail.setFocusableInTouchMode(false);
        editEmail.setClickable(false);
        editEmail.setInputType(0);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        String email = currentUser.getEmail();
        if (email != null) {
            String emailKey = email.replace(".", ",");
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(emailKey);

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("fullName").getValue(String.class);
                        String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                        String profileImageUrl = snapshot.child("profileImageUrl").getValue(String.class);

                        editFullName.setText(fullName);
                        editEmail.setText(email);
                        editPhoneNumber.setText(phoneNumber);

                        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                            Glide.with(EditProfile.this)
                                    .load(profileImageUrl)
                                    .placeholder(R.drawable.profile_icon)
                                    .into(profileImageView);

                            // Save the profile image URL to SharedPreferences
                            sharedPreferences.edit().putString("profileImageUrl", profileImageUrl).apply();
                        } else {
                            // Load the default profile image if no URL is found
                            profileImageView.setImageResource(R.drawable.profile_icon);
                        }
                    } else {
                        Toast.makeText(EditProfile.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(EditProfile.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }
    
    private void updateUserProfile() {
        String email = currentUser.getEmail();
        if (email != null) {
            String emailKey = email.replace(".", ",");
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child(emailKey);
            
            String fullName = editFullName.getText().toString().trim();
            String phoneNumber = editPhoneNumber.getText().toString().trim();
            
            if (fullName.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Full Name and Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            usersRef.child("fullName").setValue(fullName);
            usersRef.child("phoneNumber").setValue(phoneNumber);
            
            if (imageUri != null) {
                uploadProfileImage(emailKey);
            } else {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadProfileImage(String emailKey) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_images").child(emailKey + ".jpg");

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(emailKey);
                        usersRef.child("profileImageUrl").setValue(imageUrl).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                sharedPreferences.edit().putString("profileImageUrl", imageUrl).apply();
                            } else {
                                Toast.makeText(EditProfile.this, "Failed to save profile image URL", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(e -> Toast.makeText(EditProfile.this, "Failed to retrieve profile image URL", Toast.LENGTH_SHORT).show());
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Failed to upload profile image", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
        }

    }
}