package com.data.fzplayer.main.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.data.fzplayer.R;
import com.data.fzplayer.main.activities.MainFolderActivity;

import maes.tech.intentanim.CustomIntent;
/**
 * SplashScreenActivity is an activity class that represents the splash screen of the application.
 */
public class SplashScreenActivity extends AppCompatActivity {

    //View Variables
    private ImageView appLogo;
    private TextView appSlogan, poweredBy1, poweredBy2;

    //Other Variables
    private Animation topAnimation, bottomAnimation;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        initAnimation();
    }
    /**
     * Initializes the views.
     */
    private void initViews() {
        //Initialize Views
        appLogo = findViewById(R.id.app_logo);
        appSlogan = findViewById(R.id.app_slogan);
        poweredBy1 = findViewById(R.id.app_powered_by1);
        poweredBy2 = findViewById(R.id.app_powered_by2);
    }

    /**
     * Initializes the animations.
     */
    private void initAnimation() {
        //Initialize Animations
        topAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.splash_top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.splash_bottom_animation);
    }
    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped, but is now again being displayed to the user.
     */
    @Override
    protected void onStart() {
        super.onStart();
        int SPLASH_TIMER = 4000;

        //Set Animation To Views
        appLogo.setAnimation(topAnimation);
        appSlogan.setAnimation(bottomAnimation);
        poweredBy1.setAnimation(bottomAnimation);
        poweredBy2.setAnimation(bottomAnimation);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainFolderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            CustomIntent.customType(SplashScreenActivity.this, "fadein-to-fadeout");
            finish();

        }, SPLASH_TIMER);

    }
    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();



    }

}