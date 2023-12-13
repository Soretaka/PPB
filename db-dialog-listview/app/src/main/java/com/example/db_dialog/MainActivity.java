package com.example.db_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ListView listview;
    CustomAdapter adapter;
    ArrayList<String> records;
    SQLiteDB dbhelper;
    //Button btntambahdata_Period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview=(ListView)findViewById(R.id.pro_lsit);
        records=new ArrayList<String>();
        adapter=new CustomAdapter(this,R.layout.list_data,R.id.tempatid,records);
        listview.setAdapter(adapter);
        dbhelper=new SQLiteDB(this);

    }

    public void onStart(){
        super.onStart();
        insertSampleData();
        readSampleData();
        countRecords_period();

    }

    public void readSampleData(){
        //MEMBUKA DATABASE
        SQLiteDatabase database=dbhelper.getReadableDatabase();
        String sql="SELECT * FROM "+ SQLiteDB.MYDATABASE_TABLE;

        //MEMBUAT KURSOR UNTUK MEMBUKA DATABASE
        Cursor c=database.rawQuery(sql,null);
        String isicatatan, isinrp, isinama;
        int id;
        if(c.getCount()>0)
            while(c.moveToNext()){
                id=c.getInt(c.getColumnIndex(SQLiteDB.KEY_ID));
                isicatatan=c.getString(c.getColumnIndex(SQLiteDB.KEY_NOTES));
                isinrp=c.getString(c.getColumnIndex(SQLiteDB.KEY_NRP));
                isinama=c.getString(c.getColumnIndex(SQLiteDB.KEY_NAMA));

                String item=id+"__"+isicatatan+"__"+isinrp+"__"+isinama;
                records.add(item);

            }
        //notify listview of dataset changed
        adapter.notifyDataSetChanged();
    }

    //MEMASUKKAN SAMPLE DATA
    public void insertSampleData(){
        //get a writable database
        SQLiteDatabase database = dbhelper.getWritableDatabase();

        database.execSQL("INSERT INTO tabel_mahasiswa(notes,nrp,nama)"
                +" values ('Catatan 1','1','Bambang')");
        database.execSQL("INSERT INTO tabel_mahasiswa(notes,nrp,nama)"
                + " values ('Catatan 2','2','Joko')");
        database.execSQL("INSERT INTO tabel_mahasiswa(notes,nrp,nama)"
                + " values ('Catatan 3','3','New York')");
        database.execSQL("INSERT INTO tabel_mahasiswa(notes,nrp,nama)"
                + " values ('Catatan 4','4','Ice Cube')");
        database.execSQL("INSERT INTO tabel_mahasiswa(notes,nrp,nama)"
                + " values ('Catatan 5','5','Yatestha')");
        database.execSQL("INSERT INTO tabel_mahasiswa(notes,nrp,nama)"
                + " values ('Catatan 6','6','Parwata')");

    }

    // HAPUS DATA DARI TABEL
    public void deleteRow(View view){
        Button bt=(Button)view;
        final String del_id1=bt.getTag().toString();

        //MENAMPILKAN PERINGATAN MENGHAPUS DATA
        AlertDialog.Builder alertDialogHapus = new AlertDialog.Builder(MainActivity.this);
        alertDialogHapus.setTitle("Hapus Data");
        alertDialogHapus.setMessage("Anda yakin ingin menghapus data ini?");
        //KALAU YAKIN HAPUS
        alertDialogHapus.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                SQLiteDatabase database = dbhelper.getWritableDatabase();
                database.delete(SQLiteDB.MYDATABASE_TABLE, SQLiteDB.KEY_ID + "=?", new String[]{del_id1});
                for (int i = 0; i < records.size(); i++) {
                    if (records.get(i).startsWith(del_id1))
                        records.remove(i);
                }

                adapter.notifyDataSetChanged();
                countRecords_period();

                Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });

        //KALAU TIDAK YAKIN JANGAN DIHAPUS
        alertDialogHapus.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogHapus.show();

    }

    // TAMPILKAN JUMLAH DATA YANG ADA DALAM TABEL
    public void countRecords_period() {
        SQLiteDatabase database=dbhelper.getWritableDatabase();

        String sql = "SELECT * FROM tabel_mahasiswa";
        int recordCount = database.rawQuery(sql, null).getCount();
        database.close();

        TextView textViewRecordCount = (TextView) findViewById(R.id.txt_data_mahasiswa);
        textViewRecordCount.setText("DATA MAHASISWA (" + recordCount + " data)");
    }

    // EDIT DATA DARI TABEL
    public void editRow(View view){
        Button bt_edit = (Button)view;
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        final String del_id = bt_edit.getTag().toString();
        String sql_ambil_1_data = "SELECT * FROM tabel_mahasiswa WHERE _id = " + del_id;
        final Cursor cursor = database.rawQuery(sql_ambil_1_data, null);
        final Context context = view.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.edit_data, null, false);

        final EditText enterid1 = (EditText) formElementsView.findViewById(R.id.masukkanid);
        final EditText enternotes1 = (EditText) formElementsView.findViewById(R.id.masukkannotes);
        final EditText enternrp1 = (EditText) formElementsView.findViewById(R.id.masukkannrp);
        final EditText enternama1 = (EditText) formElementsView.findViewById(R.id.masukkannama);

        if (cursor.moveToFirst()) {

            int id_per = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLiteDB.KEY_ID)));
            String notes_per = cursor.getString(cursor.getColumnIndex(SQLiteDB.KEY_NOTES));
            String nrp_per = cursor.getString(cursor.getColumnIndex(SQLiteDB.KEY_NRP));
            String mood_per = cursor.getString(cursor.getColumnIndex(SQLiteDB.KEY_NAMA));

        }

        enterid1.setText(del_id);
        enternotes1.setText(cursor.getString(cursor.getColumnIndex(SQLiteDB.KEY_NOTES)));
        enternrp1.setText(cursor.getString(cursor.getColumnIndex(SQLiteDB.KEY_NRP)));
        enternama1.setText(cursor.getString(cursor.getColumnIndex(SQLiteDB.KEY_NAMA)));

        cursor.close();
        database.close();

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String notesperiod2 = enternotes1.getText().toString();
                                String nrpperiod2 = enternrp1.getText().toString();
                                String namaperiod2 = enternama1.getText().toString();
                                String idperiod2 = enterid1.getText().toString();

                                ContentValues values = new ContentValues();

                                values.put("notes", notesperiod2);
                                values.put("nrp", nrpperiod2);
                                values.put("nama", namaperiod2);

                                String where = "_id = ?";

                                String[] whereArgs = { idperiod2 };

                                SQLiteDatabase database_period = dbhelper.getWritableDatabase();

                                boolean updateSuccessful = database_period.update("tabel_mahasiswa", values, where, whereArgs) > 0;

                                dialog.cancel();
                                adapter.clear();
                                readSampleData();
                            }

                        }).show();


        adapter.notifyDataSetChanged();
        countRecords_period();

    }

    // TAMBAH DATA KE TABEL
    public void tambahRow(View view){

        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.add_data, null, false);

        final EditText enternotes = (EditText) formElementsView.findViewById(R.id.masukkannotes);
        final EditText enternrp = (EditText) formElementsView.findViewById(R.id.masukkannrp);
        final EditText enternama = (EditText) formElementsView.findViewById(R.id.masukkannama);

        Button bt_tambah = (Button)view;
        SQLiteDatabase database = dbhelper.getWritableDatabase();

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Tambah data period")
                .setPositiveButton("Simpan",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String notesperiod = enternotes.getText().toString();
                                String nrpperiod = enternrp.getText().toString();
                                String namaperiod = enternama.getText().toString();


                                ContentValues values = new ContentValues();

                                values.put("notes", notesperiod);
                                values.put("nrp", nrpperiod);
                                values.put("nama", namaperiod);


                                SQLiteDatabase database_period = dbhelper.getWritableDatabase();

                                boolean createSuccessful = database_period.insert("tabel_mahasiswa", null, values) > 0;
                                database_period.close();


                                if (createSuccessful) {
                                    Toast.makeText(context, "Data period baru BERHASIH DISIMPAN", Toast.LENGTH_SHORT).show();
                                    // listview.setSelection(adapter.getCount()-1);
                                } else {
                                    Toast.makeText(context, "Data period baru GAGAL BERHASIH DISIMPAN", Toast.LENGTH_SHORT).show();
                                }


                                dialog.cancel();
                                adapter.clear();
                                readSampleData();
                                countRecords_period();
                                //ATUR LISTVIEW KE AKHIR
                                listview.setSelection(adapter.getCount()-1);

                            }

                        }).show();

        adapter.notifyDataSetChanged();
        countRecords_period();
    }
}