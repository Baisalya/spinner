package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner countrySpinner;
    private Button addButton;
    private LinearLayout spinnerContainer;

    private List<String> countries;
    private List<String> selectedCountries;
    private ArrayAdapter<String> countryAdapter;
    private ArrayAdapter<String> selectedCountryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize lists
        countries = new ArrayList<>();
        selectedCountries = new ArrayList<>();

        // Add country names
        countries.add("Country 1");
        countries.add("Country 2");
        countries.add("Country 3");
        countries.add("Country 4");
        countries.add("Country 5");
        countries.add("Country 6");
        countries.add("Country 7");
        countries.add("Country 8");
        countries.add("Country 9");
        countries.add("Country 10");

        // Initialize views
        countrySpinner = findViewById(R.id.countrySpinner);
        addButton = findViewById(R.id.addButton);
        spinnerContainer = findViewById(R.id.spinnerContainer);

        // Set up adapter for country spinner
        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setOnItemSelectedListener(this);

        // Set up button click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCountry = countrySpinner.getSelectedItem().toString();
                selectedCountries.add(selectedCountry);
                countries.remove(selectedCountry);

                refreshSpinners();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        addButton.setEnabled(!countries.isEmpty());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void refreshSpinners() {
        // Clear the container
        spinnerContainer.removeAllViews();

        // Refresh the country spinner
        countryAdapter.notifyDataSetChanged();

        // Create spinners for selected countries
        for (String country : selectedCountries) {
            createSpinnerForCountry(country);
        }

        // Clear selection
        countrySpinner.setSelection(0);

        if (countries.isEmpty()) {
            addButton.setEnabled(false);
            Toast.makeText(MainActivity.this, "No countries available", Toast.LENGTH_SHORT).show();
        }
    }

    private void createSpinnerForCountry(final String country) {
        // Create new LinearLayout for the row
        LinearLayout rowLayout = new LinearLayout(MainActivity.this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create new spinner for selected country
        final Spinner selectedCountrySpinner = new Spinner(MainActivity.this);
        ArrayAdapter<String> selectedCountryAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, countries);
        selectedCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectedCountrySpinner.setAdapter(selectedCountryAdapter);

        // Set the selection of the new spinner
        int selectionIndex = selectedCountries.indexOf(country);
        selectedCountrySpinner.setSelection(selectionIndex);

        // Add the new spinner to the row layout
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        rowLayout.addView(selectedCountrySpinner, spinnerParams);

        // Create add button for the spinner
        Button addButton = new Button(MainActivity.this);
        addButton.setText("+");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addedCountry = selectedCountrySpinner.getSelectedItem().toString();
                selectedCountries.add(addedCountry);
                countries.remove(addedCountry);

                refreshSpinners();
            }
        });

        // Add the add button to the row layout
        LinearLayout.LayoutParams addButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rowLayout.addView(addButton, addButtonParams);

        // Create remove button for the spinner
        Button removeButton = new Button(MainActivity.this);
        removeButton.setText("-");
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String removedCountry = selectedCountrySpinner.getSelectedItem().toString();
                countries.add(removedCountry);
                selectedCountries.remove(removedCountry);

                refreshSpinners();
            }
        });

        // Add the remove button to the row layout
        LinearLayout.LayoutParams removeButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rowLayout.addView(removeButton, removeButtonParams);

        // Add the row layout to the container
        spinnerContainer.addView(rowLayout);

        // Update selectedCountryAdapter with the updated selectedCountries list
        selectedCountryAdapter.notifyDataSetChanged();
    }

}
