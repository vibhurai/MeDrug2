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
    String strEditText1,strEditText2,strEditText3;
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
                strEditText1 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Name);
                b2.setText(strEditText1);
            }
            if (resultCode==2){
                strEditText2 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Date);
                b2.setText(strEditText2);
                date=strEditText2;

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

        b3= findViewById(R.id.time);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Tame.class);
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



        Button b4= findViewById(R.id.Book);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mla < 3000) {
                    return;
                }
                mla = SystemClock.elapsedRealtime();
                Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                assert vibe != null;
                vibe.vibrate(100);
                dialog=new ProgressDialog(Bookapt.this);
                dialog.setTitle("Loading");
                dialog.setMessage("Please wait");
                dialog.show();
                //Toast.makeText(Bookapt.this, "Booked successfully", Toast.LENGTH_SHORT).show();
                getdoc_id();

                //finish();
            }
        });




    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    private void check(int s_id) {
        CharSequence dt =b2.getText();
        LocalDate lt = LocalDate.parse(dt);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        int x = sharedPreferences.getInt(TEXT, -1);
        appointment app = new appointment(x,s_id,"nothing",String.valueOf(lt));
        Call<appointment> call= Interface_proc.seeres(app);
        call.enqueue(new Callback<appointment>() {
            @Override
            public void onResponse(Call<appointment> call, Response<appointment> response) {
                dialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(Bookapt.this, "The slot has been booked by someone else!", Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(Bookapt.this, "Slot booked successfully!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent();
                    setResult(3,in);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<appointment> call, Throwable t) {
                Toast.makeText(Bookapt.this, "Whoops! Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private int getschedule_id(int a , String b) {
        CharSequence ti = b3.getText();

        Call<List<schedule>> call = Interface_proc.get_schedule(a,b);
        call.enqueue(new Callback<List<schedule>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<schedule>> call, Response<List<schedule>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Bookapt.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;}
                List<schedule> res_body = response.body();
                for(schedule med : res_body)
                {
                    if(med.getTime().contains(String.valueOf(ti)))
                        check(med.getId());
                }


            }

            @Override
            public void onFailure(Call<List<schedule>> call, Throwable t) {
                Toast.makeText(Bookapt.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void get_day(int x) {
        CharSequence dt =b2.getText();
        LocalDate lt = LocalDate.parse(dt);
        DayOfWeek d = DayOfWeek.from(lt);
        getschedule_id(x, String.valueOf(d));
    }

    private void getdoc_id() {
        Call<List<doctors>> call = Interface_proc.getdocs();
        int flag =0;
        call.enqueue(new Callback<List<doctors>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<doctors>> call, Response<List<doctors>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Bookapt.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;}
                CharSequence x = b1.getText();
                String a = x.toString();
                List<doctors> res_body = response.body();
                for(doctors med : res_body){
                    if( med.getName().equals(a))
                        get_day (med.getId());


                }}

            @Override
            public void onFailure(Call<List<doctors>> call, Throwable t) {

            }
        });
    }
}
