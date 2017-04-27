package com.NUWC_ETJ.displays;

import com.NUWC_ETJ.models.NOBCModel;
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

public class NOBC_classification_display extends AppCompatActivity {

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);
    private Bundle myBundle = new Bundle();
    private NOBCModel myNOBCModel;
    private Button NOBC_Class_Search;
    private EditText NOBC_Class_Search_Text;

    public void init8(){

        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");
        //myDB = SQLiteDatabase.openDatabase("data/data/NUWC.db", null, SQLiteDatabase.OPEN_READONLY);

        myNOBCModel = new NOBCModel(myDB, myID);

        ScrollView myScrollView = (ScrollView) findViewById(R.id.NOBC_CLASS_SCROLLER_ID);
        myNOBCModel.print(myScrollView, this);

        //Button to go back
        NOBC_Class_Search = (Button) findViewById(R.id.NOBC_CLASS_SEARCH_BUTTON);
        NOBC_Class_Search_Text = (EditText) findViewById(R.id.NOBC_CLASS_SEARCH);

        NOBC_Class_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NOBC_Class_Search_Text.setText("");
                myNOBCModel.SearchPosition("");

            }
        });

        NOBC_Class_Search_Text.addTextChangedListener(new TextWatcher()
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
                myNOBCModel.SearchPosition(SearchString);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nobc_classification_display);
        init8();
    }
}
