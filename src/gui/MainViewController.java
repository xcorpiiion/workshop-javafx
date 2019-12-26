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
import java.util.function.Consumer;
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

    @FXML // ao clicar no departamento, chama o fxml do departamento
    public void onMenuItemDepartamentoAction() {
        /* Carrega a tela através do link que eu estou enviando como argumento.
        Ele usa uma expressão LAMBDA para fazer a inicialização do departmentListController*/
        loadView("/gui/DepartmentList.fxml", (DepartmentListController departmentListController) -> {
            /* A partir da variavel departmentListController eu consigo fazer uma injeção de dependencia dentro dele */
            departmentListController.setDepartmentServices(new DepartmentServices());
            /* Após fazer a injeção de dependencia dentro do departmentListController, eu consigo atualizar a TableView
            e assim eu posso mostrar os dados do departamento na tela */
            departmentListController.updateTableView();
        });
    }

    @FXML // ao clicar no ajuda, chama o fxml do ajuda que é o 
    public void onMenuItemAboutAction() {
        /* Carrega a tela através do link que eu estou enviando como argumento 
        Eu sou obrigado a usar uma expressão LAMBDA como parametro, mas como essa tela não faz nada, então eu não faço nada
        dentro da expressão LAMBDA*/
        loadView("/gui/About.fxml", x -> {});
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
    }

    // Cria a tela e mostra ao meu usuario
    // Eu uso um Consumer que é um generic, que serve para eu poder usar as expressões LAMBDA, pois ele me permite
    // Usar um tipo qualquer na função
    /* Eu fiz a função ser generica pois assim eu não terei necessidade de criar outras loadView
    pois para cada tela, eu teria uma loadview diferente, para evitar isso eu deixei a minha loadView como um metodo generico
    que vai retornar o tipo de dado que será mandado para mim como argumento.*/
    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializinAction) {
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
            
            /* O getController vai retornar o controlador do tipo que eu chamar, por exemplo
            quando eu chamar a função loadView, o getController vai retornar o tipo que será mandado como argumento
            como por exemplo na expressão LAMBDA que foi criada no metodo onMenuItemDepartamentoAction
            ele vai retornar um departmentList, pois eu fiz isso na expressão LAMBDA.
            Caso fosse um outro controller, ele retornaria um outro controller que seria mandado como argumento.*/
            T controller = fXMLLoader.getController();
            // A função accept como o proprio nome diz, ele 'aceita', ou seja, ela permite que a função que eu mandar
            // argumento seja aceita e execute.
            initializinAction.accept(controller);
            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", null, e.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

}
