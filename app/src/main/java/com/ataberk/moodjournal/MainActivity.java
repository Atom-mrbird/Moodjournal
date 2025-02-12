package com.ataberk.moodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Spinner moodSpinner;
    private Button saveButton, statsButton;
    private TextView savedMoodText;
    private String selectedDate;

    // SharedPreferences key
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String MOOD_KEY_PREFIX = "mood_";
    private static final String HAPPY_COUNT_PREFIX = "happy_count_";
    private static final String SAD_COUNT_PREFIX = "sad_count_";
    private static final String CONFUSED_COUNT_PREFIX = "confused_count_";
    private static final String ANGRY_COUNT_PREFIX = "angry_count_";
    private static final String SICK_COUNT_PREFIX = "sick_count_";
    private static final String LOVELY_COUNT_PREFIX = "lovely_count_";

    private String currentMonthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        calendarView = findViewById(R.id.calendar1);
        moodSpinner = findViewById(R.id.spinner);
        saveButton = findViewById(R.id.button);
        statsButton = findViewById(R.id.button2);
        savedMoodText = findViewById(R.id.textView);

        // Add items to the spinner
        String[] moods = {"Happy", "Sad", "Confused", "Angry", "Sick", "Lovely"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter);

        // Get the current month and year in "YYYY-MM" format
        currentMonthYear = getCurrentMonthYear();

        // Get the selected date from the calendar
        selectedDate = getCurrentDate(); // Default to today's date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            loadMoodForDate(selectedDate);
        });

        // Save mood when button is clicked
        saveButton.setOnClickListener(v -> {
            String selectedMood = moodSpinner.getSelectedItem().toString();
            saveMoodForDate(selectedDate, selectedMood);
            updateMonthlyMoodCount(selectedMood, currentMonthYear);
        });

        // Button to go to the statistics activity
        statsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatsActivity.class);
            intent.putExtra("monthYear", currentMonthYear);  // Pass current month and year to StatsActivity
            startActivity(intent);
        });

        // Load mood for today's date when app starts
        loadMoodForDate(selectedDate);
    }

    // Method to save the selected mood for the selected date
    private void saveMoodForDate(String date, String mood) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the mood using a key that includes the date
        editor.putString(MOOD_KEY_PREFIX + date, mood);
        editor.apply();

        savedMoodText.setText("Mood for " + date + ": " + mood); // Update the TextView
    }

    // Method to load and display the saved mood for the selected date
    private void loadMoodForDate(String date) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String savedMood = sharedPreferences.getString(MOOD_KEY_PREFIX + date, "No mood selected");

        savedMoodText.setText("Mood for " + date + ": " + savedMood); // Update the TextView
    }

    // Helper method to get the current date in the format YYYY-MM-DD
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Helper method to get the current month and year in the format YYYY-MM
    private String getCurrentMonthYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Method to update the monthly mood count
    private void updateMonthlyMoodCount(String mood, String monthYear) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int count;
        switch (mood) {
            case "Happy":
                count = sharedPreferences.getInt(HAPPY_COUNT_PREFIX + monthYear, 0);
                editor.putInt(HAPPY_COUNT_PREFIX + monthYear, count + 1);
                break;
            case "Sad":
                count = sharedPreferences.getInt(SAD_COUNT_PREFIX + monthYear, 0);
                editor.putInt(SAD_COUNT_PREFIX + monthYear, count + 1);
                break;
            case "Confused":
                count = sharedPreferences.getInt(CONFUSED_COUNT_PREFIX + monthYear, 0);
                editor.putInt(CONFUSED_COUNT_PREFIX + monthYear, count + 1);
                break;
            case "Angry":
                count = sharedPreferences.getInt(ANGRY_COUNT_PREFIX + monthYear, 0);
                editor.putInt(ANGRY_COUNT_PREFIX + monthYear, count + 1);
                break;
            case "Sick":
                count = sharedPreferences.getInt(SICK_COUNT_PREFIX + monthYear, 0);
                editor.putInt(SICK_COUNT_PREFIX + monthYear, count + 1);
                break;
            case "Lovely":
                count = sharedPreferences.getInt(LOVELY_COUNT_PREFIX + monthYear, 0);
                editor.putInt(LOVELY_COUNT_PREFIX + monthYear, count + 1);
                break;
        }
        editor.apply();
    }
}