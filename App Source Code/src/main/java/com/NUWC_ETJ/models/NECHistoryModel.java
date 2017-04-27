package com.NUWC_ETJ.models;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class NECHistoryModel {
    private String[] UIC;
    private String[] ACTY_NM;
    private String[] TYPE_DUTY_CD;
    private String[] ASGN_DESIG_RCN;
    private String[] ASGN_DESIG_GRADE_RATE;
    private String[] RCVD_DT;
    private String[] TRF_DT;
    private String[] DNEC1;
    private String[] DNEC2;
    private int[] SEQ_NUM;
    private int numAssignments;
    private ExpandableTextView[] myTextViews;
    public NECHistoryModel(SQLiteDatabase Access, String DOD_EDI_PI) {

        String QueryTable = "ENL_ASGN_HIST";
        //Get all columns from the table, will deal with NULL values on print
        String[] QueryColumns = {"UIC", "ACTY_NM", "TYPE_DUTY_CD", "ASGN_DESIG_RCN",
                                    "ASGN_DESIG_GRADE_RATE", "RCVD_DT", "TRF_DT", "DNEC1",
                                    "DNEC2", "SEQ_NUM"};
        //Query for a specific user's training
        String QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";
        //Return the strings in chronological order
        String orderByColumn = "SEQ_NUM";

        //Query the database
        Cursor myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, orderByColumn);

        //Get the number of classes the person has completed, use this to set the array sizes above
        numAssignments = myCursor.getCount();
        //Set the sizes of the array based on the number of classes that person has taken
        UIC = new String[numAssignments];
        ACTY_NM = new String[numAssignments];
        TYPE_DUTY_CD = new String[numAssignments];
        ASGN_DESIG_RCN = new String[numAssignments];
        ASGN_DESIG_GRADE_RATE = new String[numAssignments];
        RCVD_DT = new String[numAssignments];
        TRF_DT = new String[numAssignments];
        DNEC1 = new String[numAssignments];
        DNEC2 = new String[numAssignments];
        SEQ_NUM = new int[numAssignments];

        //Go to the first result in the table
        myCursor.moveToFirst();
        //Loop through all the classes returned and store them into the arrays
        for(int i = 0; i < numAssignments; i++) {
            UIC[i] = myCursor.getString(0);
            ACTY_NM[i] = myCursor.getString(1);
            TYPE_DUTY_CD[i] = myCursor.getString(2);
            ASGN_DESIG_RCN[i] = myCursor.getString(3);
            ASGN_DESIG_GRADE_RATE[i] = myCursor.getString(4);
            RCVD_DT[i] = myCursor.getString(5);
            TRF_DT[i] = myCursor.getString(6);
            DNEC1[i] = myCursor.getString(7);
            DNEC2[i] = myCursor.getString(8);
            SEQ_NUM[i] = myCursor.getInt(9);
            myCursor.moveToNext();
        }
        myCursor.close();
    }

    //Method to append all the information together into a string to print to the display
    public String createDisplayString(ScrollView myScrollView, Context myContext){
        String printBuffer = "";
        String Title = "";
        myTextViews = new ExpandableTextView[numAssignments];
        LinearLayout ll = new LinearLayout(myContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        for(int i = 0; i < numAssignments; i++) {
            //pass the expandable text view the number of how many lines each entry needs to print
            myTextViews[i] = new ExpandableTextView(myContext);
            myTextViews[i].setPadding(0,0,0,20);
            printBuffer = "";
            Title = "";
            if(!UIC[i].equals("null")) {
                printBuffer += "UIC: " + UIC[i] + "\n";
            }
            if(!ACTY_NM[i].equals("null")) {
                Title += ACTY_NM[i] + "\n";
            }
            if(!TYPE_DUTY_CD[i].equals("null")) {
                printBuffer += "TYPE_DUTY_CD: " + TYPE_DUTY_CD[i] + "\n";
            }
            if(!ASGN_DESIG_RCN[i].equals("null")) {
                printBuffer += "ASGN_DESIG_RCN: " + ASGN_DESIG_RCN[i] + "\n";
            }
            if(!ASGN_DESIG_GRADE_RATE[i].equals("null")) {
                printBuffer += "ASGN_DESIG_GRADE_RATE: " + ASGN_DESIG_GRADE_RATE[i] + "\n";
            }
            if(!RCVD_DT[i].equals("null")) {
                printBuffer += "RCVD_DT: " + RCVD_DT[i] + "\n";
            }
            if(!TRF_DT[i].equals("null")) {
                printBuffer += "TRF_DT: " + TRF_DT[i] + "\n";
            }
            if(!DNEC1[i].equals("null")) {
                printBuffer += "DNEC1: " + DNEC1[i] + "\n";

            }
            if(!DNEC2[i].equals("null")) {
                printBuffer += "DNEC2: " + DNEC2[i] + "\n";
            }
            printBuffer += "SEQ_NUM: " + SEQ_NUM[i] + "\n";
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
        return printBuffer;
    }

    public void SearchPosition(String SearchString)
    {
        String SearchBuffer;
        SearchString = SearchString.toUpperCase();
        for (int i = 0; i < numAssignments; i++)
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
