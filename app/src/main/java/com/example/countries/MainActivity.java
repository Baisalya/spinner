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


        spinner.setOnItemSelectedListener(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedCountry();
            }
        });
    }

    private void removeSelectedCountry() {

        String selectedCountry = spinner.getSelectedItem().toString();


        countries.remove(selectedCountry);


        selectionsCount++;


        if (selectionsCount == 10) {
            button.setEnabled(false);
            textView.setText(getString(R.string.no_countries_present));
        }


        ((ArrayAdapter) spinner.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedCountry = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Selected: " + selectedCountry, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
