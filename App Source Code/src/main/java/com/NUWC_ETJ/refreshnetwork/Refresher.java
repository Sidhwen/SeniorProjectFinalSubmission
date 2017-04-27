package com.NUWC_ETJ.refreshnetwork;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/***********************
 ****Kevin MacAllister
 ****METJ Project********
 ****Refresh Parser
 ***********************/

public class Refresher
{
    private String TableNames[] = {"MEMBER_NAV", "ASVAB", "AWD", "LANG_HORIZ", "ENL_ASGN_HIST", "SKILL", "OFF_ASGN_HIST", "SKILL", "PERS_QUAL", "ALL_CSE_COMPL"};
    private String Input;
    private SQLiteDatabase Access;
    private int numOfTables = 10;


    public Refresher(SQLiteDatabase myAccess, String RefreshInput)
    {
        Input = RefreshInput;
        Access = myAccess;
    }

    public void flush()
    {
        for (int i = 0; i < numOfTables; i++) {
            Access.execSQL("DELETE FROM " + TableNames[i]);
        }
        Access.execSQL("VACUUM");
		/*code to flush out tables that are to be entered*/
    }
    public String enter (TextView SignInText) throws IOException
    {
        BufferedReader reader = new BufferedReader(new StringReader(Input));



        //ugly but necessary array, holds all the fields for each of the tables for the insert statements
        //order is the same as in the TableNames array from above
        String TableFields[] =  {
                "DESIG_GRADE_RATE, DNEC1, RCVD_DT, ONBD_PRD, " +
        "SCTY_INVEST_COMPL_DT, SCTY_CLEAR_CD_ELIG, SCTY_CLEAR_CD_AUTH, " +
        "SCTY_INVEST_TYPE_CD, SCTY_GRANT_DT, OE_CD",

                "TEST_DT, TEST_ID, AFQT_SCORE, " +
            "GS_SCORE, AR_SCORE, WK_SCORE, " +
            "PC_SCORE, NO_SCORE, CS_SCORE, " +
            "AS_SCORE, MK_SCORE, MC_SCORE, " +
            "EI_SCORE, VE_SCORE, AO_SCORE, AI_SCORE, NFQT_SCORE ",

            "APPR_AWD_CD, MERIT_START_DT, MERIT_STOP_DT, APPR_AWD_DT",

            "LANG1_CD, LANG1_PROFCY_LISTEN_CD, LANG1_PROFCY_READ_CD, LANG1_PROFCY_SPEAK_CD, LANG1_PROFCY_WRITE_CD, LANG1_EVAL_CD, LANG1_SRC_CD, " +
                    "LANG2_CD, LANG2_PROFCY_LISTEN_CD, LANG2_PROFCY_READ_CD, LANG2_PROFCY_SPEAK_CD, LANG2_PROFCY_WRITE_CD, LANG2_EVAL_CD, LANG2_SRC_CD, " +
                    "LANG3_CD, LANG3_PROFCY_LISTEN_CD, LANG3_PROFCY_READ_CD, LANG3_PROFCY_SPEAK_CD, LANG3_PROFCY_WRITE_CD, LANG3_EVAL_CD, LANG3_SRC_CD, " +
                    "LANG4_CD, LANG4_PROFCY_LISTEN_CD, LANG4_PROFCY_READ_CD, LANG4_PROFCY_SPEAK_CD, LANG4_PROFCY_WRITE_CD, LANG4_EVAL_CD, LANG4_SRC_CD, " +
                    "LANG5_CD, LANG5_PROFCY_LISTEN_CD, LANG5_PROFCY_READ_CD, LANG5_PROFCY_SPEAK_CD, LANG5_PROFCY_WRITE_CD, LANG5_EVAL_CD, LANG5_SRC_CD",

            "UIC, ACTY_NM, TYPE_DUTY_CD, ASGN_DESIG_RCN, ASGN_DESIG_GRADE_RATE, RCVD_DT, TRF_DT, DNEC1, DNEC2, SEQ_NUM ",

            "SKILL_TYPE, SKILL, ENL_SKILL_DT",

            "ACTY_TITLE, UIC, RDA, RDD, NOBC1, NOBC2, NOBC3 ",

            "SKILL_TYPE, SKILL, OFF_SKILL_MON_HELD ",

            "QUAL_ID, QUAL_TITLE, QUAL_DT, EXPIR_DT, QUAL_TYPE, RCRD_SRC ",

            "CIN, CDP, CLS_NUM, LOC_NM, CSE_LONG_TITLE," +
                    " TRNG_DELIV_METH_CD, RCRD_SRC, COMPL_DT, SCORE," +
                    " RCRD_IND, RETIRE_POINT, CSE_LEN, APPROX_TRNG_EQUIV_IND," +
                    " NA_IND, SYS_LOAD_DT "};

        //read first line of inputstring and save to tablesize then move to next line
        String myID = reader.readLine();
        int TableSize = Integer.parseInt(reader.readLine());

        for (int i = 0; i < numOfTables; i++)
        {
            System.out.println(i + " : " + TableSize);
            SignInText.setText("Updating Info : " + i*10 + "%");
            for (int j = 0; j < TableSize; j++)
            {
                Access.execSQL("INSERT INTO " + TableNames[i] + " (DOD_EDI_PI, " + TableFields[i] + ") VALUES " + reader.readLine());
                //move to next line of string
            }
            if (i != (numOfTables - 1)) {
                TableSize = Integer.parseInt(reader.readLine());
            }

        }

    return myID;
    }



}