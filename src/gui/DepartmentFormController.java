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

/**
 *
 * @author User
 */
public class DepartmentFormController implements Initializable{

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
    
    private void initializaNode(){
        Constraints.setTextFieldInteger(txtFieldId);
        Constraints.setTextFieldMaxLength(txtFieldId, 30);
        
    }
    
}
