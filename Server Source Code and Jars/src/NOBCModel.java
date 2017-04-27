
import java.sql.*;

//****************
//Kevin MacAllister
//March 3, 2017
//Senior Project
//****************

public class NOBCModel {

  private int[] SkillCodes; //codes of the skills
  private int[] MonthsHeld; //how long those skills have been assigned'
  private int numOfSkills; //holds the number of NOBC skills assigned to the user

  private String myDOD_EDI_PI;
  
  private Connection Conn;
  private Statement Stmt;
  private ResultSet Rs;

  public NOBCModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {


      Conn = MyConn;
      Stmt = Conn.createStatement();
      myDOD_EDI_PI = DOD_EDI_PI;



      Rs = Stmt.executeQuery("SELECT COUNT(*) FROM SKILL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' AND SKILL_TYPE = 'NOBC'");
      numOfSkills = Rs.getInt(1);
      
      Rs = Stmt.executeQuery("SELECT SKILL, OFF_SKILL_MON_HELD FROM SKILL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' AND SKILL_TYPE = 'NOBC' ORDER BY OFF_SKILL_MON_HELD");


      //initialize array sizes based off of number of skills
      SkillCodes = new int[numOfSkills];
      MonthsHeld = new int[numOfSkills];

      Rs.next(); //move cursor to first row in result set

      //loop through result rows and extract the codes and months
      for (int i = 0; i < numOfSkills; i++)
      {
          SkillCodes[i] = Rs.getInt(1);
          MonthsHeld[i] = Rs.getInt(2);
          Rs.next(); // move cursor to next row in result set
      }



      Rs.close();
      Stmt.close();

  }

  public String print()
  {

      //loop through skills and append them to the print buffer to be returned
      String PrintBuffer = numOfSkills + "\n";

      for (int i = 0; i < numOfSkills; i++)
      {
    	  PrintBuffer += "('" + myDOD_EDI_PI + "',";
    	  PrintBuffer += "'NOBC',";
          PrintBuffer += SkillCodes[i] + ",";
          PrintBuffer += MonthsHeld[i] + ")";
          PrintBuffer += "\n";
      }
      return PrintBuffer;
  }




}

