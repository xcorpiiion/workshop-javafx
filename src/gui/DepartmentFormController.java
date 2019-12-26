/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.util.Constraints;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

/**
 *
 * @author User
 */
public class DepartmentFormController implements Initializable{

    private Department entityDepartment;

    public Department getEntityDepartment() {
        return entityDepartment;
    }

    public void setEntityDepartment(Department entityDepartment) {
        this.entityDepartment = entityDepartment;
    }
    
    @FXML
    private TextField txtFieldId;
    @FXML
    private TextField txtFieldNome;
    @FXML
    private Label labelError;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;
    
    @FXML
    public void onButtonSaveAction(){
        System.out.println("Salvou");
    }
    
    @FXML
    public void onButtonCancelAction(){
        System.out.println("Cancelou");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        initializaNode();
    }
    
    // faz com que o ID apenas aceite números inteiros e define o maximo de caracteres para o nome.
    private void initializaNode(){
        Constraints.setTextFieldInteger(txtFieldId);
        Constraints.setTextFieldMaxLength(txtFieldId, 30);
        
    }
    
    public void updateFormDate(){
        if(this.entityDepartment == null) {
            throw new IllegalAccessError("Departamento esta null");
        }
        // converte o valor do id para um tipo texto
        txtFieldId.setText(String.valueOf(this.entityDepartment.getId()));
        txtFieldNome.setText(String.valueOf(this.entityDepartment.getNome()));
        
        
    }
    
}
