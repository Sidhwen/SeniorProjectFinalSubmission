package com.NUWC_ETJ.displays;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.NUWC_ETJ.models.AwardsModel;

import org.w3c.dom.Text;

public class NUWC_awards extends AppCompatActivity {

    private Bundle myBundle = new Bundle();
    private AwardsModel myAwardsModel;
    private Button Awards_Search;
    private EditText Awards_Search_Text;

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    public void init45() {
        SQLiteDatabase myDB;
        myDB = myOpenHelper.openDatabase();
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));
        String myID = getIntent().getExtras().getString("ID");
        //myDB = SQLiteDatabase.openDatabase("data/data/NUWC.db", null, SQLiteDatabase.OPEN_READONLY);
        myAwardsModel = new AwardsModel(myDB, myID);

        ScrollView myScrollView = (ScrollView) findViewById(R.id.AWARDS_SCROLLER_ID);
        Awards_Search = (Button) findViewById(R.id.AWARDS_SEARCH_BUTTON);
        Awards_Search_Text = (EditText) findViewById(R.id.AWARDS_SEARCH);
        myAwardsModel.print(myScrollView, this);

        Awards_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Awards_Search_Text.setText("");
                myAwardsModel.SearchPosition("");

            }
        });

        Awards_Search_Text.addTextChangedListener(new TextWatcher()
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
                myAwardsModel.SearchPosition(SearchString);
            }
        });



    }



   private void hideKeyboard(){
       try  {
           InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
       } catch (Exception e) {
           System.out.print(e.toString());
       }
   }

    @Override
    public void onBackPressed(){
        Intent myIntentHome = new Intent(this, NUWC_Admin.class);
        myIntentHome.putExtras(myBundle);
        startActivity(myIntentHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_awards);
        init45();
    }
}
