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

public class register extends AppCompatActivity {
    EditText f_nm;
    EditText l_nm;
    EditText snuid;
    EditText email;
    EditText pass;
    ProgressDialog dialog;
    long mLastClickTime = 0;
    interface_proc intr_reg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button b2 = findViewById(R.id.register);
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

        intr_reg = retrofit.create(interface_proc.class);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                assert vibe != null;
                vibe.vibrate(100);
                dialog = new ProgressDialog(register.this);
                dialog.setTitle("Authenticating");
                dialog.setMessage("Please wait");
                dialog.show();

                mLastClickTime = SystemClock.elapsedRealtime();

                register_user();
            }
        });
    }
    public void register_user()
    {

        f_nm = (EditText) findViewById(R.id.f_name);
        l_nm = (EditText) findViewById(R.id.l_name);
        snuid = (EditText) findViewById(R.id.snu_id);
        email = (EditText) findViewById(R.id.email_id);
        pass = (EditText) findViewById(R.id.password);

        Editable f_nm_Txt=(Editable)f_nm.getText();
        String f_nm_str = f_nm_Txt.toString();

        Editable l_nm_Txt=(Editable)l_nm.getText();
        String l_nm_str = l_nm_Txt.toString();

        Editable snuid_Txt=(Editable)snuid.getText();
        String snuid_str = snuid_Txt.toString();

        Editable email_Txt=(Editable)email.getText();
        String email_str = email_Txt.toString();

        Editable pass_Txt=(Editable)pass.getText();
        String pass_str = pass_Txt.toString();

        reg r = new reg(pass_str,null,false,false,false,email_str,f_nm_str,l_nm_str,snuid_str);
        Call<reg> call =  intr_reg.postreg(r);
        call.enqueue(new Callback<reg>() {
            @Override

            public void onResponse(Call<reg> call, Response<reg> response) {
                if (!response.isSuccessful()) {
//                    flag =0;
                    Toast.makeText(register.this, "Kindly Try again", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), register.class);
                    startActivity(i);
                    return;
                }

            }

            @Override
            public void onFailure(Call<reg> call, Throwable t) {
//                flag =1;
                Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), menu.class);
                startActivity(i);
            }
        });

    }
}

