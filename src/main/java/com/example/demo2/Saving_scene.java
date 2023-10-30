package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class Saving_scene implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static ArrayList<saving> save_values= new ArrayList<saving>();

    @FXML
    private TableView<saving> saving_table;

    @FXML
    private TableColumn<saving, String> save_date;

    @FXML
    private TableColumn<saving, Integer> save_saving;

    @FXML
    private TableColumn<saving, Integer> save_id;

    @FXML
    private DatePicker newSaveDate;

    @FXML
    private TextField newSaveAmount;

    @FXML
    private TextField newId;

    @FXML
    private Label TotalSaves;

    @FXML
    private Label AlertLabel;


    int index2 = -1;

    int totalSave =0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        save_id.setCellValueFactory(new PropertyValueFactory<saving, Integer>("id"));
        save_date.setCellValueFactory(new PropertyValueFactory<saving, String>("date"));
        save_saving.setCellValueFactory(new PropertyValueFactory<saving, Integer>("saving"));
        //save_values.add(new saving("", 50000));
        ObservableList<saving> save_list;
        try {
            giveSave();
            save_list = FXCollections.observableArrayList(save_values);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | IOException e) {
            System.out.println("error occured ="+e);
            save_list = FXCollections.observableArrayList(
                    new saving(0,"0", 0)
            );
        }
        TotalSaves.setText(String.valueOf(totalSave));
        saving_table.setItems(save_list);

        newSaveDate.setDayCellFactory(picker -> new DateCell() {
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


    @FXML
    public void getSelectedSave(javafx.scene.input.MouseEvent event){
        index2 = saving_table.getSelectionModel().getSelectedIndex();
        if(index2<=-1){
            return;
        }
        newSaveAmount.setText(save_saving.getCellData(index2).toString());
        newId.setText(save_id.getCellData(index2).toString());
        newSaveDate.setValue(LocalDate.parse(save_date.getCellData(index2), DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @FXML
    void deleteSave(ActionEvent event)  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        Statement stmt = con.createStatement();
        String q4 = "delete from savings where saving_id = ?" ;
        try{
            PreparedStatement pst = con.prepareStatement(q4);
            pst.setString(1, newId.getText());
            pst.execute();
            switchToSave(event);

        }catch(Exception e){}
    }
    private void giveSave() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
        save_values.clear();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");

        PreparedStatement p = con.prepareStatement("select * from savings where user_id="+AlertConnector.user+";");
        ResultSet rs = p.executeQuery();
        System.out.println("printing now");
        while(rs.next()){
            int id = rs.getInt("saving_id");
            String date = rs.getString("savingsdate");
            int saving = rs.getInt("amount");
            System.out.println(saving+"\t\t"+date);
            totalSave+=saving;
            save_values.add(new saving(id, date, saving));
        }
        con.close();
    }

    @FXML
    public void ApplySaveChanges(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        // checks the login is valid or not

        String tfsaveamount = newSaveAmount.getText();
        LocalDate tfsavedate = newSaveDate.getValue();
        String tfid = newId.getText();
        try{
            if(Integer.parseInt(tfsaveamount)<0){
                AlertLabel.setText("Invalid Amount");
                return;
            }
        }
        catch(Exception e){
            AlertLabel.setText("Invalid Amount");
            return;
        }
        try {
            changeSaveData(tfsaveamount,tfsavedate,tfid);
        }
        catch(Exception e)
        {
            AlertLabel.setText("Invalid Entry");
            return;
        }
        switchToSave(event);
    }

    public static void changeSaveData(String amt, LocalDate date, String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
            Statement stmt = con.createStatement();
            // Updating database
            String q3 = "UPDATE Savings set savingsdate = '" +date+ "', amount = "+ amt +" WHERE saving_id = " +id+ " and user_id ="+AlertConnector.user+";"  ;
            int y = stmt.executeUpdate(q3);

            if (y > 0)
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
    public void switchToAddSave(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("finalAddSaving.fxml"));
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