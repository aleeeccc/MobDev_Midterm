package com.antonio.midterm_application;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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

        // Set the actual data to the views
        cleanerNameTextView.setText(cleanerName);
        cleanerCategoryTextView.setText(cleanerCategory);
        cleanerRatingTextView.setText(String.valueOf(cleanerRating));
        cleanerImageView.setImageResource(R.drawable.ic_person);

        // Set action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Book " + cleanerName);
            getSupportActionBar().setSubtitle(cleanerCategory);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set minimum date to tomorrow (current date + 1)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        serviceDatePicker.setMinDate(calendar.getTimeInMillis());

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

        // Create custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_booking_confirmation, null);
        builder.setView(dialogView);

        // Get the container for booking details
        LinearLayout detailsContainer = dialogView.findViewById(R.id.booking_details_container);

        // Add booking details with icons
        addDetailRow(detailsContainer, android.R.drawable.ic_menu_myplaces, "Professional", cleanerName);
        addDetailRow(detailsContainer, android.R.drawable.ic_menu_agenda, "Category", cleanerCategory);
        addDetailRow(detailsContainer, android.R.drawable.ic_menu_manage , "Service", selectedService);
        addDetailRow(detailsContainer, android.R.drawable.ic_menu_my_calendar, "Date", formatDate(selectedDate));
        addDetailRow(detailsContainer, android.R.drawable.ic_menu_mylocation,  "Address", address);

        if (!couponCode.isEmpty()) {
            addDetailRow(detailsContainer, android.R.drawable.ic_menu_sort_by_size, "Coupon", couponCode);
        }

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set up the OK button
        Button okButton = dialogView.findViewById(R.id.btn_ok);
        okButton.setOnClickListener(v -> {
            dialog.dismiss();
            finish(); // Close the activity when user clicks OK
        });
    }

    // Helper method to add a detail row with an icon
    private void addDetailRow(LinearLayout container, int iconResId, String label, String value) {
        View rowView = getLayoutInflater().inflate(R.layout.item_booking_detail, container, false);

        ImageView icon = rowView.findViewById(R.id.detail_icon);
        TextView labelText = rowView.findViewById(R.id.detail_label);
        TextView valueText = rowView.findViewById(R.id.detail_value);

        icon.setImageResource(iconResId);
        labelText.setText(label);
        valueText.setText(value);

        container.addView(rowView);
    }

    // Format date to be more readable
    private String formatDate(String dateStr) {
        try {
            String[] parts = dateStr.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day);

            return new java.text.SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
                    .format(calendar.getTime());
        } catch (Exception e) {
            return dateStr;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}