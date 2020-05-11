package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
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

public class Main2Activity extends AppCompatActivity {
    protected static List<ExampleItem> exampleList;
    protected static List<DocItem> doctorList;
    AnimationDrawable load;

    interface_proc Interface_proc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView v =findViewById(R.id.img);
        v.setBackgroundResource(R.drawable.spalsh);
        load = (AnimationDrawable)(v.getBackground());


        fillExampleList();
        fillDoctorList();
        new Handler().postDelayed((new Runnable() {
            @Override
            public void run() {
                 Intent i = new Intent(Main2Activity.this, MainActivity.class);
                 startActivity(i);
                 finish();
            }
        }), 3000);
//        try {
//            Thread.sleep(3330);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally {
//
//
//        }



    }
    @Override
    protected void onStart() {

        super.onStart();
        load.start();



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

        Interface_proc = retrofit.create(interface_proc.class);
        Call<List<medicines>> call;

        call = Interface_proc.getmeds();
        call.enqueue(new Callback<List<medicines>>() {
            @Override
            public void onResponse(Call<List<medicines>> call, Response<List<medicines>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main2Activity.this, "Sori", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<medicines> doc = response.body();
                int doc_id = 0;
                assert doc != null;
                for (medicines med : doc) {
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
                        exampleList.add(new ExampleItem(R.drawable.drugs, med.getName(), content, med.getQuantity()));
                        doc_id++;
                    }
                }
                System.out.println(exampleList);
            }
            @Override
            public void onFailure(Call<List<medicines>> call, Throwable t) {


            }
        });
    }

    protected void fillDoctorList() {

        Gson gson = new GsonBuilder()
                .setLenient().serializeNulls()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://devilish.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        doctorList = new ArrayList<>();

        Interface_proc = retrofit.create(interface_proc.class);
        Call<List<doctors>> call;

        call = Interface_proc.getdocs();
        call.enqueue(new Callback<List<doctors>>() {
            @Override
            public void onResponse(Call<List<doctors>> call, Response<List<doctors>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main2Activity.this, "Sori", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<doctors> docList = response.body();

                for (doctors doc : docList) {
                    doctorList.add(new DocItem(R.drawable.doctortoo,doc.getName(),doc.getSpeciality(),doc.getDetails(), String.valueOf(doc.getPhone())));



                }
                System.out.println(doctorList);

            }
            @Override
            public void onFailure(Call<List<doctors>> call, Throwable t) {


            }
        });
    }
}
