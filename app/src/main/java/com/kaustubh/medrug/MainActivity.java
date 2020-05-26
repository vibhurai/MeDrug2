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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Mms.Part.TEXT;

public class MainActivity extends AppCompatActivity {

    //    https://www.youtube.com/watch?v=I-I67MNRQZM
    Button button;
    EditText user_name;
    EditText pass;
    interface_proc intr;
    static int ppaass;
    int l_s=0;
    public static final String SHARED_PREFS = "sharedPrefs";
    ProgressDialog dialog;
    private long mLastClickTime,mla,mlb = 0;

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

            button = (Button) findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                        return;
                    }
                    Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    assert vibe != null;
                    vibe.vibrate(100);
                    dialog=new ProgressDialog(MainActivity.this);
                    dialog.setTitle("Authenticating");
                    dialog.setMessage("Please wait");
                    dialog.show();

                    mLastClickTime = SystemClock.elapsedRealtime();
                    int x = log();



                }
            });

        Button b2 = findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mla < 3000) {
                    return;
                }
                mla = SystemClock.elapsedRealtime();
                Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                assert vibe != null;
                vibe.vibrate(100);
                Intent i = new Intent(getApplicationContext(), register.class);
                startActivity(i);


            }
        });
        }

      public void openActivity2() {
            Intent intent = new Intent(this, menu.class);
            dialog.dismiss();
            startActivity(intent);
//
        }

    private void loaddata() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int x = sharedPreferences.getInt(TEXT, -1);
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
                        dialog.dismiss();
                        return;
                    }
                    login x = response.body();
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                      int y = Integer.parseInt(x.getPassword());
                    editor.putInt(TEXT, y);
                    editor.apply();
//                    loaddata();
                    ppaass=y;
                    //Toast.makeText(MainActivity.this, String.valueOf(x.getPassword()), Toast.LENGTH_SHORT).show();
                    openActivity2();
                }

                @Override
                public void onFailure(Call<login> call, Throwable t) {
                    System.out.println("here");
                    openActivity2();
                }
            });

            return l_s;

        }





}
