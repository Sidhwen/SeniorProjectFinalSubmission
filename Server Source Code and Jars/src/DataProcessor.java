
import java.sql.*;

public class DataProcessor {

	Connection Conn = null;
	boolean LoginSuccess = false;
	int loginAttemptResult = -2;
	String myDOD_EDI_PI;
	String myPassword;
	String myWLAN;
	Language[] myLanguage = new Language[5];
	ASVABModel myASVABModel;
	AwardsModel myAwardsModel;
	NECHistoryModel myNECHistoryModel;
	NECModel myNECModel;
	NOBCHistoryModel myNOBCHistoryModel;
	NOBCModel myNOBCModel;
	QualCertModel myQualCertModel;
	TrainingModel myTrainingModel;
	AdminModel myAdminModel;
	
	public DataProcessor (String WLAN, String Password, Connection MyConn) throws SQLException
	{
		myWLAN = WLAN;
		Conn = MyConn;
		myPassword = Password;
		loginAttemptResult = Login();
		if (loginAttemptResult == 1)
		{
			for (int i = 0; i < 5; i++)
			{
				myLanguage[i] = new Language(myDOD_EDI_PI, i + 1, Conn);
			}
			myNECModel = new NECModel(myDOD_EDI_PI, Conn);
			myASVABModel = new ASVABModel(myDOD_EDI_PI, Conn);
			myAwardsModel = new AwardsModel(myDOD_EDI_PI, Conn);
			myNECHistoryModel = new NECHistoryModel(myDOD_EDI_PI, Conn);
			myNOBCHistoryModel = new NOBCHistoryModel(myDOD_EDI_PI, Conn);
			myNOBCModel = new NOBCModel(myDOD_EDI_PI, Conn);
			myQualCertModel = new QualCertModel(myDOD_EDI_PI, Conn);
			myTrainingModel = new TrainingModel(myDOD_EDI_PI, Conn);
			myAdminModel = new AdminModel(myDOD_EDI_PI, Conn);
		}

		}
	
	private int Login() throws SQLException
	{
		ResultSet Rs = null;
		Statement Stmt = null;
		Stmt = Conn.createStatement();
		
		String RealPassword;
        Rs = Stmt.executeQuery("SELECT PIN, DOD_EDI_PI FROM LOGIN WHERE WLAN = '" + myWLAN + "'");
	
	//ResultSet starts before the actual results. This returns true if there are results and false if there are none. 
	//No results means the WLAN was not in the table.
	if(!Rs.isBeforeFirst()){
		return 0;
	}

        RealPassword = Rs.getString(1);
	myDOD_EDI_PI = Rs.getString(2);

        if (RealPassword.equals(myPassword)) {
        	return 1;
        } 
	
	else {
		return -1;
	}
	}
	// construct the ends of INSERT INTO Sql statements to send over to the client, delimited with new lines.
	public String print()
	{
		if (loginAttemptResult == 1)
		{
		String OutputString = "";
		OutputString += myDOD_EDI_PI + "\n";
		OutputString += myAdminModel.print();
		OutputString += myASVABModel.print();
		OutputString += myAwardsModel.print();
		OutputString += "1\n(" + myDOD_EDI_PI + ",";
		for (int i = 0; i < 5; i++)
		{
			OutputString += myLanguage[i].print();
			if (i != 4) { OutputString += ","; }
		}
		OutputString += ")\n";
		OutputString += myNECHistoryModel.print();
		OutputString += myNECModel.print();
		OutputString += myNOBCHistoryModel.print();
		OutputString += myNOBCModel.print();
		OutputString += myQualCertModel.print();
		OutputString += myTrainingModel.print();
		return OutputString;
		}
		else if(loginAttemptResult == -1) {
			return "INVALID_WLAN_PIN";
		}
		else if(loginAttemptResult == 0) {
			return "WLAN_NOT_FOUND";
		}
		else {
			return "Something went wrong";
		}
	}
}
	
		
	

