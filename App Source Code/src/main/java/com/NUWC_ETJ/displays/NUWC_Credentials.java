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
import android.widget.TextView;

public class NUWC_Credentials extends AppCompatActivity {

    public Button training;
    public Button qual;
    public Button asvab;

    public TextView cred_textView;
    private Bundle myBundle = new Bundle();


    public String officerOrEnlisted;
    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);
    public SQLiteDatabase myDB;

    public void init7(){
        myDB = myOpenHelper.openDatabase();


        training = (Button)findViewById(R.id.training);
        qual = (Button)findViewById(R.id.qual);
        asvab = (Button)findViewById(R.id.asvab);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cred_textView.setText("DISPLAYS TRAINING INFORMATION");
                Intent myIntent = new Intent(NUWC_Credentials.this, CRED_training_display.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        qual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cred_textView.setText("DISPLAYS QUALIFICATIONS INFORMATION");
                Intent myIntent = new Intent(NUWC_Credentials.this, CRED_qual_display.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        asvab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cred_textView.setText("DISPLAYS ASVAB/BTB INFORMATION");
                Intent myIntent = new Intent(NUWC_Credentials.this, CRED_asvab_display.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_personal:
                                Intent myIntentPers = new Intent(NUWC_Credentials.this, NUWC_Admin.class);
                                myIntentPers.putExtras(myBundle);
                                startActivity(myIntentPers);
                                break;
                            case R.id.action_home:
                                Intent myIntentHome = new Intent(NUWC_Credentials.this, NUWC_MAIN_Activity.class);
                                myIntentHome.putExtras(myBundle);
                                startActivity(myIntentHome);
                                break;
                            case R.id.action_career:
                                getOfficerOrEnlisted();
                                if(officerOrEnlisted.equals("O")) {
                                    //Create Intent for NOBC
                                    Intent myIntent = new Intent(NUWC_Credentials.this, NUWC_NOBC.class);
                                    myIntent.putExtras(myBundle);
                                    startActivity(myIntent);
                                }
                                else if(officerOrEnlisted.equals("E")){
                                    //Create Intent for NEC
                                    Intent myIntent = new Intent(NUWC_Credentials.this, NUWC_NEC.class);
                                    myIntent.putExtras(myBundle);
                                    startActivity(myIntent);
                                }
                                else {
                                    System.out.print("You are not an Officer or Enlisted, please contact the Admins.");
                                }
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
        Intent myIntentHome = new Intent(NUWC_Credentials.this, NUWC_MAIN_Activity.class);
        myIntentHome.putExtras(myBundle);
        startActivity(myIntentHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_credentials);
        init7();

    }
}
