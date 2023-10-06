package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;

public class Trans_Scene implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static ArrayList<Transactions> values= new ArrayList<Transactions>();

    @FXML
    private TableView<Transactions> Trans_table;

    @FXML
    private TableColumn<Transactions, Integer> Trans_id;

    @FXML
    private TableColumn<Transactions, String> Trans_type;

    @FXML
    private TableColumn<Transactions, Integer> Trans_amt;

    @FXML
    private TableColumn<Transactions, String> Trans_categ;

    @FXML
    private TableColumn<Transactions, Integer> Trans_date;

    @FXML
    private TextField newTransId;

    @FXML
    private TextField newTransType;

    @FXML
    private TextField newTransAmt;

    @FXML
    private TextField newTransCateg;

    @FXML
    private DatePicker newTransDate;

    int index = -1;

    @FXML
    void getSelected(javafx.scene.input.MouseEvent event){
        index = Trans_table.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        newTransId.setText(Trans_id.getCellData(index).toString());
        newTransType.setText(Trans_type.getCellData(index).toString());
        newTransAmt.setText(Trans_amt.getCellData(index).toString());
        newTransCateg.setText(Trans_categ.getCellData(index).toString());
        //newTransDate.setValue(Trans_date.getCellData(Date).);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Trans_id.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("id"));
        Trans_type.setCellValueFactory(new PropertyValueFactory<Transactions, String>("type"));
        Trans_amt.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("amt"));
        Trans_categ.setCellValueFactory(new PropertyValueFactory<Transactions, String>("categ"));
        Trans_date.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("date"));
        ObservableList<Transactions> list;
        try {
            giveTrans();
            list = FXCollections.observableArrayList(values);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.out.println("error occured ="+e);
            list = FXCollections.observableArrayList(
                    new Transactions(0,"Error", 0, "Transportation", "22-08-23")
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

    @FXML
    public void switchToLoginPage(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToTransaction(ActionEvent event) throws IOException{        // to switch the scene to transaction
        root = FXMLLoader.load(getClass().getResource("finalTransaction.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    private static void giveTrans() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
        // connecting database
        values.clear();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");

        PreparedStatement p = con.prepareStatement("select * from transactions where user_id="+AlertConnector.user+";");
        ResultSet rs = p.executeQuery();
        System.out.println("printing now");
        while(rs.next()){
            int id = rs.getInt("transaction_id");
            String type = rs.getString("transactiontype");
            String date = rs.getString("transactiondate");
            int amt = rs.getInt("amount");
            String categ = rs.getString("categoryname");
            System.out.println(id+"\t\t"+type+"\t\t"+date+"\t\t"+amt+"\t\t"+categ);
            values.add(new Transactions(id, type, amt, categ, date));
        }
        con.close();
    }

    @FXML
    void ApplyTransChanges(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        // checks the login is valid or not
        String tfid = newTransId.getText();
        String tftype =newTransType.getText();
        String tfamt = newTransAmt.getText();
        String tfcateg = newTransCateg.getText();
        LocalDate tfdate = newTransDate.getValue();
        changeTransData(tfid,tftype,tfamt,tfcateg,tfdate);
        switchToTransaction(event);
    }

    public static void changeTransData(String id, String type, String amt, String categ, LocalDate date) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
            Statement stmt = con.createStatement();

            // Updating database
            String q2 = "UPDATE transactions set transactiontype = '" +type+ "', amount = "+ amt +", categoryname = '" +categ+ "', transactiondate = '" +date+ "' WHERE transaction_id = " +id+ " and user_id ="+AlertConnector.user+";"  ;
            int z = stmt.executeUpdate(q2);

            if (z > 0)
                System.out.println("Expenses Updated");
            else
                System.out.println("ERROR OCCURRED :(");

            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}