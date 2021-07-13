package com.example.android.regex.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.regex.app.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SLEEP_DURATION_MILLISECONDS = 1500;
    private ActivitySplashScreenBinding activitySplashScreenBinding;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivitySplashScreenBinding();
        setContentView(activitySplashScreenBinding.getRoot());
        initializeSplashScreen();
    }

    public static int getSleepDurationMilliseconds() {
        return SLEEP_DURATION_MILLISECONDS;
    }

    private void setActivitySplashScreenBinding() {
        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
    }

    private void initializeSplashScreen() {
        new SplashScreenLauncher(getSleepDurationMilliseconds()).start();
    }

    public class SplashScreenLauncher extends Thread {
        private final int sleepDuration;

        public SplashScreenLauncher(final int sleepDuration) {
            this.sleepDuration = sleepDuration;
        }

        @Override
        public void run() {
            try {
                sleep(sleepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                goToMainActivity();
            }
        }

        private void goToMainActivity() {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}