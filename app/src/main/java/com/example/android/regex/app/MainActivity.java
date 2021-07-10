package com.example.android.regex.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.regex.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setActivityMainBinding();
        setContentView(activityMainBinding.getRoot());
        setTextListener();
        setRegexListener();
    }

    private void setActivityMainBinding() {
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
    }

    private void setTextListener() {
        activityMainBinding.activityMainTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int start, final int count,
                                          final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int start, final int before,
                                      final int count) {
                activityMainBinding.activityMainOutputField.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setRegexListener() {
        activityMainBinding.activityMainRegexField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int start, final int count,
                                          final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int start, final int before,
                                      final int count) {
                if (!String.valueOf(activityMainBinding.activityMainOutputField.getText()).isEmpty()) {

                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
    }
}