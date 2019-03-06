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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    File file = new File("reasons.csv");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printFirst();
        fillSpinner1();
        fillSpinner2();

        TextView viewById = findViewById(R.id.cash);
        if(Rechnung.cash!=0)
        {
            viewById.setText("CA$H " + Rechnung.cash + " €");
        }

    }
    private void printFirst ()
    {

        try {
            FileOutputStream fos = openFileOutput(file.getName(), MODE_PRIVATE);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(fos));
            out.print("Essen;Schuhe;Shoppen;MakeUp;Party");
            out.flush();
            out.close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


    }
    private List<String> readAssets ()
    {

        List<String> list = new ArrayList<>();
        String line;
        categorys.clear();
        try {

            FileInputStream fis = openFileInput(file.getName());
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            while (true)
            {
                line = in.readLine();
                if(line == null) break;
                String[] split = line.split(";");
                for (int i = 0; i <split.length ; i++) {

                    categorys.add(split[i]);
                }
            }
        }
        catch (Exception e)
        {

        }
        return categorys;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorys);
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

        if(t.getText().toString().equals(""))
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

        TextView ca = findViewById(R.id.cash);
        if(Rechnung.cash!=0)
        {
            ca.setText("CA$H " + Rechnung.cash + " €");
        }



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
        mAdapter =new Rechnung_Adapter(this, R.layout.list_view, list);
        lv.setAdapter(mAdapter);
    }
    private void print ()
    {

        try {
            FileOutputStream fos = openFileOutput(file.getName(), MODE_PRIVATE);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(fos));
            for(String c : categorys)
            {
                out.print(c + ";");

            }
            out.flush();
            out.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

}


