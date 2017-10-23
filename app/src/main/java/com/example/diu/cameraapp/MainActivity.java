package com.example.diu.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    Button tp;
    Bitmap b;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.my_image);
        tp = (Button) findViewById(R.id.take_photo);
        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 0) {
                    // code for taking photo
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 99);
                }
                else if(flag == 1){
                    //-- code for saving photo --

                    savePhotoToMySdCard(b);
                    flag = 0;
                    tp.setText("Take Photo");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == RESULT_OK && data != null){
            b = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(b);
            flag = 1;
            tp.setText("Save Photo");
        }
    }
    public void savePhotoToMySdCard(Bitmap bit){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String pname = sdf.format(new Date());

        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root+"/Cam_pic");
        folder.mkdirs();
        File my_file = new File(folder, pname+".jpg");

        try{
            FileOutputStream stream = new FileOutputStream(my_file);
            bit.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            stream.flush();
            stream.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
