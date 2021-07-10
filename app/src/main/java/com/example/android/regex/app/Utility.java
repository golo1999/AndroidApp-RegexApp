package com.example.android.regex.app;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class Utility {
    private Utility() {

    }

    public static boolean regexIsValid(String givenRegex) {
        try {
            Pattern.compile(givenRegex);
        } catch (PatternSyntaxException exception) {
            return false;
        }

        return true;
    }
}