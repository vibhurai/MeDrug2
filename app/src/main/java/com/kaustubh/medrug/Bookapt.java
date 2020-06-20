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
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.provider.Telephony.Mms.Part.TEXT;

public class Bookapt extends AppCompatActivity {
    public static String date;
    String strEditText1,strEditText2;
    public static final String[] TEXT_HIST = {"text","wer","wer","wer","wer","wer","wer","wer","wer","wer","wer","wer"};
    static String[] details=new String[3];
    interface_proc Interface_proc;
    Button b1 ;
    Button b2;
    Button b3;
    int hour;
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
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://devilish.pythonanywhere.com/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        Interface_proc = retrofit.create(interface_proc.class);



//        Button b4= findViewById(R.id.Book);
        b3= findViewById(R.id.Book);
        b3.setOnClickListener(new View.OnClickListener() {
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
                getdoc_id();
            }
        });
//




    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void gettimeslots(int a , String b, String c)
    {
        Call<List<schedule>> call = Interface_proc.get_schedule(a,b,c);
        call.enqueue(new Callback<List<schedule>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<schedule>> call, Response<List<schedule>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Bookapt.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;}
                List<schedule> res_body = response.body();
                res_body.size();
                Tame.times.clear();
                for(int i=0;i<res_body.size();i++)
                {
                    schedule med = res_body.get(i);


                    if(!med.isSlot_booked())
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(TEXT_HIST), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String t= med.getTime();
                        editor.putString(TEXT_HIST[i], t);
                        editor.apply();
                        String date=LocalDate.now().toString();
                        if (details[1].equalsIgnoreCase(date)) {
                            if(Integer.parseInt(t.substring(0,1))<=LocalTime.now().getHour())
                            {
                                continue;
                            }
                        }

                        Tame.times.add(t.substring(0,5));
                    }


                }
                System.out.println(Tame.times);
                Intent i=new Intent(getApplicationContext(),Tame.class);
                dialog.dismiss();
                startActivityForResult(i,1);


            }

            @Override
            public void onFailure(Call<List<schedule>> call, Throwable t) {
                Toast.makeText(Bookapt.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
                CharSequence x = Bookapt.details[0];
                String a = x.toString();
                List<doctors> res_body = response.body();
                for(doctors med : res_body){
                    if( med.getName().equals(a))
                        get_day (med.getId());


                }


            }

            @Override
            public void onFailure(Call<List<doctors>> call, Throwable t) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void get_day(int x) {
        CharSequence dt =Bookapt.details[1];
        LocalDate lt = LocalDate.parse(dt);
        DayOfWeek d = DayOfWeek.from(lt);
        gettimeslots(x, String.valueOf(d), String.valueOf(dt));
    }


}
