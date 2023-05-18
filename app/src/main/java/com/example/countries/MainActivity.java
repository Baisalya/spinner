package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Button button;
    private TextView textView;
    private List<String> countries;
    private int selectionsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        // Initialize the countries list
        countries = new ArrayList<>(Arrays.asList(
                "INDIA", "Israel", "Afghanistan", "France",
                "Bhutan", "Pakistan", "Japan", "Nepal",
                "China", "UAE"
        ));

        // Create a custom adapter for the spinner
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries) {
            @Override
            public int getCount() {
                int count = super.getCount();
                return selectionsCount >= 10 && count == 0 ? 1 : count;
            }

            @Override
            public String getItem(int position) {
                if (selectionsCount >= 10 && getCount() == 1) {
                    return "No country available";
                }
                return super.getItem(position);
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set the OnItemSelectedListener for the spinner
        spinner.setOnItemSelectedListener(this);

        // Set the OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedCountry();
            }
        });
    }

    private void removeSelectedCountry() {
        // Get the selected item from the spinner
        String selectedCountry = spinner.getSelectedItem().toString();

        // Remove the selected item from the list
        countries.remove(selectedCountry);

        // Increase the selections count
        selectionsCount++;

        // If it's the 10th selection, disable the button and show the result
        if (selectionsCount == 10) {
            button.setEnabled(false);
            textView.setText(getString(R.string.no_countries_present));
        }

        // Notify the adapter that the data has changed
        ((ArrayAdapter) spinner.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Show a toast with the selected country
        String selectedCountry = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Selected: " + selectedCountry, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
