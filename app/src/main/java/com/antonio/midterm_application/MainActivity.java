package com.antonio.midterm_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private List<Cleaner> allCleaners; // Store all cleaners
    private List<Cleaner> featuredCleaners; // Store only featured cleaners

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Create cleaner data first
        createCleanerData();

        // Initialize the category list
        List<String> categories = new ArrayList<>();
        categories.add("All Categories");
        categories.add("Cleaning Service");
        categories.add("Cleaning Appliance");
        categories.add("Babysitter Service");
        categories.add("Car Wash");
        categories.add("Aircon Service");
        categories.add("Laundry Service");
        categories.add("Pest Control");
        categories.add("Gardening Service");

        // Set up RecyclerView for categories
        RecyclerView recyclerViewCategories = findViewById(R.id.recyclerCategories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(layoutManager);

        // Create and set the adapter with filtering logic
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, category -> {
            // Create intent to SelectionActivity
            Intent intent = new Intent(MainActivity.this, SelectedCategoryActivity.class);
            intent.putExtra("SELECTION_TYPE", "CATEGORY");
            intent.putExtra("CATEGORY_NAME", category);
            intent.putExtra("CATEGORY_FILTER", category);
            startActivity(intent);
        });
        recyclerViewCategories.setAdapter(categoryAdapter);

        // Show only featured cleaners initially
        displayCleaners(featuredCleaners);
    }

    private void createCleanerData() {
        allCleaners = new ArrayList<>();
        allCleaners.add(new Cleaner("John Smith", 28, 4.7, "ic_person", "+1-555-123-4567", "123 Main St", "Cleaning Service"));
        allCleaners.add(new Cleaner("Mary Johnson", 35, 4.9, "ic_person", "+1-555-987-6543", "456 Oak Ave", "Cleaning Service"));
        allCleaners.add(new Cleaner("Robert Davis", 42, 4.5, "ic_person", "+1-555-234-5678", "789 Pine Rd", "Cleaning Appliance"));
        allCleaners.add(new Cleaner("Sarah Wilson", 31, 4.8, "ic_person", "+1-555-345-6789", "101 Elm Blvd", "Babysitter Service"));
        allCleaners.add(new Cleaner("Michael Brown", 26, 4.4, "ic_person", "+1-555-456-7890", "202 Maple Dr", "Car Wash"));
        allCleaners.add(new Cleaner("Emma Davis", 29, 4.6, "ic_person", "+1-555-567-8901", "303 Cedar St", "Aircon Service"));
        allCleaners.add(new Cleaner("James Wilson", 33, 4.9, "ic_person", "+1-555-678-9012", "404 Birch Ave", "Laundry Service"));
        allCleaners.add(new Cleaner("Olivia Martin", 27, 4.7, "ic_person", "+1-555-789-0123", "505 Pine Blvd", "Pest Control"));
        allCleaners.add(new Cleaner("William Johnson", 36, 4.5, "ic_person", "+1-555-890-1234", "606 Oak Dr", "Gardening Service"));

        // Create featured cleaners list (top 3 rated cleaners)
        featuredCleaners = allCleaners.stream()
                .sorted(Comparator.comparingDouble(Cleaner::getRating).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private void displayCleaners(List<Cleaner> cleaners) {
        RecyclerView recyclerViewCleaners = findViewById(R.id.recyclerCleaners);
        recyclerViewCleaners.setLayoutManager(new LinearLayoutManager(this));

        // Update title above the recycler view to indicate these are featured cleaners
        TextView cleanersTitle = findViewById(R.id.cleaners_title);
        if (cleanersTitle != null) {
            cleanersTitle.setText("Featured Cleaners");
        }

        CleanerAdapter cleanerAdapter = new CleanerAdapter(cleaners, cleaner -> {
            Intent intent = new Intent(MainActivity.this, CleanerProfileActivity.class);
            intent.putExtra("NAME", cleaner.getName());
            intent.putExtra("AGE", cleaner.getAge());
            intent.putExtra("RATING", cleaner.getRating());
            intent.putExtra("PHONE", cleaner.getPhoneNumber());
            intent.putExtra("ADDRESS", cleaner.getAddress());
            intent.putExtra("CATEGORY", cleaner.getCategory());
            intent.putExtra("IMAGE_URL", cleaner.getImageUrl());
            startActivity(intent);
        });

        recyclerViewCleaners.setAdapter(cleanerAdapter);
    }
}