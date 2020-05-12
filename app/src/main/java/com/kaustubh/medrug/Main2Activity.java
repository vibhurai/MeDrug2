package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Main2Activity extends AppCompatActivity {
    protected static List<ExampleItem> exampleList;
    protected static List<DocItem> doctorList;
    protected static ArrayList<historyItem> HistList = new ArrayList<>();
    public static final String SWITCH1 = "switch1";
    public static final String SHARED_PREFS_HIST = "ssharedPrefs";
    public static final String TEXT_HIST = "tsext";

    ArrayList<Integer> ids=new ArrayList<>();

    AnimationDrawable load;
    interface_proc intra;

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
        fillHistoryList();
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

    private void fillHistoryList() {
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

        intra = retrofit.create(interface_proc.class);
        addappoi();
    }
    private void addappoi() {
        Call<List<appointment>> call = intra.getappoi(185);
        call.enqueue(new Callback<List<appointment>>() {
            @Override
            public void onResponse(Call<List<appointment>> call, Response<List<appointment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main2Activity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    List<appointment> b = response.body();
                    for(appointment med : b){
                        String x =getdoc(med.getScheduled());
                        String y  = gettim(med.getScheduled());
                        HistList.add(new historyItem(x, med.getDate(), y, "Confirmed",med.getScheduled()));
                    }

                }
                System.out.println(HistList);
            }

            @Override
            public void onFailure(Call<List<appointment>> call, Throwable t) {

            }
        });
    }
    @NotNull
    private String getdoc(int scheduled) {
        String s="";
        Call<s_d> call = intra.getdocnm(scheduled);
        call.enqueue(new Callback<s_d>() {
            @Override
            public void onResponse(Call<s_d> call, Response<s_d> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main2Activity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                }
                s_d x = response.body();
                String d = x.getDoc();
//                tt.append(d);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_HIST, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
//                    String d = x.getPassword());
                editor.putString(TEXT_HIST, d);
                editor.apply();
            }


            @Override
            public void onFailure(Call<s_d> call, Throwable t) {

            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_HIST, MODE_PRIVATE);
        String y  = sharedPreferences.getString(TEXT_HIST, "");
        return y;
    }
    private String gettim(int scheduled) {
//        String s="";
        Call<s_d> call = intra.getdocnm(scheduled);
        call.enqueue(new Callback<s_d>() {
            @Override
            public void onResponse(Call<s_d> call, Response<s_d> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main2Activity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                }
                s_d x = response.body();
                String d = x.getDoc();
                String a = x.getTime();
//                tt.append(d);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_HIST, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
//                    String d = x.getPassword());
//                editor.putString(TEXT, d);
                editor.putString(SWITCH1, a);
                editor.apply();
            }


            @Override
            public void onFailure(Call<s_d> call, Throwable t) {

            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_HIST, MODE_PRIVATE);
        String y  = sharedPreferences.getString(SWITCH1, "");
        return y;
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
