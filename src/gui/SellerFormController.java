/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exception.ValidationException;
import model.service.SellerServices;

/**
 *
 * @author User
 */
public class SellerFormController implements Initializable {

    private Seller entitySeller;
    private SellerServices sellerServices;
    // Essa variavel faz com que os outros objetos se inscrevam na lista e receberem o evento
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    
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
    
    public SellerServices getSellerServices() {
        return sellerServices;
    }

    public void setSellerServices(SellerServices sellerServices) {
        this.sellerServices = sellerServices;
    }

    public Seller getEntitySeller() {
        return entitySeller;
    }

    public void setEntitySeller(Seller entitySeller) {
        this.entitySeller= entitySeller;
    }

    @FXML
    public void onButtonSaveAction(ActionEvent actionEvent) {
        if (entitySeller == null) {
            throw new IllegalStateException("Error! O entitySeller está null");
        }

        if (sellerServices == null) {
            throw new IllegalStateException("Error! O sellerServices esta nullo está null");
        }
        try {
            // Chama o metodo e manda o departamento para ser salvo no banco de dados
            entitySeller = getFormData();
            // Manda o departamento para ser salvo no banco de dados
            sellerServices.saveOrUpdate(entitySeller);
            notifyDataChangeListener();
            // Esse comando serve para eu fechar a janela
            Utils.currentStage(actionEvent).close();
        } catch (DbException e) {
            Alerts.showAlert("Error save objects", "Error", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch(ValidationException e){
            serErrorMessages(e.getMapErros());
        }
    }

    @FXML
    public void onButtonCancelAction(ActionEvent actionEvent) {
        // Serve para eu fechar a minha janela
        Utils.currentStage(actionEvent).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        initializaNode();
    }

    // faz com que o ID apenas aceite números inteiros e define o maximo de caracteres para o nome.
    private void initializaNode() {
        Constraints.setTextFieldInteger(txtFieldId);
        Constraints.setTextFieldMaxLength(txtFieldId, 30);

    }

    public void updateFormDate() {
        if (this.entitySeller == null) {
            throw new IllegalAccessError("Departamento esta null");
        }
        // converte o valor do id para um tipo texto
        txtFieldId.setText(String.valueOf(this.entitySeller.getId()));
        txtFieldNome.setText(String.valueOf(this.entitySeller.getName()));
    }

    // Altera os dados e manda o departamento como retorno
    public Seller getFormData() {
        Seller seller = new Seller();

        ValidationException validationException = new ValidationException("Error mensage");
        
        seller.setId(Utils.tryParseToInt(txtFieldId.getText()));
        
        // Verifica se o campo de texto do departamento esta vazio.
        // Caso esteja, vai add uma mensagem de error ao campo map da minha classe ValidationException
        if(txtFieldNome.getText() == null || txtFieldNome.getText().trim().equals("")) {
            validationException.addError("nameError", "O nome não pode ficar vazio");
        }
        seller.setName(txtFieldNome.getText());
        
        // Caso o meu map possua 1 erro, ele vai lançar a Exception, assim informando ao usuario o erro
        if(validationException.getMapErros().size() > 0){
            throw validationException;
        }

        return seller;

    }
    
    // Permite que os meu dataChangeListeners se inscrevam na lista
    public void subscribeDataChangeListener(DataChangeListener dataChangeListener){
        this.dataChangeListeners.add(dataChangeListener);
    }

    
    private void notifyDataChangeListener() {
        for(DataChangeListener listeners : dataChangeListeners){
            listeners.onDataChange();
        }
    }
    
    private void serErrorMessages(Map<String, String> mapError){
        Set<String> fields = mapError.keySet();
        
        // Verifica se o fields contém a chave nameError, caso tenha, mostra para o usuario o que está errado através do
        // Label que foi add na tela de departamento
        if(fields.contains("nameError")) {
            labelError.setText(mapError.get("nameError"));
        }
        
    }

}
