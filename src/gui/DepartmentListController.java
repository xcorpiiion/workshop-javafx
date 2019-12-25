/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import workshop.javafx.WorkshopJavaFX;

/**
 *
 * @author User
 */
public class DepartmentListController implements Initializable {

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
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        try {
            // faz a table view ajusta-se ao tamanho da janela
            Stage stage = (Stage) WorkshopJavaFX.getMainScene().getWindow();
            tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
        } catch (Exception e) {
            System.out.println("Error here: " + e.getMessage());
        }

    }

}
