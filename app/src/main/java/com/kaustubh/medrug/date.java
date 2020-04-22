package com.kaustubh.medrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class date extends AppCompatActivity {
    public void end(View view){
        Button b1 = (Button)view;
        Intent in = new Intent();
        in.putExtra("Name",b1.getText());
        setResult(2,in);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
    }
}
