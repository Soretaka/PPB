package com.example.form_kelas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView txtnama,txtaverage,txtgrade;
    EditText nama,nilai1,nilai2,nilai3,nilai4;
    private Button simpan,ambildata;
    private SQLiteOpenHelper Opendb;
    private SQLiteDatabase dbku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nama = (EditText) findViewById(R.id.editNama);
        nilai1 = (EditText) findViewById(R.id.editNilai1);
        nilai2 = (EditText) findViewById(R.id.editNilai2);
        nilai3 = (EditText) findViewById(R.id.editNilai3);
        nilai4 = (EditText) findViewById(R.id.editNilai4);
        txtnama = (TextView) findViewById(R.id.textNama);
        txtaverage = (TextView) findViewById(R.id.textAverage);
        txtgrade = (TextView) findViewById(R.id.textGrade);
        simpan = (Button) findViewById(R.id.simpanButton);
        ambildata = (Button) findViewById(R.id.ambilButton);
        simpan.setOnClickListener(operasi);
        ambildata.setOnClickListener(operasi);

        Opendb = new SQLiteOpenHelper(this,"db.sql",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {}

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        };
        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists mhs(nama TEXT, avgnilai TEXT, grade TEXT);");
        dbku.execSQL("DELETE FROM mhs");
    }
    protected void onStop(){
        dbku.close();
        Opendb.close();
        super.onStop();
    }View.OnClickListener operasi = new View.OnClickListener(){
        public void onClick(View v){
            int id = v.getId();
            if(id == R.id.ambilButton){
                ambil();
            }else if(id == R.id.simpanButton){
                simpan();
            }
        }
    };
    public void simpan(){
      float nilai_1,nilai_2,nilai_3,nilai_4,average;
      nilai_1 = Float.parseFloat(nilai1.getText().toString());
      nilai_2 = Float.parseFloat(nilai2.getText().toString());
      nilai_3 = Float.parseFloat(nilai3.getText().toString());
      nilai_4 = Float.parseFloat(nilai4.getText().toString());
      average = (nilai_1+nilai_2+nilai_3+nilai_4)/4;
      String last_grade;
      if(average > 85){
          last_grade = "A";
      }else if(average > 75){
          last_grade = "AB";
      }else if(average > 65){
          last_grade = "B";
      }else if(average > 60){
          last_grade = "BC";
      }else if(average > 55){
          last_grade = "C";
      }else if(average > 40){
          last_grade = "D";
      }else{
          last_grade = "E";
      }
        ContentValues dataku = new ContentValues();
      dataku.put("nama",nama.getText().toString());
      dataku.put("avgnilai",String.valueOf(average));
      dataku.put("grade",last_grade);
      dbku.insert("mhs",null,dataku);
      txtnama.setText(nama.getText());
      txtaverage.setText(String.valueOf(average));
      txtgrade.setText(last_grade);
        Toast.makeText(this,"data tersimpan",Toast.LENGTH_LONG).show();
    }
    private void ambil(){
        Cursor cur = dbku.rawQuery("select * from mhs",null);
        if(cur.getCount() > 0){
            Toast.makeText(this,"Data ditemukan sejumlah "+cur.getCount(),Toast.LENGTH_LONG).show();
            // Move to the first row
            cur.moveToFirst();

            // Clear any previous text in your display (assuming 'nama' is a TextView)
            txtnama.setText("");
            txtaverage.setText("");
            txtgrade.setText("");
            // Iterate through all rows
            do {
                int namaColumnIndex = cur.getColumnIndex("nama");
                int averageColumnIndex = cur.getColumnIndex("avgnilai");
                int gradeColumnIndex = cur.getColumnIndex("grade");
                if (namaColumnIndex != -1) {
                    // Check if the "nama" column exists
                    String namaValue = cur.getString(namaColumnIndex);
                    String averageValue = cur.getString(averageColumnIndex);
                    String gradeValue = cur.getString(gradeColumnIndex);
                    // Append the name to your display (assuming 'nama' is a TextView)
                    txtnama.append(namaValue + "|");
                    txtaverage.append(averageValue+"|");
                    txtgrade.append(gradeColumnIndex+"|");
                } else {
                    // Handle the case where the "nama" column does not exist
                    Toast.makeText(this, "Column 'nama' not found in the cursor", Toast.LENGTH_LONG).show();
                }
            } while (cur.moveToNext());

        }else{
            Toast.makeText(this,"Data tidak ditemukan",Toast.LENGTH_LONG).show();
        }
    }
}