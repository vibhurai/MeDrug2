package com.kaustubh.medrug;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLOutput;
import java.util.Arrays;

public class loc extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    double latitude, longitude; int flag2;
    String[] loca=new String[2];
    String[] phoneNumber = {"7984745534","9911290551","9871792791","8510820248"};
    String message;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, locationListener);
                }
            }
            else
            {
                Toast.makeText(loc.this,"Location Permission required for using this feature",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (requestCode==2)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+phoneNumber[0]+";"+phoneNumber[1]+";"+phoneNumber[2]));
                    smsIntent.putExtra("sms_body", message);
                    startActivity(smsIntent);
                }
                }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        Button btn = findViewById(R.id.pick);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag2 == 1) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:9865327451"));
                    startActivity(i);
                }
                if (flag2 == 0) {
                    if (latitude == 0.0) {
                        Toast.makeText(loc.this, "Location not found, try restarting", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loca[0] = String.valueOf(latitude);
                    loca[1] = String.valueOf(longitude);
                    System.out.println(Arrays.toString(loca));

                    SmsManager smsManager = SmsManager.getDefault();
                    message = "Click on the URL to get the location of the patient " + "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
                    sendmessage();


                    Toast.makeText(loc.this, "HELP IS ON THE WAY!", Toast.LENGTH_SHORT).show();
                    btn.setText("Call front desk instead");
                    flag2 = 1;
                }
            }





        });

    }

    @SuppressLint("IntentReset")
    private void sendmessage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+phoneNumber[0]+";"+phoneNumber[1]+";"+phoneNumber[2]));
            smsIntent.putExtra("sms_body", message);
            startActivity(smsIntent);
//            SmsManager.getDefault().sendTextMessage(phoneNumber[0],null,message,null,null);

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                longitude = location.getLongitude();
                latitude = location.getLatitude();
                LatLng current_location = new LatLng(latitude, longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(current_location).title("Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((current_location),15));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, locationListener);

        }

    }
}
