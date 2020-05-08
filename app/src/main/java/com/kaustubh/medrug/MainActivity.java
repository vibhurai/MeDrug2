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
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    https://www.youtube.com/watch?v=I-I67MNRQZM
    Button button;
    EditText user_name;
    EditText pass;
    interface_proc intr;
    int l_s=0;
    protected static List<ExampleItem> exampleList;
    protected static List<DocItem> doctorList;
    interface_proc Interface_proc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            intr = retrofit.create(interface_proc.class);
            fillExampleList();
            fillDoctorList();
            button = (Button) findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = log();


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
        public int log()
        {
             user_name = (EditText) findViewById(R.id.Username);
             pass = (EditText) findViewById(R.id.Passwod);
            Editable newTxt=(Editable)user_name.getText();
            String usr = newTxt.toString();
            Editable newTxt1=(Editable)pass.getText();
            String pas = newTxt1.toString();

             login flag = new login(usr,pas);
            Call<login> call = intr.postlogin(flag);
            call.enqueue(new Callback<login>() {
                @Override
                public void onResponse(Call<login> call, Response<login> response) {
                    if (!response.isSuccessful()) {
//                        tt.setText("Code: " + response.code());
                        Toast.makeText(MainActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<login> call, Throwable t) {
                   openActivity2();
                }
            });

            return l_s;

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
                    Toast.makeText(MainActivity.this, "Sori", Toast.LENGTH_SHORT).show();
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
                        exampleList.add(new ExampleItem(R.drawable.drugs, med.getName(), content));
                        doc_id++;
                    }
                }
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
                    Toast.makeText(MainActivity.this, "Sori", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<doctors> docList = response.body();

                for (doctors doc : docList) {
                    doctorList.add(new DocItem(R.drawable.doctortoo,doc.getName(),doc.getSpeciality(),doc.getDetails(), doc.getPhone()));


                }
            }
            @Override
            public void onFailure(Call<List<doctors>> call, Throwable t) {


            }
        });
        System.out.println(doctorList);
    }



}
