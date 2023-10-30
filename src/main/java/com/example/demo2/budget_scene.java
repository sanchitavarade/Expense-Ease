package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class budget_scene implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static ArrayList<Budget> Bud_values= new ArrayList<Budget>();
    private static ArrayList<String> categValues= new ArrayList<String>();

    @FXML
    private TableView<Budget> Bud_table;

    @FXML
    private TableColumn<Budget, String> Bud_Categ;

    @FXML
    private TableColumn<Budget, Integer> Bud_Limit;

    @FXML
    private TableColumn<Budget, Integer> TotalBudTrans;

    @FXML
    private ComboBox<String> newBudCateg;

    @FXML
    private TextField newBudLimit;

    @FXML
    private Label AlertLabel;


    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Bud_Categ.setCellValueFactory(new PropertyValueFactory<Budget, String>("categ"));
        Bud_Limit.setCellValueFactory(new PropertyValueFactory<Budget, Integer>("limit"));
        TotalBudTrans.setCellValueFactory(new PropertyValueFactory<Budget, Integer>("TotalTrans"));
        ObservableList<Budget> Bud_list;
        ObservableList<String> categList;
        try {
            getCateg();
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.out.println("error occured ="+e);
        }
        categList = FXCollections.observableArrayList(categValues);
        newBudCateg.setItems(categList);
        try {
            giveBudget();
            Bud_list = FXCollections.observableArrayList(Bud_values);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | IOException e) {
            System.out.println("error occured ="+e);
            Bud_list = FXCollections.observableArrayList(
                    new Budget("Error", 0, 0)
            );
        }
        Bud_table.setItems(Bud_list);
    }

    @FXML
    void getSelected(javafx.scene.input.MouseEvent event){
        index = Bud_table.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        newBudCateg.setValue(Bud_Categ.getCellData(index).toString());
        newBudLimit.setText(Bud_Limit.getCellData(index).toString());
    }

    public void deleteBud(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        Statement stmt = con.createStatement();
        String q4 = "delete from budget where Budget_id = ?" ;
        try{
            PreparedStatement pst = con.prepareStatement(q4);
            pst.setString(1, newBudCateg.getValue().concat(Integer.toString(AlertConnector.user)));
            pst.execute();
            switchToBudget(event);

        }catch(Exception e){}
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
    void ApplyChanges(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        // checks the login is valid or not
        String tfCateg =newBudCateg.getValue();
        String tfLimit = newBudLimit.getText();
        try{
            if(Integer.parseInt(tfLimit)<0){
                AlertLabel.setText("Invalid Amount");
                return;
            }
        }
        catch(Exception e){
            AlertLabel.setText("Invalid Amount");
            return;
        }
        try {
            changeData(tfCateg, tfLimit);
        }
        catch(Exception e)
        {
            AlertLabel.setText("Invalid Entry");
            return;
        }
        switchToBudget(event);
    }
    public static void changeData(String categ, String limit) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
            Statement stmt = con.createStatement();

            // Updating database
            String q1 = "UPDATE budget set category_name = '" +categ+ "', elimit = "+ limit+" WHERE Budget_id = '"+categ.concat(Integer.toString(AlertConnector.user)) +"';"  ;
            int x = stmt.executeUpdate(q1);

            if (x > 0)
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
    private void giveBudget() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        Bud_values.clear();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");

        PreparedStatement p1 = con.prepareStatement("select * from budget where user_id ="+AlertConnector.user+";");
        ResultSet rs = p1.executeQuery();
        System.out.println("printing now");
        while(rs.next()){
            String categ = rs.getString("category_name");
            int limit = rs.getInt("elimit");
            PreparedStatement p2 = con.prepareStatement("select sum(amount) as Total from transactions group by Budget_id having Budget_id ='"+categ.concat(Integer.toString(AlertConnector.user))+"';");
            ResultSet rs2 = p2.executeQuery();
            int total=0;
            if(rs2.next())
                total = rs2.getInt("Total");
            System.out.println(limit+"\t\t"+categ+"\t\t"+total);
            Bud_values.add(new Budget(categ, limit, total));
        }
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

    @FXML
    public void switchToLoginPage(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToPie(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("finalPieChart.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToBar(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("finalBarChart.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}