package com.NUWC_ETJ.displays;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//import com.NUWC_ETJ.models.AwardsModel;

public class NUWC_Admin extends AppCompatActivity {

    public Button admin_data_button;
    public Button awards_button;


    public String officerOrEnlisted;
    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);
    public SQLiteDatabase myDB;

    private Bundle myBundle = new Bundle();

    //public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    public void init4(){
        myDB = myOpenHelper.openDatabase();
       // SQLiteDatabase myDB;
       // myDB = myOpenHelper.openDatabase();
        //myDB = SQLiteDatabase.openDatabase("data/data/NUWC.db", null, SQLiteDatabase.OPEN_READONLY);
       // AwardsModel myAwardsModel = new AwardsModel(myDB, "9999991001");


       // TextView myTextView = (TextView)findViewById(R.id.AWARDS_TEXT);
       // myTextView.setText(myAwardsModel.print());
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));


        admin_data_button = (Button)findViewById(R.id.admin_data_button);
        admin_data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NUWC_Admin.this, NUWC_admin_data.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        awards_button = (Button)findViewById(R.id.awards_button);
        awards_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NUWC_Admin.this, NUWC_awards.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_career:
                                getOfficerOrEnlisted();
                                if(officerOrEnlisted.equals("O")) {
                                    //Create Intent for NOBC
                                    Intent myIntent = new Intent(NUWC_Admin.this, NUWC_NOBC.class);
                                    myIntent.putExtras(myBundle);
                                    startActivity(myIntent);
                                }
                                else if(officerOrEnlisted.equals("E")){
                                    //Create Intent for NEC
                                    Intent myIntent = new Intent(NUWC_Admin.this, NUWC_NEC.class);
                                    myIntent.putExtras(myBundle);
                                    startActivity(myIntent);
                                }
                                else {
                                    System.out.print("You are not an Officer or Enlisted, please contact the Admins.");
                                }
                                break;
                            case R.id.action_home:
                                Intent myIntentHome = new Intent(NUWC_Admin.this, NUWC_MAIN_Activity.class);
                                myIntentHome.putExtras(myBundle);
                                startActivity(myIntentHome);
                                break;
                            case R.id.action_credentials:
                                Intent myIntentCred = new Intent(NUWC_Admin.this, NUWC_Credentials.class);
                                myIntentCred.putExtras(myBundle);
                                startActivity(myIntentCred);
                                break;
                        }
                        return true;
                    }
                });


    }

    public void getOfficerOrEnlisted(){
        String QUERY_TABLE = "MEMBER_NAV";
        String QUERY_RETURN[] = new String[1];
        QUERY_RETURN[0] = "OE_CD";
        String QUERY_SELECTION = "DOD_EDI_PI = '" + getIntent().getExtras().getString("ID") + "'";
        System.out.println("QUERY ID: " + QUERY_SELECTION);

        Cursor myCursor = myDB.query(QUERY_TABLE, QUERY_RETURN, QUERY_SELECTION, null, null, null, null);
        myCursor.moveToFirst();

        officerOrEnlisted = myCursor.getString(0);

        System.out.println("Cursor Count: " + myCursor.getCount());
        System.out.println("O or E: " + officerOrEnlisted);
    }

    @Override
    public void onBackPressed(){
        Intent myIntentHome = new Intent(NUWC_Admin.this, NUWC_MAIN_Activity.class);
        myIntentHome.putExtras(myBundle);
        startActivity(myIntentHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_admin);
        init4();
    }
}
