package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

public class budget_scene implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static ArrayList<Budget> Bud_values= new ArrayList<Budget>();
    
    @FXML
    private TableView<Budget> Bud_table;

    @FXML
    private TableColumn<Budget, String> Bud_Categ;

    @FXML
    private TableColumn<Budget, Integer> Bud_Limit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Bud_Categ.setCellValueFactory(new PropertyValueFactory<Budget, String>("categ"));
        Bud_Limit.setCellValueFactory(new PropertyValueFactory<Budget, Integer>("limit"));
        ObservableList<Budget> Bud_list;
        try {
            giveBudget();
            System.out.println("out of func");
            // Bud_values.add(new Budget("Expense", 50000));
            // Bud_values.add(new Budget("income", 2000));
            Bud_list = FXCollections.observableArrayList(Bud_values);
            System.out.println("Runned budget");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | IOException e) {
            System.out.println("error occured ="+e);
            Bud_list = FXCollections.observableArrayList(
                new Budget("Expense", 50000),
                new Budget("income", 2000)
            );
        }
        Bud_table.setItems(Bud_list);
    }
    private void giveBudget() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        Bud_values.clear();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_tracker", "root", "oracle");
		
        PreparedStatement p = con.prepareStatement("select * from transaction_category;");
        ResultSet rs = p.executeQuery();
        System.out.println("printing now");
        while(rs.next()){
			String categ = rs.getString("categoryname");
            int limit = rs.getInt("categLimit");
            System.out.println(limit+"\t\t"+categ);
            Bud_values.add(new Budget(categ, limit));
            System.out.println("obj added");
        }
		con.close();
    }
    @FXML
    public void switchToTransaction(ActionEvent event) throws IOException{        // to switch the scene to transaction
        root = FXMLLoader.load(getClass().getResource("finalTransaction.fxml"));
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
    @FXML
    public void switchToCateg(ActionEvent event) throws IOException{        // to switch the scene to transaction
        root = FXMLLoader.load(getClass().getResource("finalAddCategory.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

