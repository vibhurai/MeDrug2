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
import java.util.Date;

public class Tame extends AppCompatActivity implements click{
    public ArrayList<String> times=new ArrayList<>();
    MyAdapter adapter;
    int hour;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tame);
        //System.out.println(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
        times.clear();
        String date=LocalDate.now().toString();
        if (Bookapt.date.equalsIgnoreCase(date)) {
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
            times.add(hour+":00");
            hour++;
        }
        RecyclerView time=findViewById(R.id.timer);
        adapter=new MyAdapter(times,Tame.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        time.setLayoutManager(layoutManager);
        time.setAdapter(adapter);
    }

    @Override
    public void act(int pos) {
        Intent in = new Intent();
        in.putExtra("Name",times.get(pos));
        setResult(3,in);
        finish();


    }
}
