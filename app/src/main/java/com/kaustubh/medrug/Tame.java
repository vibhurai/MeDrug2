package com.kaustubh.medrug;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Tame extends AppCompatActivity{
    public ArrayList<String> times=new ArrayList<>();
    TameAdapter adapter;
    int hour;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tame);
        Button b1=findViewById(R.id.Confirm);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Book appointment
                System.out.println(Arrays.toString(Bookapt.details));
                //HANDLE THE DATA HERE
                Intent in = new Intent();
                setResult(4,in);
                finish();

            }
        });
        //System.out.println(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
        times.clear();
        String date=LocalDate.now().toString();
        if (Bookapt.date.equalsIgnoreCase(date)&&LocalTime.now().getHour()>=10) {
            hour= (LocalTime.now().getHour()+1);
            System.out.println("hier");
        }
        else{
            hour=10;
            System.out.println(date);
            System.out.println(Bookapt.date);
        }
        while (hour<18)
        {
            //Addieren Sie Oeffnugszeiten hier.
            /*
            *
            *
            *
            * KAUSTUBH
            * POPULATE THE ARRAY OF AVAILABLE TIME SLOTS HERE
            *
            *
            *
            * */
            times.add(hour+":00");
            hour++;
        }
        RecyclerView time=findViewById(R.id.timer);
        adapter=new TameAdapter(times,Tame.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        time.setLayoutManager(layoutManager);
        time.setAdapter(adapter);
    }


}
