package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

import static android.provider.Telephony.Mms.Part.TEXT;

public class menu extends AppCompatActivity {
    protected static ArrayList<historyItem> HistList = new ArrayList<>();
    interface_proc intra;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3) {
            fillHistoryList();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        fillHistoryList();
        Button b1 = findViewById(R.id.emergency_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), loc.class);
                startActivity(i);
            }
        });


        Button b2 = findViewById(R.id.book_appointments_button);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Bookapt.class);
                startActivityForResult(i,11);



            }
        });

        Button b3 = findViewById(R.id.pharmacy_button);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Farmacie.class);
                startActivity(i);
            }
        });

        Button b4 = findViewById(R.id.contact_button);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), contact.class);
                startActivity(i);
            }
        });

        Button b5 = findViewById(R.id.hist);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), History.class);
                startActivity(i);
            }
        });

        Button b6 = findViewById(R.id.alerts);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), alerts.class);
                startActivity(i);
            }
        });


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
        HistList.clear();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        int x = sharedPreferences.getInt(TEXT, -1);
        Call<List<appointment>> call = intra.getappoi(x);
        call.enqueue(new Callback<List<appointment>>() {
            @Override
            public void onResponse(Call<List<appointment>> call, Response<List<appointment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(menu.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    List<appointment> b = response.body();
                    for(appointment med : b){
                        HistList.add(new historyItem(med.getDoctor(), med.getDate(), med.getTime(), "Confirmed",med.getId()));
                        System.out.println(med.getId());
                    }

                }
                //System.out.println(HistList);
            }

            @Override
            public void onFailure(Call<List<appointment>> call, Throwable t) {

            }
        });
    }


}
