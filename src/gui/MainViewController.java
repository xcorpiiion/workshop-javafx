/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import workshop.javafx.WorkshopJavaFX;

/**
 *
 * @author User
 */
public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemVendedor;
    @FXML
    private MenuItem menuItemDepartamento;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemVendedorAction() {
        System.out.println("onMenuItemVendedorAction");
    }

    @FXML
    public void onMenuItemDepartamentoAction() {
        System.out.println("onMenuItemDepartamentoAction");
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
    }

    private synchronized void loadView(String absoluteName) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox vBox = fXMLLoader.load();
            
            Scene mainScene = WorkshopJavaFX.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(vBox.getChildren());
            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", null, e.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

}
