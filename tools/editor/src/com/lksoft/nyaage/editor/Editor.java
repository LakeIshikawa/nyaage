package com.lksoft.nyaage.editor;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by lake on 15/01/11.
 *
 * Main window
 */
public class Editor extends Application{

    /**
     * Main
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Nya Editor!");

        URL mainWindow = getClass().getResource("/main.fxml");
        Pane pane = FXMLLoader.load(mainWindow);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }
}
