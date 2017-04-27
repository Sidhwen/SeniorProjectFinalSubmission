/**
*Servlet for handling when the user needs to update their WLAN in the database.
*Replaces the WLAN, sets a new PIN, and emails the user the new PIN
*/
import java.util.Random;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Annotation that sets the URL path this servlet will respond to(http://websitename.com/WLANChange)
@WebServlet("/changeServlet")
public class changeServlet extends HttpServlet {
	private static final long serialVersonUID = 2L;

	//Object used to send email
	Mailer mail = new Mailer();
	String userEmail;

	String DOD_EDI_PI = "";
	String attemptedPin = "";
	String WLAN = "";

	private Connection connection = null;
	private String databaseURL = "jdbc:sqlite:/opt/tomcat/webapps/METJ/database/NUWC.db";

	public changeServlet() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection(databaseURL);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		DOD_EDI_PI += request.getParameter("ID");
		attemptedPin += request.getParameter("Pass");
		WLAN += request.getParameter("WLAN");
		String newPin = "";


		try{
			//Attempt to login and then do database changes
			if(login() == 1){
				//Attempt to change the WLAN
				changeWLAN(WLAN, DOD_EDI_PI);
				//Give the user a new pin in case they are not the ones that changed the WLAN
				newPin += generateNewPin();
				//Store the new pin in the database
				storeNewPin(DOD_EDI_PI, newPin);
				//Strings that make up the email
				String emailSubject = "Mobile Electronic Training Jacket: New Phone Registered";
				String emailBody = "A new phone has been registered to your Mobile Electronic Training Jacket Acccount. " +
									"We have generated a new PIN, it is " + newPin + ". If this was not you, please contact the system" +
									" administrator.";
				//Send an email to the user with the new pin
				mail.send("jakestrojny@gmail.com", "rtEcBz4d0cFJ4i6", getUserEmail(DOD_EDI_PI), emailSubject, emailBody);
				//Return success to the phone app
				out.println("SUCCESS");

			} else {
				//If the user enters an invalid ID/PIN combo
				out.println("INVALID_ID_PIN");
			}


		} catch(SQLException e) {
			//Let the app know something went wrong with the database
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private int login() throws SQLException{
		ResultSet results = null;
		Statement statement = null;
		statement = connection.createStatement();

		String query = "SELECT PIN FROM LOGIN WHERE DOD_EDI_PI = '" + DOD_EDI_PI + "'";
		String realPin;
		results = statement.executeQuery(query);

		realPin = results.getString(1);

		if(realPin.equals(attemptedPin)) {
			return 1;
		}
		else {
			return -1;
		}
	}

	private void changeWLAN(String newWLAN, String myID) throws SQLException {
		PreparedStatement stmt = null;
		String query = "UPDATE LOGIN SET WLAN = ? " 
						+ " WHERE DOD_EDI_PI = ?";
		stmt = connection.prepareStatement(query);
		stmt.setString(1, newWLAN);
		stmt.setString(2, myID);
		stmt.executeUpdate();
	}

	private String getUserEmail(String myID) throws SQLException {
		ResultSet Rs = null;
		Statement stmt = null;
		stmt = connection.createStatement();

		String query = "SELECT EMAIL FROM LOGIN WHERE DOD_EDI_PI = '" + myID + "'";
		Rs = stmt.executeQuery(query);

		return Rs.getString(1);
	}

	private String generateNewPin() {
		String numbers = "1234567890";
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 4; i++){
			int randomIndex = rand.nextInt(numbers.length());
			sb.append(numbers.charAt(randomIndex));
		}

		return sb.toString();
	}

	private void storeNewPin(String myID, String newPin) throws SQLException {
		PreparedStatement stmt = null;
		String query = "UPDATE LOGIN SET PIN = ? " 
						+ " WHERE DOD_EDI_PI = ?";
		stmt = connection.prepareStatement(query);
		stmt.setString(1, newPin);
		stmt.setString(2, myID);
		stmt.executeUpdate();
	}
}
