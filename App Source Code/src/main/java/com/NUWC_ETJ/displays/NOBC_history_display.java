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
import com.NUWC_ETJ.models.NOBCHistoryModel;

public class NOBC_history_display extends AppCompatActivity {

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    private Bundle myBundle = new Bundle();
    private Button NOBC_History_Search;
    private EditText NOBC_History_Search_Text;
    private NOBCHistoryModel nobcHistoryModel;

    public void init10() {

        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");

        nobcHistoryModel = new NOBCHistoryModel(myDB, myID);
        //Set up the text view and set the text in it to the string created by the data model
        nobcHistoryModel.createDisplayString((ScrollView) this.findViewById(R.id.NOBC_HISTORY_SCROLLER_ID), this);


        NOBC_History_Search = (Button) findViewById(R.id.NOBC_HISTORY_SEARCH_BUTTON);
        NOBC_History_Search_Text = (EditText) findViewById(R.id.NOBC_HISTORY_SEARCH);

        NOBC_History_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NOBC_History_Search_Text.setText("");
                nobcHistoryModel.SearchPosition("");
            }
        });
        NOBC_History_Search_Text.addTextChangedListener(new TextWatcher()
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
                nobcHistoryModel.SearchPosition(SearchString);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nobc_history_display);
        init10();
    }
}
