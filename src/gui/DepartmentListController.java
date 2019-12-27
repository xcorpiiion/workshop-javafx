/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.service.DepartmentServices;
import workshop.javafx.WorkshopJavaFX;

/**
 *
 * @author User
 */
public class DepartmentListController implements Initializable, DataChangeListener {

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
    private TableColumn<Department, Department> tableColumnEdit;
    @FXML
    private Button btnNew;

    @FXML //Cria um novo departamento
    public void onButtonNewAction(ActionEvent actionEvent) {
        // A partir do actionEvent eu consigo acessar o Stage.
        Stage parentStage = Utils.currentStage(actionEvent);

        Department department = new Department();

        // manda a tela DepartmentForm para criar a caixa de dialogo e também pede o 'PAI' da caixa
        createDialogForm(parentStage, "/gui/DepartmentForm.fxml", department);
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
    public void updateTableView() {
        // caso o departamento esteja nullo, o programa vai mandar essa exception para o programador
        if (departmentServices == null) {
            throw new IllegalStateException("O departmentServices estava null");
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
        // Esse metodo vai adicionar um botão com o texto 'edit' em cada linha da tabela
        initEditButtons();

    }

    // Cria uma caixa de dialogo na tela do usuario
    public void createDialogForm(Stage parentStage, String absoluteName, Department department) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = fXMLLoader.load();

            // cria a instancia do departamento
            DepartmentFormController departmentFormController = fXMLLoader.getController();
            // Manda o departamento para ser tratado pela classe
            departmentFormController.setEntityDepartment(department);
            // Cria a dependencia do departmentServices
            departmentFormController.setDepartmentServices(new DepartmentServices());
            // Esse comando faz com que eu me inscreva, para receber o evento
            // ou seja, quando o evento for disparado, eu estarei pegando a interface que foi implementada aqui
            // fazendo com que os meus dados sejam atualizados
            departmentFormController.subscribeDataChangeListener(this);
            // Atualiza os dados
            departmentFormController.updateFormDate();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Informe o nome do departamento");
            /* Coloca a cena na frente da cena já existente */
            dialogStage.setScene(new Scene(pane));
            /* verifica se a janela pode ou não ser redimencionada */
            dialogStage.setResizable(false);
            /* Verifica quem é o 'PAI' da janela */
            dialogStage.initOwner(parentStage);
            /* Verifica se a janela é modal, ou seja, se vai travar, no caso eu não posso acessar outra janela enquanto
            não fechar esta mesma janela */
            dialogStage.initModality(Modality.WINDOW_MODAL);
            /* Mostra na tela e espera uma ação ser executada */
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error Loading view", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override //Sempre que eu chamar esse metodo, eu vou atualizar os dados da minha tabela.
    // Esse metodo será chamado no momento em que eu apertar no botão salvar
    public void onDataChange() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEdit.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> createDialogForm(Utils.currentStage(event), "/gui/DepartmentForm.fxml", obj));
            }
        });
    }

}
