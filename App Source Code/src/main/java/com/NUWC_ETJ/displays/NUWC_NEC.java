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

public class NUWC_NEC extends AppCompatActivity {

    public Button nec_classification;
    public Button nec_lang;
    public Button nec_history;



    private Bundle myBundle = new Bundle();

    public void init6(){

        nec_classification = (Button)findViewById(R.id.nec_classification);
        nec_lang = (Button)findViewById(R.id.nec_lang);
        nec_history = (Button)findViewById(R.id.nec_history);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));

        nec_classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nec_textView7.setText("DISPLAYS CLASSIFICATION INFORMATION");
                Intent myIntent = new Intent(NUWC_NEC.this, NEC_classification_display.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        nec_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nec_textView7.setText("DISPLAYS LANGUAGE SKILL INFORMATION");
                Intent myIntent = new Intent(NUWC_NEC.this, NEC_lang_display.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        nec_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nec_textView7.setText("DISPLAYS CAREER HISTORY INFORMATION");
                Intent myIntent = new Intent(NUWC_NEC.this, NEC_history_display.class);
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
                                Intent myIntentPers = new Intent(NUWC_NEC.this, NUWC_Admin.class);
                                myIntentPers.putExtras(myBundle);
                                startActivity(myIntentPers);
                                break;
                            case R.id.action_home:
                                Intent myIntentHome = new Intent(NUWC_NEC.this, NUWC_MAIN_Activity.class);
                                myIntentHome.putExtras(myBundle);
                                startActivity(myIntentHome);
                                break;
                            case R.id.action_credentials:
                                Intent myIntentCred = new Intent(NUWC_NEC.this, NUWC_Credentials.class);
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
        Intent myIntentHome = new Intent(NUWC_NEC.this, NUWC_MAIN_Activity.class);
        myIntentHome.putExtras(myBundle);
        startActivity(myIntentHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_nec);
        init6();
    }
}
