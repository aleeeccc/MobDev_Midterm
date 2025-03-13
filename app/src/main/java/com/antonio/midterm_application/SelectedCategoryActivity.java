package com.antonio.midterm_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectedCategoryActivity extends AppCompatActivity {
    private List<Cleaner> allCleaners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        // Get data from intent
        String selectionType = getIntent().getStringExtra("SELECTION_TYPE");
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        String categoryFilter = getIntent().getStringExtra("CATEGORY_FILTER");

        // Set title based on selection type
        TextView titleTextView = findViewById(R.id.selection_title);
        RecyclerView recyclerView = findViewById(R.id.recycler_selection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize cleaner data - this should ideally be shared with MainActivity
        createCleanerData();

        if (selectionType.equals("CATEGORY")) {
            // Display cleaners for this category
            titleTextView.setText(categoryName + " Professionals");

            // Filter cleaners by category
            List<Cleaner> filteredCleaners = getCleanersForCategory(categoryFilter);

            // Set up adapter
            CleanerAdapter adapter = new CleanerAdapter(filteredCleaners, cleaner -> {
                Intent intent = new Intent(SelectedCategoryActivity.this, CleanerProfileActivity.class);
                intent.putExtra("NAME", cleaner.getName());
                intent.putExtra("AGE", cleaner.getAge());
                intent.putExtra("RATING", cleaner.getRating());
                intent.putExtra("PHONE", cleaner.getPhoneNumber());
                intent.putExtra("ADDRESS", cleaner.getAddress());
                intent.putExtra("CATEGORY", cleaner.getCategory());
                intent.putExtra("IMAGE_URL", cleaner.getImageUrl());
                startActivity(intent);
            });

            recyclerView.setAdapter(adapter);
        }

        // Set back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(categoryName);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void createCleanerData() {
        // Same data as in MainActivity
        allCleaners.add(new Cleaner("John Smith", 28, 4.7, "ic_person", "+1-555-123-4567", "123 Main St", "Cleaning Service"));
        allCleaners.add(new Cleaner("Mary Johnson", 35, 4.9, "ic_person", "+1-555-987-6543", "456 Oak Ave", "Cleaning Service"));
        allCleaners.add(new Cleaner("Robert Davis", 42, 4.5, "ic_person", "+1-555-234-5678", "789 Pine Rd", "Cleaning Appliance"));
        allCleaners.add(new Cleaner("Sarah Wilson", 31, 4.8, "ic_person", "+1-555-345-6789", "101 Elm Blvd", "Babysitter Service"));
        allCleaners.add(new Cleaner("Michael Brown", 26, 4.4, "ic_person", "+1-555-456-7890", "202 Maple Dr", "Car Wash"));
        allCleaners.add(new Cleaner("Emma Davis", 29, 4.6, "ic_person", "+1-555-567-8901", "303 Cedar St", "Aircon Service"));
        allCleaners.add(new Cleaner("James Wilson", 33, 4.9, "ic_person", "+1-555-678-9012", "404 Birch Ave", "Laundry Service"));
        allCleaners.add(new Cleaner("Olivia Martin", 27, 4.7, "ic_person", "+1-555-789-0123", "505 Pine Blvd", "Pest Control"));
        allCleaners.add(new Cleaner("William Johnson", 36, 4.5, "ic_person", "+1-555-890-1234", "606 Oak Dr", "Gardening Service"));
    }

    private List<Cleaner> getCleanersForCategory(String category) {
        if (category.equals("All Categories")) {
            return new ArrayList<>(allCleaners);
        } else {
            List<Cleaner> filteredCleaners = new ArrayList<>();
            for (Cleaner cleaner : allCleaners) {
                if (cleaner.getCategory().equals(category)) {
                    filteredCleaners.add(cleaner);
                }
            }
            return filteredCleaners;
        }
    }
}