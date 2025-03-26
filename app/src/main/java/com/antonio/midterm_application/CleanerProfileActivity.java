package com.antonio.midterm_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CleanerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cleaner_profile);

        // Get extras from intent
        String name = getIntent().getStringExtra("NAME");
        int age = getIntent().getIntExtra("AGE", 0);
        double rating = getIntent().getDoubleExtra("RATING", 0.0);
        String phone = getIntent().getStringExtra("PHONE");
        String address = getIntent().getStringExtra("ADDRESS");
        String category = getIntent().getStringExtra("CATEGORY");
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");
        float attitude = getIntent().getFloatExtra("ATTITUDE", 4.0f);
        float cleaningQuality = getIntent().getFloatExtra("CLEANING_QUALITY", 4.0f);
        float customerSatisfaction = getIntent().getFloatExtra("CUSTOMER_SATISFACTION", 4.0f);
        int yearsOfExperience = getIntent().getIntExtra("YEARS_OF_EXPERIENCE", 0);
        String about = getIntent().getStringExtra("ABOUT");

        // Set data to views
        TextView nameTextView = findViewById(R.id.cleaner_name);
        TextView ageTextView = findViewById(R.id.cleaner_age);
        RatingBar ratingBar = findViewById(R.id.cleaner_rating_bar);
        TextView ratingTextView = findViewById(R.id.cleaner_rating);
        TextView experienceTextView = findViewById(R.id.cleaner_experience_years);
        TextView aboutTextView = findViewById(R.id.cleaner_about);
        TextView phoneTextView = findViewById(R.id.cleaner_phone);
        TextView addressTextView = findViewById(R.id.cleaner_address);
        TextView categoryTextView = findViewById(R.id.cleaner_category);
        ImageView profileImageView = findViewById(R.id.cleaner_image);
        Button bookButton = findViewById(R.id.book_button);


        setupCapabilityMetrics(attitude, cleaningQuality, customerSatisfaction);

        nameTextView.setText(name);
        ageTextView.setText(age + " years old");
        ratingBar.setRating((float) rating);
        ratingTextView.setText("" + rating);
        phoneTextView.setText(phone);
        addressTextView.setText(address);
        categoryTextView.setText(category);
        experienceTextView.setText(yearsOfExperience + " years");
        aboutTextView.setText(about);

        int resourceId = getResources().getIdentifier(imageUrl, "drawable", getPackageName());
        if (resourceId != 0) {
            profileImageView.setImageResource(resourceId);
        } else {
            // Use default image if resource not found
            profileImageView.setImageResource(R.drawable.ic_person);
        }

        // Existing code for setting action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Cleaner Profile");
            getSupportActionBar().setSubtitle(category);
        }

        // Existing code for setting action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Cleaner Profile");
            // Display category as subtitle instead (optional)
            getSupportActionBar().setSubtitle(category);
        }

        bookButton.setOnClickListener(v -> {
            Intent bookingIntent = new Intent(CleanerProfileActivity.this, BookingActivity.class);
            bookingIntent.putExtra("CLEANER_NAME", name);
            bookingIntent.putExtra("CLEANER_CATEGORY", category);
            bookingIntent.putExtra("CLEANER_RATING", rating);
            startActivity(bookingIntent);
        });

    }

    private void setupCapabilityMetrics(float attitude, float cleaningQuality, float customerSatisfaction) {
        // Set progress bars
        ProgressBar attitudeBar = findViewById(R.id.attitude_bar);
        ProgressBar cleaningQualityBar = findViewById(R.id.cleaning_quality_bar);
        ProgressBar customerSatisfactionBar = findViewById(R.id.customer_satisfaction_bar);

        // Set text values
        TextView attitudeValue = findViewById(R.id.attitude_value);
        TextView cleaningQualityValue = findViewById(R.id.cleaning_quality_value);
        TextView customerSatisfactionValue = findViewById(R.id.customer_satisfaction_value);

        // Set progress directly (since max is already 5 in the XML)
        attitudeBar.setMax(50);  // Using 10x scale for better precision with decimals
        cleaningQualityBar.setMax(50);
        customerSatisfactionBar.setMax(50);

        // Multiply by 10 to preserve one decimal place of precision
        attitudeBar.setProgress((int)(attitude * 10));
        cleaningQualityBar.setProgress((int)(cleaningQuality * 10));
        customerSatisfactionBar.setProgress((int)(customerSatisfaction * 10));

        // Set text values
        attitudeValue.setText(String.format("%.1f", attitude));
        cleaningQualityValue.setText(String.format("%.1f", cleaningQuality));
        customerSatisfactionValue.setText(String.format("%.1f", customerSatisfaction));
    }
}