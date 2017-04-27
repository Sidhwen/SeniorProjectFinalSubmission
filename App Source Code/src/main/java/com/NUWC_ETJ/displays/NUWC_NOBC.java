package com.NUWC_ETJ.displays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NUWC_NOBC extends AppCompatActivity {

    public Button classifications;
    public Button lang;
    public Button cHistory;

    private Bundle myBundle = new Bundle();


    public void init5(){

        classifications = (Button)findViewById(R.id.classifications);
        lang = (Button)findViewById(R.id.lang);
        cHistory = (Button)findViewById(R.id.cHistory);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));

        classifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOBC_textView.setText("Displays Classification Information on NOBCs");
                Intent myIntent = new Intent(NUWC_NOBC.this, NOBC_classification_display.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOBC_textView.setText("Displays Language Information on NOBCs");
                Intent myIntent = new Intent(NUWC_NOBC.this, NOBC_language_display.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        cHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOBC_textView.setText("Displays career history Information on NOBCs");
                Intent myIntent = new Intent(NUWC_NOBC.this, NOBC_history_display.class);
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
                                Intent myIntentPers = new Intent(NUWC_NOBC.this, NUWC_Admin.class);
                                myIntentPers.putExtras(myBundle);
                                startActivity(myIntentPers);
                                break;
                            case R.id.action_home:
                                Intent myIntentHome = new Intent(NUWC_NOBC.this, NUWC_MAIN_Activity.class);
                                myIntentHome.putExtras(myBundle);
                                startActivity(myIntentHome);
                                break;
                            case R.id.action_credentials:
                                Intent myIntentCred = new Intent(NUWC_NOBC.this, NUWC_Credentials.class);
                                myIntentCred.putExtras(myBundle);
                                startActivity(myIntentCred);
                                break;
                        }
                        return true;
                    }
                });

    }

    @Override
    public void onBackPressed(){
        Intent myIntentHome = new Intent(NUWC_NOBC.this, NUWC_MAIN_Activity.class);
        myIntentHome.putExtras(myBundle);
        startActivity(myIntentHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_nobc);
        //System.out.println(savedInstanceState.getString("ID"));

        init5();
    }
}
