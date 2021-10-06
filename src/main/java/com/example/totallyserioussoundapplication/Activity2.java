package com.example.totallyserioussoundapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Activity2 extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
      starter();
    }
    private void starter(){
        this.getWindow().getDecorView().setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        String key = getIntent().getStringExtra("fragment");
        switch (key){
            case "ayarlar":{
                Log.e("Activity 2","Ayarlar");
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frameLayout,new FragmentSettings());
                fragmentTransaction.commit();


                break;}
            case "info":{
                Log.e("Activity 2","Info");
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frameLayout,new FragmentInfo());
                fragmentTransaction.commit();


                break;


            }
            default:Log.e("Activity2","Default");


        }



    }
}