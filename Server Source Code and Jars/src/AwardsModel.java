
import java.sql.*;

//****************
//Kevin MacAllister
//March 4, 2017
//Senior Project
//****************

public class AwardsModel {

  private String[] AwardCodes; //Holds the codes of the awards for later query

  private String[] MeritStart; //beginning of merit periods
  private String[] MeritEnd; //end of merit periods
  private String[] AwardDate; //when the awards were approved
  private int numOfAwards; //holds the number of awards assigned to the user
  private Connection Conn;
  private Statement Stmt;
  private ResultSet Rs;
  
  private String myDOD_EDI_PI;

  public AwardsModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {

	  myDOD_EDI_PI = DOD_EDI_PI;



      Conn = MyConn;
      Stmt = Conn.createStatement();


      //*************************************************************

 
      Rs = Stmt.executeQuery("SELECT COUNT(*) FROM AWD WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "';");
      
      numOfAwards = Rs.getInt(1);
            
      Rs = Stmt.executeQuery("SELECT APPR_AWD_CD, MERIT_START_DT, MERIT_STOP_DT, APPR_AWD_DT FROM AWD WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' ORDER BY APPR_AWD_DT;");

      //*********************
      //extract codes and dates held from the ResultSet, dynamically set array size based off of number of results returned
      //*********************


      //initialize array sizes based off of number of skills
      AwardCodes = new String[numOfAwards];
      MeritStart = new String[numOfAwards];
      MeritEnd = new String[numOfAwards];
      AwardDate = new String[numOfAwards];


      //loop through result rows and extract the codes and dates
      for (int i = 0; i < numOfAwards; i++)
      {
          Rs.next(); // move cursor to next row in result set

          AwardCodes[i] = Rs.getString(1);
          MeritStart[i] = Rs.getString(2);
          MeritEnd[i] = Rs.getString(3);
          AwardDate[i] = Rs.getString(4);
      }



      Stmt.close();
  }

  public String print()
  {

      //loop through skills and append them to the print buffer to be returned
	  String PrintBuffer = numOfAwards + "\n";
      

      for (int i = 0; i < numOfAwards; i++)
      {
    	  PrintBuffer += "('" + myDOD_EDI_PI + "',";
          PrintBuffer += "'" + AwardCodes[i] + "',";
          PrintBuffer += "'" + MeritStart[i] + "','" + MeritEnd[i] + "',";
          PrintBuffer += "'" + AwardDate[i] + "')\n";
      }
      return PrintBuffer;
  }




}

