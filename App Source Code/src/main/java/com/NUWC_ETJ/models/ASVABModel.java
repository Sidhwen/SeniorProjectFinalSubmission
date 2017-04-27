package com.NUWC_ETJ.models;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;


//****************
//Kevin MacAllister
//March 4, 2017
//Senior Project
//****************

public class ASVABModel {

    private String[] TestDates; //Dates of the tests
    private String[] TestIDs; //IDs of the tests
    //all of the scores from the tests (some will be null)
    private String[][] Scores; //

    private int numOfTests; //holds the number of qualifications assigned to the user


    public ASVABModel(SQLiteDatabase Access, String DOD_EDI_PI) {

        String[] QueryColumns = new String[16];
        String QuerySelection = "";
        String orderByColumn = "TEST_DT";

        Cursor myCursor;

        //*************************************************************
        //Construct the query to retrieve codes for the language
        QueryColumns[0] = "TEST_DT";
        QueryColumns[1] = "TEST_ID";
        QueryColumns[2] = "AFQT_SCORE";
        QueryColumns[3] = "GS_SCORE";
        QueryColumns[4] = "AR_SCORE";
        QueryColumns[5] = "WK_SCORE";
        QueryColumns[6] = "PC_SCORE";
        QueryColumns[7] = "NO_SCORE";
        QueryColumns[8] = "CS_SCORE";
        QueryColumns[9] = "AS_SCORE";
        QueryColumns[10] = "MK_SCORE";
        QueryColumns[11] = "MC_SCORE";
        QueryColumns[12] = "EI_SCORE";
        QueryColumns[13] = "VE_SCORE";
        QueryColumns[14] = "AI_SCORE";
        QueryColumns[15] = "NFQT_SCORE";


        String QueryTable = "ASVAB";
        QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";

        //query function arguments described below
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

        //*********************
        //call the SQLiteOpenHelper with the query for Cursor/ResultSet
        //*********************

        myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, orderByColumn);

        //*********************
        //extract info held from the ResultSet, dynamically set array size based off of number of results returned
        //*********************

        numOfTests = myCursor.getCount(); //set number of Tests

        //initialize array sizes based off of number of Qualifications


        TestDates = new String[numOfTests];
        TestIDs = new String[numOfTests];
        Scores = new String[numOfTests][14];


        myCursor.moveToFirst(); //move cursor to first row in result set

        int ScoreBuffer;
        //loop through result rows and extract the codes and months
        for (int i = 0; i < numOfTests; i++) {
            TestDates[i] = myCursor.getString(0);
            TestIDs[i] = myCursor.getString(1);
            for (int j = 0; j < 14; j++)
            {
                ScoreBuffer = myCursor.getInt(2 + j);
                if (ScoreBuffer == 0)
                {
                    Scores[i][j] = "null";
                }
                else {
                    Scores[i][j] = String.valueOf(ScoreBuffer);
                }
            }
            myCursor.moveToNext(); // move cursor to next row in result set
        }
        myCursor.close();
    }

    public String print()
    {

        //loop through skills and append them to the print buffer to be returned
        String PrintBuffer = "";

        for (int i = 0; i < numOfTests; i++)
        {
            if(!TestDates[i].equals("null")) {
                PrintBuffer += "Test Date : " + TestDates[i] + "\n";
            }
            if(!TestIDs[i].equals("null")) {
                PrintBuffer += "Test ID : " + TestIDs[i] + "\n";
            }
            if(!Scores[i][0].equals("null")) {
                PrintBuffer += "AFQT Score : " + Scores[i][0] + "\n";
            }
            if(!Scores[i][1].equals("null")) {
                PrintBuffer += "GS Score : " + Scores[i][1] + "\n";
            }
            if(!Scores[i][2].equals("null")) {
                PrintBuffer += "AR Score : " + Scores[i][2] + "\n";
            }
            if(!Scores[i][3].equals("null")) {
                PrintBuffer += "WK Score : " + Scores[i][3] + "\n";
            }
             if(!Scores[i][4].equals("null")) {
                 PrintBuffer += "PC Score : " + Scores[i][4] + "\n";
             }
             if(!Scores[i][5].equals("null")) {
                 PrintBuffer += "NO Score : " + Scores[i][5] + "\n";
             }
             if(!Scores[i][6].equals("null")) {
                 PrintBuffer += "CS Score : " + Scores[i][6] + "\n";
             }
             if(!Scores[i][7].equals("null")) {
                 PrintBuffer += "AS Score : " + Scores[i][7] + "\n";
             }
             if(!Scores[i][8].equals("null")){
                  PrintBuffer += "MK Score : " + Scores[i][8] + "\n";
             }
             if(!Scores[i][9].equals("null")) {
                 PrintBuffer += "MC Score : " + Scores[i][9] + "\n";
             }
             if(!Scores[i][10].equals("null")) {
                 PrintBuffer += "EI Score : " + Scores[i][10] + "\n";
             }
             if(!Scores[i][11].equals("null")) {
                 PrintBuffer += "VE Score : " + Scores[i][11] + "\n";
             }
             if(!Scores[i][12].equals("null")) {
                 PrintBuffer += "AI Score : " + Scores[i][12] + "\n";
             }
             if(!Scores[i][13].equals("null")) {
                 PrintBuffer += "NFQT Score : " + Scores[i][13] + "\n";
             }
             PrintBuffer += "\n";

        }
        return PrintBuffer;
    }





}

