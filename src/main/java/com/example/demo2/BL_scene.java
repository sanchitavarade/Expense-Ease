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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BL_scene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public static ArrayList<BorrowLend> values= new ArrayList<BorrowLend>();
    private static ArrayList<String> typeValues= new ArrayList<String>();
    @FXML
    private TableView<BorrowLend> bl_Table;

    @FXML
    private TableColumn<BorrowLend, String> bl_ddate;

    @FXML
    private TableColumn<BorrowLend, String> bl_type;

    @FXML
    private TableColumn<BorrowLend, String> bl_desc;
    @FXML
    private ComboBox<String> Combo_type;
    @FXML
    private TableColumn<BorrowLend, Integer> bl_amt;

    @FXML
    private DatePicker BLdate;

    @FXML
    private TextField BLdesc;

    @FXML
    private TextField BLamt;

    @FXML
    private DatePicker BLdueDate;

    @FXML
    void addBL(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
        LocalDate date = BLdate.getValue();
        LocalDate duedate = BLdueDate.getValue();
        String type = Combo_type.getValue();
        String desc = BLdesc.getText();
        String amt = BLamt.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        PreparedStatement ps = con.prepareStatement("INSERT INTO borrow_lend (bor_len_date, bor_len_due_date, bor_len_type, bor_len_description, bor_len_amount, user_id) VALUES ('"+date+"', '"+duedate+"', '"+type+"','"+desc+"',"+amt+", "+AlertConnector.user+");");
        int status = ps.executeUpdate();//to execute that statement
        if (status==0){
            System.out.println("wrong");
        }
        switchToBL(event);
        con.close();

    }

    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        bl_ddate.setCellValueFactory(new PropertyValueFactory<BorrowLend, String>("ddate"));
        bl_type.setCellValueFactory(new PropertyValueFactory<BorrowLend, String>("type"));
        bl_desc.setCellValueFactory(new PropertyValueFactory<BorrowLend, String>("desc"));
        bl_amt.setCellValueFactory(new PropertyValueFactory<BorrowLend, Integer>("amt"));
        typeValues.clear();

        ObservableList<String> TypeList;
        typeValues.add("Borrowed");
        typeValues.add("Lent");
        TypeList = FXCollections.observableArrayList(typeValues);
        Combo_type.setItems(TypeList);

        ObservableList<BorrowLend> list;
        try {
            giveBl();
            list = FXCollections.observableArrayList(values);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.out.println("error occured ="+e);
            list = FXCollections.observableArrayList(
                    new BorrowLend("22-08-2023", "Borrowed", "Error", 0)
            );
        }
        bl_Table.setItems(list);

        BLdate.setDayCellFactory(picker -> new DateCell() {
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
    public void switchToLoginPage(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private static void giveBl() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {//to throw basic exceptions
        // connecting database
        values.clear();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");

        PreparedStatement p1 = con.prepareStatement("select * from borrow_lend where user_id=" + AlertConnector.user + ";");
        ResultSet rs1 = p1.executeQuery();
        System.out.println("printing now");
        while (rs1.next()) {
            String ddate = rs1.getString("bor_len_due_date");
            String type = rs1.getString("bor_len_type");
            String desc = rs1.getString("bor_len_description");
            int amt = rs1.getInt("bor_len_amount");
            //System.out.println(type+"\t\t"+date+"\t\t"+amt+"\t\t"+categ);
            values.add(new BorrowLend(ddate, type, desc, amt));
        }
        con.close();
    }
}


