package com.NUWC_ETJ.models;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class NOBCHistoryModel {
    private String[] ACTY_TITLE;
    private String[] UIC;
    private String[] RDA;
    private String[] RDD;
    private String[] NOBC1;
    private String[] NOBC2;
    private String[] NOBC3;
    private int numAssignments;
    ExpandableTextView[] myTextViews;
    public NOBCHistoryModel(SQLiteDatabase Access, String DOD_EDI_PI) {

        String QueryTable = "OFF_ASGN_HIST";
        //Get all columns from the table, will deal with NULL values on print
        String[] QueryColumns = {"ACTY_TITLE", "UIC", "RDA", "RDD",
                                    "NOBC1", "NOBC2", "NOBC3"};
        //Query for a specific user's training
        String QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";
        //Return the strings in chronological order
        String orderByColumn = "RDA";

        //Query the database
        Cursor myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, orderByColumn);

        //Get the number of classes the person has completed, use this to set the array sizes above
        numAssignments = myCursor.getCount();
        //Set the sizes of the array based on the number of classes that person has taken
        ACTY_TITLE = new String[numAssignments];
        UIC = new String[numAssignments];
        RDA = new String[numAssignments];
        RDD = new String[numAssignments];
        NOBC1 = new String[numAssignments];
        NOBC2 = new String[numAssignments];
        NOBC3 = new String[numAssignments];

        //Go to the first result in the table
        myCursor.moveToFirst();
        //Loop through all the classes returned and store them into the arrays
        for(int i = 0; i < numAssignments; i++) {

            ACTY_TITLE[i] = myCursor.getString(0);
            UIC[i] = myCursor.getString(1);
            RDA[i] = myCursor.getString(2);
            RDD[i] = myCursor.getString(3);
            NOBC1[i] = myCursor.getString(4);
            NOBC2[i] = myCursor.getString(5);
            NOBC3[i] = myCursor.getString(6);
            myCursor.moveToNext();
        }
        myCursor.close();
    }

    //Method to append all the information together into a string to print to the display
    public void createDisplayString(ScrollView myScrollView, Context myContext){
        String printBuffer = "";
        String Title = "";
        myTextViews = new ExpandableTextView[numAssignments];
        LinearLayout ll = new LinearLayout(myContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        for(int i = 0; i < numAssignments; i++) {
            myTextViews[i] = new ExpandableTextView(myContext);
            myTextViews[i].setPadding(0,0,0,20);
            printBuffer = "";
            Title = "";
            if(!ACTY_TITLE[i].equals("null")) {
                Title += ACTY_TITLE[i];
            }
            if(!UIC[i].equals("null")) {
                printBuffer += "UIC: " + UIC[i] + "\n";
            }
            if(!RDA[i].equals("null")) {
                printBuffer += "RDA: " + RDA[i] + "\n";
            }
            if(!RDD[i].equals("null")) {
                printBuffer += "RDD: " + RDD[i] + "\n";
            }
            if(!NOBC1[i].equals("null")) {
                printBuffer += "NOBC1: " + NOBC1[i] + "\n";
            }
            if(!NOBC2[i].equals("null")) {
                printBuffer += "NOBC2: " + NOBC2[i] + "\n";
            }
            if(!NOBC3[i].equals("null")) {
                printBuffer += "NOBC3: " + NOBC3[i] + "\n";
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
