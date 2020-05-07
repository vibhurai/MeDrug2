package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    protected static List<ExampleItem> exampleList;
    interface_proc Interface_proc;
//    https://www.youtube.com/watch?v=I-I67MNRQZM

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillExampleList();
        Button button = (Button) findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity2();
                }
            });

        Button b2 = findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), register.class);
                startActivity(i);
            }
        });
        }

        public void openActivity2() {
            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
//
        }

    private void fillExampleList() {

        Gson gson = new GsonBuilder()
                .setLenient().serializeNulls()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://devilish.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        exampleList = new ArrayList<>();

        Call<List<meds_intr>> call;
        Interface_proc = retrofit.create(interface_proc.class);
        call = Interface_proc.getmeds();
        call.enqueue(new Callback<List<meds_intr>>() {
            @Override
            public void onResponse(Call<List<meds_intr>> call, Response<List<meds_intr>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Sori", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<meds_intr> doc = response.body();
                int doc_id = 0;
                assert doc != null;
                for (meds_intr med : doc) {
                    if (doc_id < 10) {
                        String content = "";
                        int x = med.getCategory();
                        if (x == 1)
                            content += "Fever";
                        else if (x == 2)
                            content += "Diabetes";
                        else if (x == 3)
                            content += "Cold";
                        else if (x == 4)
                            content += "General";
                        else
                            content += "Allergy";
                        exampleList.add(new ExampleItem(R.drawable.drugs, med.getName(), content));
                        doc_id++;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<meds_intr>> call, Throwable t) {


            }
        });
    }
}
