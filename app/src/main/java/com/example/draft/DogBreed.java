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

public class DogBreed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dog_breed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imageViewBulldog = findViewById(R.id.imageViewBulldog);
        ImageView imageViewCorgi = findViewById(R.id.imageViewCorgi);
        ImageView imageViewGermanShepherd = findViewById(R.id.imageViewGermanShepherd);
        ImageView imageViewGoldenRetriever = findViewById(R.id.imageViewGoldenRetriever);
        ImageView imageViewPoodle = findViewById(R.id.imageViewPoodle);
        ImageView imageViewShihTzu = findViewById(R.id.imageViewShihTzu);

        imageViewBulldog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.bulldog);
                intent.putExtra("petType", "A bulldog is a medium-sized breed known for its muscular, sturdy build and distinctive wrinkled face. They have a short, smooth coat, typically in colors like white, brindle, and various shades of fawn. Bulldogs are characterized by their pushed-in nose, broad shoulders, and loose, saggy skin, especially around their neck and face. Despite their somewhat intimidating appearance, bulldogs are generally gentle, affectionate, and great with children. They are known for their loyal and courageous nature, making them excellent companions and family pets.");
                startActivity(intent);
            }
        });

        imageViewCorgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.corgi);
                intent.putExtra("petType", "A corgi is a small herding breed known for its short legs, long body, and distinctive fox-like face. They have a double coat that can be red, sable, fawn, or tricolor, often with white markings. Corgis are energetic, intelligent, and agile, making them excellent at herding despite their size. They are affectionate and loyal, forming strong bonds with their families and often showing a playful, friendly demeanor. Corgis are also known for their expressive ears and tail, which add to their charming and distinctive appearance.");
                startActivity(intent);
            }
        });

        imageViewGermanShepherd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.german_shepherd);
                intent.putExtra("petType", "A German Shepherd is a large, intelligent, and versatile breed known for its strength, loyalty, and protective nature. They have a double coat that is dense and weather-resistant, typically in colors like black and tan, sable, or all black. German Shepherds are highly trainable and excel in various roles such as police and military work, search and rescue, and as service dogs. They are known for their courage, confidence, and keen sense of duty, making them excellent working dogs and devoted family pets. With a strong, athletic build and a sharp, alert expression, German Shepherds are both powerful and graceful in their movements");
                startActivity(intent);
            }
        });

        imageViewGoldenRetriever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.golden_retriever);
                intent.putExtra("petType", "A Golden Retriever is a medium to large breed known for its friendly, gentle temperament and beautiful golden coat. They have a dense, water-repellent outer coat and a soft undercoat, typically in shades of cream to rich gold. Golden Retrievers are highly intelligent, easy to train, and excel in obedience, making them popular choices for service, therapy, and search and rescue work. They are incredibly loyal, social, and good-natured, often getting along well with children and other pets. With their expressive eyes and wagging tails, Golden Retrievers exude warmth and enthusiasm, making them beloved family companions.");
                startActivity(intent);
            }
        });

        imageViewPoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.poodle);
                intent.putExtra("petType", "The Poodle is a highly intelligent and elegant breed known for its curly, hypoallergenic coat that comes in various solid colors such as black, white, apricot, and silver. They have a distinctive appearance with a long, straight muzzle and an athletic build. Poodles are known for their alertness, trainability, and versatility, excelling in obedience, agility, and water retrieving sports. They are often seen in three size varieties: standard, miniature, and toy, each sharing the breed's characteristic intelligence and charm. Poodles have a proud bearing and a lively personality, making them not only skilled performers but also delightful companions.");
                startActivity(intent);
            }
        });

        imageViewShihTzu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogBreed.this, PetDescription.class);
                intent.putExtra("petImage", R.drawable.shih_tzu);
                intent.putExtra("petType", "The Shih Tzu is a small toy breed known for its luxurious, flowing coat and distinctive facial features, including a pushed-in nose and large, round eyes. They have a sturdy, compact body with a tail that curls over the back. Shih Tzus were originally bred as companion dogs and are cherished for their affectionate and outgoing nature. They come in various coat colors and combinations, often with a range of markings. Shih Tzus are known for their playful and lively demeanor, making them excellent indoor pets and companions for families and individuals alike.");
                startActivity(intent);
            }
        });
    }
}