package com.NUWC_ETJ.displays;

import com.NUWC_ETJ.models.QualCertModel;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class CRED_qual_display extends AppCompatActivity {

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);
    private Bundle myBundle = new Bundle();
    private Button Cred_Qual_Search;
    private EditText Cred_Qual_Search_Text;
    private QualCertModel myQualCertModel;

    public void init15(){

        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");
        //myDB = SQLiteDatabase.openDatabase("data/data/NUWC.db", null, SQLiteDatabase.OPEN_READONLY);
        int initCounter = 0;
        myQualCertModel = new QualCertModel(myDB, myID);

        ScrollView myScrollView = (ScrollView) findViewById(R.id.QUAL_SCROLLER_ID);
        myQualCertModel.print(myScrollView, this);
        Cred_Qual_Search = (Button) findViewById(R.id.CRED_QUAL_SEARCH_BUTTON);
        Cred_Qual_Search_Text = (EditText) findViewById(R.id.CRED_QUAL_SEARCH);


        Cred_Qual_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cred_Qual_Search_Text.setText("");
                myQualCertModel.SearchPosition("");

            }
        });
        Cred_Qual_Search_Text.addTextChangedListener(new TextWatcher()
        {
            String SearchString;
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                SearchString = cs.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                myQualCertModel.SearchPosition(SearchString);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cred_qual_display);
        init15();
    }
}
