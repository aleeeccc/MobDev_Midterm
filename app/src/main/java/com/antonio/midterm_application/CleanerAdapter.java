package com.antonio.midterm_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CleanerAdapter extends RecyclerView.Adapter<CleanerAdapter.ViewHolder> {
    private List<Cleaner> cleaners;
    private OnCleanerClickListener listener;

    public CleanerAdapter(List<Cleaner> cleaners, OnCleanerClickListener listener) {
        this.cleaners = cleaners;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cleaner_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cleaner cleaner = cleaners.get(position);
        holder.name.setText(cleaner.getName());
        holder.category.setText(cleaner.getCategory());
        holder.rating.setText(String.valueOf(cleaner.getRating()));
        holder.ratingBar.setRating((float) cleaner.getRating());
        holder.age.setText(cleaner.getAge() + " years old");

        // Set availability text
        TextView availabilityText = holder.itemView.findViewById(R.id.cleaner_availability);
        availabilityText.setText(cleaner.getAvailability());

        // Set a placeholder image
        ImageView imageView = holder.itemView.findViewById(R.id.cleaner_image);
        imageView.setImageResource(R.drawable.ic_person);

        holder.itemView.setOnClickListener(v -> listener.onCleanerClick(cleaner));
    }

    @Override
    public int getItemCount() {
        return cleaners.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, category, rating, age;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cleaner_name);
            category = itemView.findViewById(R.id.cleaner_category);
            rating = itemView.findViewById(R.id.cleaner_rating);
            ratingBar = itemView.findViewById(R.id.cleaner_rating_bar);
            age = itemView.findViewById(R.id.cleaner_age);
        }
    }

    public interface OnCleanerClickListener {
        void onCleanerClick(Cleaner cleaner);
    }

    public void updateData(List<Cleaner> newData) {
        this.cleaners = newData;
        notifyDataSetChanged();
    }
}

