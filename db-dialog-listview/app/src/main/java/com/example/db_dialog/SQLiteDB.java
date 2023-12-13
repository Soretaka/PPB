package com.example.db_dialog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper{


    public static final int MYDATABASE_VERSION = 1;
    public static final String MYDATABASE_NAME = "data_mahasiswa";
    public static final String MYDATABASE_TABLE = "tabel_mahasiswa";
    public static final String KEY_ID = "_id";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_NRP = "nrp";
    public static final String KEY_NAMA = "nama";

    //-------------DEKLARASI UNTUK MEMBUAT TABEL-------------//
    private static final String SCRIPT_CREATE_TABLE =
            "create table " + MYDATABASE_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NOTES + " text not null, "
                    + KEY_NRP + " text not null, "
                    + KEY_NAMA + " text not null);";

    //-------------DEKLARASI UNTUK MENGHAPUS TABEL-------------//
    private static final String SCRIPT_DELETE_TABLE="DROP TABLE IF EXISTS " + MYDATABASE_TABLE;


    public SQLiteDB(Context context){
        //BUAT DATABASE JIKA TIDAK ADA
        super(context,MYDATABASE_NAME,null,MYDATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        //BUAT TABEL
        db.execSQL(SCRIPT_CREATE_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db,int olv,int newv){
        db.execSQL(SCRIPT_DELETE_TABLE);
        onCreate(db);
    }
}