package com.example.demo2;
import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class  AlertConnector {

	public static String tfTransamt="";
	public static String tfTranscateg="";
	public static String tfTranstype="";
	public static String tfTransdate="";

	public static String username="user";

	public static int user;
	static Alert a = new Alert(AlertType.ERROR);
	// alert function for invalid login
	public static void Handle1()
	{
		// set alert type
		a = new Alert(AlertType.ERROR, "Invalid Password", ButtonType.CANCEL);

		// show the dialog
		a.show();
	}
	public static void wrongPass()
	{
		// set alert type
		a.setAlertType(AlertType.ERROR);
		// content to show
		a.setContentText("Confirm with same password");
		// show the dialog
		a.show();
	}
	public static void BudgetBeyond()
	{
		// set alert type
		a.setAlertType(AlertType.WARNING);
		// content to show
		a.setContentText("You have Exceeded the Budget");
		// show the dialog
		a.show();
	}
	public static void Handle2()
	{
		// set alert type
		a.setAlertType(AlertType.ERROR);
		// content to show
		a.setContentText("Invalid Username or Password Length!");
		// show the dialog
		a.show();
	}
	public static void Handle3()
	{
		// set alert type
		a = new Alert(AlertType.ERROR,
				"No such user found", ButtonType.CANCEL);

		// show the dialog
		a.show();
	}
	public static void Handle4()
	{
		// set alert type
		a = new Alert(AlertType.ERROR,
				"Set stronger password", ButtonType.CANCEL);

		// show the dialogue
		a.show();
	}
	public static boolean checkLogin(String name, String pass) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
		// connecting database
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
		Statement stmt = con.createStatement();

		// SELECT query
		String q1 = "select user_id, password from user WHERE username = '" + name + "';";
		ResultSet rs = stmt.executeQuery(q1);
		if (rs.next())
		{
			if(pass.compareTo(rs.getString("password"))==0){
				System.out.println("Valid login");
				user= rs.getInt("user_id");
				username= "".concat(name);
				return true;
			}
			else{
				System.out.println("Invalid Login");
				Handle1();
				con.close();
				return false;
			}
		}
		else {
			System.out.println("No such Valid User");
			Handle3();
			con.close();
			return false;

		}
	}
}