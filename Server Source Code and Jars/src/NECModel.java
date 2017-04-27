
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

//****************
//Kevin MacAllister
//January 20, 2017
//Senior Project
//****************

public class NECModel {

  private int[] SkillCodes; //Codes of the skills
  private String[] DateAwarded; //when these skills have been assigned
  private int numOfSkills; //holds the number of NEC skills assigned to the user
  private String myDOD_EDI_PI;
  
  Connection Conn = null;
  Statement Stmt = null;
  ResultSet Rs = null;

  public NECModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {


	  myDOD_EDI_PI = DOD_EDI_PI;
      Conn = MyConn;
      Stmt = Conn.createStatement();

      Rs = Stmt.executeQuery("SELECT COUNT(*) FROM SKILL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' AND SKILL_TYPE = 'NEC';");

      numOfSkills = Rs.getInt(1);
      
      Rs = Stmt.executeQuery("SELECT SKILL, ENL_SKILL_DT FROM SKILL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' AND SKILL_TYPE = 'NEC' ORDER BY OFF_SKILL_MON_HELD;");

      //*********************
      //extract codes and months held from the ResultSet, dynamically set array size based off of number of results returned
      //*********************


      //initialize array sizes based off of number of skills
      SkillCodes = new int[numOfSkills];
      DateAwarded = new String[numOfSkills];

      //loop through result rows and extract the codes and months
      for (int i = 0; i < numOfSkills; i++)
      {
          Rs.next(); // move cursor to next row in result set

          SkillCodes[i] = Rs.getInt(1);
          DateAwarded[i] = Rs.getString(2);
      }

      



      Stmt.close();

  }

  public String print()
  {

      //loop through skills and append them to the print buffer to be returned
      String PrintBuffer = numOfSkills + "\n";

      for (int i = 0; i < numOfSkills; i++)
      {
    	  PrintBuffer += "('" + myDOD_EDI_PI + "',";
    	  PrintBuffer += "'NEC',";
          PrintBuffer += "" + SkillCodes[i] + ",";
          PrintBuffer += "'" + DateAwarded[i] + "')\n";
      } 
      return PrintBuffer;
  }




}


