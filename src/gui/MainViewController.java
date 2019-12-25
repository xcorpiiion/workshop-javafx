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
import model.service.DepartmentServices;
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
        /* Carrega a tela através do link que eu estou enviando como argumento */
        loadView2("/gui/DepartmentList.fxml");
    }

    @FXML
    public void onMenuItemAboutAction() {
        /* Carrega a tela através do link que eu estou enviando como argumento */
        loadView("/gui/About.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
    }

    private synchronized void loadView(String absoluteName) {
        try {
            // carrega a tela através do nome do arquivo que esta na variavel absotute name
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource(absoluteName));
            // faz o vBox iniciar
            VBox vBox = fXMLLoader.load();
            
            // pega o returno da cena principal que é o nome da cena
            Scene mainScene = WorkshopJavaFX.getMainScene();
            /* converte primeiro converte para um ScrollPane, assim acessa o mainScene e pega o conteudo do content e convert
            para um vbox */
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            /* Pega todo o conteudo do mainMenuVBob, ou seja, ele pega todas as interfaces e manda para a variavel mainMenu*/
            Node mainMenu = mainVBox.getChildren().get(0);
            /* Limpa toda a interface */
            mainVBox.getChildren().clear();
            /* Passa toda a interface para a pareavel mainVBox, pois a variavel mainMenu recebeu todo o conteudo de mainVBix
            antes de ser limpada, ou seja, foi feita uma troca de valores como se fosse uma variavel n1 = null, n2 = 2
            n1 = n2; n2 = null; n2 = n1; ou seja, teve uma troca de valores*/
            mainVBox.getChildren().add(mainMenu);
            /* Adiciona todas as interfaces dentro de vBox para a variavel mainVBox */
            mainVBox.getChildren().addAll(vBox.getChildren());
            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", null, e.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    
    private synchronized void loadView2(String absoluteName) {
        try {
            // carrega a tela através do nome do arquivo que esta na variavel absotute name
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource(absoluteName));
            // faz o vBox iniciar
            VBox vBox = fXMLLoader.load();
            
            // pega o returno da cena principal que é o nome da cena
            Scene mainScene = WorkshopJavaFX.getMainScene();
            /* converte primeiro converte para um ScrollPane, assim acessa o mainScene e pega o conteudo do content e convert
            para um vbox */
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            /* Pega todo o conteudo do mainMenuVBob, ou seja, ele pega todas as interfaces e manda para a variavel mainMenu*/
            Node mainMenu = mainVBox.getChildren().get(0);
            /* Limpa toda a interface */
            mainVBox.getChildren().clear();
            /* Passa toda a interface para a pareavel mainVBox, pois a variavel mainMenu recebeu todo o conteudo de mainVBix
            antes de ser limpada, ou seja, foi feita uma troca de valores como se fosse uma variavel n1 = null, n2 = 2
            n1 = n2; n2 = null; n2 = n1; ou seja, teve uma troca de valores*/
            mainVBox.getChildren().add(mainMenu);
            /* Adiciona todas as interfaces dentro de vBox para a variavel mainVBox */
            mainVBox.getChildren().addAll(vBox.getChildren());
            
            /* Com a variavel FXMLLoader eu consigo pegar uma REFERENCIA do CONTROLLER desta view */
            DepartmentListController departmentListController = fXMLLoader.getController();
            /* A partir da variavel departmentListController eu consigo fazer uma injeção de dependencia dentro dele */
            departmentListController.setDepartmentServices(new DepartmentServices());
            /* Após fazer a injeção de dependencia dentro do departmentListController, eu consigo atualizar a TableView
            e assim eu posso mostrar os dados do departamento na tela */
            departmentListController.updateTableView();
            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", null, e.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

}
