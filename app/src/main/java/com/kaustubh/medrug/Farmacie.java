package com.kaustubh.medrug;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Farmacie extends AppCompatActivity {

    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        fillExampleList();
        setUpRecyclerView();
        SearchView searchView = (SearchView) findViewById(R.id.action_search);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void fillExampleList() {
        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.drugs, "Crocin", "Paracetamol"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "Ishit", "chota nunnu"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "kaustubh", "micro nunnu"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "mihir", "gawaar"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "megha", "awesome human"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "Sasta", "Nasha"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "khaana", "peena"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "Rona", "Dhona"));
        exampleList.add(new ExampleItem(R.drawable.drugs, "babu shona", "randi rona"));
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    }


