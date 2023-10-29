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
import javafx.scene.input.MouseEvent;
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
    private TableColumn<BorrowLend, Integer> bl_id;

    @FXML
    private DatePicker BLdate;

    @FXML
    private TextField BLdesc;

    @FXML
    private TextField BLamt;

    @FXML
    private TextField BLid;

    @FXML
    private DatePicker BLdueDate;

    @FXML
    private Label AlertLabel;

    @FXML
    void addBL(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
        LocalDate date = BLdate.getValue();
        LocalDate duedate = BLdueDate.getValue();
        String type = Combo_type.getValue();
        String desc = BLdesc.getText();
        String amt = BLamt.getText();

        if(BLid.getText().compareTo("")!=0){
            AlertLabel.setText("Record Already Exists");
            return;
        }

        try{
            if(Integer.parseInt(amt)<0){
                AlertLabel.setText("Invalid Amount");
                return;
            }
        }
        catch(Exception e){
            AlertLabel.setText("Invalid Amount");
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
            PreparedStatement ps = con.prepareStatement("INSERT INTO borrow_lend (bor_len_date, bor_len_due_date, bor_len_type, bor_len_description, bor_len_amount, user_id) VALUES ('"+date+"', '"+duedate+"', '"+type+"','"+desc+"',"+amt+", "+AlertConnector.user+");");
            int status = ps.executeUpdate();//to execute that statement
            if (status==0){
                AlertLabel.setText("Invalid Entry");
            }
            con.close();
        }
        catch(Exception e)
        {
            AlertLabel.setText("Invalid Entry");
            return;
        }
        switchToBL(event);
    }

    @FXML
    void deleteBL(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        Statement stmt = con.createStatement();
        String q4 = "delete from borrow_lend where bor_len_id = ? ;" ;
        try{
            PreparedStatement pst = con.prepareStatement(q4);
            pst.setString(1, BLid.getText());
            pst.execute();
            switchToBL(event);

        }catch(Exception e){
            System.out.println("error");
        }

    }

    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        bl_ddate.setCellValueFactory(new PropertyValueFactory<BorrowLend, String>("ddate"));
        bl_type.setCellValueFactory(new PropertyValueFactory<BorrowLend, String>("type"));
        bl_id.setCellValueFactory(new PropertyValueFactory<BorrowLend, Integer>("id"));
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
                    new BorrowLend(101, "22-08-2023", "Borrowed", "Error", 0)
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
    void editBL(ActionEvent event) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        String tftype =Combo_type.getValue();
        String tfamt = BLamt.getText();
        String tfDesc = BLdesc.getText();
        LocalDate tfDdate = BLdueDate.getValue();
        LocalDate tfdate = BLdate.getValue();
        String tfid = BLid.getText();

        if(BLid.getText().compareTo("")==0){
            AlertLabel.setText("Record comparison cannot be done\nPlease select from table alongside");
            return;
        }

        try{
            if(Integer.parseInt(tfamt)<0){
                AlertLabel.setText("Invalid Amount");
                return;
            }
        }
        catch(Exception e){
            AlertLabel.setText("Invalid Amount");
        }
        try {
            changeBLData(tfid, tftype, tfamt, tfDesc, tfDdate, tfdate);
        }
        catch(Exception e)
        {
            AlertLabel.setText("Invalid Entry");
            return;
        }
        switchToBL(event);
    }

    private void changeBLData(String tfid, String tftype, String tfamt, String tfDesc, LocalDate tfDdate, LocalDate tfdate) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        Statement stmt = con.createStatement();

        // Updating database
        String q2 = "UPDATE borrow_Lend set bor_len_date = '" +tfdate+ "', bor_len_due_date = '"+ tfDdate +"', bor_len_amount = "+tfamt+", bor_len_type = '" +tftype+ "', bor_len_description = '" +tfDesc+ "' WHERE bor_len_id = " +tfid+ " and user_id ="+AlertConnector.user+";"  ;
        int z = stmt.executeUpdate(q2);

        if (z > 0)
            System.out.println("Expenses Updated");
        else
            System.out.println("ERROR OCCURRED :(");

        con.close();
    }

    @FXML
    void getSelected(MouseEvent event) {
        index = bl_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        BLid.setText(bl_id.getCellData(index).toString());
        BLdesc.setText(bl_desc.getCellData(index).toString());
        Combo_type.setValue(bl_type.getCellData(index).toString());
        BLamt.setText(bl_amt.getCellData(index).toString());
        BLdueDate.setValue(LocalDate.parse(bl_ddate.getCellData(index), DateTimeFormatter.ISO_LOCAL_DATE));
        BLdate.setValue(LocalDate.now());
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
            int id = rs1.getInt("bor_len_id");
            String ddate = rs1.getString("bor_len_due_date");
            String type = rs1.getString("bor_len_type");
            String desc = rs1.getString("bor_len_description");
            int amt = rs1.getInt("bor_len_amount");
            //System.out.println(type+"\t\t"+date+"\t\t"+amt+"\t\t"+categ);
            values.add(new BorrowLend(id, ddate, type, desc, amt));
        }
        con.close();
    }
}


