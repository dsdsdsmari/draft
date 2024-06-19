package com.example.draft;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PetsYouMayKnow extends AppCompatActivity {

    private ImageView petImageView;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView ageTextView;
    private TextView genderTextView;
    private TextView breedTextView;
    private TextView categoryTextView;
    private TextView behaviourTextView;
    private TextView bioTextView;
    private TextView ownerNameTextView;
    private TextView ownerNumberTextView;
    private CardView ownerCardView;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_you_may_know);

        // Initialize views
        petImageView = findViewById(R.id.pet_image);
        nameTextView = findViewById(R.id.name);
        addressTextView = findViewById(R.id.address);
        ageTextView = findViewById(R.id.age);
        genderTextView = findViewById(R.id.gender);
        breedTextView = findViewById(R.id.breed);
        categoryTextView = findViewById(R.id.category);
        behaviourTextView = findViewById(R.id.behaviour);
        bioTextView = findViewById(R.id.bio);
        ownerNameTextView = findViewById(R.id.userName);
        ownerNumberTextView = findViewById(R.id.userPhoneNumber);
        ownerCardView = findViewById(R.id.ownerCardView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // Handle case where user is not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // Finish activity if user is not logged in
            return;
        }

        // Get pet ID from intent
        String petId = getIntent().getStringExtra("PET_ID");
        if (petId == null || petId.isEmpty()) {
            Toast.makeText(this, "Pet ID not found", Toast.LENGTH_SHORT).show();
            finish(); // Finish activity if pet ID is not valid
            return;
        }

        // Reference to the pet in Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("pets")
                .child(petId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get pet details
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    String age = dataSnapshot.child("age").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String breed = dataSnapshot.child("breed").getValue(String.class);
                    String category = dataSnapshot.child("category").getValue(String.class);
                    String behaviour = dataSnapshot.child("behavior").getValue(String.class);
                    String bio = dataSnapshot.child("bio").getValue(String.class);

                    // Set retrieved details to views
                    nameTextView.setText(name);
                    addressTextView.setText(address);
                    ageTextView.setText(age);
                    genderTextView.setText(gender);
                    breedTextView.setText(breed);
                    categoryTextView.setText(category);
                    behaviourTextView.setText(behaviour);
                    bioTextView.setText(bio);

                    // Load image using Glide or another image loading library
                    Glide.with(PetsYouMayKnow.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.profile_icon) // Placeholder image
                            .into(petImageView);

                    // Retrieve owner information
                    retrieveOwnerInformation(petId);
                } else {
                    Toast.makeText(PetsYouMayKnow.this, "Pet not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PetsYouMayKnow", "Error fetching data", error.toException());
            }
        });

    }

    private void retrieveOwnerInformation(String petId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        if (userSnapshot.child("pets").hasChild(petId)) {
                            String ownerFullName = userSnapshot.child("fullName").getValue(String.class);
                            String ownerPhoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);

                            ownerNameTextView.setText(ownerFullName);
                            ownerNumberTextView.setText(ownerPhoneNumber);

                            ownerCardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(PetsYouMayKnow.this, OwnerProfileActivity.class);
                                    intent.putExtra("OWNER_EMAIL", userSnapshot.getKey());
                                    startActivity(intent);
                                }
                            });

                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PetsYouMayKnow", "Error fetching owner data", error.toException());
            }
        });
    }
}
