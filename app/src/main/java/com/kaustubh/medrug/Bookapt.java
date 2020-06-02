package com.kaustubh.medrug;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static android.provider.Telephony.Mms.Part.TEXT;

public class Bookapt extends AppCompatActivity {
    public static String date;
    String strEditText1,strEditText2;
    static String[] details=new String[3];
    interface_proc Interface_proc;
    Button b1 ;
    Button b2;
    Button b3;
    long mla=0;
    ProgressDialog dialog;
//    private Object interface_proc;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
               details[0]= strEditText1 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Name);
                b2.setText(strEditText1);
            }
            if (resultCode==2){
                details[1]=strEditText2 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Date);

                b2.setText(strEditText2);
                date=strEditText2;

            }
            if (resultCode==4) {
                Intent i = new Intent();
                setResult(3,i);
                finish();
            }

            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookapt);

        b1= findViewById(R.id.Name);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), docs.class);
                startActivityForResult(i,1);
            }
        });
        b2= findViewById(R.id.Date);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), date.class);
                startActivityForResult(i,1);


            }


        });


        Gson gson = new GsonBuilder()
                .setLenient().serializeNulls()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://devilish.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Interface_proc = retrofit.create(interface_proc.class);



//        Button b4= findViewById(R.id.Book);
        b3= findViewById(R.id.Book);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Tame.class);
                startActivityForResult(i,1);
            }
        });
//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (SystemClock.elapsedRealtime() - mla < 3000) {
//                    return;
//                }
//                mla = SystemClock.elapsedRealtime();
//                Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//                assert vibe != null;
//                vibe.vibrate(100);
//                dialog=new ProgressDialog(Bookapt.this);
//                dialog.setTitle("Loading");
//                dialog.setMessage("Please wait");
//                dialog.show();
//                //Toast.makeText(Bookapt.this, "Booked successfully", Toast.LENGTH_SHORT).show();
//                getdoc_id();
//
//                //finish();
//            }
//        });




    }


}
