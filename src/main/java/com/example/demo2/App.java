package com.example.demo2;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException{
        //to open the starting page
        Parent root;
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
        Stage stage = new Stage();
        stage.setWidth(1270);
        stage.setHeight(720);
        Image logo = new Image("file:src/main/resources/com/example/demo2/projectLogoUsed.png");
        primaryStage.getIcons().add(logo);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        //title of the app
        primaryStage.setTitle("Expense Ease");
        primaryStage.setScene(scene);
        primaryStage.show();

    }




    public static void main(String[] args){
        launch(args);
    }
}