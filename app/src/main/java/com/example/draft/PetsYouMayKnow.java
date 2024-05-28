package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PetsYouMayKnow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pets_you_may_know);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView petImageView = findViewById(R.id.imageView3);
        TextView aboutTextView = findViewById(R.id.about);
        TextView personalityTraitsTextView = findViewById(R.id.personalityTraits);

        Intent intent = getIntent();
        int petImage = intent.getIntExtra("petImage", -1);
        String about = intent.getStringExtra("about");
        String personalityTraits = intent.getStringExtra("personalityTraits");

        if (petImage != -1) {
            petImageView.setImageResource(petImage);
        }
        aboutTextView.setText(about);
        personalityTraitsTextView.setText(personalityTraits);
    }
}