
import java.sql.*;

public class TrainingModel {
    //All of the field names in the ALL_CSE_COMPL table(All Classes Completed?)
    private String[] CIN;
    private String[] CDP;
    private String[] CLS_NUM;
    private String[] LOC_NM;
    private String[] CSE_LONG_TITLE;
    private String[] TRNG_DELIV_METH_CD;
    private String[] RCRD_SRC;
    private  String[] COMPL_DT;
    private int[] SCORE;
    private String[] RCRD_IND;
    private String[] RETIRE_POINT;
    private String[] CSE_LEN;
    private String[] APPROX_TRNG_EQUIV_IND;
    private String[] NA_IND;
    private String[] SYS_LOAD_DT;
    private int trainingAmount;
    
    private String myDOD_EDI_PI;
    private Connection Conn;
    private Statement Stmt;
    private ResultSet Rs;

    public TrainingModel(String DOD_EDI_PI, Connection MyConn) throws SQLException {

    	Conn = MyConn;
    	Stmt = Conn.createStatement();
    	myDOD_EDI_PI = DOD_EDI_PI;
    	
    	Rs = Stmt.executeQuery("SELECT COUNT(*) FROM ALL_CSE_COMPL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "'");
    	
    	trainingAmount = Rs.getInt(1);
    	
        String QueryColumns = "CIN, CDP, CLS_NUM, LOC_NM, CSE_LONG_TITLE," +
                                    " TRNG_DELIV_METH_CD, RCRD_SRC, COMPL_DT, SCORE," +
                                    " RCRD_IND, RETIRE_POINT, CSE_LEN, APPROX_TRNG_EQUIV_IND, " +
                                    " NA_IND, SYS_LOAD_DT ";

        //Query the database
        
        Rs = Stmt.executeQuery("SELECT " + QueryColumns + "FROM ALL_CSE_COMPL WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "' ORDER BY COMPL_DT");


        //Set the sizes of the array based on the number of classes that person has taken
        CIN = new String[trainingAmount];
        CDP = new String[trainingAmount];
        CLS_NUM = new String[trainingAmount];
        LOC_NM = new String[trainingAmount];
        CSE_LONG_TITLE = new String[trainingAmount];
        TRNG_DELIV_METH_CD = new String[trainingAmount];
        RCRD_SRC = new String[trainingAmount];
        COMPL_DT = new String[trainingAmount];
        SCORE = new int[trainingAmount];
        RCRD_IND = new String[trainingAmount];
        RETIRE_POINT = new String[trainingAmount];
        CSE_LEN = new String[trainingAmount];
        APPROX_TRNG_EQUIV_IND = new String[trainingAmount];
        NA_IND = new String[trainingAmount];
        SYS_LOAD_DT = new String[trainingAmount];

        //Go to the first result in the table
        Rs.next();
        //Loop through all the classes returned and store them into the arrays
        for(int i = 0; i < trainingAmount; i++) {
            CIN[i] = Rs.getString(1);
            CDP[i] = Rs.getString(2);
            CLS_NUM[i] = Rs.getString(3);
            LOC_NM[i] = Rs.getString(4);
            CSE_LONG_TITLE[i] = Rs.getString(5);
            TRNG_DELIV_METH_CD[i] = Rs.getString(6);
            RCRD_SRC[i] = Rs.getString(7);
            COMPL_DT[i] = Rs.getString(8);
            SCORE[i] = Rs.getInt(9);
            RCRD_IND[i] = Rs.getString(10);
            RETIRE_POINT[i] = Rs.getString(11);
            CSE_LEN[i] = Rs.getString(12);
            APPROX_TRNG_EQUIV_IND[i] = Rs.getString(13);
            NA_IND[i] = Rs.getString(14);
            SYS_LOAD_DT[i] = Rs.getString(15);
            Rs.next();
        }
        Rs.close();
        Stmt.close();
    }

    //Method to append all the information together into a string to print to the display
    public String print(){
        String printBuffer = trainingAmount + "\n";
        for(int i = 0; i < trainingAmount; i++) {
        	printBuffer += "('" + myDOD_EDI_PI + "',";
            printBuffer += "'" + CIN[i] + "',";
            printBuffer += "'" + CDP[i] + "',";
            printBuffer += "'" + CLS_NUM[i] + "',";
            printBuffer += "'" + LOC_NM[i] + "',";
            printBuffer += "'" + CSE_LONG_TITLE[i] + "',";
            printBuffer += "'" + TRNG_DELIV_METH_CD[i] + "',";
            printBuffer += "'" + RCRD_SRC[i] + "',";
            printBuffer += "'" + COMPL_DT[i] + "',";
            printBuffer += "'" + SCORE[i] + "',";
            printBuffer += "'" + RCRD_IND[i] + "',";
            printBuffer += "'" + RETIRE_POINT[i] + "',";
            printBuffer += "'" + CSE_LEN[i] + "',";
            printBuffer += "'" + APPROX_TRNG_EQUIV_IND[i] + "',";
            printBuffer += "'" + NA_IND[i] + "',";
            printBuffer += "'" + SYS_LOAD_DT[i] + "')";
            printBuffer += "\n";
        }
        return printBuffer;
    }

}
