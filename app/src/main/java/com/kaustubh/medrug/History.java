package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    private ArrayList<historyItem> HistList = new ArrayList<>();
    private HistoryAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);
        HistList.add(new historyItem("D1", "2020-15-05", "11:00", "Confirmed"));
        HistList.add(new historyItem("D2", "2020-16-05", "12:00", "Confirmed"));
        HistList.add(new historyItem("D3", "2020-17-05", "13:00", "Confirmed"));
        HistList.add(new historyItem("D4", "2020-18-05", "14:00", "Confirmed"));
        HistList.add(new historyItem("D5", "2020-19-05", "15:00", "Confirmed"));
        HistList.add(new historyItem("D6", "2020-20-05", "16:00", "Confirmed"));
        adapter = new HistoryAdapter(HistList);
        RecyclerView recyclerView2 = findViewById(R.id.ap_rec_vw);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(layoutManager);



    }
}