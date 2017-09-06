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

import java.util.ArrayList;
import java.util.List;

import static com.example.alexia.db2.AddCategoryDialog.category_names;
import static com.example.alexia.db2.MainActivity.selectedFromList;

/**
 * Created by alexia on 20/12/2016.
 */

public class ModifyCategory extends DialogFragment {

    LayoutInflater inflater;
    View v;
    EditText name,description;
    String category,details;
    DatabaseHelper db ;
    Category cat;





    public Dialog onCreateDialog(Bundle savedInstanceState){

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.cat_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //retrieve selected category from database
        db = new DatabaseHelper(getActivity());
        cat = db.getCategory(selectedFromList);
        Log.i("Retrieved from database", cat.getName());
        //Change the title and hint of dialog box
        TextView title = (TextView) v.findViewById(R.id.enter_category);
        title.setText("Modify Category");
        name = (EditText) v.findViewById(R.id.eCategoryNameEditText);
        description = (EditText) v.findViewById(R.id.categoryDescriptionEditText);
        name.setHint(cat.getName());
        description.setHint(cat.getDescription());




        builder.setView(v).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                //get new input and store to database
                details = description.getText().toString();
                category = name.getText().toString();
                cat.setName(category);
                cat.setDescription(details);

                long result = db.updateCategory(cat);

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

                for (Category ct : categories) {
                    String log = ct.getName();
                    category_names.add(log);
                    // Writing Contacts to log
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

