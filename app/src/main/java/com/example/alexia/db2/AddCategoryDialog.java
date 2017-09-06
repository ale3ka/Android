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
import android.widget.Toast;

import com.example.alexia.db2.com.example.alexia.DBhelper.DatabaseHelper;
import com.example.alexia.db2.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexia on 12/12/2016.
 */

public class AddCategoryDialog extends DialogFragment {

    LayoutInflater inflater;
    View v;
    EditText name,description;
    String category,details;
    DatabaseHelper adapter ;
    public static ArrayList<String> category_names = new ArrayList<String>();



    public Dialog onCreateDialog( Bundle savedInstanceState){

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.cat_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                name = (EditText) v.findViewById(R.id.eCategoryNameEditText);
                category = name.getText().toString();
                description = (EditText) v.findViewById(R.id.categoryDescriptionEditText);
                details = description.getText().toString();

                Category cat = new Category(category,details);
                //store to database
                DatabaseHelper db = new DatabaseHelper(getActivity());

                long result = db.createCategory(cat);
                //if the data are stored clear the editTexts for the user,else pop a message
                if(result>0){
                    name.setText("");
                    description.setText("");
                    Toast.makeText(getActivity(),"Data saved",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Failure to access database",Toast.LENGTH_SHORT).show();
                }


                category_names.clear();
                List<Category> categories = db.getAllCategories();


                // Writing Category to log
                for (Category ct : categories) {
                    String log = ct.getName();
                    category_names.add(log);

                    Log.d("Category: ", log);
                }
                }
                //close DB




        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
