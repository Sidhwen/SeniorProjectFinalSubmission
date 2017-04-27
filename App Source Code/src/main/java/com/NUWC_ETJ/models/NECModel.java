package com.NUWC_ETJ.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.LinearLayout;
import android.widget.ScrollView;


//****************
//Kevin MacAllister
//January 20, 2017
//Senior Project
//****************

public class NECModel {

    private String[] Skills; //name of the skills
    private int[] SkillCodes; //Codes of the skills
    private String[] DateAwarded; //when these skills have been assigned
    private int numOfSkills; //holds the number of NEC skills assigned to the user
    private ExpandableTextView[] myTextViews;


    public NECModel(SQLiteDatabase Access, String DOD_EDI_PI) {

        String QueryTable = "SKILL";
        String[] QueryColumns = new String[2];
        String QuerySelection =  "DOD_EDI_PI = '" + DOD_EDI_PI + "' AND SKILL_TYPE = 'NEC'";
        String orderByColumn = "OFF_SKILL_MON_HELD";


        Cursor myCursor;


        //*************************************************************

        //Construct the query to retrieve codes for the language
        QueryColumns[0] = "SKILL";
        QueryColumns[1] = "ENL_SKILL_DT";


        //query function arguments described below
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

        //*********************
        //call the SQLiteOpenHelper with the query for Cursor/ResultSet
        //*********************

        myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, orderByColumn);

        //*********************
        //extract codes and months held from the ResultSet, dynamically set array size based off of number of results returned
        //*********************

        numOfSkills = myCursor.getCount(); //set number of skills

        //initialize array sizes based off of number of skills
        SkillCodes = new int[numOfSkills];
        Skills = new String[numOfSkills];
        DateAwarded = new String[numOfSkills];

        myCursor.moveToFirst(); //move cursor to first row in result set

        //loop through result rows and extract the codes and months
        for (int i = 0; i < numOfSkills; i++)
        {
            SkillCodes[i] = myCursor.getInt(0);
            DateAwarded[i] = myCursor.getString(1);
            myCursor.moveToNext(); // move cursor to next row in result set
        }



        String[] selectionBuffer = new String[1];
        selectionBuffer[0] = "SKILL_NM";
        QueryTable = "SKILL_TO_TRAIN";


        //loop through skills, query the SKILL_TO_TRAIN table and obtain the SKILL_NM (skill name) using our SkillCodes
        for (int i = 0; i < numOfSkills; i++)
        {
            myCursor = Access.query(QueryTable, selectionBuffer,"SKILL = " + SkillCodes[i], null, null, null, null);
            myCursor.moveToFirst();
            Skills[i] = myCursor.getString(0);
        }
        myCursor.close();


    }

    public void print(ScrollView myScrollView, Context myContext)
    {

        //loop through skills and append them to the print buffer to be returned
        String PrintBuffer = "";
        String Title = "";
        myTextViews = new ExpandableTextView[numOfSkills];
        LinearLayout ll = new LinearLayout(myContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < numOfSkills; i++)
        {
            myTextViews[i] = new ExpandableTextView(myContext);
            myTextViews[i].setPadding(0,0,0,20);
            PrintBuffer = "";
            Title = "";
            if(!Skills[i].equals("null")) {
                Title += Skills[i];
            }
            PrintBuffer = "Skill Code : " + "[" + SkillCodes[i] + "]\n";
            if(!DateAwarded[i].equals("null")) {
                PrintBuffer += "Date Awarded : " + DateAwarded[i] + "\n";
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

    public int getSize()
    {
        return numOfSkills;
    }

    public void SearchPosition(String SearchString)
    {
        String SearchBuffer;
        SearchString = SearchString.toUpperCase();
        for (int i = 0; i < numOfSkills; i++)
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

