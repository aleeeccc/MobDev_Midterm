package com.antonio.midterm_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllCategoriesActivity extends AppCompatActivity {
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        ArrayList<String> categories = getIntent().getStringArrayListExtra("ALL_CATEGORIES");

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerAllCategories);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        categoryAdapter = new CategoryAdapter(categories, category -> {
            Intent intent = new Intent(AllCategoriesActivity.this, SelectedCategoryActivity.class);
            intent.putExtra("SELECTION_TYPE", "CATEGORY");
            intent.putExtra("CATEGORY_NAME", category);
            intent.putExtra("CATEGORY_FILTER", category);
            startActivity(intent);
            finish(); // Close the categories screen
        });

        recyclerView.setAdapter(categoryAdapter);

        // Set up search functionality
        setupSearchFunctionality();

        // Set title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("All Categories");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupSearchFunctionality() {
        SearchView searchView = findViewById(R.id.searchViewCategories);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCategories(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCategories(newText);
                return true;
            }
        });
    }

    private void setupClearButton() {
        ImageButton clearButton = findViewById(R.id.clearSearchButton);
        SearchView searchView = findViewById(R.id.searchViewCategories);
        TextView noResultsText = findViewById(R.id.noResultsText);

        clearButton.setOnClickListener(v -> {
            searchView.setQuery("", false);
            clearButton.setVisibility(View.GONE);
            noResultsText.setVisibility(View.GONE);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCategories(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                clearButton.setVisibility(newText.isEmpty() ? View.GONE : View.VISIBLE);
                filterCategories(newText);
                return true;
            }
        });
    }

    private void filterCategories(String query) {
        ArrayList<String> allCategories = getIntent().getStringArrayListExtra("ALL_CATEGORIES");
        TextView noResultsText = findViewById(R.id.noResultsText);

        if (query == null || query.isEmpty()) {
            categoryAdapter.updateCategories(allCategories);
            noResultsText.setVisibility(View.GONE);
        } else {
            List<String> filteredCategories = allCategories.stream()
                    .filter(category ->
                            category.toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());

            categoryAdapter.updateCategories(filteredCategories);
            noResultsText.setVisibility(filteredCategories.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

}