package com.example.alexia.db2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.view.View.OnClickListener;

import com.example.alexia.db2.com.example.alexia.DBhelper.DatabaseHelper;
import com.example.alexia.db2.model.Category;
import com.example.alexia.db2.model.Expense;

/**
 * Created by alexia on 28/12/2016.
 */

public class ExpesesByDate extends AppCompatActivity {

    ArrayList<String> category_names = new ArrayList<>();


    ArrayAdapter arrayAdapter;
    ListView expensesByDate;
    EditText startDate;
    EditText endDate;
    DatePickerDialog fromDatePickerDialog;
    DatePickerDialog toDatePickerDialog;
    SimpleDateFormat dateFormatter;
    DatabaseHelper db;
    String start;
    String end;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_by_date);


        
        dateFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());


        startDate = (EditText) findViewById(R.id.startDateEditText);
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.requestFocus();

        endDate = (EditText) findViewById(R.id.EndDateEditText);
        endDate.setInputType(InputType.TYPE_NULL);

        Calendar newCalendar = Calendar.getInstance();

        //create datePickers
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                startDate.setText(dateFormatter.format(newDate.getTime()));
                start=dateFormatter.format(newDate.getTime());
            }
        },newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                endDate.setText(dateFormatter.format(newDate.getTime()));
                end=dateFormatter.format(newDate.getTime());
            }
        },newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
        startDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });

        expensesByDate = (ListView)findViewById(R.id.expenses_by_date_LV);
        db = new DatabaseHelper(this);
        Button button =(Button)findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                category_names.clear();
                //all_categories = db.getCatDates(start,end);
                for(Category cat:db.getCatDates(start,end)){
                    String addition= String.format("%1$-10s %2$50s" ,cat.getName(),cat.getDescription());
                    category_names.add(addition);
                   // Log.i("By Date",cat.toString());
                }

                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,category_names);
                expensesByDate.setAdapter(arrayAdapter);
            }
        });




    }

}