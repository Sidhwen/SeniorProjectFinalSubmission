
import java.sql.*;

public class NOBCHistoryModel {
    private String[] ACTY_TITLE;
    private String[] UIC;
    private String[] RDA;
    private String[] RDD;
    private String[] NOBC1;
    private String[] NOBC2;
    private String[] NOBC3;
    private int numAssignments;
    
    private Connection Conn = null;
    private Statement Stmt = null;
    private ResultSet Rs = null;
    
    String myDOD_EDI_PI;
    
    public NOBCHistoryModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {

    	
    	Conn = MyConn;
    	Stmt = Conn.createStatement();
    	myDOD_EDI_PI = DOD_EDI_PI;
    	
    	Rs = Stmt.executeQuery("SELECT COUNT(*) FROM OFF_ASGN_HIST WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "'");
    	
        //Get the number of classes the person has completed, use this to set the array sizes above
    	numAssignments = Rs.getInt(1);

        //Query the database
        Rs = Stmt.executeQuery("SELECT ACTY_TITLE, UIC, RDA, RDD, NOBC1, NOBC2, NOBC3 FROM OFF_ASGN_HIST WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' ORDER BY RDA");


        //Set the sizes of the array based on the number of classes that person has taken
        ACTY_TITLE = new String[numAssignments];
        UIC = new String[numAssignments];
        RDA = new String[numAssignments];
        RDD = new String[numAssignments];
        NOBC1 = new String[numAssignments];
        NOBC2 = new String[numAssignments];
        NOBC3 = new String[numAssignments];

        //Go to the first result in the table
        Rs.next();
        //Loop through all the classes returned and store them into the arrays
        for(int i = 0; i < numAssignments; i++) {

            ACTY_TITLE[i] = Rs.getString(1);
            UIC[i] = Rs.getString(2);
            RDA[i] = Rs.getString(3);
            RDD[i] = Rs.getString(4);
            NOBC1[i] = Rs.getString(5);
            NOBC2[i] = Rs.getString(6);
            NOBC3[i] = Rs.getString(7);
            Rs.next();
        }
        Rs.close();
        Stmt.close();
    }

    //Method to append all the information together into a string to print to the display
    public String print(){
        String printBuffer = numAssignments + "\n";
        for(int i = 0; i < numAssignments; i++) {
        	printBuffer += "('" + myDOD_EDI_PI + "',";
            printBuffer += "'" + ACTY_TITLE[i] + "',";
            printBuffer += "'" + UIC[i] + "',";
            printBuffer += "'" + RDA[i] + "',";
            printBuffer += "'" + RDD[i] + "',";
            printBuffer += "'" + NOBC1[i] + "',";
            printBuffer += "'" + NOBC2[i] + "',";
            printBuffer += "'" + NOBC3[i] + "')";
            printBuffer += "\n";
        }
        return printBuffer;
    }
}
