package com.kaustubh.medrug;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class KiFarma extends AppCompatActivity {

    String num="smsto:919582440674";
    ImageView imageView;
    TextView tv;
    Bitmap photo;
    Uri imageUri;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ki_farma);
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

         imageView= findViewById(R.id.imageview);
        Button wap=findViewById(R.id.wap);
        wap.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SENDTO,Uri.parse(num));
                //i.setType("text/plain");

               try {
                   i.setPackage("com.whatsapp");
                   startActivity(Intent.createChooser(i,""));

               } catch (Exception e){
                   Toast.makeText(KiFarma.this,"Whatsapp not installed",Toast.LENGTH_SHORT).show();

               }




            }
        });

        Button photoButton = findViewById(R.id.Wassap);
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                }
                else
                {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                }
            }
        });
        Button send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv=findViewById(R.id.editText);
                String message=tv.getText().toString();
                try {
                    imageUri = getImageUri(KiFarma.this, photo);
                    System.out.println(photo);
                    System.out.println(imageUri);
                    sendmessage(message, imageUri);
                }catch (Exception e){
                    Toast.makeText(KiFarma.this,"Click a photo first",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @SuppressLint("IntentReset")
    private void sendmessage(String message, Uri imageUri) {


        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"mv134@snu.edu.in"});
//        Intent intent= new Intent(Intent.ACTION_SEND);

//        intent.setData(Uri.parse(message));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Prescription");
        intent.putExtra(Intent.EXTRA_TEXT, message+"\n Phone Number: <Include Phone Number Here>");

        intent.putExtra(Intent.EXTRA_STREAM,imageUri);
        intent.setType("image/jpeg");
       intent.setPackage("com.google.android.gm");
        //intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent,""));
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
//                photo = BitmapFactory.decodeStream(KiFarma.this.getContentResolver().openInputStream(imageUri), null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(photo);
            imageView.setImageBitmap(photo);
        }
    }
}
