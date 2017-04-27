
import java.sql.*;

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
  
  private String myDOD_EDI_PI;

  private int numOfTests; //holds the number of qualifications assigned to the user
  
  private Connection Conn = null;
  private Statement Stmt = null;
  private ResultSet Rs = null;

  public ASVABModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {

	  myDOD_EDI_PI = DOD_EDI_PI;
      String[] QueryColumns = new String[17];
      
      Conn = MyConn;
      Stmt = Conn.createStatement();
      
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
      QueryColumns[14] = "AO_SCORE";
      QueryColumns[15] = "AI_SCORE";
      QueryColumns[16] = "NFQT_SCORE";
      String CompiledQuerys = "";
      
      for (int i = 0; i < 17; i++)
      {
    	  CompiledQuerys += QueryColumns[i];
    	  if (i != 16)
    	  {
    		  CompiledQuerys += ",";
    	  }
      }


      System.out.println(CompiledQuerys);
      //query function arguments described below
      //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

      //*********************
      //call the SQLiteOpenHelper with the query for Cursor/ResultSet
      //*********************

      Rs = Stmt.executeQuery("SELECT COUNT(*) FROM ASVAB WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "';");
      
      numOfTests = Rs.getInt(1);
      
      Rs = Stmt.executeQuery("SELECT " + CompiledQuerys + " FROM ASVAB WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' ORDER BY TEST_DT;");

      //*********************
      //extract info held from the ResultSet, dynamically set array size based off of number of results returned
      //*********************


      //initialize array sizes based off of number of Qualifications


      TestDates = new String[numOfTests];
      TestIDs = new String[numOfTests];
      Scores = new String[numOfTests][15];



      int ScoreBuffer;
      //loop through result rows and extract the codes and months
      for (int i = 0; i < numOfTests; i++) {
          Rs.next(); // move cursor to next row in result set

          TestDates[i] = Rs.getString(1);
          TestIDs[i] = Rs.getString(2);
          for (int j = 0; j < 15; j++)
          {
              ScoreBuffer = Rs.getInt(3 + j);
              if (ScoreBuffer == 0)
              {
                  Scores[i][j] = "null";
              }
              else {
                  Scores[i][j] = String.valueOf(ScoreBuffer);
              }
          }
      }

      Stmt.close();
  }

  public String print()
  {

      //loop through skills and append them to the print buffer to be returned
      String PrintBuffer = numOfTests + "\n";

      for (int i = 0; i < numOfTests; i++)
      {
          PrintBuffer += "('" + myDOD_EDI_PI + "',";
          PrintBuffer += "'" + TestDates[i] + "',";
          PrintBuffer += "'" + TestIDs[i] + "',";
          for (int j = 0; j < 15; j++)
          {
        	  PrintBuffer += "'" + Scores[i][j] + "'";
        	  if (j != 14) { PrintBuffer += ","; }
          }

          PrintBuffer += ")\n";

      }
      return PrintBuffer;
  }





}

