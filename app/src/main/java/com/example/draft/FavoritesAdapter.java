package com.example.draft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private Context context;
    private List<Pet> favoritePets;

    public FavoritesAdapter(Context context, List<Pet> favoritePets) {
        this.context = context;
        this.favoritePets = favoritePets;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_pet, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = favoritePets.get(position);
        holder.nameTextView.setText(pet.getName());
        holder.petBreed.setText(pet.getBreed());
        holder.petAddress.setText(pet.getAddress());
        Glide.with(context).load(pet.getImageUrl()).into(holder.petImageView);
    }

    public int getItemCount() {
        return favoritePets.size();
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
