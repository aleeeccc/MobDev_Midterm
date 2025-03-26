package com.antonio.midterm_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SelectedCategoryActivity extends AppCompatActivity {
    private List<Cleaner> allCleaners = new ArrayList<>();
    private CleanerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        String selectionType = getIntent().getStringExtra("SELECTION_TYPE");
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        String categoryFilter = getIntent().getStringExtra("CATEGORY_FILTER");

        TextView titleTextView = findViewById(R.id.selection_title);
        RecyclerView recyclerView = findViewById(R.id.recycler_selection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadDataFromJson();

        if (selectionType.equals("CATEGORY")) {
            titleTextView.setText(categoryName + " Professionals");

            List<Cleaner> filteredCleaners = getCleanersForCategory(categoryFilter);

            adapter = new CleanerAdapter(filteredCleaners, cleaner -> {
                Intent intent = new Intent(SelectedCategoryActivity.this, CleanerProfileActivity.class);
                intent.putExtra("NAME", cleaner.getName());
                intent.putExtra("AGE", cleaner.getAge());
                intent.putExtra("RATING", cleaner.getRating());
                intent.putExtra("PHONE", cleaner.getPhoneNumber());
                intent.putExtra("ADDRESS", cleaner.getAddress());
                intent.putExtra("CATEGORY", cleaner.getCategory());
                intent.putExtra("IMAGE_URL", cleaner.getImageUrl());
                intent.putExtra("ATTITUDE", cleaner.getAttitude());
                intent.putExtra("CLEANING_QUALITY", cleaner.getCleaningQuality());
                intent.putExtra("CUSTOMER_SATISFACTION", cleaner.getCustomerSatisfaction());
                intent.putExtra("YEARS_OF_EXPERIENCE", cleaner.getYearsOfExperience());
                intent.putExtra("ABOUT", cleaner.getAbout());
                startActivity(intent);
            });

            recyclerView.setAdapter(adapter);
            setupSearch();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(categoryName);
        }
    }

    private void loadDataFromJson() {
        try {
            JSONObject jsonObject = JSONParserActivity.getJsonData(this, "data.json");
            allCleaners = JSONParserActivity.parseCleanersFromJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSearch() {
        SearchView searchView = findViewById(R.id.searchViewSelection);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCleaners(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCleaners(newText);
                return false;
            }
        });
    }

    private void filterCleaners(String query) {
        List<Cleaner> tempList = new ArrayList<>();
        if (query.isEmpty()) {
            String categoryFilter = getIntent().getStringExtra("CATEGORY_FILTER");
            tempList.addAll(getCleanersForCategory(categoryFilter));
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Cleaner cleaner : getCleanersForCategory(getIntent().getStringExtra("CATEGORY_FILTER"))) {
                if (cleaner.getName().toLowerCase().contains(lowerCaseQuery)) {
                    tempList.add(cleaner);
                }
            }
        }
        adapter.updateData(tempList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private List<Cleaner> getCleanersForCategory(String category) {
        if (category.equals("All Cleaners")) {
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