package com.example.android.regex.app;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
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

    public static boolean wordIsUnique(final @NonNull ArrayList<String> wordsList, final @NonNull String matchedWord) {
        for (final String word : wordsList) {
            if (word.equals(matchedWord)) {
                return false;
            }
        }

        return true;
    }

    // method for checking if the given word is found into the unique matched words list
    public static boolean wordIsContainedIntoUniqueMatchedWordsList(final ArrayList<String> uniqueMatchedWordsList,
                                                                    final String givenWord) {
        return uniqueMatchedWordsList.contains(givenWord);
    }

    public static boolean wordContainsUniqueMatchedWord(final ArrayList<String> uniqueMatchedWordsList,
                                                        final String word) {
        boolean containsUniqueMatchedWord = false;

        for (final String uniqueMatchedWord : uniqueMatchedWordsList) {
            if (word.contains(uniqueMatchedWord)) {
                containsUniqueMatchedWord = true;
                break;
            }
        }

        return containsUniqueMatchedWord;
    }

    public static String getMatchedSequence(final ArrayList<String> uniqueMatchedWordsList, final String word) {
        if (wordIsContainedIntoUniqueMatchedWordsList(uniqueMatchedWordsList, word)) {
            return word;
        } else if (wordContainsUniqueMatchedWord(uniqueMatchedWordsList, word)) {
            for (final String uniqueMatchedWord : uniqueMatchedWordsList) {
                if (word.contains(uniqueMatchedWord)) {
                    return uniqueMatchedWord;
                }
            }
        }

        return null;
    }

    public static String getHighlightedWord(final String word, final Context context) {
        return "<span style ='color: " + context.getColor(R.color.primary_color) + "; background-color: " +
                context.getColor(R.color.secondary_color) + "'>" + word + "</span>";
    }
}