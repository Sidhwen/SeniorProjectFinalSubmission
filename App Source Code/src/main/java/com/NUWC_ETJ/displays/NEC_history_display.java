package com.NUWC_ETJ.displays;

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
import com.NUWC_ETJ.models.NECHistoryModel;

public class NEC_history_display extends AppCompatActivity {

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);
    private Button NEC_History_Search;
    private EditText NEC_History_Search_Text;
    private Bundle myBundle = new Bundle();
    NECHistoryModel necHistoryModel;

    public void init13(){
        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");
        //This uses 01 as the ID instead of 30 because 30 is an officer not an enlisted member
        necHistoryModel= new NECHistoryModel(myDB, myID);

        //Set up the text view and set the text in it to the string created by the data model
        necHistoryModel.createDisplayString((ScrollView) findViewById(R.id.NEC_HISTORY_SCROLLER_ID), this);

        NEC_History_Search = (Button) findViewById(R.id.NEC_HISTORY_SEARCH_BUTTON);
        NEC_History_Search_Text = (EditText) findViewById(R.id.NEC_HISTORY_SEARCH);

        NEC_History_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEC_History_Search_Text.setText("");
                necHistoryModel.SearchPosition("");

            }
        });

        NEC_History_Search_Text.addTextChangedListener(new TextWatcher()
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
                necHistoryModel.SearchPosition(SearchString);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nec_history_display);
        init13();
    }
}
