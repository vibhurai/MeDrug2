package com.kaustubh.medrug;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.provider.Telephony.Mms.Part.TEXT;

public class Tame extends AppCompatActivity {
    public static ArrayList<String> times = new ArrayList<>();
    TameAdapter adapter;
    interface_proc Interface_proc;
    long mla = 0;
    ContentResolver cr;
    ContentValues values = new ContentValues();
    ProgressDialog dialog;
    static long eventID;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tame);
        System.out.println(times);

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

        Button b1 = findViewById(R.id.Confirm);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //Book appointment
//
                if (SystemClock.elapsedRealtime() - mla < 3000) {
                    return;
                }
                mla = SystemClock.elapsedRealtime();
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                assert vibe != null;
                vibe.vibrate(100);
                dialog = new ProgressDialog(Tame.this);
                dialog.setTitle("Loading");
                dialog.setMessage("Please wait");
                dialog.show();
//                Toast.makeText(Tame.this, "Booked successfully", Toast.LENGTH_SHORT).show();
                getdoc_id();
            }
        });
        //System.out.println(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

        RecyclerView time = findViewById(R.id.timer);
        adapter = new TameAdapter(times, Tame.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        time.setLayoutManager(layoutManager);
        time.setAdapter(adapter);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    cr = getContentResolver();
                    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                    long eventID = Long.parseLong(uri.getLastPathSegment());
                    System.out.println(eventID);

                }
            }
        }
    }

    //===============ALL THE API CALLS============================================
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void check(int s_id) {
        CharSequence dt = Bookapt.details[1];
        LocalDate lt = LocalDate.parse(dt);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        int x = sharedPreferences.getInt(TEXT, -1);
        appointment app = new appointment(x, s_id, "nothing", String.valueOf(lt));
        Call<appointment> call = Interface_proc.seeres(app);
        call.enqueue(new Callback<appointment>() {
            @Override
            public void onResponse(Call<appointment> call, Response<appointment> response) {
                dialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(Tame.this, "The slot has been booked by someone else!", Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(Tame.this, "Slot booked successfully!", Toast.LENGTH_SHORT).show();
                    addevent();


                    Intent in = new Intent();
                    setResult(4,in);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<appointment> call, Throwable t) {
                Toast.makeText(Tame.this, "Whoops! Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addevent() {
        long calID;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            calID=3;
        }else
        {
            calID=1;
        }
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        String[] time=Bookapt.details[1].split("-");
        int year=Integer.parseInt(time[0]);
        int month=Integer.parseInt(time[1]);
        int date=Integer.parseInt(time[2]);
        int hor=Integer.parseInt(Bookapt.details[2].substring(0,2));
        beginTime.set(year, (month-1), date, hor, 0);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, (month-1), date, (hor+1), 0);
        endMillis = endTime.getTimeInMillis();




        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Appointment with Blue Circle");
        values.put(CalendarContract.Events.DESCRIPTION, "Your appointment with "+ Bookapt.details[0]);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Kolkata");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_CALENDAR},333);
        }
        else {
            cr = getContentResolver();
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            eventID = Long.parseLong(uri.getLastPathSegment());
            System.out.println(eventID);
        }

// get the event ID that is the last element in the Uri

    }

    private int getschedule_id(int a , String b, String c) {
        CharSequence ti = Bookapt.details[2];

        Call<List<schedule>> call = Interface_proc.get_schedule(a,b,c);
        call.enqueue(new Callback<List<schedule>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<schedule>> call, Response<List<schedule>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Tame.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;}
                List<schedule> res_body = response.body();
                for(schedule med : res_body)
                {
                    if(med.getTime().contains(String.valueOf(ti)))
                        check(med.getId());
                }


            }

            @Override
            public void onFailure(@NotNull Call<List<schedule>> call, @NotNull Throwable t) {
                Toast.makeText(Tame.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void get_day(int x) {
        CharSequence dt =Bookapt.details[1];
        LocalDate lt = LocalDate.parse(dt);
        DayOfWeek d = DayOfWeek.from(lt);
        getschedule_id(x, String.valueOf(d), String.valueOf(dt));
    }

    private void getdoc_id() {
        Call<List<doctors>> call = Interface_proc.getdocs();
        int flag =0;
        call.enqueue(new Callback<List<doctors>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<doctors>> call, Response<List<doctors>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Tame.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;}
                CharSequence x = Bookapt.details[0];
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
