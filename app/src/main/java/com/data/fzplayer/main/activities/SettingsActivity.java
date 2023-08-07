package com.data.fzplayer.main.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;


import com.data.fzplayer.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find the back button and set a click listener to navigate back
        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
