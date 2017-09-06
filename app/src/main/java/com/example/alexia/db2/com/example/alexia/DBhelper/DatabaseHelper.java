package com.example.alexia.db2.com.example.alexia.DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alexia.db2.model.Category;
import com.example.alexia.db2.model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by alexia on 16/12/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";

    //DATABASE PROPERTIES
    static final String DB_NAME ="my_DB4";
    static final int DBVERSION=1;

    //TABLE NAMES
    static final String TABLE_CATEGORY ="category";
    static final String TABLE_ADDRESS ="address";
    static final String TABLE_EXPENSE ="expense";
    static final String TABLE_DETAILS ="details";

    //COMMON COLUMNS , CATEGORY TABLE COLUMNS
    static final String ROW_ID ="id";
    static final String NAME ="name";
    static final String DESCRIPTION = "description";

    //EXPENSE TABLE COLUMNS
    static final String AMOUNT= "amount";
    static final String DATETIME= "datetime";
    static final String CATEGORY_ID = "category_id";
    static final String ADDRESS_ID = "address_id";

    //ADDRESS TABLE COLUMNS
    static final String LONGITUDE = "longitude";
    static final String LATITUDE = "latitude";


    static final String EXPENSE_ID = "expense_id";






    //CREATE TABLES
    static final String CREATE_TABLE_CATEGORY= "CREATE TABLE "+TABLE_CATEGORY+" ("+ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +NAME+" TEXT NOT NULL,"+DESCRIPTION+" TEXT)";
    static final String CREATE_TABLE_EXPENSES = "CREATE TABLE "+TABLE_EXPENSE+" ("+ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +NAME+" TEXT NOT NULL,"+DESCRIPTION+" TEXT,"+AMOUNT+" TEXT NOT NULL,"+DATETIME+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)";

    static final String CREATE_TABLE_ADDRESS= "CREATE TABLE "+TABLE_ADDRESS+" ("+ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +LONGITUDE+" INTEGER NOT NULL,"+LATITUDE+" INTEGER NOT NULL, "+DESCRIPTION+" TEXT)";
    static final String CREATE_TABLE_EXPENSE_DETAILS = "CREATE TABLE "+TABLE_DETAILS+" ("+ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CATEGORY_ID+" INTEGER NOT NULL, "+ADDRESS_ID+" INTEGER , "+EXPENSE_ID+" INTEGER NOT NULL)";



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_ADDRESS);
            db.execSQL(CREATE_TABLE_CATEGORY);
            db.execSQL(CREATE_TABLE_EXPENSES);
            db.execSQL(CREATE_TABLE_EXPENSE_DETAILS);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DBAdapter", "Upgrading DB");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXPENSE);
        onCreate(db);


    }

    //CRUD (Create, Read, Update and Delete) Operations
    /*
    *Create an expense
     */
    public long createExpense(Expense expense, long category_id/*,long address_id*/){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(NAME,expense.getName());
        values.put(AMOUNT,expense.getAmount());
        values.put(DESCRIPTION,expense.getDescription());

        long expense_id = db.insert(TABLE_EXPENSE,null,values);
        createDetails(expense_id,category_id/*,address_id*/);

        return expense_id;
    }
    /*
        get single expense
     */

    public Expense getExpense(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_EXPENSE, new String[] {ROW_ID,
                        NAME,AMOUNT , DESCRIPTION, DATETIME}, NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (c != null)
            c.moveToFirst();

        Expense xp = new Expense();
        xp.setId(c.getInt(c.getColumnIndex(ROW_ID)));
        xp.setAmount((c.getInt(c.getColumnIndex(AMOUNT))));
        xp.setDatetime(c.getString(c.getColumnIndex(DATETIME)));
        xp.setName(c.getString(c.getColumnIndex(NAME)));
        xp.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
        return xp;
    }

    public Expense getExpense(long expense_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE + " WHERE "
                + ROW_ID + " = " + expense_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Expense xp = new Expense();
        xp.setId(c.getInt(c.getColumnIndex(ROW_ID)));
        xp.setAmount((c.getInt(c.getColumnIndex(AMOUNT))));
        xp.setDatetime(c.getString(c.getColumnIndex(DATETIME)));
        xp.setName(c.getString(c.getColumnIndex(NAME)));
        xp.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
        return xp;
    }


    /*
 * getting all expenses
 * */
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<Expense>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Expense xp = new Expense();
                xp.setId(c.getInt((c.getColumnIndex(ROW_ID))));
                xp.setAmount((c.getInt(c.getColumnIndex(AMOUNT))));
                xp.setDatetime(c.getString(c.getColumnIndex(DATETIME)));
                xp.setName(c.getString(c.getColumnIndex(NAME)));
                xp.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));

                // adding to expsense list
                expenses.add(xp);
            } while (c.moveToNext());
        }

        return expenses;
    }

    /*
* getting all expenses names
* */
    public List<String> getAllExpensesNames() {
        List<String> expenses = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to expsense list
                expenses.add(c.getString(1));
            } while (c.moveToNext());
        }

        return expenses;
    }
    /*
 * getting all expenses under single category
 * */
    public List<Expense> getAllExpensesByCategory(String cat_name) {
        List<Expense> expenses = new ArrayList<Expense>();

        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE + " xp, "
                + TABLE_CATEGORY + " cat, " + TABLE_DETAILS + " de WHERE cat."
                + NAME + " = '" + cat_name + "'" + " AND cat." + ROW_ID
                + " = " + "de." + CATEGORY_ID + " AND xp." + ROW_ID + " = "
                + "de." + EXPENSE_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Expense xp = new Expense();
                xp.setId(c.getInt((c.getColumnIndex(ROW_ID))));
                xp.setAmount((c.getInt(c.getColumnIndex(AMOUNT))));
                xp.setDatetime(c.getString(c.getColumnIndex(DATETIME)));
                xp.setName(c.getString(c.getColumnIndex(NAME)));
                xp.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));

                // adding to expenses list
                expenses.add(xp);
            } while (c.moveToNext());
        }

        return expenses;
    }

  /*
 *get category by the expense name
 */
    public Category getCategoryByExpense(String exp_name) {



        String selectQuery = "select category.id,category.name,category.description from category,expense,details where expense.name='"+exp_name+"'"+" and expense.id=details.expense_id  and category.id=details.category_id";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Category cat= new Category();
        // looping through all rows and adding to list
        //if (c!=null && c.getCount()>0)
           if( c.moveToFirst()) {
                do {
                    //category = new Category(Integer.parseInt(c.getString(0)),
                    //       c.getString(1), c.getString(2));
                    //cat = new Category();
                    cat.setId(c.getInt((c.getColumnIndex(ROW_ID))));
                    cat.setName(c.getString(c.getColumnIndex(NAME)));
                    cat.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
                }while (c.moveToNext());
           }
        return cat;

    }

    /*
    * update an expense
     */
    public void updateExpense(Expense expense){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String[] args={String.valueOf(expense.getId())};
        cv.put(NAME ,expense.getName());
        cv.put(DESCRIPTION, expense.getDescription());
        cv.put(AMOUNT,expense.getAmount());
        db.update(TABLE_EXPENSE,cv,"id=?",args);

    }
    /*
    * update details
     */
    public void updateDetails(Expense expense, long category_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String[] args={String.valueOf(expense.getId())};
        cv.put(CATEGORY_ID ,category_id);
        db.update(TABLE_DETAILS,cv,"expense_id=?",args);
    }

    /*
    * delete an expense
     */
    //delete
    public void deleteExpense (long  expense_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSE,"id=?",new String[] {String.valueOf(expense_id)});
    }

    /*
    * create a category
     */

    public long createCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, category.getName());
        values.put(DESCRIPTION, category.getDescription());

        // insert row
        long cat_id = db.insert(TABLE_CATEGORY, null, values);

        return cat_id;
    }


    /*
    * get category
     */
    public Category getCategory(long id){
        Category cat =new Category();
        String selectQuery = "SELECT * FROM category where id="+ id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                cat.setId(c.getInt((c.getColumnIndex(ROW_ID))));
                cat.setName(c.getString(c.getColumnIndex(NAME)));
                cat.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));

            } while (c.moveToNext());
        }
        return cat;
    }

    public Category getCategory(String name) {

           SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.query(TABLE_CATEGORY, new String[] {ROW_ID,
                            NAME , DESCRIPTION}, NAME + "=?",
                    new String[] { String.valueOf(name) }, null, null, null, null);
        Category cat = new Category();
        if (c.moveToFirst()) {

            do {

                cat.setId(c.getInt(c.getColumnIndex(ROW_ID)));
                cat.setName(c.getString(c.getColumnIndex(NAME)));
                cat.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
            }
            while (c.moveToNext());
        }
            return cat;


        }


    /*
    *   get all category names
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setId(c.getInt((c.getColumnIndex(ROW_ID))));
                cat.setName(c.getString(c.getColumnIndex(NAME)));
                cat.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
                // adding to tags list
                categories.add(cat);
            } while (c.moveToNext());
        }
        return categories;
    }

    /*
 * Updating a category
 */
    public int updateCategory(Category cat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String[] args = {String.valueOf(cat.getId())};
        values.put(NAME, cat.getName());
        values.put(DESCRIPTION,cat.getDescription());

        // updating row
        return db.update(TABLE_CATEGORY, values, "id = ?", args);
    }

    /*
    *  Delete a categoy.
     */

    // This method deletes all associated expenses as well
    public void deleteCategory(Category cat) {
        SQLiteDatabase db = this.getWritableDatabase();

            // get all relates expenses
            List<Expense> allExpenses = getAllExpensesByCategory(cat.getName());

            // delete all expenses
            for (Expense expense : allExpenses) {
                // delete expense
                deleteExpense(expense.getId());
            }


        // now delete the category
        String args[] = { String.valueOf(cat.getId()) };
        db.delete(TABLE_CATEGORY, "id = ?", args);
    }

    /*
    *  create details
    */
    public long createDetails(long expense_id, long category_id /*,long address_id*/ ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_ID, expense_id);
        values.put(CATEGORY_ID, category_id);
        //values.put(ADDRESS_ID, address_id);

        long id = db.insert(TABLE_DETAILS, null, values);

        return id;
    }

    /*
*  create details
*/
    public long createAddress(long logtitude, long latitude,String address ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LONGITUDE, logtitude);
        values.put(LATITUDE, latitude);
        values.put(DESCRIPTION,address);


        long id = db.insert(TABLE_ADDRESS, null, values);

        return id;
    }

    public Category getCategoryFromExpense(long id){
        Category cat = new Category();
        String selectQuery = "SELECT * FROM details where expense_id="+ id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                //cat.setId(c.getInt((c.getColumnIndex(CATEGORY_ID))));
                cat = getCategory(c.getLong(c.getColumnIndex(CATEGORY_ID)));

            } while (c.moveToNext());
        }
        return cat;
    }


    /*
    *  change category of expense
     */
    public int updateCategoryOfExpense(long id,long cat_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID,cat_id);
        String[] args={String.valueOf(id) };
        return db.update(TABLE_EXPENSE,values,"id+?",args);
    }

    /*
    *  close database
     */
    public void closeDB(){
        SQLiteDatabase db =this.getReadableDatabase();
        if (db !=null && db.isOpen()){
            db.close();
        }
    }

    /**
     * get datetime
     * */
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public List<Category> getCatDates(String start,String end ) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select category.name as name, sum(expense.amount) total from category,expense,details where  expense.id=details.expense_id and category.id=details.category_id and datetime(expense.datetime) between datetime(" + "'" + start + " 00:00:00') and datetime(" + "'" + end + " 00:00:00')  group by category.id order by total desc";
        Log.i("Query", query);
        Cursor c = db.rawQuery(query, null);


        List<Category> categories = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setName(c.getString(c.getColumnIndex("name")));
                cat.setDescription(c.getString(c.getColumnIndex("total")));
                categories.add(cat);
                Log.i("Catrgory", categories.get(0).toString());

            } while (c.moveToNext());
        }

        return categories;
    }
}



