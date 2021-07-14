package com.example.android.regex.app;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.regex.app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setVariables();
        setContentView(activityMainBinding.getRoot());
        setOnHighlightButtonClickListener();
    }

    private void setVariables() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setActivityMainBinding();
    }

    private void setActivityMainBinding() {
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
    }

    // problema e la patterurile complexe (gen: ([A-Za])\w etc) ca recunoaste toate cuvinte, dar nu afiseaza bine pe ecran
    private void setOnHighlightButtonClickListener() {
        activityMainBinding.activityMainHighlightButton.setOnClickListener(view -> {
            String inputText = String.valueOf(activityMainBinding.activityMainTextField.getText());
            final String inputRegex = String.valueOf(activityMainBinding.activityMainRegexField.getText());

            if (!inputText.isEmpty() && Utility.regexIsValid(inputRegex)) {
                if (!inputRegex.equals(viewModel.getLastUsedRegex()) ||
                        !inputText.equals(viewModel.getLastUsedText())) {
                    if (inputRegex.isEmpty()) {
                        activityMainBinding.activityMainOutputField.setText(inputText);
                    } else {
                        final String[] inputWordsArray = inputText.split(" ");
                        final ArrayList<String> uniqueMatchedWordsList = new ArrayList<>();
                        final Pattern pattern = Pattern.compile(inputRegex);

                        if (!inputRegex.equals(viewModel.getLastUsedRegex())) {
                            viewModel.setLastUsedRegex(inputRegex);
                        }

                        if (!inputText.equals(viewModel.getLastUsedText())) {
                            viewModel.setLastUsedText(inputText);
                        }

                        // looping through the input words and adding them to the unique words list
                        // if they match the regex
                        for (final String word : inputWordsArray) {
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
                            if (String.valueOf(activityMainBinding.activityMainOutputField.getText()).isEmpty()) {
                                activityMainBinding.activityMainOutputField.setText("");
                            }

                            inputText = inputText.replaceAll("\n", "<br>");

                            for (String sequence : uniqueMatchedWordsList) {
                                inputText = inputText.replaceAll(sequence,
                                        Utility.getHighlightedWord(MainActivity.this, sequence));
                            }

                            activityMainBinding.activityMainOutputField.setText(Html.fromHtml(inputText,
                                    Html.FROM_HTML_MODE_LEGACY));
                        }
                    }
                }
            } else {
                Utility.showMessage(this, getResources().getString(inputText.isEmpty() && inputRegex.isEmpty() ?
                        R.string.error_empty_fields : inputText.isEmpty() ?
                        R.string.error_empty_text : R.string.error_regex_not_valid));
            }
        });
    }
}

// https://stackoverflow.com/questions/23150307/java-code-goes-infinite-loop-when-trying-to-replace-certain-string

// https://stackoverflow.com/questions/3472663/replace-all-occurrences-of-a-string-using-stringbuilder

// https://stackoverflow.com/questions/31596991/java-lang-outofmemoryerror-on-string-replace/31598833#31598833

// https://stackoverflow.com/questions/31596991/java-lang-outofmemoryerror-on-string-replace/31597201

// https://stackoverflow.com/questions/21670174/how-to-have-html-new-line-feature-in-android-text-view

// https://stackoverflow.com/questions/1326682/java-replacing-multiple-different-substring-in-a-string-at-once-or-in-the-most