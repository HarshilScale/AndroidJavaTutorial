package com.demo.androidjavatutorial.ChipTagView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.demo.androidjavatutorial.ChipTagView.TagView.Person;
import com.demo.androidjavatutorial.ChipTagView.TagView.PersonAdapter;
import com.demo.androidjavatutorial.MainActivity;
import com.demo.androidjavatutorial.ChipTagView.TagView.ContactsCompletionView;
import com.demo.androidjavatutorial.ChipTagView.TagView.TokenCompleteTextView;
import com.example.androidjavatutorial.R;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity implements TokenCompleteTextView.TokenListener<Person> {

    ContactsCompletionView completionView;
    ArrayAdapter<Person> adapter;
    TextView lastEvent, txtvalue;
    Person[] people;
    ArrayList<String> hobbiesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        people = Person.samplePeople();
        adapter = new PersonAdapter(this, R.layout.person_layout, people);


        completionView = (ContactsCompletionView) findViewById(R.id.tagView);
        lastEvent = findViewById(R.id.lastEvent);
        txtvalue = findViewById(R.id.textValue);
        completionView.setAdapter(adapter);
        completionView.setThreshold(1);
        completionView.setTokenListener((TokenCompleteTextView.TokenListener<Person>) TagActivity.this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        completionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ((TextView) findViewById(R.id.textValue)).setText(editable.toString());
            }
        });
    }

    private void updateTokenConfirmation() {
        if (!hobbiesArrayList.isEmpty()) {
            hobbiesArrayList.clear();
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        for (Object token : completionView.getObjects()) {
            sb.append(token.toString());
            sb.append(",");

            hobbiesArrayList.add(token.toString());
        }
    }

    @Override
    public void onTokenAdded(Person token) {
        ((TextView) findViewById(R.id.lastEvent)).setText("Added: " + token);
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Person token) {
        ((TextView) findViewById(R.id.lastEvent)).setText("Removed: " + token);
        updateTokenConfirmation();
    }

    @Override
    public void onTokenIgnored(Person token) {
        ((TextView) findViewById(R.id.lastEvent)).setText("Ignored: " + token);
        updateTokenConfirmation();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TagActivity.this, MainActivity.class));
        finish();
    }
}