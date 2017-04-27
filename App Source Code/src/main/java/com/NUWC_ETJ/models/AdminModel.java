package com.NUWC_ETJ.models;


import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;


/**
 * Robert Huard
 * CIS498/99
 * Senior Design
 * 3/18/2017.
 */

public class AdminModel {


    //vars to be populated by cursor for output
    private String[] rate;
    private String[] dnec;
    private String[] dateRecievedOB;
    private String[] projRotDt;
    private String[] secCompDt;
    private String[] secClearCdElig;
    private String[] secClearCdAuth;
    private String[] secInvestTypeCd;
    private String[] secGrantDt;
    private int rowCnt = 0;

    public AdminModel(SQLiteDatabase Access, String DOD_EDI_PI) {



        //vars to construct query with
        String QueryTable = "MEMBER_NAV";
        String[] QueryColumns = new String[9];
        String QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";
        String orderByColumn = "RCVD_DT";

        Cursor myCursor;


        QueryColumns[0] = "DESIG_GRADE_RATE";
        QueryColumns[1] = "DNEC1";
        QueryColumns[2] = "RCVD_DT";
        QueryColumns[3] = "ONBD_PRD";
        QueryColumns[4] = "SCTY_INVEST_COMPL_DT";
        QueryColumns[5] = "SCTY_CLEAR_CD_ELIG";
        QueryColumns[6] = "SCTY_CLEAR_CD_AUTH";
        QueryColumns[7] = "SCTY_INVEST_TYPE_CD";
        QueryColumns[8] = "SCTY_GRANT_DT";

        //query function arguments described below
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

        //*********************
        //call the SQLiteOpenHelper with the query for Cursor/ResultSet
        //*********************

        myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, orderByColumn);

        //*********************
        //extract codes and dates held from the ResultSet, dynamically set array size based off of number of results returned
        //*********************

        rowCnt = myCursor.getCount(); //set number of rows

        //initialize arrays dynamically from results in cursor
        rate = new String[rowCnt];
        dnec = new String[rowCnt];
        dateRecievedOB = new String[rowCnt];
        projRotDt = new String[rowCnt];
        secCompDt = new String[rowCnt];
        secClearCdElig = new String[rowCnt];
        secClearCdAuth = new String[rowCnt];
        secInvestTypeCd = new String[rowCnt];
        secGrantDt = new String[rowCnt];

        myCursor.moveToFirst(); //move cursor to first row in result set

        //loop through result rows and extract the codes and dates
        for (int i = 0; i < rowCnt; i++)
        {
            rate[i] = myCursor.getString(0);
            dnec[i] = myCursor.getString(1);
            dateRecievedOB[i] = myCursor.getString(2);
            projRotDt[i] = myCursor.getString(3);
            secCompDt[i] = myCursor.getString(4);
            secClearCdElig[i] = myCursor.getString(5);
            secClearCdAuth[i] = myCursor.getString(6);
            secInvestTypeCd[i] = myCursor.getString(7);
            secGrantDt[i] = myCursor.getString(8);
            myCursor.moveToNext(); // move cursor to next row in result set
        }

        myCursor.close();
    }

    public String print()
    {

        //loop through skills and append them to the print buffer to be returned
        String PrintBuffer = "";
        for (int i = 0; i < rowCnt; i++) {
            if (!rate[i].equals("null")) {
                PrintBuffer += "Rate : " + rate[i] + "\n";
            }
            if (!dnec[i].equals("null")) {
                PrintBuffer += "DNEC : " + dnec[i] + "\n";
            }
            if (!dateRecievedOB[i].equals("null")) {
                PrintBuffer += "\nOnboard service period \nOB Service Rec: " + dateRecievedOB[i] + "\n" + "Rotation Date: " + projRotDt[i] + "\n";
            }
            if (!secCompDt[i].equals("null")) {
                PrintBuffer += "\nSecurity Clearance Completion Date \n" + secCompDt[i] + "\n";
            }
            if (!secCompDt[i].equals("null") && !secClearCdElig[i].equals("null") && !secClearCdAuth[i].equals("null") && !secInvestTypeCd[i].equals("null") && !secGrantDt.equals("null")){
                PrintBuffer += "\nSecurity Clearance Data \nCompletion Date: " + secCompDt[i] + "\n" + "Clearance Eligibility Code: " + secClearCdElig[i] + "\n" +
                        "Authorization Code: " + secClearCdAuth[i] + "\n" + "Investigation Type Code: " + secInvestTypeCd[i] + "\n" + "Grant Date: " + secGrantDt[i];
            }

            PrintBuffer += "\n";
        }
        return PrintBuffer;
    }

}
