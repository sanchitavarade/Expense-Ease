package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Trans_Scene implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static ArrayList<Transactions> values= new ArrayList<Transactions>();
    
    @FXML
    private TableView<Transactions> Trans_table;

    @FXML
    private TableColumn<Transactions, String> Trans_type;

    @FXML
    private TableColumn<Transactions, Integer> Trans_amt;

    @FXML
    private TableColumn<Transactions, String> Trans_categ;

    @FXML
    private TableColumn<Transactions, String> Trans_date;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Trans_type.setCellValueFactory(new PropertyValueFactory<Transactions, String>("type"));
        Trans_amt.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("amt"));
        Trans_categ.setCellValueFactory(new PropertyValueFactory<Transactions, String>("categ"));
        Trans_date.setCellValueFactory(new PropertyValueFactory<Transactions, String>("date"));
        ObservableList<Transactions> list;
        try {
            giveTrans();
            System.out.println("out of func");
            // values.add(new Transactions("Expense", 2000, "Transportation", "22-08-23"));
            // values.add(new Transactions("income", 10000, "Job", "1-08-23"));
            list = FXCollections.observableArrayList(values);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.out.println("error occured ="+e);
            list = FXCollections.observableArrayList(
                new Transactions("Expense", 2000, "Transportation", "22-08-23"),
                new Transactions("income", 10000, "Job", "1-08-23")
            ); 
        }
        Trans_table.setItems(list);
    }
    public void switchToAddTrans(ActionEvent event) throws IOException{        // to switch the scene to add transaction
        root = FXMLLoader.load(getClass().getResource("finalAddTransaction.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToEditTrans(ActionEvent event) throws IOException{        // to switch the scene to edit transaction
        root = FXMLLoader.load(getClass().getResource("finalEditTrans.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToBL(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("finalBorrow&Lend.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToBudget(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("finalBudget.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSave(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("finalSavings.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private static void giveTrans() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
		// connecting database
        values.clear();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_tracker", "root", "oracle");
		
        PreparedStatement p = con.prepareStatement("select * from transactions;");
        ResultSet rs = p.executeQuery();
        System.out.println("printing now");
        while(rs.next()){
            String type = rs.getString("description");
			String date = rs.getString("transactiondate");
			int amt = rs.getInt("amount");
			String categ = rs.getString("Ecategory_id");
            System.out.println(type+"\t\t"+date+"\t\t"+amt+"\t\t"+categ);
            values.add(new Transactions(type, amt, categ, date));
            System.out.println("obj added");
        }
		con.close();
    }
    
}
