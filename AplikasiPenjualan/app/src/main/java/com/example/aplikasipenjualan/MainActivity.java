package com.example.aplikasipenjualan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editNama,editBarang,editJumlah,editHargaBarang,editBayar;
    TextView NamaPembeliTxt,NamaBarangTxt,JumlahBarangTxt,HargaBarangTxt,UangBayarTxt,TotalBelanjaTxt,UangKembalianTxt,BonusTxt,KeteranganTxt;
    Button ProsesBtn,HapusDataBtn,KeluarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editNama = (EditText) findViewById(R.id.editNamaPembeli);
        editBarang = (EditText) findViewById(R.id.editNamaBarang);
        editJumlah = (EditText) findViewById(R.id.editJumlahBarang);
        editHargaBarang = (EditText) findViewById(R.id.editHarga);
        editBayar = (EditText) findViewById(R.id.editUangBayar);
        NamaBarangTxt = (TextView) findViewById(R.id.NamaBarangText);
        NamaPembeliTxt = (TextView) findViewById(R.id.NamaPembeliText);
        JumlahBarangTxt = (TextView) findViewById(R.id.JumlahBarangText);
        HargaBarangTxt = (TextView) findViewById(R.id.HargaBarangText);
        UangBayarTxt = (TextView) findViewById(R.id.UangBayarText);
        TotalBelanjaTxt = (TextView) findViewById(R.id.TotalBelanjaText);
        UangKembalianTxt = (TextView) findViewById(R.id.UangKembalianText);
        BonusTxt = (TextView) findViewById(R.id.BonusText);
        KeteranganTxt = (TextView) findViewById(R.id.KeteranganText);
        ProsesBtn = (Button)findViewById(R.id.ProsesButton);
        HapusDataBtn = (Button)findViewById(R.id.HapusDataButton);
        KeluarBtn = (Button)findViewById(R.id.KeluarButton);
        setText("","","","","","","","","");

        ProsesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process();
            }
        });
        HapusDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText("","","","","","","","","");
            }
        });
        KeluarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void setText(String namaBarang,String namaPembeli,String jumlahBarang, String hargaBarang,String uangBayar,String totalBelanja,String uangKembalian,String bonus,String keterangan){
        NamaBarangTxt.setText("Nama Barang : " + namaBarang);
        NamaPembeliTxt.setText("Nama Pembeli : " + namaPembeli);
        JumlahBarangTxt.setText("Jumlah Barang : "+ jumlahBarang);
        HargaBarangTxt.setText("Harga Barang : " + hargaBarang);
        UangBayarTxt.setText("Uang Bayar : " + uangBayar);
        TotalBelanjaTxt.setText("Total Belanja : " + totalBelanja);
        UangKembalianTxt.setText("Uang Kembalian : " + uangKembalian);
        BonusTxt.setText("Bonus : " + bonus);
        KeteranganTxt.setText("Keterangan : " + keterangan);
    }
    public void process(){
        String namaBarang = editBarang.getText().toString();
        String namaPembeli = editNama.getText().toString();
        Double jumlahBarang = Double.parseDouble(editJumlah.getText().toString());
        Double hargaBarang = Double.parseDouble(editHargaBarang.getText().toString());
        Double uangBayar = Double.parseDouble(editBayar.getText().toString());
        Double totalBelanja = jumlahBarang*hargaBarang;
        Double uangKembalian = uangBayar-totalBelanja;
        String bonus = "";
        String keterangan = "";
        if(uangKembalian < 0){
            bonus = "Bayar lunas terlebih dahulu!";
        }
        else if(totalBelanja < 1000000){
            bonus = "USB 64GB";
        }else if(totalBelanja < 2000000){
            bonus = "HardDisk 500GB";
        }else{
            bonus = "HardDisk 1TB";
        }
        if(uangKembalian < 0){
            keterangan = "Uang kurang";
        }else if (uangKembalian == 0){
            keterangan = "Uang pas";
        }else{
            keterangan = "Tunggu kembalian";
        }
        setText(namaBarang,namaPembeli,jumlahBarang.toString(),hargaBarang.toString(),uangBayar.toString(),totalBelanja.toString(),uangKembalian.toString(),bonus,keterangan);
    }
}