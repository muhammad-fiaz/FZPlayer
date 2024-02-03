package com.data.fzplayer.main.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;


import com.data.fzplayer.R;
/**
 * SettingsActivity is an activity class that represents the settings screen of the application.
 */
public class SettingsActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find the back button and set a click listener to navigate back
        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the activity has detected the user's press of the back key.
             */
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
