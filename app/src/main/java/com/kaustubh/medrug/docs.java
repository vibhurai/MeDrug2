package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class docs extends AppCompatActivity implements DoctorAdapter.docclick {
    protected DoctorAdapter adapter2;
    public void end(int pos){
//        Button b1 = (Button)view;
        Intent in = new Intent();
//
       String text = Main2Activity.doctorList.get(pos).getText1();


        in.putExtra("Name",text);
        setResult(1,in);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);
        setUpDoctorView();

    }

    private void setUpDoctorView() {
        RecyclerView recyclerView2 = findViewById(R.id.doctor_recycler);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter2 = new DoctorAdapter(Main2Activity.doctorList,docs.this);


        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(adapter2);




    }


    @Override
    public void ondocclick(int position) {
        end(position);

    }
}
