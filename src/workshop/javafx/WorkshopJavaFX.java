/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop.javafx;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class WorkshopJavaFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            Parent parent = fXMLLoader.load();
            
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("JavaFx aplication");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
