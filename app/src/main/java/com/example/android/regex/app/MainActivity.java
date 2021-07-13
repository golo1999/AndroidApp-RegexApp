package com.example.android.regex.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.regex.app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
                String outputFieldText = String.valueOf(activityMainBinding.activityMainOutputField.getText());
                final String inputRegex = String.valueOf(activityMainBinding.activityMainRegexField.getText());

//                final StringBuilder stringBuilder = new StringBuilder("ana");
//                stringBuilder.append(" are ");
//                stringBuilder.append("mere");
//                Toast.makeText(MainActivity.this, stringBuilder, Toast.LENGTH_SHORT).show();
//                activityMainBinding.activityMainOutputField.setText(stringBuilder);

                // if the output field isn't empty and the regex is valid
                if (!outputFieldText.isEmpty() && Utility.regexIsValid(inputRegex)) {
                    final String[] outputWordsArray = outputFieldText.split(" ");
                    final ArrayList<Integer> positionsOfNewLine = new ArrayList<>();
                    final ArrayList<String> uniqueMatchedWordsList = new ArrayList<>();
                    final Pattern pattern = Pattern.compile(inputRegex);
                    int wordIndex = 0;

                    for (final String word : outputWordsArray) {
                        final String newline = System.getProperty("line.separator");

                        Log.d("outputWord:", word);

                        if (newline != null && word.contains(newline)) {
                            int numberOfNewLines = 0;

                            for (final char character : word.toCharArray()) {
                                if (String.valueOf(character).equals(newline)) {
                                    ++numberOfNewLines;
                                }
                            }

                            Log.d("outputWordContainsNewLine", word);
                            Log.d("outputWordContainsNumberOfNewLines", String.valueOf(numberOfNewLines));
                        }
                    }

                    // looping through the output words and adding them to the unique words list if they match the regex
                    for (final String word : outputWordsArray) {
                        final Matcher matcher = pattern.matcher(word);

                        while (matcher.find()) {
                            if (uniqueMatchedWordsList.isEmpty() ||
                                    Utility.wordIsUnique(uniqueMatchedWordsList, matcher.group())) {
                                uniqueMatchedWordsList.add(matcher.group());
                                Log.d("matched: ", matcher.group());
                            }
                        }
                    }

                    // highlighting the matched words if the unique matched words list isn't empty
                    if (!uniqueMatchedWordsList.isEmpty()) {
                        final StringBuilder newOutputText =
                                new StringBuilder(String.valueOf(activityMainBinding.activityMainTextField.getText()));

                        activityMainBinding.activityMainOutputField.setText("");

//                        final SpannableString spannableString = new SpannableString(String
//                                .valueOf(activityMainBinding.activityMainTextField.getText()));

                        outputFieldText = outputFieldText.replaceAll("\n", "<br>");

                        for (String sequence : uniqueMatchedWordsList) {
                            outputFieldText = outputFieldText.replaceAll(sequence, Utility.getHighlightedWord(sequence,
                                    MainActivity.this));
                        }

                        activityMainBinding.activityMainOutputField.setText(Html.fromHtml(outputFieldText));

                        // looping through each word from the output
//                        for (final String word : outputWordsArray) {
//                            // a word is found if it contains any of the matched unique word/s or
//                            // it is a matched unique word
//                            final boolean found = wordContainsUniqueMatchedWord(uniqueMatchedWordsList, word) ||
//                                    wordIsContainedIntoUniqueMatchedWordsList(uniqueMatchedWordsList, word);
//
//                            ++wordIndex;
//
//                            if (found) {
//                                final String matchedSequence = getMatchedSequence(uniqueMatchedWordsList, word);
//
//                                if (matchedSequence != null) {
//                                    final String modifiedSequence = word.replaceAll(matchedSequence,
//                                            getHighlightedWord(matchedSequence));
//
//                                    Log.d("matchedWordFound", word);
//                                    Log.d("modifiedSequence", modifiedSequence);
//
//                                    newOutputText.append(wordIndex > 0 ? modifiedSequence + " " : modifiedSequence);
//
//                                    Log.d("newOutputText", String.valueOf(newOutputText));
//                                }
////                                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getColor(R.color.primary_color));
////                                BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(getColor(R.color.secondary_color));
////
////                                spannableString.setSpan(foregroundColorSpan, currentIndex, currentIndex + word.length(),
////                                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
////                                spannableString.setSpan(backgroundColorSpan, currentIndex, currentIndex + word.length(),
////                                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//
//
//                            } else {
//                                newOutputText.append(wordIndex > 0 ? word + " " : word);
//                            }
//                            //Log.d("foundFrom", word + ": " + currentIndex + ":" + (currentIndex + word.length() - 1));
//
////                            currentIndex += word.length() - 1;
//                        }

                        //activityMainBinding.activityMainOutputField.setText(Html.fromHtml(String.valueOf(newOutputText)));

//                        activityMainBinding.activityMainOutputField.setText(spannableString);
                    }


//                        final ForegroundColorSpan[] foregroundColorSpansArray = new ForegroundColorSpan[3];
//                        final BackgroundColorSpan[] backgroundColorSpansArray = new BackgroundColorSpan[3];
//
//                        for (int i = 0; i < foregroundColorSpansArray.length; i++) {
//                            foregroundColorSpansArray[i] = new ForegroundColorSpan(getColor(R.color.primary_color));
//                            backgroundColorSpansArray[i] = new BackgroundColorSpan(getColor(R.color.secondary_color));
//                        }
//
//                        for (int i = 0; i < foregroundColorSpansArray.length; i++) {
//                            if (i == 0) {
//                                spannableString.setSpan(foregroundColorSpansArray[0], 6, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                                spannableString.setSpan(backgroundColorSpansArray[0], 6, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                            } else if (i == 1) {
//                                spannableString.setSpan(foregroundColorSpansArray[1], 25, 30, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                                spannableString.setSpan(backgroundColorSpansArray[1], 25, 30, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                            } else {
//                                spannableString.setSpan(foregroundColorSpansArray[2], 35, 45, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                                spannableString.setSpan(backgroundColorSpansArray[2], 35, 45, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                            }
//                        }
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
    }
}