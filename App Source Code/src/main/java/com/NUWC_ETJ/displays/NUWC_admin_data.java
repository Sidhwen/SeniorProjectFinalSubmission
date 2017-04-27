package com.NUWC_ETJ.displays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.NUWC_ETJ.models.AdminModel;


public class NUWC_admin_data extends AppCompatActivity {

    public TextView admin_data_textview;

    private Bundle myBundle = new Bundle();

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    public void init44() {
        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");
        //myDB = SQLiteDatabase.openDatabase("data/data/NUWC.db", null, SQLiteDatabase.OPEN_READONLY);
        AdminModel myAwardsModel = new AdminModel(myDB, myID);


        admin_data_textview = (TextView)findViewById(R.id.admin_textview);
        admin_data_textview.setText(myAwardsModel.print());



    }


    @Override
    public void onBackPressed(){
        Intent myIntentHome = new Intent(this, NUWC_Admin.class);
        myIntentHome.putExtras(myBundle);
        startActivity(myIntentHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_admin_data);
        init44();
    }
}
