package com.example.draft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Integer> imageResources;
    private Context context;

    private List<String> petNames;

    public CardAdapter(List<Integer> imageResources, Context context, List<String> petNames) {
        this.imageResources = imageResources;
        this.context = context;
        this.petNames = petNames;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageResources.get(position));
        holder.petNameTextView.setText(petNames.get(position));
    }

    @Override
    public int getItemCount() {
        return imageResources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView petNameTextView;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            petNameTextView = itemView.findViewById(R.id.textView);
        }
    }
}
