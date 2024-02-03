package com.data.fzplayer.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.data.fzplayer.R;

/**
 * AppinfoActivity is an activity class that represents the app information screen of the application.
 */
public class AppinfoActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
    }
}