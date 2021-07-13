package com.example.android.regex.app;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private String lastUsedRegex;
    private String lastUsedText;

    public String getLastUsedRegex() {
        return lastUsedRegex;
    }

    public void setLastUsedRegex(String lastUsedRegex) {
        this.lastUsedRegex = lastUsedRegex;
    }

    public String getLastUsedText() {
        return lastUsedText;
    }

    public void setLastUsedText(String lastUsedText) {
        this.lastUsedText = lastUsedText;
    }
}