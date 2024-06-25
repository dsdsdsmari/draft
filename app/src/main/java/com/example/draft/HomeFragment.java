package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private LinearLayout petLayout;
    private Button button1, button2, button3;
    private FirebaseUser currentUser;

    private DatabaseReference userPetsRef;
    private DatabaseReference sharedPetsRef;

    private Set<String> displayedPetIds;

    public HomeFragment() {
        displayedPetIds = new HashSet<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        petLayout = view.findViewById(R.id.petsumayknow);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String emailKey = currentUser.getEmail().replace(".", ",");
            userPetsRef = FirebaseDatabase.getInstance().getReference().child("users").child(emailKey).child("pets");
            sharedPetsRef = FirebaseDatabase.getInstance().getReference().child("pets");
        }

        EditText btnEditSearch = view.findViewById(R.id.btnEditSearch);
        Button btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = btnEditSearch.getText().toString().trim();
                if (query.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a text", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), PetSearch.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DogBreed.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CatBreed.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BothBreed.class);
                startActivity(intent);
            }
        });

        Button btn_AdoptNow = view.findViewById(R.id.btn_AdoptNow);
        btn_AdoptNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdoptNow.class);
                startActivity(intent);
            }
        });

        Button btn_CreatePetProfile = view.findViewById(R.id.btn_CreatePetProfile);
        btn_CreatePetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreatePetProfile.class);
                startActivity(intent);
            }
        });

        retrievePetsFromDatabase();

        return view;
    }

    private void retrievePetsFromDatabase() {
        if (userPetsRef != null) {
            userPetsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String petId = dataSnapshot.getKey();
                        if (!displayedPetIds.contains(petId)) {
                            String petName = dataSnapshot.child("name").getValue(String.class);
                            String petCategory = dataSnapshot.child("category").getValue(String.class);
                            String petBreed = dataSnapshot.child("breed").getValue(String.class);
                            String petAddress = dataSnapshot.child("address").getValue(String.class);
                            String petImageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                            if (petName != null && petCategory != null && petBreed != null && petAddress != null && petImageUrl != null) {
                                addPetCardView(petImageUrl, petName, petCategory, petBreed, petAddress, petId);
                                displayedPetIds.add(petId);
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to retrieve user's pets: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Fetch pets from shared pets (pets not owned by the current user)
        if (sharedPetsRef != null) {
            sharedPetsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String petId = dataSnapshot.getKey(); // Get the petId (key of the pet)
                        if (!displayedPetIds.contains(petId)) {
                            String petName = dataSnapshot.child("name").getValue(String.class);
                            String petCategory = dataSnapshot.child("category").getValue(String.class);
                            String petBreed = dataSnapshot.child("breed").getValue(String.class);
                            String petAddress = dataSnapshot.child("address").getValue(String.class);
                            String petImageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                            if (petName != null && petCategory != null && petBreed != null && petAddress != null && petImageUrl != null) {
                                addPetCardView(petImageUrl, petName, petCategory, petBreed, petAddress, petId);
                                displayedPetIds.add(petId);
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to retrieve shared pets: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addPetCardView(String petImageUrl, String name, String category, String breed, String address, String petId) {
        View petView = LayoutInflater.from(requireContext()).inflate(R.layout.item_pet_profile, petLayout, false);

        CardView cardView = petView.findViewById(R.id.petCardView);
        ImageView imageView = petView.findViewById(R.id.pet_profile);
        TextView tvPetName = petView.findViewById(R.id.pet_name);
        TextView tvPetBreed = petView.findViewById(R.id.pet_breed);
        TextView tvPetAddress = petView.findViewById(R.id.pet_address);

        Glide.with(requireContext())
                .load(petImageUrl)
                .placeholder(R.drawable.profile_icon)
                .error(R.drawable.bulldog)
                .into(imageView);

        tvPetName.setText(name);
        tvPetBreed.setText(breed);
        tvPetAddress.setText(address);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PetsYouMayKnow.class);
                intent.putExtra("PET_ID", petId);
                startActivity(intent);
            }
        });

        petLayout.addView(petView);
    }

}