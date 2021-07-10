package com.example.android.regex.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.regex.app.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding activitySplashScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivitySplashScreenBinding();
        setContentView(activitySplashScreenBinding.getRoot());
        initializeSplashScreen();
    }

    private void setActivitySplashScreenBinding() {
        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
    }

    private void initializeSplashScreen() {
        new SplashScreenLauncher().start();
    }

    public class SplashScreenLauncher extends Thread {
        @Override
        public void run() {
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                goToMainActivity();
            }
        }

        private void goToMainActivity() {
            final Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}