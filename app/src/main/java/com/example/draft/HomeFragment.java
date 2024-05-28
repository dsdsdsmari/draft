package com.example.draft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);

        ImageView maxImageView = view.findViewById(R.id.max);
        ImageView lunaImageView = view.findViewById(R.id.luna);
        ImageView charlieImageView = view.findViewById(R.id.charlie);
        ImageView shadowImageView = view.findViewById(R.id.shadow);
        ImageView cooperImageView = view.findViewById(R.id.cooper);
        ImageView oliverImageView = view.findViewById(R.id.oliver);

        maxImageView.setOnClickListener(v -> onImageClick(v));
        lunaImageView.setOnClickListener(v -> onImageClick(v));
        charlieImageView.setOnClickListener(v -> onImageClick(v));
        shadowImageView.setOnClickListener(v -> onImageClick(v));
        cooperImageView.setOnClickListener(v -> onImageClick(v));
        oliverImageView.setOnClickListener(v -> onImageClick(v));

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

        return view;
    }

    public void onImageClick(View view) {
        Context context = getActivity();
        Intent intent = new Intent(context, PetsYouMayKnow.class);

        int id = view.getId();
        if (id == R.id.max) {
            intent.putExtra("petImage", R.drawable.bulldog_1);
            intent.putExtra("about", "Max is a one-year-old Bulldog with a lovable and charming personality. Bulldogs are known for their distinctive wrinkled faces and sturdy builds, and Max is no exception. He has a sleek, short coat that is easy to maintain and a distinctive pushed-in nose that gives him a unique look. Despite his somewhat serious appearance, Max is a playful and affectionate companion who loves to spend time with his family.");
            intent.putExtra("personalityTraits", "Max is incredibly affectionate and loves to cuddle with his owners. He enjoys being petted and often seeks out attention and affection. At one year old, Max is still quite playful. He enjoys short bursts of playtime and loves toys that he can chew on or fetch. Bulldogs are known for their gentle demeanor, and Max is no different. He is calm and patient, making him great around children and other pets. Like many Bulldogs, Max can be a bit stubborn at times. Training requires patience and consistency, but he is eager to please once he understands what is expected of him.");
        }

        else if (id == R.id.luna) {
            intent.putExtra("petImage", R.drawable.persian_1);
            intent.putExtra("about", "Luna is a delightful 3-year-old Persian cat with a regal and elegant appearance. Her luxurious, long fur and strikingly expressive eyes make her a stunning companion. Persians are known for their gentle and calm demeanor, and Luna is no exception. She enjoys lounging in sunny spots, being pampered with regular grooming sessions, and basking in the affection of her human companions. Luna is not only a beautiful addition to any home but also a source of comfort and companionship.");
            intent.putExtra("personalityTraits", "Luna loves to be around people and enjoys gentle petting and cuddles. She often seeks out her owners for a cozy snuggle. True to her Persian breed, Luna has a very relaxed and calm nature. She enjoys a serene and peaceful environment. Luna forms strong bonds with her family members and is very loyal. She likes to follow her favorite humans around the house. Despite her calm demeanor, Luna has her playful moments. She enjoys batting at feather toys and chasing after laser pointers. Luna appreciates her alone time and is perfectly content entertaining herself when her humans are busy.");
        }

        else if (id == R.id.charlie) {
            intent.putExtra("petImage", R.drawable.german_shepherd_2);
            intent.putExtra("about", "Charlie is a spirited 2-year-old German Shepherd with a vibrant personality. His sleek, muscular build and striking black and tan coat make him a standout in any crowd. German Shepherds are known for their intelligence, loyalty, and versatility, and Charlie is no exception. He thrives on physical activity and mental stimulation, making him an excellent companion for an active individual or family.");
            intent.putExtra("personalityTraits", "Charlie forms strong bonds with his family and is incredibly loyal. He loves being around his human companions and is always eager to please. With a sharp mind and a keen ability to learn, Charlie picks up new commands and tricks quickly. He enjoys problem-solving games and activities that challenge his intellect. Full of energy, Charlie loves to run, play fetch, and go on long walks. He needs regular exercise to keep him happy and healthy. As a typical German Shepherd, Charlie has a natural protective instinct. He is watchful and alert, making him an excellent watchdog. Despite his protective nature, Charlie is friendly and sociable, especially when properly introduced to new people and pets.");
        }

        else if (id == R.id.shadow) {
            intent.putExtra("petImage", R.drawable.siamese_2);
            intent.putExtra("about", "Shadow is a strikingly beautiful Siamese cat with piercing blue eyes and a sleek, elegant coat. At 3 years old, she is in her prime, combining youthful energy with a mature and loving demeanor. Shadow is known for her intelligence and inquisitive nature, always exploring her surroundings and engaging with her favorite humans.");
            intent.putExtra("personalityTraits", "Shadow loves to cuddle and often seeks out attention from her owners. She is happiest when she is close to her family, whether it's sitting on laps or snuggling in bed. Like most Siamese cats, Shadow is very vocal and loves to \"talk\" to her owners. She uses a variety of meows, purrs, and chirps to communicate her needs and desires. Despite her elegant appearance, Shadow is quite playful. She enjoys chasing after toys, playing hide and seek, and engaging in interactive games. Shadow is highly intelligent and can quickly learn new tricks and commands. She enjoys mental stimulation through puzzle toys and interactive play. Shadow is a loyal companion who forms strong bonds with her family. She can be quite protective and is always there to offer comfort and companionship.");
        }

        else if (id == R.id.cooper) {
            intent.putExtra("petImage", R.drawable.golden_retriever_2);
            intent.putExtra("about", "Cooper is a vibrant and energetic 2-year-old Golden Retriever. With his golden fur and friendly demeanor, he quickly becomes the heart of any gathering. Cooper loves spending time outdoors, especially running and playing fetch in the park. His playful nature is balanced by his gentle and affectionate side, making him a wonderful companion for both active adventures and cozy cuddle sessions. He’s well-socialized, gets along well with other dogs, and is known for his intelligence and eagerness to please.");
            intent.putExtra("personalityTraits", "Cooper is extremely friendly and enjoys meeting new people and animals. His wagging tail and joyful demeanor make him a favorite among neighbors and fellow dog park visitors. Full of energy, Cooper loves to play. Whether it’s a game of fetch, tug-of-war, or chasing after toys, he is always up for fun. Cooper is a quick learner and enjoys training sessions. He is always eager to learn new tricks and commands, making training a rewarding experience. As a true Golden Retriever, Cooper is very loyal to his family. He loves to follow his owners around and always wants to be involved in whatever they are doing. Cooper enjoys the company of other dogs and is well-behaved in social settings. He loves making new canine friends and playing in groups.");
        }

        else if (id == R.id.oliver) {
            intent.putExtra("petImage", R.drawable.scottish_fold_1);
            intent.putExtra("about", "Oliver is a male cat who is 1 year old. Scottish Folds are medium-sized cats with a rounded head and a sweet, expressive face. They have a dense and soft coat, typically short-haired but can also be long-haired.");
            intent.putExtra("personalityTraits", "Scottish Folds like Oliver are often very affectionate and enjoy being close to their human companions. They tend to have a gentle and calm demeanor, making them great companions for families and individuals alike. Scottish Folds, including Oliver, are adaptable to different environments and typically get along well with other pets. They are intelligent cats that can learn tricks and enjoy mental stimulation.");
        }

        context.startActivity(intent);
    }


}
