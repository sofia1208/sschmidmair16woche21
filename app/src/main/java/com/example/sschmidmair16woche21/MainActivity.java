package com.example.sschmidmair16woche21;

import android.app.DatePickerDialog;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    View view;
    Rechnung rechnung;
    List<Rechnung> list = new ArrayList<>();
    ArrayAdapter<Rechnung> mAdapter;
    List<String> categorys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillSpinner1();
        fillSpinner2();

        TextView viewById = findViewById(R.id.cash);
        if(Rechnung.cash!=0)
        {
            viewById.setText("CA$H " + Rechnung.cash + " €");
        }

    }
    private List<String> readAssets ()
    {

        InputStream in = getInputStreamForAsset("reasons.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {

            while((line = bufferedReader.readLine()) != null)
            {
                String [] array  =  line.split(";");
                for (int i = 0; i <array.length ; i++) {
                    categorys.add(array[i]);
                }
                 return categorys;
            }
        }
        catch (Exception e)
        {

        }
        return new ArrayList<>();

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
        List<String> strings = readAssets();
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

    public void addRechnung(View view) {
        Spinner spinnerAusgaben = findViewById(R.id.spinner);
        Spinner spinnerCategory = findViewById(R.id.spinner2);

        String ausgabe = (String) spinnerAusgaben.getSelectedItem().toString();

        String category = "";
        TextView t = findViewById(R.id.textCategory);
        if(t.getText().equals(""))
        {
             category = (String) spinnerCategory.getSelectedItem().toString();
        }
        else
        {
             category = String.valueOf( t.getText());
        }


       TextView textDate =  findViewById(R.id.dateText);
        EditText viewById = findViewById(R.id.moneyText);


        String text = String.valueOf(viewById.getText());
        String text1 = (String) textDate.getText();

        list.add(new Rechnung(text1,ausgabe,text ,category));

       ListView lv =  findViewById(R.id.listView);
        bindAdapterToListView(lv,list);

     checkArray(category);
     fillSpinner2();



    }
    private void checkArray(String string)
    {  if(!categorys.contains(string))
    {
        categorys.add(string);
        print();
    }

    }


    private void bindAdapterToListView (ListView lv, List list)
    {
        mAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );
        lv.setAdapter(mAdapter);
    }
    private void print ()
    {
        File file = new File("reasons.csv");
        // if file doesnt exists, then create it


        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
          for(String s : categorys)
          {
              bw.write(s +";");
          }

            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }
}


