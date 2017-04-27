package com.NUWC_ETJ.displays;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import 	android.support.v4.app.ActivityCompat;
import android.widget.TextView;


public class WELCOME_display extends AppCompatActivity {

    public Button start;



    public void startInit(){

        start = (Button)findViewById(R.id.Start_Button);

        String perms [] = new String[5];
        perms[0] = "android.permission.READ_EXTERNAL_STORAGE";
        perms[1] = "android.permission.WRITE_EXTERNAL_STORAGE";
        perms[2] = "android.permission.INTERNET";
        perms[3] = "android.permission.ACCESS_NETWORK_STATE";
        perms[4] = "android.permission.ACCESS_WIFI_STATE";
        ActivityCompat.requestPermissions(this,
                perms,
                0);




        //check if permissions have already been handled
        if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(WELCOME_display.this, NUWC_Sign_In.class));
        }


        //create onclick listener and onclick handler
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WELCOME_display.this, NUWC_Sign_In.class));
            }
        });


    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_display);
        startInit();
    }


}
