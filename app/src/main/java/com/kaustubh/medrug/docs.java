package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;


public class docs extends AppCompatActivity {

    public void end(View view){
        Button b1 = (Button)view;
        Intent in = new Intent();
        String[] text = ((String) b1.getText()).split("\n");


        in.putExtra("Name",text[0]);
        setResult(1,in);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);

//
//        ArrayList<String> docs= new ArrayList<String>();
//        docs.add("LOL");
//        docs.add("Kaustubh");
//        docs.add("kachhota");
//        docs.add("Nunnu");
//
//
//        ListView listv=findViewById(R.id.lv);
//        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,docs);


//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       //listv.setAdapter(adapter);
//        sp.setOnItemClickListener((AdapterView.OnItemClickListener) this);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//       });
    }
}
