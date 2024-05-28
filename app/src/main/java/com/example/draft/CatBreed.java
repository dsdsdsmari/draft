package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CatBreed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cat_breed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imageViewPersian = findViewById(R.id.imageViewPersian);
        ImageView imageViewRagdoll = findViewById(R.id.imageViewRagdoll);
        ImageView imageViewScottishFold = findViewById(R.id.imageViewScottishFold);
        ImageView imageViewSiamese = findViewById(R.id.imageViewSiamese);

        imageViewPersian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.persian);
                intent.putExtra("petType", "The Persian cat is a long-haired breed known for its luxurious coat and distinctive flat face. They have a broad, round head with large, expressive eyes and a short, snub nose. Persians come in a variety of colors and patterns, from solid shades to bi-color and tabby variations. Known for their calm and gentle temperament, Persians are typically laid-back and enjoy lounging in comfortable environments. They require regular grooming due to their thick, dense fur, which can mat if not properly cared for. Persians are affectionate and enjoy companionship, often forming strong bonds with their owners.");
                startActivity(intent);
            }
        });

        imageViewRagdoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.ragdoll);
                intent.putExtra("petType", "The Ragdoll cat is a large, semi-longhaired breed known for its striking blue eyes and affectionate nature. They have a soft, silky coat that comes in colorpoint patterns, typically with a lighter body and darker points on the ears, face, paws, and tail. Ragdolls are known for their relaxed and docile temperament, often going limp when picked up, hence the name \"Ragdoll.\" They are friendly, gentle, and enjoy being around people, making them great companions for families and individuals alike. Ragdolls are relatively low-maintenance in terms of grooming but benefit from regular brushing to keep their coat in good condition.");
                startActivity(intent);
            }
        });

        imageViewScottishFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.scottish_fold);
                intent.putExtra("petType", "The Scottish Fold cat is known for its unique folded ears that give it an owl-like appearance. They have a round face with large, expressive eyes and a sturdy body with a dense, plush coat. Scottish Folds come in various colors and patterns, including solid, tabby, and bi-color. They are known for their sweet and gentle temperament, often being affectionate and loyal companions. Scottish Folds enjoy human interaction and are generally good with children and other pets. Due to their folded ears, they may require occasional ear cleaning to prevent wax buildup.");
                startActivity(intent);
            }
        });

        imageViewSiamese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.siamese);
                intent.putExtra("petType", "The Siamese cat is a sleek and elegant breed known for its striking blue almond-shaped eyes and short coat with color points on the ears, face, paws, and tail. They have a slender, athletic build and a wedge-shaped head with large ears. Siamese cats are highly vocal and social, often forming strong bonds with their owners. They are intelligent, curious, and playful, enjoying interactive toys and activities. Siamese cats thrive on attention and companionship, making them affectionate and loyal pets that enjoy being involved in household activities.");
                startActivity(intent);
            }
        });
    }
}