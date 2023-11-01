package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
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
import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;

public class Trans_Scene implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static ArrayList<Transactions> values= new ArrayList<Transactions>();
    private static ArrayList<String> categValues= new ArrayList<String>();
    private static ArrayList<String> typeValues= new ArrayList<String>();

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
    private TableColumn<Transactions, String> Trans_date;

    @FXML
    private ComboBox<String> Combo_Searchtype;

    @FXML
    private ComboBox<String> Combo_Searchcateg;

    @FXML
    private ComboBox<String> Combo_categ;

    @FXML
    private ComboBox<String> Combo_type;

    @FXML
    private TextField newTransId;

    @FXML
    private TextField newTransAmt;

    @FXML
    private DatePicker newTransDate;

    @FXML
    private TextField search_amt;

    @FXML
    private DatePicker search_date;

    @FXML
    private Label amtLabel;

    @FXML
    private Label searchLabel;

    int index = -1;

    @FXML
    void getSelected(javafx.scene.input.MouseEvent event){
        index = Trans_table.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        newTransId.setText(Trans_id.getCellData(index).toString());
        Combo_type.setValue(Trans_type.getCellData(index).toString());
        newTransAmt.setText(Trans_amt.getCellData(index).toString());
        Combo_categ.setValue(Trans_categ.getCellData(index).toString());
        newTransDate.setValue(LocalDate.parse(Trans_date.getCellData(index), DateTimeFormatter.ISO_LOCAL_DATE));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Trans_id.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("id"));
        Trans_type.setCellValueFactory(new PropertyValueFactory<Transactions, String>("type"));
        Trans_amt.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("amt"));
        Trans_categ.setCellValueFactory(new PropertyValueFactory<Transactions, String>("categ"));
        Trans_date.setCellValueFactory(new PropertyValueFactory<Transactions, String>("date"));

        typeValues.clear();
        ObservableList<String> categList;
        ObservableList<String> TypeList;
        typeValues.add("Expense");
        typeValues.add("Income");
        TypeList = FXCollections.observableArrayList(typeValues);
        Combo_type.setItems(TypeList);
        Combo_Searchtype.setItems(TypeList);

        ObservableList<Transactions> list;
        try {
            giveTrans();
            getCateg();
            if(AlertConnector.tfTransamt.compareTo("")!=0){
                Iterator itr = values.iterator();
                int compare = Integer.parseInt(AlertConnector.tfTransamt);
                while(itr.hasNext()){
                    Transactions value = (Transactions)itr.next();
                    if((value.getAmt()<compare*0.9)||(value.getAmt()>compare*1.1)){
                        itr.remove();
                    }
                }
                AlertConnector.tfTransamt="";
            }
            else {
                if (AlertConnector.tfTranscateg.compareTo("") != 0) {
                    Iterator itr = values.iterator();
                    while (itr.hasNext()) {
                        Transactions value = (Transactions) itr.next();
                        if (value.getCateg().compareTo(AlertConnector.tfTranscateg) != 0) {
                            itr.remove();
                        }
                    }
                    AlertConnector.tfTranscateg = "";
                }
                if (AlertConnector.tfTranstype.compareTo("") != 0) {
                    Iterator itr = values.iterator();
                    while (itr.hasNext()) {
                        Transactions value = (Transactions) itr.next();
                        if (value.getType().compareTo(AlertConnector.tfTranstype) != 0) {
                            itr.remove();
                        }
                    }
                    AlertConnector.tfTranstype = "";
                }
                if (AlertConnector.tfTransdate.compareTo("") != 0) {
                    Iterator itr = values.iterator();
                    while (itr.hasNext()) {
                        Transactions value = (Transactions) itr.next();
                        if (value.getDate().compareTo(AlertConnector.tfTransdate) != 0) {
                            itr.remove();
                        }
                    }
                    AlertConnector.tfTransdate = "";
                }
            }
            categList = FXCollections.observableArrayList(categValues);
            Combo_categ.setItems(categList);
            Combo_Searchcateg.setItems(categList);

            list = FXCollections.observableArrayList(values);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.out.println("error occured ="+e);
            list = FXCollections.observableArrayList(
                    new Transactions(0,"Error", 0, "Transportation", "22-08-23")
            );
        }
        Trans_table.setItems(list);

        newTransDate.setDayCellFactory(picker -> new DateCell() {
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

        search_date.setDayCellFactory(picker -> new DateCell() {
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
    public void searchTrans(ActionEvent event) throws IOException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions
        try{
            if(Integer.parseInt(search_amt.getText())<0){
                searchLabel.setText("Invalid Amount");
                System.out.println(search_amt.getText());
                return;
            }
        }
        catch(Exception e){
            searchLabel.setText("Invalid Entry");
        }
        AlertConnector.tfTransamt=AlertConnector.tfTransamt.concat(search_amt.getText());
        if(Combo_Searchcateg.getValue()!=null) {
            AlertConnector.tfTranscateg = AlertConnector.tfTranscateg.concat(Combo_Searchcateg.getValue());
        }
        if(Combo_Searchtype.getValue()!=null) {
            AlertConnector.tfTranstype = AlertConnector.tfTranstype.concat(Combo_Searchtype.getValue());
        }
        final DateTimeFormatter dft= DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            AlertConnector.tfTransdate = AlertConnector.tfTransdate.concat(dft.format(search_date.getValue()));
        }
        catch(Exception e) {
            System.out.println("No date entered"+ e);
        }
        switchToTransaction(event);
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
            String categ = rs.getString("category_name");
            System.out.println(id+"\t\t"+type+"\t\t"+date+"\t\t"+amt+"\t\t"+categ);
            values.add(new Transactions(id, type, amt, categ, date));
        }
        con.close();
    }

    @FXML
    void ApplyTransChanges(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        // checks the login is valid or not
        String tfid = newTransId.getText();
        String tftype =Combo_type.getValue();
        String tfamt = newTransAmt.getText();
        String tfcateg = Combo_categ.getValue();
        LocalDate tfdate = newTransDate.getValue();
        try{
            if(Integer.parseInt(tfamt)<0){
                amtLabel.setText("Invalid Amount");
                return;
            }
        }
        catch(Exception e){
            amtLabel.setText("Invalid Amount");
        }
        if(tftype.compareTo("Expense")==0)
            check_limit(tfcateg, Integer.parseInt(tfamt));
        try {
            changeTransData(tfid, tftype, tfamt, tfcateg, tfdate);
        }
        catch(Exception e)
        {
            amtLabel.setText("Invalid Entry");
            return;
        }
        switchToTransaction(event);
    }

    public boolean check_limit(String categ, int amt) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");

        PreparedStatement p1 = con.prepareStatement("select elimit from budget where Budget_id ='"+categ.concat(Integer.toString(AlertConnector.user))+"';");
        ResultSet rs1 = p1.executeQuery();
        int limit = 7500;
        if(rs1.next())
            limit = rs1.getInt("elimit");
        PreparedStatement p2 = con.prepareStatement("select sum(amount) as Total from transactions group by Budget_id, transactiontype, transaction_id having transaction_id not in ("+newTransId.getText()+") and transactiontype='Expense' and Budget_id ='"+categ.concat(Integer.toString(AlertConnector.user))+"';");
        ResultSet rs2 = p2.executeQuery();
        int total=0;
        if(rs2.next())
            total = rs2.getInt("Total");
        total +=amt;
        if(total>limit) {
            AlertConnector.BudgetBeyond();
            return false;
        }
        return true;
    }

    public void changeTransData(String id, String type, String amt, String categ, LocalDate date) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{//to throw basic exceptions

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        Statement stmt = con.createStatement();

        // Updating database
        String q2 = "UPDATE transactions set transactiontype = '" +type+ "', amount = "+ amt +", category_name = '" +categ+ "', transactiondate = '" +date+ "',Budget_id= '"+categ.concat(Integer.toString(AlertConnector.user))+"' WHERE transaction_id = " +id+ " and user_id ="+AlertConnector.user+";"  ;
        int z = stmt.executeUpdate(q2);

        if (z > 0)
            System.out.println("Expenses Updated");
        else
            System.out.println("ERROR OCCURRED :(");

        con.close();
    }

    public void deleteTrans(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
        Statement stmt = con.createStatement();
        String q4 = "delete from transactions where transaction_id = ? ;" ;
        try{
            PreparedStatement pst = con.prepareStatement(q4);
            pst.setString(1, newTransId.getText());
            pst.execute();
            switchToTransaction(event);

        }catch(Exception e){}

    }
    public void switchToDashBoard(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalDashboard.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToAddTrans(ActionEvent event) throws IOException{        // to switch the scene to add transaction
        root = FXMLLoader.load(getClass().getResource("finalAddTransaction.fxml"));
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