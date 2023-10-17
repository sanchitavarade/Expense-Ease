package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class scene_controller implements Initializable {
    @FXML
    private Label Welcome=new Label("Welcome user");
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToLoginPage(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private Label passLabel1;

    @FXML
    private Label passLabel2;

    public void switchToSign(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalsignup.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToDashBoard(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalDashboard.fxml"));
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
    public void switchToTransaction(ActionEvent event) throws IOException{        // to switch the scene to transaction
        root = FXMLLoader.load(getClass().getResource("finalTransaction.fxml"));
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
    private TextField tfSignUser;

    @FXML
    private PasswordField tfSignPass;

    @FXML
    private PasswordField tfSignPass1;

    @FXML
    void createAcc(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
        String signUser = tfSignUser.getText();
        String signpass = tfSignPass.getText();
        String signpass2 = tfSignPass1.getText();
        int status =0;

        if(signpass.compareTo(signpass2)==0) {
            if ((signUser.length() == 0) || (signpass.length() == 0)) {
                AlertConnector.Handle2();
                return;
            }
            if (signpass.length() < 8){
                AlertConnector.Handle4();
                return;
            }
            int flag =0;
            for (char chr: signpass.toCharArray()){
                if ((chr>32 && chr<48)||(chr>57 && chr<65)){
                    flag=1;
                    break;
                }
            }
            if (flag==0){
                AlertConnector.Handle4();
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exp_Tracker", "root", "oracle");
            PreparedStatement ps = con.prepareStatement("insert into User (Username, Password) values('"+signUser+"', '"+signpass+"');");
            status = ps.executeUpdate();//to execute that statement
            switchToLoginPage(event);
            con.close();
            return;
        }
        else if((signUser.length()==0)||(signpass.length()==0)){
            AlertConnector.Handle2();
        }
        else{
            AlertConnector.wrongPass();
        }
        if(status!=0){
            System.out.println("database was connected");
            System.out.println("record was inserted");
        }
    }

    //inputs of login page
    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField tfPass;

    @FXML
    void btnClicked(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        // checks the login is valid or not
        String email = tfEmail.getText();
        String pass = tfPass.getText();
//        System.out.println(AlertConnector.checkLogin1(email, pass));
        if(AlertConnector.checkLogin(email, pass)){
            switchToDashBoard(event);
            System.out.println("true");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Welcome.setText("Welcome, "+ AlertConnector.username+" !");
    }
}