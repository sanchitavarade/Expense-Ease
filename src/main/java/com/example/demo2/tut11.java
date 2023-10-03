package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class tut11 extends Application{
    public void start(Stage stage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        stage.setWidth(1270);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.setX(100);
        stage.setY(50);
        // stage.setFullScreen(true);
        // stage.setFullScreenExitHint("enter q to escape");
        // stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

