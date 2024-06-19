package com.example.draft;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdoptNow extends AppCompatActivity {

    private CardAdapter cardAdapter;
    private List<Integer> imageResources;
    private List<String> petNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageResources = new ArrayList<>();
        imageResources.add(R.drawable.bulldog_1);
        imageResources.add(R.drawable.corgi_1);
        imageResources.add(R.drawable.german_shepherd_1);
        imageResources.add(R.drawable.golden_retriever_1);
        imageResources.add(R.drawable.poodle_1);
        imageResources.add(R.drawable.shih_tzu_1);
        imageResources.add(R.drawable.persian_1);
        imageResources.add(R.drawable.ragdoll_1);
        imageResources.add(R.drawable.scottish_fold_1);
        imageResources.add(R.drawable.siamese_1);
        imageResources.add(R.drawable.shih_tzu_2);
        imageResources.add(R.drawable.poodle_2);
        imageResources.add(R.drawable.golden_retriever_2);
        imageResources.add(R.drawable.german_shepherd_2);
        imageResources.add(R.drawable.corgi_2);
        imageResources.add(R.drawable.bulldog_2);
        imageResources.add(R.drawable.siamese_2);
        imageResources.add(R.drawable.scottish_fold_2);
        imageResources.add(R.drawable.ragdoll_2);
        imageResources.add(R.drawable.persian_2);

        petNames = new ArrayList<>();
        petNames.add("Max");
        petNames.add("Bella");
        petNames.add("Charlie");
        petNames.add("Lucy");
        petNames.add("Cooper");
        petNames.add("Daisy");
        petNames.add("Whiskers");
        petNames.add("Luna");
        petNames.add("Shadow");
        petNames.add("Oliver");
        petNames.add("Buddy");
        petNames.add("Luna");
        petNames.add("Rocky");
        petNames.add("Molly");
        petNames.add("Duke");
        petNames.add("Lyn");
        petNames.add("Bella");
        petNames.add("Simba");
        petNames.add("Cleo");
        petNames.add("Milo");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(imageResources, this, petNames);
        recyclerView.setAdapter(cardAdapter);

        ItemTouchHelper itemTouchHelper = getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @NonNull
    private ItemTouchHelper getItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String petName = petNames.get(position);
                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(AdoptNow.this, "Save to Favorites!", Toast.LENGTH_SHORT).show();
                } else if (direction == ItemTouchHelper.RIGHT) {
//                    Toast.makeText(AdoptNow.this, "Swiped Right!", Toast.LENGTH_SHORT).show();
                }
                imageResources.remove(position);
                petNames.remove(position);
                cardAdapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        return itemTouchHelper;
    }
}