package com.example.draft;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PetSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pet_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView petOne = findViewById(R.id.petOne);
        TextView textOne = findViewById(R.id.textOne);
        ImageView petTwo = findViewById(R.id.petTwo);
        TextView textTwo = findViewById(R.id.textTwo);
        ImageView petThree = findViewById(R.id.petThree);
        TextView textThree = findViewById(R.id.textThree);
        ImageView petFour = findViewById(R.id.petFour);
        TextView textFour = findViewById(R.id.textFour);
        ImageView petFive = findViewById(R.id.petFive);
        TextView textFive = findViewById(R.id.textFive);
        ImageView petSix = findViewById(R.id.petSix);
        TextView textSix = findViewById(R.id.textSix);
        ImageView pet7 = findViewById(R.id.pet7);
        TextView text7 = findViewById(R.id.text7);
        ImageView pet8 = findViewById(R.id.pet8);
        TextView text8 = findViewById(R.id.text8);
        ImageView pet9 = findViewById(R.id.pet9);
        TextView text9 = findViewById(R.id.text9);
        ImageView pet10 = findViewById(R.id.pet10);
        TextView text10 = findViewById(R.id.text10);
        ImageView pet11 = findViewById(R.id.pet11);
        TextView text11 = findViewById(R.id.text11);
        ImageView pet12 = findViewById(R.id.pet12);
        TextView text12 = findViewById(R.id.text12);


        String query = getIntent().getStringExtra("query");
        boolean found = false;

        if ("bulldog".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.bulldog_1);
            textOne.setText("Bulldog 1");
            petTwo.setImageResource(R.drawable.bulldog_2);
            textTwo.setText("Bulldog 2");
        }

        else if ("corgi".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.corgi_1);
            textOne.setText("Corgi 1");
            petTwo.setImageResource(R.drawable.corgi_2);
            textTwo.setText("Corgi 2");
        }

        else if ("german shepherd".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.german_shepherd_1);
            textOne.setText("German Shepherd 1");
            petTwo.setImageResource(R.drawable.german_shepherd_2);
            textTwo.setText("German Shepherd 2");
        }

        else if ("golden retriever".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.golden_retriever_1);
            textOne.setText("Golden Retriever 1");
            petTwo.setImageResource(R.drawable.golden_retriever_2);
            textTwo.setText("Golden Retriever 2");
        }

        else if ("poodle".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.poodle_1);
            textOne.setText("Poodle 1");
            petTwo.setImageResource(R.drawable.poodle_2);
            textTwo.setText("Poodle 2");
        }

        else if ("shih tzu".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.shih_tzu_1);
            textOne.setText("Shih Tzu 1");
            petTwo.setImageResource(R.drawable.shih_tzu_2);
            textTwo.setText("Shih Tzu 2");
        }

        else if ("persian".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.persian_1);
            textOne.setText("Persian 1");
            petTwo.setImageResource(R.drawable.persian_2);
            textTwo.setText("Persian 2");
        }

        else if ("ragdoll".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.ragdoll_1);
            textOne.setText("Ragdoll 1");
            petTwo.setImageResource(R.drawable.ragdoll_2);
            textTwo.setText("Ragdoll 2");
        }

        else if ("scottish fold".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.scottish_fold_1);
            textOne.setText("Scottish Fold 1");
            petTwo.setImageResource(R.drawable.scottish_fold_2);
            textTwo.setText("Scottish Fold 2");
        }

        else if ("siamese".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.siamese_1);
            textOne.setText("Siamese 1");
            petTwo.setImageResource(R.drawable.siamese_2);
            textTwo.setText("Siamese 2");
        }

        else if ("dog".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.bulldog_1);
            textOne.setText("Bulldog 1");
            petTwo.setImageResource(R.drawable.bulldog_2);
            textTwo.setText("Bulldog 2");
            petThree.setImageResource(R.drawable.corgi_1);
            textThree.setText("Corgi 1");
            petFour.setImageResource(R.drawable.corgi_2);
            textFour.setText("Corgi 2");
            petFive.setImageResource(R.drawable.german_shepherd_1);
            textFive.setText("German Shepherd 1");
            petSix.setImageResource(R.drawable.german_shepherd_2);
            textSix.setText("German Shepherd 2");
            pet7.setImageResource(R.drawable.golden_retriever_1);
            text7.setText("Golden Retriever 1");
            pet8.setImageResource(R.drawable.golden_retriever_2);
            text8.setText("Golden Retriever 2");
            pet9.setImageResource(R.drawable.poodle_1);
            text9.setText("Poodle 1");
            pet10.setImageResource(R.drawable.poodle_2);
            text10.setText("Poodle 2");
            pet11.setImageResource(R.drawable.shih_tzu_1);
            text11.setText("Shih Tzu 1");
            pet12.setImageResource(R.drawable.shih_tzu_2);
            text12.setText("Shih Tzu 2");
        }

        else if ("cat".equalsIgnoreCase(query)) {
            petOne.setImageResource(R.drawable.persian_1);
            textOne.setText("Persian 1");
            petTwo.setImageResource(R.drawable.persian_2);
            textTwo.setText("Persian 2");
            petThree.setImageResource(R.drawable.ragdoll_1);
            textThree.setText("Ragdoll 1");
            petFour.setImageResource(R.drawable.ragdoll_2);
            textFour.setText("Ragdoll 2");
            petFive.setImageResource(R.drawable.scottish_fold_1);
            textFive.setText("Scottish Fold 1");
            petSix.setImageResource(R.drawable.scottish_fold_2);
            textSix.setText("Scottish Fold 2");
            pet7.setImageResource(R.drawable.siamese_1);
            text7.setText("Siamese 1");
            pet8.setImageResource(R.drawable.siamese_2);
            text8.setText("Siamese 2");
        }

        else {
            Toast.makeText(this, query + " not found", Toast.LENGTH_SHORT).show();
        }

    }
}