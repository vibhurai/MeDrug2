package com.kaustubh.medrug;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.content.Intent;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class date extends AppCompatActivity implements click{
    ArrayList<String> dates=new ArrayList<>();
    MyAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        LocalDate lt = LocalDate.now();
        for (int i=0;i<7;i++) {
            String s1 = String.valueOf(lt.plusDays(i));
            dates.add(s1);
        }
        RecyclerView date=findViewById(R.id.recview);
        adapter=new MyAdapter(dates,date.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        date.setLayoutManager(layoutManager);
        date.setAdapter(adapter);

    }

    @Override
    public void act(int pos) {
        Intent in = new Intent();
        in.putExtra("Name",dates.get(pos));
        setResult(2,in);
        finish();

    }
}

