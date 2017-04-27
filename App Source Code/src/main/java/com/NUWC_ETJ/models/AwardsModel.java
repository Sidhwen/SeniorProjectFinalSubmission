package com.NUWC_ETJ.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.LinearLayout;
import android.widget.ScrollView;


//****************
//Kevin MacAllister
//March 4, 2017
//Senior Project
//****************

public class AwardsModel {

    private String[] Awards; //name of the awards
    private String[] MeritStart; //beginning of merit periods
    private String[] MeritEnd; //end of merit periods
    private String[] AwardDate; //when the awards were approved
    private int numOfAwards; //holds the number of awards assigned to the user
    private ExpandableTextView[] myTextViews;


    public AwardsModel(SQLiteDatabase Access, String DOD_EDI_PI) {

        String QueryTable = "AWD";
        String[] QueryColumns = new String[4];
        String QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";
        String orderByColumn = "APPR_AWD_DT";


        Cursor myCursor;

        String[] AwardCodes; //Holds the codes of the awards for later query

        //*************************************************************

        //Construct the query to retrieve codes for the language
        QueryColumns[0] = "APPR_AWD_CD";
        QueryColumns[1] = "MERIT_START_DT";
        QueryColumns[2] = "MERIT_STOP_DT";
        QueryColumns[3] = "APPR_AWD_DT";


        //query function arguments described below
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

        //*********************
        //call the SQLiteOpenHelper with the query for Cursor/ResultSet
        //*********************

        myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, orderByColumn);

        //*********************
        //extract codes and dates held from the ResultSet, dynamically set array size based off of number of results returned
        //*********************

        numOfAwards = myCursor.getCount(); //set number of skills

        //initialize array sizes based off of number of skills
        AwardCodes = new String[numOfAwards];
        MeritStart = new String[numOfAwards];
        MeritEnd = new String[numOfAwards];
        AwardDate = new String[numOfAwards];
        Awards = new String[numOfAwards];

        myCursor.moveToFirst(); //move cursor to first row in result set

        //loop through result rows and extract the codes and dates
        for (int i = 0; i < numOfAwards; i++)
        {
            AwardCodes[i] = myCursor.getString(0);
            MeritStart[i] = myCursor.getString(1);
            MeritEnd[i] = myCursor.getString(2);
            AwardDate[i] = myCursor.getString(3);
            myCursor.moveToNext(); // move cursor to next row in result set
        }



        String[] selectionBuffer = new String[1];
        selectionBuffer[0] = "APPR_AWD_DESCR";
        QueryTable = "LK_APPR_AWD";


        //loop through skills, query the LK_APPR_AWD table and obtain the APPR_AWD_DESCR (award name) using our AwardCodes
        for (int i = 0; i < numOfAwards; i++)
        {
            myCursor = Access.query(QueryTable, selectionBuffer,"APPR_AWD_CD = '" + AwardCodes[i] +"'", null, null, null, null);
            myCursor.moveToFirst();
            Awards[i] = myCursor.getString(0);
        }
        myCursor.close();


    }

    public void print(ScrollView myScrollView, Context myContext)
    {

        //loop through skills and append them to the print buffer to be returned
        String PrintBuffer = "";
        String Title = "";
        myTextViews = new ExpandableTextView[numOfAwards];
        LinearLayout ll = new LinearLayout(myContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < numOfAwards; i++)
        {
            myTextViews[i] = new ExpandableTextView(myContext);
            myTextViews[i].setPadding(0,0,0,20);
            PrintBuffer = "";
            Title = "";
            if(!Awards[i].equals("null")) {
                Title += Awards[i] + "\n";
            }
            if(!MeritStart[i].equals("null") && !MeritEnd[i].equals("null")) {
                PrintBuffer += "Meritorious service period \n ------------------- \n" + MeritStart[i] + "\n" + MeritEnd[i] + "\n";
            }
            if(!AwardDate[i].equals("null")) {
                PrintBuffer += "Date Approved : " + AwardDate[i] + "\n";
            }
            //PrintBuffer += "\n";

            myTextViews[i].setText(Title);
            myTextViews[i].myChild.setText(PrintBuffer);
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
        for (int i = 0; i < numOfAwards; i++)
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

