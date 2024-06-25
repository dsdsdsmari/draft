package com.example.draft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class FavoritesFragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private FavoritesAdapter favoritesAdapter;
    private List<Pet> favoritePets;
    private FirebaseUser currentUser;

    public FavoritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoritePets = new ArrayList<>();
        favoritesAdapter = new FavoritesAdapter(getContext(), favoritePets);
        favoritesRecyclerView.setAdapter(favoritesAdapter);

        loadFavoritePets();

        return view;
    }

    private void loadFavoritePets() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            return;
        }

        String userEmail = currentUser.getEmail().replace(".", ",");
        DatabaseReference userFavoritesRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userEmail)
                .child("favorites");

        userFavoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoritePets.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    favoritePets.add(pet);
                }
                favoritesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
