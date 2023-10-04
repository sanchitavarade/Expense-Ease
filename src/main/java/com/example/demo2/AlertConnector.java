package com.example.demo2;
import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertConnector {
	public static int user;
	static Alert a = new Alert(AlertType.NONE);
	// alert function for invalid login
	public static void handle()
	{
		// set alert type
		a.setAlertType(AlertType.ERROR);
		// content to show
		a.setContentText("Invalid Login");
		// show the dialog
		a.show();
	}
	public static boolean checkLogin1(String name, String pass) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
		// connecting database
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "PHW#84#jeor");
		Statement stmt = con.createStatement();

		// SELECT query
		String q1 = "select user_id, password from user WHERE username = '" + name + "';";
		ResultSet rs = stmt.executeQuery(q1);
		if (rs.next())
		{
			if(pass.compareTo(rs.getString("password"))==0){
				System.out.println("Valid login");
				user= rs.getInt("user_id");
				return true;
			}
			else{
				System.out.println("Invalid login");
				handle();
			}
		}
		else
		{
			System.out.println("No such Valid User");
			handle();
		}
		con.close();
		return false;
	}
}