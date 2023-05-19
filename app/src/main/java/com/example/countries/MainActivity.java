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
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
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

                // Create new LinearLayout for the row
                LinearLayout rowLayout = new LinearLayout(MainActivity.this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Create new spinner for selected country
                final Spinner selectedCountrySpinner = new Spinner(MainActivity.this);
                ArrayAdapter<String> selectedCountryAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, selectedCountries);
                selectedCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectedCountrySpinner.setAdapter(selectedCountryAdapter);

                // Set the selection of the new spinner
                int selectionIndex = selectedCountries.indexOf(selectedCountry);
                selectedCountrySpinner.setSelection(selectionIndex);

                // Add the new spinner to the row layout
                LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                rowLayout.addView(selectedCountrySpinner, spinnerParams);

                // Create remove button for the spinner
                Button removeButton = new Button(MainActivity.this);
                removeButton.setText("-");
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String removedCountry = selectedCountrySpinner.getSelectedItem().toString();
                        selectedCountries.remove(removedCountry);
                        countries.add(removedCountry);

                        // Update first spinner adapter
                        ArrayAdapter<String> countryAdapter = (ArrayAdapter<String>) countrySpinner.getAdapter();
                        countryAdapter.notifyDataSetChanged();

                        // Remove the row layout
                        spinnerContainer.removeView(rowLayout);
                    }
                });

                // Add the remove button to the row layout
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rowLayout.addView(removeButton, buttonParams);

                // Add the row layout to the container
                spinnerContainer.addView(rowLayout);

                // Clear selection
                countrySpinner.setSelection(0);

                if (countries.isEmpty()) {
                    addButton.setEnabled(false);
                    Toast.makeText(MainActivity.this, "No countries available", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        addButton.setEnabled(!countries.isEmpty());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
