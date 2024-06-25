package com.example.draft;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPets extends AppCompatActivity {

    private RecyclerView myPetsRecyclerView;
    private MyPetsAdapter myPetsAdapter;
    private List<Pet> myPets;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_pets);

        myPetsRecyclerView = findViewById(R.id.myPetRecyclerView);
        myPetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myPets = new ArrayList<>();
        myPetsAdapter = new MyPetsAdapter(this, myPets);
        myPetsRecyclerView.setAdapter(myPetsAdapter);

        loadMyPets();
    }

    private void loadMyPets() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            return;
        }

        String userEmail = currentUser.getEmail().replace(".", ",");
        DatabaseReference userPetsRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userEmail)
                .child("pets");

        userPetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPets.clear();

                for (DataSnapshot petSnapshot : snapshot.getChildren()) {
                    Pet pet = petSnapshot.getValue(Pet.class);
                    if (pet != null) {
                        pet.setId(petSnapshot.getKey());
                        myPets.add(pet);
                    }
                }

                myPetsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyPetsActivity", "Error fetching my pets data: " + error.getMessage());
            }
        });
    }
}