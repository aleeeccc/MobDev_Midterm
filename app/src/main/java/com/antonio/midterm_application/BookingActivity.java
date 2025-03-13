package com.antonio.midterm_application;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingActivity extends AppCompatActivity {

    private EditText serviceAddressInput;
    private Spinner serviceItemsSpinner;
    private DatePicker serviceDatePicker;
    private EditText couponInput;
    private String cleanerName;
    private String cleanerCategory;
    private double cleanerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Get cleaner info from intent
        cleanerName = getIntent().getStringExtra("CLEANER_NAME");
        cleanerCategory = getIntent().getStringExtra("CLEANER_CATEGORY");
        cleanerRating = getIntent().getDoubleExtra("CLEANER_RATING", 0.0);

        // Initialize views
        serviceAddressInput = findViewById(R.id.serviceAddressInput);
        serviceItemsSpinner = findViewById(R.id.serviceItemsSpinner);
        serviceDatePicker = findViewById(R.id.serviceDatePicker);
        couponInput = findViewById(R.id.couponInput);
        Button submitButton = findViewById(R.id.submitBookingButton);

        // Set cleaner details in the card
        TextView cleanerNameTextView = findViewById(R.id.booking_cleaner_name);
        TextView cleanerCategoryTextView = findViewById(R.id.booking_cleaner_category);
        TextView cleanerRatingTextView = findViewById(R.id.booking_cleaner_rating);
        ImageView cleanerImageView = findViewById(R.id.cleaner_image_small);

        // Set action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Book " + cleanerName);
            getSupportActionBar().setSubtitle(cleanerCategory);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set spinner data based on cleaner category
        setupServiceItems(cleanerCategory);

        // Set click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBooking();
            }
        });
    }

    private void setupServiceItems(String category) {
        List<String> serviceItems = new ArrayList<>();

        // Populate service items based on category
        switch (category) {
            case "Cleaning Service":
                serviceItems.add("House Cleaning - Basic");
                serviceItems.add("House Cleaning - Deep Clean");
                serviceItems.add("Office Cleaning");
                serviceItems.add("Move-in/Move-out Cleaning");
                break;
            case "Cleaning Appliance":
                serviceItems.add("Refrigerator Cleaning");
                serviceItems.add("Oven Cleaning");
                serviceItems.add("AC Cleaning");
                serviceItems.add("Washing Machine Cleaning");
                break;
            case "Babysitter Service":
                serviceItems.add("Hourly Babysitting");
                serviceItems.add("Full Day Babysitting");
                serviceItems.add("After School Care");
                serviceItems.add("Weekend Care");
                break;
            default:
                serviceItems.add("Standard Service");
                serviceItems.add("Premium Service");
                serviceItems.add("Consultation");
        }

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, serviceItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceItemsSpinner.setAdapter(adapter);
    }

    private void submitBooking() {
        // Validate inputs
        String address = Objects.requireNonNull(serviceAddressInput.getText()).toString().trim();
        String selectedService = serviceItemsSpinner.getSelectedItem().toString();
        String couponCode = Objects.requireNonNull(couponInput.getText()).toString().trim();

        // Get date from date picker
        int day = serviceDatePicker.getDayOfMonth();
        int month = serviceDatePicker.getMonth() + 1; // Month is 0-indexed
        int year = serviceDatePicker.getYear();
        String selectedDate = year + "-" + month + "-" + day;

        if (address.isEmpty()) {
            serviceAddressInput.setError("Address is required");
            return;
        }

        // Here you would typically save the booking to a database
        // For now, just show a confirmation message
        String message = "Booking confirmed for " + selectedService + " on " + selectedDate;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Close the activity and return to previous screen
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}