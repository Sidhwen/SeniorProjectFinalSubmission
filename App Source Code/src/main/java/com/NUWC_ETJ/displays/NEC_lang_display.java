package com.NUWC_ETJ.displays;

import com.NUWC_ETJ.models.ExpandableTextView;
import com.NUWC_ETJ.models.language;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class NEC_lang_display extends AppCompatActivity {

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    private Bundle myBundle = new Bundle();
    public void init12(){
        String[] Output = new String[5];
        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");

        int initCounter = 0;
        ScrollView myScrollView = (ScrollView) findViewById(R.id.NEC_LANG_SCROLLER_ID);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        language[] myLanguage = new language[5];
        ExpandableTextView[] myTextViews = new ExpandableTextView[5];
        while (initCounter < 5)
        {
            myTextViews[initCounter] = new ExpandableTextView(this);
            myLanguage[initCounter] = new language(myDB, myID ,initCounter + 1);
            Output[initCounter] = myLanguage[initCounter].print(myTextViews[initCounter]);
            if(initCounter%2 == 1) {
                myTextViews[initCounter].setBackgroundColor(0xFFE9EBF6);
                myTextViews[initCounter].myChild.setBackgroundColor(0xFFE9EBF6);
            }
            ll.addView(myTextViews[initCounter].Line);
            ll.addView(myTextViews[initCounter]);
            ll.addView(myTextViews[initCounter].myChild);

            initCounter++;
        }
        ll.addView(myTextViews[0].EndLine);
        myScrollView.addView(ll);






    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nec_lang_display);
        init12();
    }
}
