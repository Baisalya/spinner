package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private Spinner countriesSpinner;
    private Button submitButton;
    private List<String> selectedCountries;
    private int count = 0;
    private List<String> countries;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        countriesSpinner = findViewById(R.id.countriesSpinner);
        submitButton = findViewById(R.id.submitButton);

        selectedCountries = new ArrayList<>();

        // Create a list of countries
        countries = new ArrayList<>();
        countries.add("INDIA");
        countries.add("Country 2");
        countries.add("Country 3");
        countries.add("Country 4");
        countries.add("Country 5");
        countries.add("Country 6");
        countries.add("Country 7");
        countries.add("Country 8");
        countries.add("Country 9");
        countries.add("Country 10");

        // Create an ArrayAdapter using the countries list and a default spinner layout
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        countriesSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCountry = countriesSpinner.getSelectedItem().toString();
                selectedCountries.add(selectedCountry);

                if (++count >= 10) {
                    Toast.makeText(MainActivity2.this, "No more countries left", Toast.LENGTH_SHORT).show();
                    submitButton.setEnabled(false);
                } else {
                    countries.remove(selectedCountry);
                    adapter.notifyDataSetChanged();
                    createNewSpinner();
                }
            }
        });
    }

    private void createNewSpinner() {
        // Disable the previous spinner
        countriesSpinner.setEnabled(false);

        Spinner newSpinner = new Spinner(this);
        newSpinner.setLayoutParams(countriesSpinner.getLayoutParams());
        newSpinner.setAdapter(adapter);
        newSpinner.setSelection(0);

        ((LinearLayout) findViewById(R.id.spinnerLayout)).addView(newSpinner);

        // Set the newly created spinner as the current spinner
        countriesSpinner = newSpinner;
    }
}
