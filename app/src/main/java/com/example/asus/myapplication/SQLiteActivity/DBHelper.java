package com.example.asus.myapplication.SQLiteActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.asus.myapplication.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 4/27/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    //coloums of the appointment table
    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_NAME = "appointments";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_DATE = "date";
    public static final String TABLE_COLUMN_TIME = "time";
    public static final String TABLE_COLUMN_TITLE = "title";
    public static final String TABLE_COLUMN_DETAIL = "detail";


      public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factoryCursor, int version) {
   super(context, DATABASE_NAME, factoryCursor, DB_VERSION);
 }


    public void onCreate(SQLiteDatabase db) {     //CRETES TABLES AND COLOUMS

String que =  " CREATE TABLE " + TABLE_NAME + "(" + TABLE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+TABLE_COLUMN_DATE + " TEXT ,"+
        TABLE_COLUMN_TIME + " DATETIME ," + TABLE_COLUMN_TITLE + " TEXT ," +TABLE_COLUMN_DETAIL +" TEXT "+ ");";

//EXECUTE DB SQLITE DATABASE
        db.execSQL(que);
        Log.i("db",que);
    }


    /**
     * If you want to change the database, this method  drops/delete the current table
     *IF IT IS EXISTS
     * @param db The SQLite database
     * @param oldVersion Old Version of the db
     * @param newVersion New Version of the db
     */

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * checking whther appoinemnts exists with the same appoinement title
     *
     *
     * @param appointmentUser
     * @return
     */

public boolean createAppointment(Appointment appointmentUser){
    SQLiteDatabase DB = getWritableDatabase();


    //excute insert quries
    String sqlDB = " SELECT * FROM " + TABLE_NAME + " WHERE "
            + TABLE_COLUMN_DATE + "=\'" + appointmentUser.getDate() + "\'" + " AND " + TABLE_COLUMN_TITLE
            + "=\'" + appointmentUser.getTitle() + "\';";

    //select query for retrieving data from table
    Cursor cur = DB.rawQuery(sqlDB,null);


    /**
     * test whether the query returned an empty set
     * (by testing the return value) and it moves the cursor to the first result/row
     * (when the set is not empty)
     */

    if(cur==null || !cur.moveToFirst()){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_DATE , appointmentUser.getDate());
        contentValues.put(TABLE_COLUMN_TIME , appointmentUser.getTime());
        contentValues.put(TABLE_COLUMN_TITLE , appointmentUser.getTitle());
        contentValues.put(TABLE_COLUMN_DETAIL , appointmentUser.getDetail());


        DB.insert(TABLE_NAME , null , contentValues);//inserting values into the database
        DB.close(); //restoring the memory
        cur.close();

        return true;
    }else{


        return false;
    }



}

    /**
     *
     * when user clicks delete btn ,it will delete selectd appointmnets accrdng to the date
     * @param date
     * @param title
     */

        public void deleteAppointment(String date,String title){
            SQLiteDatabase DB = getWritableDatabase();
            Log.i("dsdsdsdddddd","date");
            DB.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_COLUMN_DATE  + "=\'" + date + "\'" + " AND " + TABLE_COLUMN_TITLE +
            "=\'" + title + "\';" );
            DB.close();
        }

/**
 * delete all appointments
 *
 */

        public void deleteAllAppointment(String date){
            SQLiteDatabase DB = getWritableDatabase();
            Log.i("dsdsdsdddddd","date");
            DB.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_COLUMN_DATE  + "=\'" + date + "\';");
            DB.close();

            }



            /**
             * show the data base in string
             */
    public  String showDatabase(){
        String show="";
        SQLiteDatabase DB = getWritableDatabase();
        String que = "SELECT * FROM " + TABLE_NAME + " WHERE 1 ";//search every where
        Cursor cur = DB.rawQuery(que,null);
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            show += cur.getString(cur.getColumnIndex("date"));
            show += ("\n");
            show += cur.getString(cur.getColumnIndex("time"));
            show += ("\n");
            show += cur.getString(cur.getColumnIndex("title"));
            show += ("\n");
            show += cur.getString(cur.getColumnIndex("detail"));
            cur.moveToNext();

        }
        DB.close();
return show;
    }
//show appointments in a one day
public List<Appointment> show_Appointments(String date){


    List<Appointment> listArray = new ArrayList<>();

    SQLiteDatabase DB = getWritableDatabase();
    String que = "SELECT * FROM " + TABLE_NAME + " WHERE "+ TABLE_COLUMN_DATE + "=\'" + date + "\'" + " ORDER BY " + TABLE_COLUMN_TIME + " ASC" ; //sort the result set in ascending order
   //exposes the result from database
    Cursor cur = DB.rawQuery(que,null);
    cur.moveToFirst();

    while (!cur.isAfterLast()) {
        if (cur.getString(cur.getColumnIndex("title")) != null) {
            Appointment appoint = new Appointment(cur.getString(cur.getColumnIndex("date")),
                    cur.getString(cur.getColumnIndex("time")),
                    cur.getString(cur.getColumnIndex("title")),
                    cur.getString(cur.getColumnIndex("detail")));
            listArray.add(appoint);
        }
        cur.moveToNext();
    }
DB.close();
return  listArray;
}

public boolean edit(Appointment appointmentUser,String time,String title,String detail ) {
    SQLiteDatabase DB = getWritableDatabase();

    String sqlDB = " SELECT * FROM " + TABLE_NAME + " WHERE "
            + TABLE_COLUMN_DATE + "=\'" + appointmentUser.getDate() + "\'" + " AND " + TABLE_COLUMN_TITLE
            + "=\'" + appointmentUser.getTitle() + "\';";

    Cursor cur = DB.rawQuery(sqlDB, null);

    if (cur == null || !cur.moveToFirst()) {


        return  false;
    } else {
        //check the values and update
        ContentValues contentValues = new ContentValues();

        contentValues.put(TABLE_COLUMN_TIME , time);
        contentValues.put(TABLE_COLUMN_TITLE , title);
        contentValues.put(TABLE_COLUMN_DETAIL , detail);

        DB.update(TABLE_NAME, contentValues , TABLE_COLUMN_DATE + "='" + appointmentUser.getDate() + "'" + " AND " +//inserting values
                TABLE_COLUMN_TITLE + "='" + appointmentUser.getTitle() + "'" , null);
        cur.close();
    DB.close();//resoritng db memmory
        return true;
    }

}


public  boolean move (Appointment appointmentUser,String date){
    SQLiteDatabase DB = getWritableDatabase();
    String sqlDB = " SELECT * FROM " + TABLE_NAME + " WHERE "
            + TABLE_COLUMN_DATE + "=\'" + appointmentUser.getDate() + "\'" + " AND " + TABLE_COLUMN_TITLE
            + "=\'" + appointmentUser.getTitle() + "\';";


    Cursor cur = DB.rawQuery(sqlDB, null);
    if (cur == null || !cur.moveToFirst()) {


        return  false;
    }else{
        ContentValues contentValues = new ContentValues();

        contentValues.put(TABLE_COLUMN_DATE , date);


        DB.update(TABLE_NAME, contentValues , TABLE_COLUMN_DATE + "='" + appointmentUser.getDate() + "'" + " AND " +//inserting values
                TABLE_COLUMN_TITLE + "='" + appointmentUser.getTitle() + "'" , null);
        cur.close();
        DB.close();//resoritng db memmory
        return true;
    }

}

    /**
     *
     * returns all the appointmnets
     * @return
     */
    public List<Appointment> showAllAppointments( ){
        List<Appointment> arrList = new ArrayList<>();

        SQLiteDatabase DB = getWritableDatabase();
        String que = "SELECT * FROM " + TABLE_NAME +";"  ; //sort the result set in ascending order
        //exposes the result from database
        Cursor cur = DB.rawQuery(que,null);
        cur.moveToFirst();

        while (!cur.isAfterLast()) { //see wthere theres any coloums
            if (cur.getString(cur.getColumnIndex("title")) != null) {
                Appointment appoint = new Appointment(cur.getString(cur.getColumnIndex("date")),
                        cur.getString(cur.getColumnIndex("time")),
                        cur.getString(cur.getColumnIndex("title")),
                        cur.getString(cur.getColumnIndex("detail")));
                arrList.add(appoint);
            }
            cur.moveToNext();
        }
        DB.close();
 return arrList;
}

}
