package com.NUWC_ETJ.displays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.NUWC_ETJ.models.TrainingModel;

public class CRED_training_display extends AppCompatActivity {

    private Button Cred_Train_Search;
    private EditText Cred_Train_Search_Text;
    private TrainingModel trainingModel;
    private ScrollView myScrollView;
    //Object to help open the database
    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);
    private Bundle myBundle = new Bundle();

    public void init14(){
        //Open the database and then pass it to the model along with the ID of the person we want to query for
        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");
        trainingModel = new TrainingModel(myDB, myID);

        //Set up the text view and set the text in it to the string created by the data model
        myScrollView = (ScrollView) findViewById(R.id.TRAINING_SCROLLER_ID);
        trainingModel.createDisplayString(myScrollView, this);

        //Button to go back
        Cred_Train_Search = (Button) findViewById(R.id.CRED_TRAINING_SEARCH_BUTTON);
        Cred_Train_Search_Text = (EditText) findViewById(R.id.CRED_TRAINING_SEARCH);


        Cred_Train_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cred_Train_Search_Text.setText("");
                trainingModel.SearchPosition("");

            }
        });


        Cred_Train_Search_Text.addTextChangedListener(new TextWatcher()
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
                trainingModel.SearchPosition(SearchString);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cred_training_display);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init14();
    }
}
