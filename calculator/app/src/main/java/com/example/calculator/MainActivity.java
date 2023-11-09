package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText num1,num2;
    TextView hasil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num1 = (EditText) findViewById(R.id.editAngka1);
        num2 = (EditText) findViewById(R.id.editAngka2);
        hasil = (TextView) findViewById(R.id.textHasil);
    }
    public void operasi(View v){
        float bil1,bil2, hasil_operasi = 0;
        bil1= Float.parseFloat(num1.getText().toString());
        bil2 = Float.parseFloat(num2.getText().toString());
        int id = v.getId();
        if (id == R.id.butTambah) {
            hasil_operasi = bil1 + bil2;
        } else if (id == R.id.butKurang) {
            hasil_operasi = bil1 - bil2;
        } else if (id == R.id.butKali) {
            hasil_operasi = bil1 * bil2;
        } else if (id == R.id.butBagi) {
            hasil_operasi = bil1 / bil2;
        }
        hasil.setText(String.valueOf(hasil_operasi));
    }
}