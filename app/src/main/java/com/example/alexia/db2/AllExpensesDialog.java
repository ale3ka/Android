package com.example.alexia.db2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexia.db2.com.example.alexia.DBhelper.DatabaseHelper;
import com.example.alexia.db2.model.Category;
import com.example.alexia.db2.model.Expense;

import java.util.ArrayList;

import static com.example.alexia.db2.AllExpenses.selectedFromExpenses;

/**
 * Created by alexia on 21/12/2016.
 */

public class AllExpensesDialog extends DialogFragment {

    LayoutInflater inflater;
    View v;
    EditText name, amount, address, description;
    String name_str, address_str, description_str;
    float amount_float;
    DatabaseHelper adapter;
    Category cat;
    Expense exp = new Expense();
    String details = "";


    static final ArrayList<String> expenses = new ArrayList<>();

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        adapter = new DatabaseHelper(getActivity());
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.all_expenses_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView title = (TextView) v.findViewById(R.id.all_expenses_TV);
        exp = adapter.getExpense(selectedFromExpenses);
        cat = adapter.getCategoryByExpense(selectedFromExpenses);


        details = String.format("%s %2$10s", cat.toString(), exp.toString());
        title.setText(details);
        builder.setView(v).setPositiveButton("Change", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                ModifyExpense modifyExpense = new ModifyExpense();
                modifyExpense.show(getActivity().getSupportFragmentManager(), ",modify_expense_dialog");
            }
        });
        return builder.create();
    }
}
