package com.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {



    public static String DATABASE_NAME = "patient_db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_PATIENT = "patient";
    private static final String TABLE_Reservation = "Reservation";


    private static final String KEY_ID = "id";


    private static final String KEY_NAME_DOCTOR= "nameDoc";
    private static final String KEY_NAME_CLINIC = "clinic";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";



    private static final String KEY_ID_PATIENT = "IDpatient";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_AGE = "age";



    private static final String CREATE_TABLE_PATIENT = "CREATE TABLE "
            + TABLE_PATIENT + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_AGE  + " TEXT );";




    private static final String CREATE_TABLE_Reservation = "CREATE TABLE "
            + TABLE_Reservation + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME_DOCTOR + " TEXT, " + KEY_NAME_CLINIC + " TEXT, " + KEY_DATE + " TEXT, " + KEY_TIME  + " TEXT, " + KEY_ID_PATIENT  + " INTEGER );";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PATIENT);
        db.execSQL(CREATE_TABLE_Reservation);


    }

    //In onUpgrade() method, already exist tables are dropped and then all tables are recreated.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_PATIENT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_Reservation + "'");

        onCreate(db);
    }



    public long Add_Patient(String name, String password, String email,String age ) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_EMAIL, email);
        values.put(KEY_AGE, age);

        // insert row in students table
        long insert = db.insert(TABLE_PATIENT, null, values);

        return insert;
    }

    public int checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};


        Cursor cursor = db.query(TABLE_PATIENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        cursor.moveToFirst();
     int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return  id;
        }

        return -1;
    }



    public long Add_Reservation(String nameDoc, String nameCin, String date ,String time ,int  idPatient) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_DOCTOR, nameDoc);
        values.put(KEY_NAME_CLINIC, nameCin);
        values.put(KEY_DATE, date);
        values.put(KEY_TIME, time);
        values.put(KEY_ID_PATIENT, idPatient);

        // insert row in students table
        long insert = db.insert(TABLE_Reservation, null, values);

        return insert;
    }


    public int updateRes(Reservation_Model reservation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_DOCTOR,reservation.getNameD());
        values.put(KEY_NAME_CLINIC, reservation.getNameC());
        values.put(KEY_TIME, reservation.getTime());
        values.put(KEY_DATE, reservation.getDate());
        values.put(KEY_ID_PATIENT, reservation.getId_patient());
        // updating row
        return db.update(TABLE_Reservation, values, KEY_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
    }

    public void deleteRes(Reservation_Model reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Reservation, KEY_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
        db.close();
    }

    public ArrayList<Reservation_Model> getAllReservatinList(int idP) {
    //    ArrayList<String> studentsArrayList = new ArrayList<String>();
        ArrayList<Reservation_Model> ReservatinArrayList = new ArrayList<Reservation_Model>();
        int id= 0,id_patient = 0;
        String nameDoc="",nameCli="",date ="",time ="";

        String selectQuery = "SELECT  * FROM " + TABLE_Reservation + " WHERE " + KEY_ID_PATIENT + " = " + "'" + idP + "'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                id = c.getInt(c.getColumnIndex(KEY_ID));
                nameDoc = c.getString(c.getColumnIndex(KEY_NAME_DOCTOR));
                nameCli = c.getString(c.getColumnIndex(KEY_NAME_CLINIC));
                time = c.getString(c.getColumnIndex(KEY_TIME));
                date = c.getString(c.getColumnIndex(KEY_DATE));
                id_patient = c.getInt(c.getColumnIndex(KEY_ID_PATIENT));

                Reservation_Model res = new Reservation_Model(id,nameDoc,nameCli,time,date ,id_patient);

                ReservatinArrayList.add(res);
            } while (c.moveToNext());
            Log.d("array", ReservatinArrayList.toString());
        }
        return ReservatinArrayList;
    }
}