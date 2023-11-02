package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class scene_controller implements Initializable {
    public static ArrayList<String> quotesList=new ArrayList<String>();
    @FXML
    private Label Welcome=new Label("Welcome user");
    @FXML
    private Label Quotes=new Label("Error");
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label passLabel1;

    @FXML
    private Label passLabel2;

    //inputs of login page
    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField tfPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Welcome.setText("Welcome, "+ AlertConnector.username+" !");
        quotesList.addAll(Arrays.asList("\"Savings, remember, is the prerequisite of investment.\"",
                "\"Money is a terrible master but an excellent servant.\"",
                "\"It's not your salary that makes you rich; it's your spending habits.\"",
                "\"Money, like emotions, is something you must control to keep your life on the right track.\"",
                "\"Do not save what is left after spending, but spend what is left after saving.\"",
                "\"The art is not in making money, but in keeping it.\"",
                "\"A budget is telling your money where to go instead of wondering where it went.\""));


        Random random = new Random();
        String finalQuote = quotesList.get(random.nextInt(quotesList.size()));
        Quotes.setText(finalQuote);
    }

    public void switchToLoginPage(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

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
                passLabel1.setTextFill(Color.RED);
                passLabel2.setTextFill(Color.RED);
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
                passLabel1.setTextFill(Color.RED);
                passLabel2.setTextFill(Color.RED);
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

}