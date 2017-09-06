package com.example.alexia.db2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexia.db2.com.example.alexia.DBhelper.DatabaseHelper;
import com.example.alexia.db2.model.Category;
import com.example.alexia.db2.model.Expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.alexia.db2.MainActivity.selectedFromList;

public class AllExpenses extends AppCompatActivity {
    ArrayList<String> expenses_names = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public static  String selectedFromExpenses;
    DatabaseHelper db;
    private String details="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);


        db = new DatabaseHelper(this);
        List<Expense> expeses = db.getAllExpenses();
        for (Expense exp : expeses) {
            String expense = exp.getName();
            expenses_names.add(expense);
        }
        final ListView list_expenses = (ListView) findViewById(R.id.all_expenses_listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, expenses_names);
        list_expenses.setAdapter(arrayAdapter);



        list_expenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFromExpenses = list_expenses.getItemAtPosition(position).toString();
                AllExpensesDialog exp_dialog = new AllExpensesDialog();
                exp_dialog.show(getSupportFragmentManager(), "all_expenses_dialog");

            }
        });
    }
}

