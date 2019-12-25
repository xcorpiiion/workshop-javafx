/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.service.DepartmentServices;
import workshop.javafx.WorkshopJavaFX;

/**
 *
 * @author User
 */
public class DepartmentListController implements Initializable {

    private DepartmentServices departmentServices;
    /* Esse ObservableList será associado ao TableView, assim fazendo os departamentos aparecerem na tela */
    private ObservableList<Department> observableList; 
    
    @FXML
    private TableView<Department> tableViewDepartment;
    @FXML
    private TableColumn<Department, Integer> tableColumnId;
    @FXML
    private TableColumn<Department, String> tableColumnNome;
    @FXML
    private Button btnNew;

    @FXML
    public void onButtonNewAction() {
        System.out.println("Clicou");
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        InitializableNodes();
    }

    private void InitializableNodes() {
        // esses comandos iniciam o comportamento das colunas
        // ele envia o nome das variaveis criadas no Department como argumento
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // faz a table view ajusta-se ao tamanho da janela
        // pega o tamanho da janela da cena principal e coloca na variavel stage
        Stage stage = (Stage) WorkshopJavaFX.getMainScene().getWindow();
        // ajusta o tamanho da table view de acordo com o tamanho da cena principal
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
        //***********************************************************************
    }

    public DepartmentServices getDepartmentServices() {
        return departmentServices;
    }

    public void setDepartmentServices(DepartmentServices departmentServices) {
        this.departmentServices = departmentServices;
    }
    
    /* Esse metodo é responsavel por acessar o serviço, carregar os departamentos e jogar esses departamentos dentro da
    ObservableList*/
    public  void updateTableView(){
        // caso o departamento esteja nullo, o programa vai mandar essa exception para o programador
        if(departmentServices == null){
            throw  new IllegalStateException("O departmentServices estava null");
        }
        
        // Vai retornar todos os departamentos que foram criados para dentro da variavel departmentList
        List<Department> departmentList = this.departmentServices.findAll();
        // Coloca a minha lista de departamentos dentro desta variavel, para assim poder mostrar na tela
        observableList = FXCollections.observableArrayList(departmentList);
        /* Mostrar os departamento na tela, pois os departamentos foram colocados dentro de uma ObservableList
        que é a collection padrão do FX.
        O TableView mostrará esses itens na tela através do metodo setItem, pois ele está settando os itens na tela
        no caso vai settar a minha lista de departamentos*/
        tableViewDepartment.setItems(observableList);
        
    }

}
