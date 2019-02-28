package com.example.sschmidmair16woche21;

import android.app.DatePickerDialog;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    View view;
    Rechnung rechnung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillSpinner1();
        fillSpinner2();

       

    }
    private String[] readAssets ()
    {

        InputStream in = getInputStreamForAsset("reasons.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {

            while((line = bufferedReader.readLine()) != null)
            {
                return line.split(";");


            }
        }
        catch (Exception e)
        {

        }
        return new String[5];

    }

    private InputStream getInputStreamForAsset (String filename)
    {
        AssetManager assets = getAssets();
        try {
            return assets.open(filename);
        }
        catch (IOException e)
        {
            return null;
        }
    }
    private void fillSpinner1()
    {
        Spinner spinner = findViewById(R.id.spinner);
        String[] items = new String []{"Ausgaben", "Einnahmen"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }
    private void fillSpinner2()
    {
        Spinner spinner = findViewById(R.id.spinner2);
        String[] strings = readAssets();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        spinner.setAdapter(adapter);
    }

    public void pickDate(View view) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        TextView viewById = findViewById(R.id.dateText);
                        viewById.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    }


