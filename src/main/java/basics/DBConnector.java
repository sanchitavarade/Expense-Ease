package basics;


import java.sql.*;

public class DBConnector {
    public static void main(String[] args) {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");

            if (con != null)
                System.out.println("Connected!");
            else
                System.out.println("Not Connected");

            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
