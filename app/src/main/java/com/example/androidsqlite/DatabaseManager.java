package com.example.androidsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.ArrayList;
import android.database.Cursor;
import android.util.Log;

public class DatabaseManager {
    private static final String ROW_ID = "_id";
    private static final String ROW_NAMA = "nama";
    private static final String ROW_TELP = "telp";
    private static final String ROW_EMAIL = "email";
    private static final String ROW_JARAK = "jarak";
    private static final String ROW_POINT = "point";
    private static final String NAMA_DB = "database1";
    private static final String NAMA_TABEL = "tblpelanggan";
    private static final int DB_VERSION = 3;
    private static final String CREATE_TABLE = "create table IF NOT EXISTS " + NAMA_TABEL + " (" + ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, " + ROW_NAMA + " text," + ROW_TELP + " text," + ROW_EMAIL + " text, " + ROW_JARAK + " integer, " + ROW_POINT + " integer)";

    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(context);
        setDb(dbHelper.getWritableDatabase());
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context) {
            super(context, NAMA_DB, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL);
            onCreate(db);
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void UpdateRecord(int iId, String sName, String stelp, String sEmail, int iJarak, int iPoint) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, iId);
        values.put(ROW_NAMA, sName);
        values.put(ROW_TELP, stelp);
        values.put(ROW_EMAIL, sEmail);
        values.put(ROW_JARAK, iJarak);
        values.put(ROW_POINT, iPoint);
        try {
            db.update(NAMA_TABEL, values, ROW_ID + "=" + iId, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void DeleteRecord(int Iid) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, Iid);
        try {
            db.delete(NAMA_TABEL, ROW_ID + "=" + Iid, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRow(int Iid, String anama, String atelp, String aEmail, int iJarak, int iPoint) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, Iid);
        values.put(ROW_NAMA, anama);
        values.put(ROW_TELP, atelp);
        values.put(ROW_EMAIL, aEmail);
        values.put(ROW_JARAK, iJarak);
        values.put(ROW_POINT, iPoint);
        try {
            db.insert(NAMA_TABEL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilSemuaBaris() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<>();
        Cursor cur;
        try {
            cur = db.query(NAMA_TABEL, new String[]{ROW_ID, ROW_NAMA, ROW_TELP, ROW_EMAIL, ROW_JARAK, ROW_POINT}, null, null, null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<>();
                    dataList.add(cur.getLong(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getInt(4));
                    dataList.add(cur.getInt(5));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Database ERROR", e.toString());
        }
        return dataArray;
    }
}