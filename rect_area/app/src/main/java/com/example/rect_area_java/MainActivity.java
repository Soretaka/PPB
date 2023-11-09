package com.example.rect_area_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.String;
public class MainActivity extends AppCompatActivity {
    EditText mEditWidth;
    EditText mEditHeight;
    TextView mTextResult;
    Button mButtonCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditHeight = (EditText) findViewById(R.id.editLebar);
        mEditWidth = (EditText) findViewById(R.id.editPanjang);
        mTextResult = (TextView) findViewById(R.id.hasilText);
        mButtonCount = (Button) findViewById(R.id.hitungButton);

        mButtonCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });
    }
    public void calculate(){
        Double value1 = Double.parseDouble(mEditWidth.getText().toString());
        Double value2= Double.parseDouble(mEditHeight.getText().toString());
        Double calV = value1*value2;
        mTextResult.setText(calV.toString());
    }
}