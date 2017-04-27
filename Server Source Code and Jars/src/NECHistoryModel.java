import java.sql.*;

public class NECHistoryModel {
    private String[] UIC;
    private String[] ACTY_NM;
    private String[] TYPE_DUTY_CD;
    private String[] ASGN_DESIG_RCN;
    private String[] ASGN_DESIG_GRADE_RATE;
    private String[] RCVD_DT;
    private String[] TRF_DT;
    private String[] DNEC1;
    private String[] DNEC2;
    private int[] SEQ_NUM;
    private int numAssignments;
    
    private Connection Conn;
    private Statement Stmt;
    private ResultSet Rs;
    
    private String myDOD_EDI_PI;
    
    public NECHistoryModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {

    	Conn = MyConn;
    	Stmt = Conn.createStatement();
    	
    	myDOD_EDI_PI = DOD_EDI_PI;
    	
    	Rs = Stmt.executeQuery("SELECT COUNT(*) FROM ENL_ASGN_HIST WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "';");
    	numAssignments = Rs.getInt(1);
        //Query the database
         Rs = Stmt.executeQuery("SELECT UIC, ACTY_NM, TYPE_DUTY_CD, ASGN_DESIG_RCN, ASGN_DESIG_GRADE_RATE, RCVD_DT, TRF_DT, DNEC1, DNEC2, SEQ_NUM FROM ENL_ASGN_HIST WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' ORDER BY SEQ_NUM;");

        //Get the number of classes the person has completed, use this to set the array sizes above
        //Set the sizes of the array based on the number of classes that person has taken
        UIC = new String[numAssignments];
        ACTY_NM = new String[numAssignments];
        TYPE_DUTY_CD = new String[numAssignments];
        ASGN_DESIG_RCN = new String[numAssignments];
        ASGN_DESIG_GRADE_RATE = new String[numAssignments];
        RCVD_DT = new String[numAssignments];
        TRF_DT = new String[numAssignments];
        DNEC1 = new String[numAssignments];
        DNEC2 = new String[numAssignments];
        SEQ_NUM = new int[numAssignments];

        //Go to the first result in the table
        //Loop through all the classes returned and store them into the arrays
        for(int i = 0; i < numAssignments; i++) {
            Rs.next();
            UIC[i] = Rs.getString(1);
            ACTY_NM[i] = Rs.getString(2);
            TYPE_DUTY_CD[i] = Rs.getString(3);
            ASGN_DESIG_RCN[i] = Rs.getString(4);
            ASGN_DESIG_GRADE_RATE[i] = Rs.getString(5);
            RCVD_DT[i] = Rs.getString(6);
            TRF_DT[i] = Rs.getString(7);
            DNEC1[i] = Rs.getString(8);
            DNEC2[i] = Rs.getString(9);
            SEQ_NUM[i] = Rs.getInt(10);
        }
        Rs.close();
        Stmt.close();
    }

    //Method to append all the information together into a string to print to the display
    public String print(){
        String printBuffer = numAssignments + "\n";
        for(int i = 0; i < numAssignments; i++) {
        	printBuffer += "('" + myDOD_EDI_PI + "',";
            printBuffer += "'" + UIC[i] + "',";
            printBuffer += "'" + ACTY_NM[i] + "',";
            printBuffer += "'" + TYPE_DUTY_CD[i] + "',";
            printBuffer += "'" + ASGN_DESIG_RCN[i] + "',";
            printBuffer += "'" + ASGN_DESIG_GRADE_RATE[i] + "',";
            printBuffer += "'" + RCVD_DT[i] + "',";
            printBuffer += "'" + TRF_DT[i] + "',";
            printBuffer += "'" + DNEC1[i] + "',";
            printBuffer += "'" + DNEC2[i] + "',";
            printBuffer += SEQ_NUM[i] + ")";
            printBuffer += "\n";
        }
        return printBuffer;
    }
}
