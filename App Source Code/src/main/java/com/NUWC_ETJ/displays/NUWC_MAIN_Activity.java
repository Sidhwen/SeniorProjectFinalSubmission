package com.NUWC_ETJ.displays;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;



public class NUWC_MAIN_Activity extends AppCompatActivity {

    public Button refresh;
    public Button personal;
    public Button career;
    public Button cred;
    public String officerOrEnlisted;

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);
    public SQLiteDatabase myDB;

    private Bundle myBundle = new Bundle();

    public void init1(){
        myDB = myOpenHelper.openDatabase();







        //links sign_in button from design screen/xml to sign_in button object in activity
        refresh = (Button)findViewById(R.id.refresh);
        personal = (Button)findViewById(R.id.personal);
        career = (Button)findViewById(R.id.career);
        cred = (Button)findViewById(R.id.cred);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        //Create new bundle to pass user values to new Activities
        System.out.println(getIntent().getExtras().getString("ID"));
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));


        //create onclick listener and onclick handler
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NUWC_MAIN_Activity.this, NUWC_refresh.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NUWC_MAIN_Activity.this, NUWC_Admin.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getOfficerOrEnlisted();

                if(officerOrEnlisted.equals("O")) {
                    //Create Intent for NOBC
                    Intent myIntent = new Intent(NUWC_MAIN_Activity.this, NUWC_NOBC.class);
                    myIntent.putExtras(myBundle);
                    startActivity(myIntent);
                }
                else if(officerOrEnlisted.equals("E")){
                    //Create Intent for NEC
                    Intent myIntent = new Intent(NUWC_MAIN_Activity.this, NUWC_NEC.class);
                    myIntent.putExtras(myBundle);
                    startActivity(myIntent);
                }
                else{
                    System.out.print("You are not an Officer or Enlisted, please contact the Admins.");
                }

            }
        });


        cred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NUWC_MAIN_Activity.this, NUWC_Credentials.class);
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
                                Intent myIntentPers = new Intent(NUWC_MAIN_Activity.this, NUWC_Admin.class);
                                myIntentPers.putExtras(myBundle);
                                startActivity(myIntentPers);
                                break;
                            case R.id.action_career:
                                getOfficerOrEnlisted();
                                if(officerOrEnlisted.equals("O")) {
                                    //Create Intent for NOBC
                                    Intent myIntent = new Intent(NUWC_MAIN_Activity.this, NUWC_NOBC.class);
                                    myIntent.putExtras(myBundle);
                                    startActivity(myIntent);
                                }
                                else if(officerOrEnlisted.equals("E")){
                                    //Create Intent for NEC
                                    Intent myIntent = new Intent(NUWC_MAIN_Activity.this, NUWC_NEC.class);
                                    myIntent.putExtras(myBundle);
                                    startActivity(myIntent);
                                }
                                else {
                                    System.out.print("You are not an Officer or Enlisted, please contact the Admins.");
                                }
                                break;
                            case R.id.action_credentials:
                                Intent myIntentCred = new Intent(NUWC_MAIN_Activity.this, NUWC_Credentials.class);
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


    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Exit and sign out of the Mobile ETJ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        moveTaskToBack(true);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_main_);
        init1();
    }

}
