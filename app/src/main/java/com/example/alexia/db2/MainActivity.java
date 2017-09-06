package com.example.alexia.db2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexia.db2.com.example.alexia.DBhelper.DatabaseHelper;
import com.example.alexia.db2.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.example.alexia.db2.AddCategoryDialog.category_names;

public class MainActivity extends AppCompatActivity  {
    private SQLiteDatabase db;
    DatabaseHelper DBHelper ;
    private Cursor cursor = null;
    ArrayAdapter<String> arrayAdapter;
    public static String selectedFromList;
    public ArrayList<String> categories;
    public static final String KEY="key";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.duck);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

       final ListView list = (ListView) findViewById(R.id.categoryListView);

        //to show the list of categories
        // i return them from database
        DBHelper = new DatabaseHelper(this);

        //for testing
        // DBHelper.deleteALL();


        //add to Array
        category_names.clear();



        List<Category> categories = DBHelper.getAllCategories();

        for (Category ct : categories) {
            String log = ct.getName();
            category_names.add(log);
            // Writing Contacts to log
            Log.d("Category: ", log);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, category_names);
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFromList = list.getItemAtPosition(position).toString();
                AddExpenseDialog exp_dialog = new AddExpenseDialog();

                exp_dialog.show(getSupportFragmentManager(), "exp_dialog");
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position,long id){
                selectedFromList = list.getItemAtPosition(position).toString();
                ModifyCategory modifyCategory = new ModifyCategory();
                modifyCategory.show(getSupportFragmentManager(), "exp_dialog");
                Toast.makeText(getApplicationContext(),"Button pressed long",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }




        //method on click for adding category

    public void AddCategory(View view) {
        AddCategoryDialog cat_dialog = new AddCategoryDialog();
        cat_dialog.show(getSupportFragmentManager(), "cat_dialog");
    }


    //adding to list onClick finctionality that shows the dialog for adding an expense

    public void AddExpense( View view) {
        // Do something when a list item is clicked
        AddExpenseDialog exp_dialog = new AddExpenseDialog();
        exp_dialog.show(getSupportFragmentManager(), "exp_dialog");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.all_expenses) {
            Intent i = new Intent(this,AllExpenses.class);

            startActivity(i);
            return true;
        }
        if (id == R.id.expenses_by_date) {
            Intent i = new Intent(this,ExpesesByDate.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.manual) {
            Intent i = new Intent(this,Manual.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
