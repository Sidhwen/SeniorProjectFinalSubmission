
import java.sql.*;

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

  private String myDOD_EDI_PI;
  private Connection Conn;
  private Statement Stmt;
  private ResultSet Rs;
  
  public QualCertModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {

      Conn = MyConn;
      Stmt = Conn.createStatement();
      myDOD_EDI_PI = DOD_EDI_PI;
      
      Rs = Stmt.executeQuery("SELECT COUNT(*) FROM PERS_QUAL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "'");

      numOfQualifications = Rs.getInt(1);
      
      Rs = Stmt.executeQuery("SELECT QUAL_ID, QUAL_TITLE, QUAL_DT, EXPIR_DT, QUAL_TYPE, RCRD_SRC FROM PERS_QUAL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' ORDER BY QUAL_DT");

      //initialize array sizes based off of number of Qualifications


      QualificationIDs = new int[numOfQualifications];
      Qualifications = new String[numOfQualifications];
      AssignedDates = new String[numOfQualifications];
      ExpirationDates = new String[numOfQualifications];
      QualificationTypes = new String[numOfQualifications];
      QualificationSources = new String[numOfQualifications];

      Rs.next(); //move cursor to first row in result set

      //loop through result rows and extract the codes and months
      for (int i = 0; i < numOfQualifications; i++)
      {
          QualificationIDs[i] = Rs.getInt(1);
          Qualifications[i] = Rs.getString(2);
          AssignedDates[i] = Rs.getString(3);
          ExpirationDates[i] = Rs.getString(4);
          QualificationTypes[i] = Rs.getString(5);
          QualificationSources[i] = Rs.getString(6);
          Rs.next(); // move cursor to next row in result set
      }
      Rs.close();
      Stmt.close();



  }

  public String print()
  {

      //loop through skills and append them to the print buffer to be returned
      String PrintBuffer = numOfQualifications + "\n";

      for (int i = 0; i < numOfQualifications; i++)
      {
    	  PrintBuffer += "('" + myDOD_EDI_PI + "',";
          PrintBuffer += QualificationIDs[i] + ",'" + Qualifications[i] + "',";
          PrintBuffer += "'" + AssignedDates[i] + "',";
          PrintBuffer += "'" + ExpirationDates[i] + "',";
          PrintBuffer += "'" + QualificationTypes[i] + "',";
          PrintBuffer += "'" + QualificationSources[i] + "')";
          PrintBuffer += "\n";

      }
      return PrintBuffer;
  }




}


