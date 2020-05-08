package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Bookapt extends AppCompatActivity {
    String strEditText1,strEditText2,strEditText3;
    interface_proc Interface_proc;
    Button b1 ;
    Button b2;
    Button b3;
//    private Object interface_proc;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                strEditText1 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Name);
                b2.setText(strEditText1);
            }
            if (resultCode==2){
                strEditText2 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.Date);
                b2.setText(strEditText2);

            }
            if (resultCode==3){
                strEditText3 = data.getStringExtra("Name");
                Button b2 = findViewById(R.id.time);
                b2.setText(strEditText3);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookapt);

        b1= findViewById(R.id.Name);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), docs.class);
                startActivityForResult(i,1);
            }
        });
        b2= findViewById(R.id.Date);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), date.class);
                startActivityForResult(i,1);


            }


        });

        b3= findViewById(R.id.time);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Tame.class);
                startActivityForResult(i,1);
            }
        });
        Gson gson = new GsonBuilder()
                .setLenient().serializeNulls()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://devilish.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Interface_proc = retrofit.create(interface_proc.class);
//         checkavailbility();

        Button b4= findViewById(R.id.Book);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Bookapt.this, "Booked successfully", Toast.LENGTH_SHORT).show();

                finish();
            }
        });


    }

    public void checkavailbility() {
        String doc_name = (String) b1.getText();
        final int[] doc_id = new int[1];
        Call<List<doctors>> call;
        call = Interface_proc.getdocs();
        call.enqueue(new Callback<List<doctors>>() {
            @Override
            public void onResponse(Call<List<doctors>> call, Response<List<doctors>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Bookapt.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<doctors> doc= response.body();
                for (doctors med : doc) {
                   if(med.getName()==doc_name)
                   {
                       doc_id[0] = med.getId();
                       break;}
                }
            }

            @Override
            public void onFailure(Call<List<doctors>> call, Throwable t) {
//                tv.setText(t.getMessage());
            }
        });









    }
}
