
import java.sql.*;

public class Language {




//****************
//Kevin MacAllister
//January 20, 2017
//Senior Project
//****************

	private String LangCode;

    private int ProficiencyListenCode;
    private int ProficiencyReadCode;
    private int ProficiencySpeakCode;
    private int ProficiencyWriteCode;

    private int EvaluationCode;
    private int SourceCode;

    private Connection Conn = null;
    private Statement Stmt = null;
    private ResultSet Rs = null;
        
        public Language(String DOD_EDI_PI, int LangNum, Connection MyConn) throws SQLException {

        	Conn = MyConn;
        	Stmt = Conn.createStatement();
            String QueryTable = "";
            String[] QueryColumns = new String[8];
            String QuerySelection = "";
            String LangNumString = Integer.toString(LangNum); //gives us the number of the language being queried in string form
            String CompiledQueryColumns = "";


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
            for (int i=0; i<8; i++)
            {
            CompiledQueryColumns += QueryColumns[i];
            if (i != 7) { CompiledQueryColumns += ","; }
            }
            QueryTable += "LANG_HORIZ";
            QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";


            //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

            //*********************
            //call the SQLiteOpenHelper with the query for Cursor/ResultSet
            //*********************
            System.out.println(CompiledQueryColumns);
            Rs = Stmt.executeQuery("SELECT " + CompiledQueryColumns + " FROM " + QueryTable + " WHERE " + QuerySelection + ";");
            System.out.println(Rs.getString(1));
            

            //*********************
            //extract various codes and test date from the ResultSet
            //*********************
            LangCode = Rs.getString(1);


            ProficiencyListenCode = Rs.getInt(2);
            ProficiencyReadCode = Rs.getInt(3);
            ProficiencySpeakCode = Rs.getInt(4);
            ProficiencyWriteCode = Rs.getInt(5);

            EvaluationCode = Rs.getInt(6);
            SourceCode = Rs.getInt(7);



            Stmt.close();

        }

        public String print()
        {
        	String PrintBuffer = "";
            PrintBuffer += "'" + LangCode + "',";
            PrintBuffer += ProficiencyListenCode + ",";
            PrintBuffer += ProficiencyReadCode + ",";
            PrintBuffer += ProficiencySpeakCode + ",";
            PrintBuffer += ProficiencyWriteCode + ",";
            PrintBuffer += EvaluationCode + ",";
            PrintBuffer += SourceCode;
            return PrintBuffer;
        }




    }

