package com.example.filepathdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById (R.id.button).setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 File filesDir = getFilesDir();
                 Log.d("MainActivity", "getFilesDir() == " + filesDir.getAbsolutePath());
//                 08-01 06:43:21.010 17993-17993/com.example.filepathdemo D/MainActivity: getFilesDir() == /data/user/0/com.example.filepathdemo/files
                 File dex = getDir("dex", Context.MODE_PRIVATE);
                 Log.d("MainActivity", "getDir(\"dex\", Context.MODE_PRIVATE) == " + dex.getAbsolutePath());
//                 08-01 06:43:21.011 17993-17993/com.example.filepathdemo D/MainActivity: getDir("dex", Context.MODE_PRIVATE) == /data/user/0/com.example.filepathdemo/app_dex
                 File cacheDir = getCacheDir();
                 Log.d("MainActivity", "getCacheDir() == " + cacheDir.getAbsolutePath());
//                 08-01 06:48:49.605 19096-19096/com.example.filepathdemo D/MainActivity: getCacheDir() == /data/user/0/com.example.filepathdemo/cache
                 String[] fileList = fileList();
                 List<String> asList = Arrays.asList(fileList);
                 Log.d("MainActivity", "fileList() == " + asList.toString());

             }
         });

        findViewById (R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File externalCacheDir = getExternalCacheDir();
                Log.d("MainActivity", "getExternalCacheDir() == " + externalCacheDir.getAbsolutePath());
                File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                Log.d("MainActivity", "getExternalFilesDir(Environment.DIRECTORY_PICTURES) == " + externalFilesDir.getAbsolutePath());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    File[] externalCacheDirs = getExternalCacheDirs();
                    Log.d("MainActivity", "externalCacheDirs == " + externalCacheDirs.toString());
                    File[] externalFilesDirs = getExternalFilesDirs(Environment.DIRECTORY_PICTURES);
                    Log.d("MainActivity", "externalFilesDirs == " + externalFilesDirs.toString());


                }


            }
        });
    }
}
