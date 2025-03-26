package com.antonio.midterm_application;

import android.content.Context;

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

public class JSONParserActivity {

    public static String readJsonFromAssets(Context context, String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = context.getAssets().open(fileName);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    public static List<Cleaner> parseCleanersFromJson(JSONObject jsonObject) throws JSONException {
        List<Cleaner> allCleaners = new ArrayList<>();
        JSONArray cleanersArray = jsonObject.getJSONArray("cleaners");

        for (int i = 0; i < cleanersArray.length(); i++) {
            JSONObject cleanerJson = cleanersArray.getJSONObject(i);

            Cleaner cleaner = new Cleaner(
                    cleanerJson.getString("name"),
                    cleanerJson.getInt("age"),
                    cleanerJson.getDouble("rating"),
                    cleanerJson.getString("imageUrl"),
                    cleanerJson.getString("phoneNumber"),
                    cleanerJson.getString("address"),
                    cleanerJson.getString("category"),
                    (float) cleanerJson.getDouble("attitude"),
                    (float) cleanerJson.getDouble("cleaningQuality"),
                    (float) cleanerJson.getDouble("customerSatisfaction"),
                    cleanerJson.getInt("yearsOfExperience"),
                    cleanerJson.getString("about"),
                    cleanerJson.getString("availability")
            );

            allCleaners.add(cleaner);
        }
        return allCleaners;
    }

    public static List<Cleaner> getFeaturedCleaners(List<Cleaner> allCleaners) {
        return allCleaners.stream()
                .sorted(Comparator.comparingDouble(Cleaner::getRating).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public static List<String> parseCategoriesFromJson(JSONObject jsonObject) throws JSONException {
        List<String> allCategories = new ArrayList<>();
        JSONArray categoriesArray = jsonObject.getJSONArray("categories");

        for (int i = 0; i < categoriesArray.length(); i++) {
            allCategories.add(categoriesArray.getString(i));
        }
        return allCategories;
    }

    public static JSONObject getJsonData(Context context, String fileName) throws IOException, JSONException {
        String jsonString = readJsonFromAssets(context, fileName);
        return new JSONObject(jsonString);
    }
}