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

public class QualCertModel {

    private int[] QualificationIDs; //ID numbers of the qualifications
    private String[] Qualifications; //name of the qualifications
    private String[] AssignedDates; //Dates of when the qualifications were assigned
    private String[] ExpirationDates; //Dates of when the qualifications will expire
    private String[] QualificationTypes; //Types of the qualifications
    private String[] QualificationSources; //Sources of the qualifications
    private int numOfQualifications; //holds the number of qualifications assigned to the user
    private ExpandableTextView[] myTextViews;


    public QualCertModel(SQLiteDatabase Access, String DOD_EDI_PI) {

        String[] QueryColumns = new String[6];
        String QuerySelection = "";
        String orderByColumn = "QUAL_DT";


        Cursor myCursor;


        //*************************************************************

        //Construct the query to retrieve codes for the language
        QueryColumns[0] = "QUAL_ID";
        QueryColumns[1] = "QUAL_TITLE";
        QueryColumns[2] = "QUAL_DT";
        QueryColumns[3] = "EXPIR_DT";
        QueryColumns[4] = "QUAL_TYPE";
        QueryColumns[5] = "RCRD_SRC";

        String QueryTable = "PERS_QUAL";
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

        numOfQualifications = myCursor.getCount(); //set number of Qualifications

        //initialize array sizes based off of number of Qualifications


        QualificationIDs = new int[numOfQualifications];
        Qualifications = new String[numOfQualifications];
        AssignedDates = new String[numOfQualifications];
        ExpirationDates = new String[numOfQualifications];
        QualificationTypes = new String[numOfQualifications];
        QualificationSources = new String[numOfQualifications];

        myCursor.moveToFirst(); //move cursor to first row in result set

        //loop through result rows and extract the codes and months
        for (int i = 0; i < numOfQualifications; i++)
        {
            QualificationIDs[i] = myCursor.getInt(0);
            Qualifications[i] = myCursor.getString(1);
            AssignedDates[i] = myCursor.getString(2);
            ExpirationDates[i] = myCursor.getString(3);
            QualificationTypes[i] = myCursor.getString(4);
            QualificationSources[i] = myCursor.getString(5);
            myCursor.moveToNext(); // move cursor to next row in result set
        }
        myCursor.close();



    }

    public void print(ScrollView myScrollView, Context myContext)
    {

        //loop through skills and append them to the print buffer to be returned
        String PrintBuffer = "";
        String Title = "";
        myTextViews = new ExpandableTextView[numOfQualifications];
        LinearLayout ll = new LinearLayout(myContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < numOfQualifications; i++) {
            myTextViews[i] = new ExpandableTextView(myContext);
            myTextViews[i].setPadding(0, 0, 0, 20);
            PrintBuffer = "";
            Title = "";
            Title += Qualifications[i] + " [" + QualificationIDs[i] + "]\n";
            if (!AssignedDates.equals("null")) {
                PrintBuffer += "Assigned : " + AssignedDates[i] + "\n";
            }
            if (!ExpirationDates[i].equals("null")) {
                PrintBuffer += "Expires : " + ExpirationDates[i] + "\n";
            }
            if (!QualificationTypes[i].equals("null")) {
                PrintBuffer += "Type : " + QualificationTypes[i] + "\n";
            }
            if (!QualificationSources[i].equals("null")){
                PrintBuffer += "Source : " + QualificationSources[i] + "\n";
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
        for (int i = 0; i < numOfQualifications; i++)
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

