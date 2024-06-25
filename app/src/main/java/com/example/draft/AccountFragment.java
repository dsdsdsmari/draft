package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    TextView userFullName, userEmail;
    CircleImageView profileImage;
    Button editProfileButton;

    DatabaseReference userDatabaseReference;
    FirebaseAuth firebaseAuth;

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        userFullName = view.findViewById(R.id.userFullName);
        userEmail = view.findViewById(R.id.userEmail);
        editProfileButton = view.findViewById(R.id.editProfile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String email = currentUser.getEmail();
            userEmail.setText(email);

            String emailKey = email.replace(".", ",");
            userDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(emailKey);
            userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String fullName = dataSnapshot.child("fullName").getValue(String.class);
                        userFullName.setText(fullName);

                        // Retrieve profile image URL and load it using Glide
                        String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
                        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                            Glide.with(getActivity())
                                    .load(profileImageUrl)
                                    .placeholder(R.drawable.profile_icon) // Add a default placeholder
                                    .into(profileImage);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = userFullName.getText().toString();
                String email = userEmail.getText().toString();
                Intent intent = new Intent(getActivity(), EditProfile.class);
                intent.putExtra("fullName", fullName);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Button petPreferences = view.findViewById(R.id.petPreferences);
        petPreferences.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PetPreferences.class);
            startActivity(intent);
        });

        Button myPets = view.findViewById(R.id.myPets);
        myPets.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyPets.class);
            startActivity(intent);
        });

        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        return view;
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), "Successfully logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
        startActivity(intent);
    }


}
