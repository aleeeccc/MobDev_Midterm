package com.antonio.midterm_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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

        // Set data to views
        TextView nameTextView = findViewById(R.id.cleaner_name);
        TextView ageTextView = findViewById(R.id.cleaner_age);
        RatingBar ratingBar = findViewById(R.id.cleaner_rating_bar);
        TextView ratingTextView = findViewById(R.id.cleaner_rating);
        TextView phoneTextView = findViewById(R.id.cleaner_phone);
        TextView addressTextView = findViewById(R.id.cleaner_address);
        TextView categoryTextView = findViewById(R.id.cleaner_category);
        ImageView profileImageView = findViewById(R.id.cleaner_image);
        Button bookButton = findViewById(R.id.book_button);

        nameTextView.setText(name);
        ageTextView.setText(age + " years old");
        ratingBar.setRating((float) rating);
        ratingTextView.setText("" + rating);
        phoneTextView.setText(phone);
        addressTextView.setText(address);
        categoryTextView.setText(category);

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
}