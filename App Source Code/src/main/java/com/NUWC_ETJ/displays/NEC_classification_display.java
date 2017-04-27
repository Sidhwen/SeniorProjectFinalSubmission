package com.NUWC_ETJ.displays;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.NUWC_ETJ.models.NECModel;

public class NEC_classification_display extends AppCompatActivity {

    private Button Search_NEC_Class;
    private EditText Search_NEC_Class_Text;
    private ScrollView myScrollView;
    private NECModel myNECModel;
    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    private Bundle myBundle = new Bundle();

    public void init11(){
        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");
        //myDB = SQLiteDatabase.openDatabase("data/data/NUWC.db", null, SQLiteDatabase.OPEN_READONLY);

        myNECModel = new NECModel(myDB, myID);



        myScrollView = (ScrollView) findViewById(R.id.NEC_CLASS_SCROLLER_ID);
        myNECModel.print(myScrollView, this);
        Search_NEC_Class = (Button) findViewById(R.id.NEC_CLASS_SEARCH_BUTTON);
        Search_NEC_Class_Text = (EditText) findViewById(R.id.NEC_CLASS_SEARCH);



        Search_NEC_Class.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Search_NEC_Class_Text.setText("");
                                                    myNECModel.SearchPosition("");
                                                }
                                            }

        );
        Search_NEC_Class_Text.addTextChangedListener(new TextWatcher()
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
                myNECModel.SearchPosition(SearchString);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nec_classification_display);
        init11();
    }
}
