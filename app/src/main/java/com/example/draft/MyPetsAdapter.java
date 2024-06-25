package com.example.draft;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.util.List;

public class MyPetsAdapter extends RecyclerView.Adapter<MyPetsAdapter.ViewHolder> {
    private Context context;
    private List<Pet> myPets;

    public MyPetsAdapter(Context context, List<Pet> myPets) {
        this.context = context;
        this.myPets = myPets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_pet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = myPets.get(position);
        holder.nameTextView.setText(pet.getName());
        holder.petBreed.setText(pet.getBreed());
        holder.petAddress.setText(pet.getAddress());
        Glide.with(context).load(pet.getImageUrl()).into(holder.petImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ManageMyPets.class);
            intent.putExtra("PET_ID", pet.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myPets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView petBreed;
        public ImageView petImageView;
        public TextView petAddress; 
        
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pet_name);
            petImageView = itemView.findViewById(R.id.pet_image);
            petBreed = itemView.findViewById(R.id.pet_breed);
            petAddress = itemView.findViewById(R.id.pet_address);
            
        }
    }
}
