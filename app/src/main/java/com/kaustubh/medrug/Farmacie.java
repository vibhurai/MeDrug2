package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class Farmacie extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        SearchView searchView;
        ListView listView;
        final ArrayList<String> list;
        final ArrayAdapter<String> adapter;



            searchView = (SearchView) findViewById(R.id.searchView);
            listView = (ListView) findViewById(R.id.lv1);

            list = new ArrayList<>();
            list.add("Crocin");
            list.add("Dolo");
            list.add("Zolpidem");
            list.add("Vizylac");
            list.add("Pan-40");
            list.add("Zifi");
            list.add("Allegra");
            list.add("Ondem");
            list.add("Metrogyl");
            list.add("Ambien");

        list.add("Crocin2");
        list.add("Dolo2");
        list.add("Zolpidem2");
        list.add("Vizylac2");
        list.add("Pan-402");
        list.add("Zifi2");
        list.add("Allegra2");
        list.add("Ondem2");
        list.add("Metrogyl2");
        list.add("Ambien2");

            adapter = new ArrayAdapter<String>(this, R.layout.list_fancy, list);
            listView.setAdapter(adapter);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    if (list.contains(query)) {
                        adapter.getFilter().filter(query);
                    } else {
                        Toast.makeText(Farmacie.this, "No Match found", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                       adapter.getFilter().filter(newText);
                    return false;
                }
            });


        }
    }


