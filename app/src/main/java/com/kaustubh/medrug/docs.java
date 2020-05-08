package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;
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


public class docs extends AppCompatActivity {
    interface_proc intr_docs;
    public void end(View view){
        Button b1 = (Button)view;
        Intent in = new Intent();
//
        String[] text = ((String) b1.getText()).split("\n");


        in.putExtra("Name",text[0]);
        setResult(1,in);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);
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

        intr_docs = retrofit.create(interface_proc.class);
        fillbuttons();
    }

    private void fillbuttons() {
        Call<List<doctors>> call;
        call = intr_docs.getdocs();
        call.enqueue(new Callback<List<doctors>>() {
            @Override
            public void onResponse(Call<List<doctors>> call, Response<List<doctors>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<doctors> doc= response.body();
                for (doctors med : doc) {

                }
            }

            @Override
            public void onFailure(Call<List<doctors>> call, Throwable t) {
//                tv.setText(t.getMessage());
            }
        });
    }
}
