package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Bookapt extends AppCompatActivity {
    String strEditText1,strEditText2,strEditText3;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                strEditText1 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Name);
                b2.setText(strEditText1);
            }
            if (resultCode==2){
                strEditText2 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Date);
                b2.setText(strEditText2);

            }
            if (resultCode==3){
                strEditText3 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.time);
                b2.setText(strEditText3);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookapt);

        Button b1 = findViewById(R.id.Name);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), docs.class);
                startActivityForResult(i,1);


            }


        });
        Button b2= findViewById(R.id.Date);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), date.class);
                startActivityForResult(i,1);


            }


        });

        Button b3= findViewById(R.id.time);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Tame.class);
                startActivityForResult(i,1);
            }
        });

        Button b4= findViewById(R.id.Book);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Bookapt.this, "Booked successfully", Toast.LENGTH_SHORT).show();

                finish();
            }
        });


    }
}
