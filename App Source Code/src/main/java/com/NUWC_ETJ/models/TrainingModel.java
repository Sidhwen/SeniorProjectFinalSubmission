package com.NUWC_ETJ.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 *Jacob Strojny
 * March , 2017
 * Senior Design Project: NUWC Mobile Electronic Training Jacket
 */

public class TrainingModel {
    //All of the field names in the ALL_CSE_COMPL table(All Classes Completed?)
    private String[] CIN;
    private String[] CDP;
    private String[] CLS_NUM;
    private String[] LOC_NM;
    private String[] CSE_LONG_TITLE;
    private String[] TRNG_DELIV_METH_CD;
    private String[] RCRD_SRC;
    private  String[] COMPL_DT;
    private int[] SCORE;
    private String[] RCRD_IND;
    private String[] RETIRE_POINT;
    private String[] CSE_LEN;
    private String[] APPROX_TRNG_EQUIV_IND;
    private String[] NA_IND;
    private String[] SYS_LOAD_DT;
    private int trainingAmount;
    private ExpandableTextView[] myTextViews;

    public TrainingModel(SQLiteDatabase Access, String DOD_EDI_PI) {

        String QueryTable = "ALL_CSE_COMPL";
        //Get all columns from the table, will deal with NULL values on print
        String[] QueryColumns = {"CIN", "CDP", "CLS_NUM", "LOC_NM", "CSE_LONG_TITLE",
                                    "TRNG_DELIV_METH_CD", "RCRD_SRC", "COMPL_DT", "SCORE",
                                    "RCRD_IND", "RETIRE_POINT", "CSE_LEN", "APPROX_TRNG_EQUIV_IND",
                                    "NA_IND", "SYS_LOAD_DT"};
        //Query for a specific user's training
        String QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";
        //Return the strings in chronological order
        String orderByColumn = "COMPL_DT";

        //Query the database
        Cursor myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, orderByColumn);

        //Get the number of classes the person has completed, use this to set the array sizes above
        trainingAmount = myCursor.getCount();
        //Set the sizes of the array based on the number of classes that person has taken
        CIN = new String[trainingAmount];
        CDP = new String[trainingAmount];
        CLS_NUM = new String[trainingAmount];
        LOC_NM = new String[trainingAmount];
        CSE_LONG_TITLE = new String[trainingAmount];
        TRNG_DELIV_METH_CD = new String[trainingAmount];
        RCRD_SRC = new String[trainingAmount];
        COMPL_DT = new String[trainingAmount];
        SCORE = new int[trainingAmount];
        RCRD_IND = new String[trainingAmount];
        RETIRE_POINT = new String[trainingAmount];
        CSE_LEN = new String[trainingAmount];
        APPROX_TRNG_EQUIV_IND = new String[trainingAmount];
        NA_IND = new String[trainingAmount];
        SYS_LOAD_DT = new String[trainingAmount];

        //Go to the first result in the table
        myCursor.moveToFirst();
        //Loop through all the classes returned and store them into the arrays
        for(int i = 0; i < trainingAmount; i++) {
            CIN[i] = myCursor.getString(0);
            CDP[i] = myCursor.getString(1);
            CLS_NUM[i] = myCursor.getString(2);
            LOC_NM[i] = myCursor.getString(3);
            CSE_LONG_TITLE[i] = myCursor.getString(4);
            TRNG_DELIV_METH_CD[i] = myCursor.getString(5);
            RCRD_SRC[i] = myCursor.getString(6);
            COMPL_DT[i] = myCursor.getString(7);
            SCORE[i] = myCursor.getInt(8);
            RCRD_IND[i] = myCursor.getString(9);
            RETIRE_POINT[i] = myCursor.getString(10);
            CSE_LEN[i] = myCursor.getString(11);
            APPROX_TRNG_EQUIV_IND[i] = myCursor.getString(12);
            NA_IND[i] = myCursor.getString(13);
            SYS_LOAD_DT[i] = myCursor.getString(14);
            myCursor.moveToNext();
        }
        myCursor.close();
    }

    //Method to append all the information together into a string to print to the display
    public void createDisplayString(ScrollView myScrollView, Context myContext){
        String printBuffer = "";
        String Title = "";
        myTextViews = new ExpandableTextView[trainingAmount];
        LinearLayout ll = new LinearLayout(myContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        for(int i = 0; i < trainingAmount; i++) {
            myTextViews[i] = new ExpandableTextView(myContext);
            myTextViews[i].setPadding(0,0,0,20);
            printBuffer = "";
            Title = "";
            if(!CIN[i].equals("null")) {
                printBuffer += "CIN: " + CIN[i] + "\n";
            }
            if(!CDP[i].equals("null")) {
                printBuffer += "CDP: " + CDP[i] + "\n";
            }
            if(!CLS_NUM[i].equals("null")) {
                printBuffer += "Class: " + CLS_NUM[i] + "\n";
            }
            if(!LOC_NM[i].equals("null")) {
                printBuffer += "Location Name: " + LOC_NM[i] + "\n";
            }
            if(!CSE_LONG_TITLE[i].equals("null")) {
                Title += CSE_LONG_TITLE[i];
            }
            if(!TRNG_DELIV_METH_CD[i].equals("null")) {
                printBuffer += "Training Method: " + TRNG_DELIV_METH_CD[i] + "\n";
            }
            if(!RCRD_SRC[i].equals("null")) {
                printBuffer += "Record Source: " + RCRD_SRC[i] + "\n";
            }
            if(!COMPL_DT[i].equals("null")) {
                printBuffer += "Completion Date: " + COMPL_DT[i] + "\n";

            }
            printBuffer += "Score: " + SCORE[i] + "\n";
            if(!RCRD_IND[i].equals("null")) {
                printBuffer += "RCRD_IND: " + RCRD_IND[i] + "\n";
            }
            if(!RETIRE_POINT[i].equals("null")){
                printBuffer += "RETIRE_POINT: " + RETIRE_POINT[i] + "\n";
            }
            if(!CSE_LEN[i].equals("null")) {
                printBuffer += "Course Length: " + CSE_LEN[i] + "\n";
            }
            if(!APPROX_TRNG_EQUIV_IND[i].equals("null")) {
                printBuffer += "Approximate Training Equivalent: " + APPROX_TRNG_EQUIV_IND[i] + "\n";
            }
            if(!NA_IND[i].equals("null")) {
                printBuffer += "NA_IND: " + NA_IND[i] + "\n";
            }
            if(!SYS_LOAD_DT[i].equals("null")) {
                printBuffer += "SYS_LOAD_DT: " + SYS_LOAD_DT[i] + "\n";
            }
            //printBuffer += "\n";
            myTextViews[i].setText(Title);
            myTextViews[i].myChild.setText(printBuffer);
            if(i%2 == 1) {
                myTextViews[i].setBackgroundColor(0xFFE9EBF6);
                myTextViews[i].myChild.setBackgroundColor(0xFFE9EBF6);
            }
            if(!Title.equals("")) {
                ll.addView(myTextViews[i].Line);
                ll.addView(myTextViews[i]);
                ll.addView(myTextViews[i].myChild);
            }

        }
        ll.addView(myTextViews[0].EndLine);
        myScrollView.addView(ll);

        return;
    }

    public void SearchPosition(String SearchString)
    {
        String SearchBuffer;
        SearchString = SearchString.toUpperCase();
        for (int i = 0; i < trainingAmount; i++)
        {
            SearchBuffer = (String) myTextViews[i].getText();
            SearchBuffer = SearchBuffer.toUpperCase();
            if (SearchBuffer.contains(SearchString))
            {
                myTextViews[i].HideReveal(true);
            }
            else
            {
                myTextViews[i].HideReveal(false);
            }
        }
    }

}
