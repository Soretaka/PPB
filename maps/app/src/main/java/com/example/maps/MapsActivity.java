package com.example.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    FrameLayout map;
    Button go,cari;
    EditText edtLat,edtLon,edtZoom,edtLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        map = findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        go = findViewById(R.id.goBtn);
        cari = findViewById(R.id.cariBtn);
        edtLoc = findViewById(R.id.gotoloc);
        edtLat = findViewById(R.id.editLat);
        edtLon = findViewById(R.id.editLon);
        edtZoom = findViewById(R.id.editZoom);
        mapFragment.getMapAsync(this);
        go.setOnClickListener(op);
        cari.setOnClickListener(op);
    }
    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.goBtn) {
                gotoLokasi();
            } else if (id == R.id.cariBtn) {
                goCari();
            }
        }
    };
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        this.gMap= googleMap;
        LatLng mapITS = new LatLng (-7.28,112.79);
        this.gMap.addMarker(new MarkerOptions().position(mapITS).title("ITS"));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapITS));
    }

    private void gotoLokasi(){
        Double dbllat = Double.parseDouble(edtLat.getText().toString());
        Double dbllng = Double.parseDouble(edtLon.getText().toString());
        Float dblzoom = Float.parseFloat(edtZoom.getText().toString());
        Toast.makeText(this,"Move to Lat:" +dbllat + " Long:" +dbllng, Toast.LENGTH_LONG).show();
        gotoPeta(dbllat,dbllng,dblzoom);
    }
    private void gotoPeta(Double lat, Double lng, float z){
        LatLng Lokasibaru = new LatLng(lat,lng);
        gMap.addMarker(new MarkerOptions().position(Lokasibaru).title("Marker in  " +lat +":" +lng));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lokasibaru,z));
    }

    private void goCari(){
        Geocoder g = new Geocoder(getBaseContext());
        try{
            List<Address> daftar = g.getFromLocationName(edtLoc.getText().toString(),1);
            Address alamat = daftar.get(0);

            String nemuAlamat = alamat.getAddressLine(0);
            Double lintang = alamat.getLatitude();
            Double bujur = alamat.getLongitude();
            Toast.makeText(getBaseContext(), "ketemu " + nemuAlamat, Toast.LENGTH_LONG).show();
            Float dblzoom = Float.parseFloat(edtZoom.getText().toString());
            Toast.makeText(this,"Move to "+ nemuAlamat +" Lat:" +
                    lintang + " Long:" +bujur,Toast.LENGTH_LONG).show();
            gotoPeta(lintang,bujur,dblzoom);
            Double dbllat = Double.parseDouble(edtLat.getText().toString());
            Double dbllon = Double.parseDouble(edtLon.getText().toString());
            hitungJarak(dbllat,dbllon,lintang,bujur);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void hitungJarak(double latAsal, double lngAsal, double latTujuan, double lngTujuan){
        Location asal = new Location("asal");
        Location tujuan = new Location("tujuan");
        tujuan.setLatitude(latTujuan);
        tujuan.setLongitude(lngTujuan);
        asal.setLatitude(latAsal);
        asal.setLongitude(lngAsal);
        float jarak = (float) asal.distanceTo(tujuan)/1000;
        String jaraknya = String.valueOf(jarak);
        Toast.makeText(getBaseContext(),"jarak : " + jaraknya + " km ",Toast.LENGTH_LONG).show();
    }
}