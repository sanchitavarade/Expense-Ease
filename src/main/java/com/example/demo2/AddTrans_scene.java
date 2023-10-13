package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddTrans_scene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private static ArrayList<String> categValues= new ArrayList<String>();
    private static ArrayList<String> typeValues= new ArrayList<String>();

    @FXML
    private DatePicker Trans_addDate;

    @FXML
    private ComboBox<String> Combo_categ;

    @FXML
    private ComboBox<String> Combo_type;

    @FXML
    private TextField Trans_addAmt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> categList;
        ObservableList<String> TypeList;
        typeValues.add("Expense");
        typeValues.add("Income");
        TypeList = FXCollections.observableArrayList(typeValues);
        Combo_type.setItems(TypeList);

        try {
            getCateg();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        categList = FXCollections.observableArrayList(categValues);
        Combo_categ.setItems(categList);
        // Set a DateCell factory to restrict date selection to on or before today
        Trans_addDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isAfter(LocalDate.now())) {
                    // Disable dates after today
                    setDisable(true);
                    setStyle("-fx-background-color: #808080;"); // Optional: Apply a different style to disabled dates
                }
            }
        });
    }

    private void getCateg() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
        // connecting database
        categValues.clear();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");

        PreparedStatement p = con.prepareStatement("select * from budget where user_id="+AlertConnector.user+";");
        ResultSet rs = p.executeQuery();
        System.out.println("printing now");
        while(rs.next()){
            String categ = rs.getString("category_name");
            categValues.add(categ);
        }
        System.out.println(categValues);
        con.close();
    }

    @FXML
    void Add_Trans(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
        LocalDate date = Trans_addDate.getValue();
        String type = Combo_type.getValue();
        String categ = Combo_categ.getValue();
        String amt = Trans_addAmt.getText();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        PreparedStatement ps = con.prepareStatement("insert into transactions (transactiondate, amount, transactiontype, category_name, user_id) values ('"+date+"', "+amt+", '"+type+"','"+categ+"',"+AlertConnector.user+");");
        int status = ps.executeUpdate();//to execute that statement
        if (status==0){
            System.out.println("wrong");
        }
        switchToTransaction(event);
        con.close();
    }
    public void switchToDashBoard(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalDashboard.fxml"));
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
    public void switchToLoginPage(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
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
