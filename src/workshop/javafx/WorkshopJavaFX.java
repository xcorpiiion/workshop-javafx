/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop.javafx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class WorkshopJavaFX extends Application {
    
    private static Scene mainScene;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = fXMLLoader.load();
            
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            
            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("JavaFx aplication");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public  static Scene getMainScene(){
        return mainScene;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
