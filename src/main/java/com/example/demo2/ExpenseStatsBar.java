package com.example.demo2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ExpenseStatsBar {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private void addDataToChart() {
        String dbUrl = "jdbc:mysql://localhost:3306/Exp_Tracker";
        String dbUser = "root";
        String dbPassword = "oracle";

        try {

            Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = con.createStatement();

            String barChartQuery = "SELECT tc.user_id,tc.category_name, SUM(tc.amount) AS total_expense " +
                    "FROM Transactions tc " +
                    "GROUP BY tc.category_name, tc.user_id";

            ResultSet barChartResult = statement.executeQuery(barChartQuery);

            XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();

            while (barChartResult.next()) {
                if(barChartResult.getInt("user_id")==101) {
                    String category = barChartResult.getString("category_name");
                    double totalExpense = barChartResult.getDouble("total_expense");
                    barChartSeries.getData().add(new XYChart.Data<>(category, totalExpense));
                }
            }

            Platform.runLater(() -> {
                barChart.getData().clear();
                barChart.getData().add(barChartSeries);
                        barChart.setAnimated(false);
            }

            );

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void switchToLoginPage(ActionEvent event) throws IOException {         // to switch the scene to dashboard
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
}