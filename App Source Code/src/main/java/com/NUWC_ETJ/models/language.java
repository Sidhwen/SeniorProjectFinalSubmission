package com.NUWC_ETJ.models;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;


//****************
//Kevin MacAllister
//January 20, 2017
//Senior Project
//****************

    public class language {

        private String LangName; //name of the language
        private String LangDesc; //description of language, normally also the name
        private String ListenProficiencyDesc; //description of their listening proficiency in the language
        private String ReadProficiencyDesc; //description of their reading proficiency in the language
        private String WriteProficiencyDesc; //description of their writing proficiency in the language
        private String SpeakProficiencyDesc; //description of their speaking proficiency in the language
        private String EvaluationDesc; //description of how they were evaluated in this language
        private String SourceDesc; //description of where they learned this language
        private String TestDate = ""; //The date on which they were tested (if applies)

        public language(SQLiteDatabase Access, String DOD_EDI_PI, int LangNum) {

            String QueryTable = "";
            String[] QueryColumns = new String[8];
            String QuerySelection = "";
            String LangNumString = Integer.toString(LangNum); //gives us the number of the language being queried in string form


            Cursor myCursor;

            //Codes will be placed in these buffers for later queries ****
            String LangCode;

            int ProficiencyListenCode;
            int ProficiencyReadCode;
            int ProficiencySpeakCode;
            int ProficiencyWriteCode;

            int EvaluationCode;
            int SourceCode;
            //*************************************************************

            //Construct the query to retrieve codes for the language
            QueryColumns[0] = "LANG" + LangNumString + "_CD";
            QueryColumns[1] = "LANG" + LangNumString + "_PROFCY_LISTEN_CD";
            QueryColumns[2] = "LANG" + LangNumString + "_PROFCY_READ_CD";
            QueryColumns[3] = "LANG" + LangNumString + "_PROFCY_SPEAK_CD";
            QueryColumns[4] = "LANG" + LangNumString + "_PROFCY_WRITE_CD";
            QueryColumns[5] = "LANG" + LangNumString + "_EVAL_CD";
            QueryColumns[6] = "LANG" + LangNumString + "_SRC_CD";
            QueryColumns[7] = "LANG" + LangNumString + "_QUAL_TEST_DT";
            QueryTable += "LANG_HORIZ";
            QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";


            //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

            //*********************
            //call the SQLiteOpenHelper with the query for Cursor/ResultSet
            //*********************

            myCursor = Access.query(QueryTable, QueryColumns, QuerySelection, null, null, null, null);

            //*********************
            //extract various codes and test date from the ResultSet
            //*********************
            myCursor.moveToFirst();
            LangCode = myCursor.getString(0);
            LangCode = "'" + LangCode + "'";

            ProficiencyListenCode = myCursor.getInt(1);
            ProficiencyReadCode = myCursor.getInt(2);
            ProficiencySpeakCode = myCursor.getInt(3);
            ProficiencyWriteCode = myCursor.getInt(4);

            EvaluationCode = myCursor.getInt(5);
            SourceCode = myCursor.getInt(6);

            String[] selectionBuffer = new String[1];


            selectionBuffer[0] = "LANG_DESCR";
            myCursor = Access.query("LK_LANG", selectionBuffer,"LANG_CD = " + LangCode, null, null, null, null);
            myCursor.moveToFirst();

            LangName = myCursor.getString(0);


            selectionBuffer[0] = "LANG_PROFCY_DESCR";
            myCursor = Access.query("LK_LANG_PROFCY", selectionBuffer,"LANG_PROFCY_CD = " + ProficiencyListenCode, null, null, null, null);
            myCursor.moveToFirst();

            ListenProficiencyDesc = myCursor.getString(0);

            selectionBuffer[0] = "LANG_PROFCY_DESCR";
            myCursor = Access.query("LK_LANG_PROFCY", selectionBuffer,"LANG_PROFCY_CD = " + ProficiencyReadCode, null, null, null, null);
            myCursor.moveToFirst();
            ReadProficiencyDesc = myCursor.getString(0);

            selectionBuffer[0] = "LANG_PROFCY_DESCR";
            myCursor = Access.query("LK_LANG_PROFCY", selectionBuffer,"LANG_PROFCY_CD = " + ProficiencySpeakCode, null, null, null, null);
            myCursor.moveToFirst();
            SpeakProficiencyDesc = myCursor.getString(0);

            selectionBuffer[0] = "LANG_PROFCY_DESCR";
            myCursor = Access.query("LK_LANG_PROFCY", selectionBuffer,"LANG_PROFCY_CD = " + ProficiencyWriteCode, null, null, null, null);
            myCursor.moveToFirst();
            WriteProficiencyDesc = myCursor.getString(0);

            selectionBuffer[0] = "LANG_EVAL_DESCR";
            myCursor = Access.query("LK_LANG_EVAL", selectionBuffer,"LANG_EVAL_CD = " + EvaluationCode, null, null, null, null);
            myCursor.moveToFirst();
            EvaluationDesc = myCursor.getString(0);

            selectionBuffer[0] = "LANG_SRC_DESCR";
            myCursor = Access.query("LK_LANG_SRC", selectionBuffer,"LANG_SRC_CD = " + SourceCode, null, null, null, null);
            myCursor.moveToFirst();
            SourceDesc = myCursor.getString(0);




            myCursor.close();
        }

        public String print(ExpandableTextView myTextView)
        {
            String PrintBuffer = "";
            String Title = LangName + "\n";
            if(!ListenProficiencyDesc.equals("null")) {
                PrintBuffer += "Listening Proficiency : " + ListenProficiencyDesc + "\n";
            }
            if(!ReadProficiencyDesc.equals("null")) {
                PrintBuffer += "Reading Proficiency : " + ReadProficiencyDesc + "\n";
            }
            if(!SpeakProficiencyDesc.equals("null")) {
                PrintBuffer += "Speaking Proficiency : " + SpeakProficiencyDesc + "\n";
            }
            if(!WriteProficiencyDesc.equals("null")) {
                PrintBuffer += "Writing Proficiency : " + WriteProficiencyDesc + "\n";
            }
            if(!EvaluationDesc.equals("null")) {
                PrintBuffer += "Evaluated by : " + EvaluationDesc + "\n";
            }
            if(!SourceDesc.equals("null")) {
                PrintBuffer += "Learned from : " + SourceDesc;
            }
                PrintBuffer += "\n";
            myTextView.setText(Title);
            myTextView.myChild.setText(PrintBuffer);
            return PrintBuffer;
        }




    }

