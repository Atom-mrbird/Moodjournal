package com.ataberk.moodjournal;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {
    private TextView statsTextView;

    // SharedPreferences keys
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String HAPPY_COUNT_PREFIX = "happy_count_";
    private static final String SAD_COUNT_PREFIX = "sad_count_";
    private static final String CONFUSED_COUNT_PREFIX = "confused_count_";
    private static final String ANGRY_COUNT_PREFIX = "angry_count_";
    private static final String SICK_COUNT_PREFIX = "sick_count_";
    private static final String LOVELY_COUNT_PREFIX = "lovely_count_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        statsTextView = findViewById(R.id.stats_text_view);

        // Get the current month and year passed from MainActivity
        String monthYear = getIntent().getStringExtra("monthYear");

        loadMonthlyMoodStatistics(monthYear);
    }

    private void loadMonthlyMoodStatistics(String monthYear) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        int happyCount = sharedPreferences.getInt(HAPPY_COUNT_PREFIX + monthYear, 0);
        int sadCount = sharedPreferences.getInt(SAD_COUNT_PREFIX + monthYear, 0);
        int confusedCount = sharedPreferences.getInt(CONFUSED_COUNT_PREFIX + monthYear, 0);
        int angryCount = sharedPreferences.getInt(ANGRY_COUNT_PREFIX + monthYear, 0);
        int sickCount = sharedPreferences.getInt(SICK_COUNT_PREFIX + monthYear, 0);
        int lovelyCount = sharedPreferences.getInt(LOVELY_COUNT_PREFIX + monthYear, 0);

        // Display the mood counts
        String stats = "Mood Statistics for " + monthYear + ":\n\n" +
                "Happy: " + happyCount + "\n" +
                "Sad: " + sadCount + "\n" +
                "Confused: " + confusedCount + "\n" +
                "Angry: " + angryCount + "\n" +
                "Sick: " + sickCount + "\n" +
                "Lovely: " + lovelyCount;

        statsTextView.setText(stats);
    }
}