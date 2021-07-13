package com.example.android.regex.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

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
//        setTextListener();
//        setRegexListener();
    }

    private void setVariables() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setActivityMainBinding();
    }

    private void setActivityMainBinding() {
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
    }

    private void setOnHighlightButtonClickListener() {
        activityMainBinding.activityMainHighlightButton.setOnClickListener(view -> {
            String inputText = String.valueOf(activityMainBinding.activityMainTextField.getText());
            final String inputRegex = String.valueOf(activityMainBinding.activityMainRegexField.getText());

            if (!inputText.isEmpty() && Utility.regexIsValid(inputRegex)) {
                final String[] inputWordsArray = inputText.split(" ");
                final ArrayList<String> uniqueMatchedWordsList = new ArrayList<>();
                final Pattern pattern = Pattern.compile(inputRegex);

                if (viewModel.getLastUsedRegex() == null || viewModel.getLastUsedText() == null ||
                        !inputRegex.equals(viewModel.getLastUsedRegex()) ||
                        !inputText.equals(viewModel.getLastUsedText())) {
                    if (!inputRegex.equals(viewModel.getLastUsedRegex())) {
                        viewModel.setLastUsedRegex(inputRegex);
                    }

                    if (!inputText.equals(viewModel.getLastUsedText())) {
                        viewModel.setLastUsedText(inputText);
                    }

                    // looping through the output words and adding them to the unique words list if they match the regex
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
//                    final StringBuilder outputText = new StringBuilder();
//                    final StringBuilder newOutputText =
//                            new StringBuilder(String.valueOf(activityMainBinding.activityMainTextField.getText()));

                        if (String.valueOf(activityMainBinding.activityMainOutputField.getText()).isEmpty()) {
                            activityMainBinding.activityMainOutputField.setText("");
                        }
                        inputText = inputText.replaceAll("\n", "<br>");

                        for (String sequence : uniqueMatchedWordsList) {
//                            Pattern p = Pattern.compile(sequence);
//                            Matcher m = p.matcher("one cat two cats in the yard");
//                            StringBuffer sb = new StringBuffer();
//
//                            while (m.find()) {
//                                m.appendReplacement(sb, "dog");
//                            }
//
//                            m.appendTail(sb);

                            inputText = inputText.replaceAll(sequence, Utility.getHighlightedWord(sequence,
                                    MainActivity.this));
                        }

                        activityMainBinding.activityMainOutputField.setText(Html.fromHtml(inputText,
                                Html.FROM_HTML_MODE_LEGACY));
                    }
                }
            } else {
                showMessage(getResources().getString(inputText.isEmpty() && inputRegex.isEmpty() ?
                        R.string.error_empty_fields : inputText.isEmpty() ?
                        R.string.error_empty_text : R.string.error_regex_not_valid));
            }
        });
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
                activityMainBinding.activityMainOutputField
                        .setText(!String.valueOf(activityMainBinding.activityMainRegexField.getText()).isEmpty() ?
                                charSequence : "");
            }

            @Override
            public void afterTextChanged(final Editable editable) {

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

                // if the output field isn't empty and the regex is valid
                if (!outputFieldText.isEmpty() && Utility.regexIsValid(inputRegex)) {
                    final String[] outputWordsArray = outputFieldText.split(" ");
                    final ArrayList<String> uniqueMatchedWordsList = new ArrayList<>();
                    final Pattern pattern = Pattern.compile(inputRegex);

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
                        outputFieldText = outputFieldText.replaceAll("\n", "<br>");

                        for (String sequence : uniqueMatchedWordsList) {
//                            Pattern p = Pattern.compile(sequence);
//                            Matcher m = p.matcher("one cat two cats in the yard");
//                            StringBuffer sb = new StringBuffer();
//
//                            while (m.find()) {
//                                m.appendReplacement(sb, "dog");
//                            }
//
//                            m.appendTail(sb);

                            outputFieldText = outputFieldText.replaceAll(sequence, Utility.getHighlightedWord(sequence,
                                    MainActivity.this));
                        }

                        activityMainBinding.activityMainOutputField.setText(Html.fromHtml(outputFieldText,
                                Html.FROM_HTML_MODE_LEGACY));
                    }
                } else {
                    activityMainBinding.activityMainOutputField.setText("");
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
    }

    private void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}