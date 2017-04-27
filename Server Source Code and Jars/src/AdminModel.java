
import java.sql.*;

/**
 * Kevin MacAllister
 * CIS498/99
 * Senior Design
 * 3/23/2017.
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
    private String[] OE_CD;
    private int rowCnt = 0;
    
    private Connection Conn;
    private Statement Stmt;
    private ResultSet Rs;
    String myDOD_EDI_PI;

    public AdminModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {

    	myDOD_EDI_PI = DOD_EDI_PI;
    	Conn = MyConn;
    	Stmt = Conn.createStatement();
    	
        //vars to construct query with
        String QueryTable = "MEMBER_NAV";
        String QueryColumns = "";
        String QuerySelection = "DOD_EDI_PI = '" + DOD_EDI_PI + "'";
        String orderByColumn = "RCVD_DT";

       


        QueryColumns = "DESIG_GRADE_RATE, ";
        QueryColumns += "DNEC1, ";
        QueryColumns += "RCVD_DT, ";
        QueryColumns += "ONBD_PRD, ";
        QueryColumns += "SCTY_INVEST_COMPL_DT, ";
        QueryColumns += "SCTY_CLEAR_CD_ELIG, ";
        QueryColumns += "SCTY_CLEAR_CD_AUTH, ";
        QueryColumns += "SCTY_INVEST_TYPE_CD, ";
        QueryColumns += "SCTY_GRANT_DT, ";
	QueryColumns += "OE_CD ";

        Rs = Stmt.executeQuery("SELECT COUNT(*) FROM MEMBER_NAV WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "'");
        rowCnt = Rs.getInt(1);
        
        Rs = Stmt.executeQuery("SELECT " + QueryColumns + "FROM MEMBER_NAV WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' ORDER BY RCVD_DT");
        

        //*********************
        //extract codes and dates held from the ResultSet, dynamically set array size based off of number of results returned
        //*********************


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
	OE_CD = new String[rowCnt];

        Rs.next(); //move cursor to first row in result set

        //loop through result rows and extract the codes and dates
        for (int i = 0; i < rowCnt; i++)
        {
            rate[i] = Rs.getString(1);
            dnec[i] = Rs.getString(2);
            dateRecievedOB[i] = Rs.getString(3);
            projRotDt[i] = Rs.getString(4);
            secCompDt[i] = Rs.getString(5);
            secClearCdElig[i] = Rs.getString(6);
            secClearCdAuth[i] = Rs.getString(7);
            secInvestTypeCd[i] = Rs.getString(8);
            secGrantDt[i] = Rs.getString(9);
            OE_CD[i] = Rs.getString(10);
		Rs.next(); // move cursor to next row in result set
        }

        Rs.close();
    }

    public String print()
    {

        //loop through skills and append them to the print buffer to be returned
        String PrintBuffer = rowCnt + "\n";

        for (int i = 0; i < rowCnt; i++)
        {
        	PrintBuffer += "('" + myDOD_EDI_PI + "',";
            PrintBuffer += "'" + rate[i] + "',";
            PrintBuffer += "'" + dnec[i] + "',";
            PrintBuffer += "'" + dateRecievedOB[i] + "','" + projRotDt[i] + "',";
            PrintBuffer += "'" + secCompDt[i] + "','" + secClearCdElig[i] + "'," +
                    "'" + secClearCdAuth[i] + "','" + secInvestTypeCd[i] + "','" + secGrantDt[i] + "','" + OE_CD[i];
            PrintBuffer += "')\n";
        }
        return PrintBuffer;
    }

}

