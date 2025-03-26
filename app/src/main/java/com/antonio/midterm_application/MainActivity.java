package com.antonio.midterm_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private List<String> allCategories;
    private List<Cleaner> allCleaners;
    private List<Cleaner> featuredCleaners;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadDataFromJson();

        setupSearchFunctionality();

        displayCleaners(featuredCleaners);
    }

    private void loadDataFromJson() {
        try {
            JSONObject jsonObject = JSONParserActivity.getJsonData(this, "data.json");

            allCleaners = JSONParserActivity.parseCleanersFromJson(jsonObject);
            featuredCleaners = JSONParserActivity.getFeaturedCleaners(allCleaners);
            allCategories = JSONParserActivity.parseCategoriesFromJson(jsonObject);

            initializeCategoryUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeCategoryUI() {
        List<String> displayCategories = new ArrayList<>();
        displayCategories.add("All Cleaners");
        displayCategories.add("Cleaning Service");
        displayCategories.add("Cleaning Appliance");
        displayCategories.add("Babysitter Service");
        displayCategories.add("More Categories");

        RecyclerView recyclerViewCategories = findViewById(R.id.recyclerCategories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(displayCategories, category -> {
            if ("More Categories".equals(category)) {
                Intent intent = new Intent(MainActivity.this, AllCategoriesActivity.class);
                intent.putStringArrayListExtra("ALL_CATEGORIES", new ArrayList<>(allCategories));
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, SelectedCategoryActivity.class);
                intent.putExtra("SELECTION_TYPE", "CATEGORY");
                intent.putExtra("CATEGORY_NAME", category);
                intent.putExtra("CATEGORY_FILTER", category);
                startActivity(intent);
            }
        });
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private void displayCleaners(List<Cleaner> cleaners) {
        RecyclerView recyclerViewCleaners = findViewById(R.id.recyclerCleaners);
        recyclerViewCleaners.setLayoutManager(new LinearLayoutManager(this));

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
            intent.putExtra("ATTITUDE", cleaner.getAttitude());
            intent.putExtra("CLEANING_QUALITY", cleaner.getCleaningQuality());
            intent.putExtra("CUSTOMER_SATISFACTION", cleaner.getCustomerSatisfaction());
            intent.putExtra("YEARS_OF_EXPERIENCE", cleaner.getYearsOfExperience());
            intent.putExtra("ABOUT", cleaner.getAbout());
            startActivity(intent);
        });

        recyclerViewCleaners.setAdapter(cleanerAdapter);
    }

    private void setupSearchFunctionality() {
        SearchView searchView = findViewById(R.id.searchView);

        int searchEditId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = searchView.findViewById(searchEditId);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                filterCleaners(query);
                filterCategories(query);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void filterCleaners(String query) {
        if (query == null || query.isEmpty()) {
            displayCleaners(featuredCleaners);
            TextView cleanersTitle = findViewById(R.id.cleaners_title);
            cleanersTitle.setText("Featured Cleaners");
        } else {
            List<Cleaner> filteredList = allCleaners.stream()
                    .filter(cleaner ->
                            cleaner.getName().toLowerCase().contains(query.toLowerCase()) ||
                                    cleaner.getCategory().toLowerCase().contains(query.toLowerCase()) ||
                                    cleaner.getAddress().toLowerCase().contains(query.toLowerCase())
                    )
                    .collect(Collectors.toList());

            displayCleaners(filteredList);
            TextView cleanersTitle = findViewById(R.id.cleaners_title);
            cleanersTitle.setText("Cleaner Search Results");
        }
    }

    private void filterCategories(String query) {
        if (query == null || query.isEmpty()) {
            List<String> displayCategories = new ArrayList<>();
            displayCategories.add("All Cleaners");
            displayCategories.add("Cleaning Service");
            displayCategories.add("Cleaning Appliance");
            displayCategories.add("Babysitter Service");
            displayCategories.add("More Categories");
            categoryAdapter.updateCategories(displayCategories);
        } else {
            List<String> filteredCategories = allCategories.stream()
                    .filter(category ->
                            category.toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());

            categoryAdapter.updateCategories(filteredCategories);
        }
    }
}