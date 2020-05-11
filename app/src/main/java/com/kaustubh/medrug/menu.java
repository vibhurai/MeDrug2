package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class menu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button b1 = findViewById(R.id.emergency_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:9428394228"));
                startActivity(i);
            }
        });

        Button b2 = findViewById(R.id.book_appointments_button);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Bookapt.class);
                startActivity(i);
            }
        });

        Button b3 = findViewById(R.id.pharmacy_button);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Farmacie.class);
                startActivity(i);
            }
        });

        Button b4 = findViewById(R.id.contact_button);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), contact.class);
                startActivity(i);
            }
        });

        Button b5 = findViewById(R.id.hist);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), History.class);
                startActivity(i);
            }
        });

        Button b6 = findViewById(R.id.alerts);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), alerts.class);
                startActivity(i);
            }
        });


    }
}
