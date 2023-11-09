package com.example.form_kelas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView txtnama,txtaverage,txtgrade;
    EditText nama,nilai1,nilai2,nilai3,nilai4;

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
    }
    public void simpan(View v){
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
      txtnama.setText(nama.getText());
      txtaverage.setText(String.valueOf(average));
      txtgrade.setText(last_grade);
    }
}