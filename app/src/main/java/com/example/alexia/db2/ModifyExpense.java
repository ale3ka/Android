package com.example.alexia.db2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexia.db2.AllExpenses;
import com.example.alexia.db2.R;
import com.example.alexia.db2.com.example.alexia.DBhelper.DatabaseHelper;
import com.example.alexia.db2.model.Category;
import com.example.alexia.db2.model.Expense;

import java.util.ArrayList;
import java.util.List;

import static com.example.alexia.db2.AllExpenses.selectedFromExpenses;
import static com.example.alexia.db2.MainActivity.selectedFromList;

/**
 * Created by alexia on 21/12/2016.
 */

public class ModifyExpense extends DialogFragment {

    LayoutInflater inflater;
    View v;
    EditText amount,description;
    Spinner category;
    String name_str, category_str, description_str;
    float amount_float;
    DatabaseHelper adapter;


     ArrayList<Category> categories = new ArrayList<>();
    ArrayList names = new ArrayList<>();

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        adapter = new DatabaseHelper(getActivity());
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.modify_expense_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView title = (TextView) v.findViewById(R.id.ModifyExpenseTextView);
        title.setText(selectedFromExpenses);
        category = (Spinner) v.findViewById(R.id.category_Spinner);
        categories = (ArrayList)adapter.getAllCategories();

        for(Category cat:categories){
            names.add(cat.getName());
        }
        ArrayAdapter<String> dataAdapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,names);
        category.setAdapter(dataAdapter);

        amount = (EditText) v.findViewById(R.id.amount_modify);
        amount.setText(Float.toString(adapter.getExpense(selectedFromExpenses).getAmount()));
        builder.setView(v).setPositiveButton("Alter", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        amount_float = Float.parseFloat(amount.getText().toString());

                        description = (EditText) v.findViewById(R.id.description_modify);
                        description_str = description.getText().toString();

                        category_str = category.getSelectedItem().toString();
                        Log.i("Updated",category_str);
                        Expense expense = adapter.getExpense(selectedFromExpenses);
                        expense.setAmount(amount_float);
                        expense.setDescription(description_str);
                        adapter.updateExpense(expense);
                        Category cat = adapter.getCategory(category_str);
                        adapter.updateDetails(expense,(long) cat.getId());
                        Toast.makeText(getActivity(),"Updated"+category_str,Toast.LENGTH_LONG);

                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.deleteExpense((long) adapter.getExpense(selectedFromExpenses).getId());

                    }

                    });
        return builder.create();
    }
}

