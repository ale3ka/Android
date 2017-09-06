package com.example.alexia.db2;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexia.db2.com.example.alexia.DBhelper.DatabaseHelper;
import com.example.alexia.db2.model.Category;
import com.example.alexia.db2.model.Expense;

import java.util.ArrayList;
import java.util.List;
//import static com.example.alexia.db2.MainActivity.mLatitude;
//import static com.example.alexia.db2.MainActivity.mLongitude;
import static com.example.alexia.db2.AllExpenses.selectedFromExpenses;
import static com.example.alexia.db2.MainActivity.selectedFromList;

/**
 * Created by alexia on 14/12/2016.
 */

public class AddExpenseDialog extends DialogFragment {

    LayoutInflater inflater;
    View v;
    EditText name,amount,address,description;
    String name_str, address_str, description_str;
    float amount_float;
    DatabaseHelper adapter;


    static final ArrayList<String> expenses = new ArrayList<>();

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.exp_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView title = (TextView) v.findViewById(R.id.add_expense_textView);
        title.setText("Add Expense");
        builder.setView(v).setPositiveButton("Add", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                name = (EditText) v.findViewById(R.id.exp_name_editText);
                name_str = name.getText().toString();
                amount = (EditText) v.findViewById(R.id.exp_amount_editText);
                amount_float = Float.parseFloat(amount.getText().toString());
                address = (EditText) v.findViewById(R.id.exp_address_editText);
                address_str = address.getText().toString();
                description = (EditText) v.findViewById(R.id.exp_description_editText);
                description_str = description.getText().toString();

                adapter = new DatabaseHelper(getActivity());
                String dateSaved = adapter.getDateTime();
                Expense expense = new Expense(name_str, amount_float, description_str, dateSaved);

                //store to database

                List<Category> categories = adapter.getAllCategories();
                long cat_id=-1;
                for (Category cat : categories) {
                    Log.d("Category name ",cat.getName());
                    Log.d ("Selected from list",selectedFromList);
                    boolean isT = cat.getName().intern() == selectedFromList.intern();
                    Log.d("Apotelesma",cat.getName()+Boolean.toString(isT));
                    if (cat.getName().intern() == selectedFromList.intern()) {
                        cat_id = (long) cat.getId();
                        String log = "Id: " + cat.getId() + " ,Name: " + cat.getName() + " ,Desc: " + cat.getDescription();
                        // Writing Contacts to log
                        Log.d("Contact: ", log);
                        Toast.makeText(getActivity(),log,Toast.LENGTH_LONG).show();
                    }
                }
                Log.d("cat_id: ", Long.toString(cat_id));
                //long address_id= adapter.createAddress(MainActivity.mLongitude,MainActivity.mLatitude,address_str);
                long result = adapter.createExpense(expense, cat_id/*,address_id*/);


                //if the data are stored clear the editTexts for the user,else pop a message
                if (result > 0) {
                    name.setText("");
                    description.setText("");
                    Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Failure to access database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }
}
