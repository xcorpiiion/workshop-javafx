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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exception.ValidationException;
import model.service.DepartmentServices;
import model.service.SellerServices;

/**
 *
 * @author User
 */
public class SellerFormController implements Initializable {

    private Seller entitySeller;
    private SellerServices sellerServices;
    private DepartmentServices departmentServices;
    // Essa variavel faz com que os outros objetos se inscrevam na lista e receberem o evento
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    private ObservableList<Department> observableListDepartment;

    @FXML
    private TextField txtFieldId;
    @FXML
    private TextField txtFieldNome;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private DatePicker datePickerBirthDate;
    @FXML
    private TextField txtFieldBaseSalary;
    @FXML
    private ComboBox<Department> comboBoxDepartment;
    @FXML
    private Label labelError;
    @FXML
    private Label labelErrorEmail;
    @FXML
    private Label labelErrorBirthDate;
    @FXML
    private Label labelErrorBaseSalary;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    public SellerServices getSellerServices() {
        return sellerServices;
    }

    public void setSellerAndDepartmentServices(SellerServices sellerServices, DepartmentServices departmentServices) {
        this.sellerServices = sellerServices;
        this.departmentServices = departmentServices;
    }

    public Seller getEntitySeller() {
        return entitySeller;
    }

    public void setEntitySeller(Seller entitySeller) {
        this.entitySeller = entitySeller;
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
        } catch (ValidationException e) {
            
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
        Constraints.setTextFieldDouble(this.txtFieldBaseSalary, 0);
        Constraints.setTextFieldMaxLength(txtFieldEmail, 50);
        Utils.formatDatePicker(datePickerBirthDate, "dd/MM/yyyy");
        initializeComboBoxDepartment();
    }

    // Atualiza os dados do vendedor
    public void updateFormDate() {
        if (this.entitySeller == null) {
            throw new IllegalAccessError("Departamento esta null");
        }
        // converte o valor do id para um tipo texto
        txtFieldId.setText(String.valueOf(this.entitySeller.getId()));
        txtFieldNome.setText(String.valueOf(this.entitySeller.getName()));
        txtFieldEmail.setText(String.valueOf(this.entitySeller.getEmail()));
        Locale.setDefault(Locale.US);
        txtFieldBaseSalary.setText(String.format("%.2f", this.entitySeller.getBaseSalary()));
        if (entitySeller.getBirthDate() != null) {
            datePickerBirthDate.setValue(LocalDateTime.ofInstant(entitySeller.getBirthDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
        }
        // Verifica se o vendedor possui um departamento, caso ele não tenha eu seleciono o primeiro departamento
        if (entitySeller.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(entitySeller.getDepartment());
        }
    }

    // Altera os dados e manda o departamento como retorno
    public Seller getFormData() {
        Seller seller = new Seller();

        ValidationException validationException = new ValidationException("Error mensage");

        seller.setId(Utils.tryParseToInt(txtFieldId.getText()));

        // Verifica se o campo de texto do vendedor esta vazio.
        // Caso esteja, vai add uma mensagem de error ao campo map da minha classe ValidationException
        if (txtFieldNome.getText() == null || txtFieldNome.getText().trim().equals("")) {
            validationException.addError("nameError", "O nome não pode ficar vazio");
        }
        seller.setName(txtFieldNome.getText());

        if (txtFieldEmail.getText() == null || txtFieldEmail.getText().trim().equals("")) {
            validationException.addError("emailError", "O email não pode ficar vazio");
        }
        seller.setEmail(txtFieldEmail.getText());

        if (datePickerBirthDate.getValue() == null) {
            validationException.addError("dateError", "A data não pode ficar vazia");
        } else {
            Instant instant = Instant.from(datePickerBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            seller.setBirthDate(Date.from(instant));
        }

        if (txtFieldBaseSalary.getText() == null || txtFieldBaseSalary.getText().trim().equals("")) {
            validationException.addError("baseSalaryError", "O salario não pode ficar vazio");
        }
        seller.setBaseSalary(Utils.tryParseToDouble(txtFieldBaseSalary.getText()));
        
        // Define o departamento do vendedor baseado no valor que aparece no combo box
        seller.setDepartment(comboBoxDepartment.getValue());

        // Caso o meu map possua 1 erro, ele vai lançar a Exception, assim informando ao usuario o erro
        if (validationException.getMapErros().size() > 0) {
            throw validationException;
        }

        return seller;

    }

    // Permite que os meu dataChangeListeners se inscrevam na lista
    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    private void notifyDataChangeListener() {
        for (DataChangeListener listeners : dataChangeListeners) {
            listeners.onDataChange();
        }
    }

    public void loadAssociatedObjects() {
        if (this.departmentServices == null) {
            throw new IllegalStateException("DepartmentServices está null");
        }
        List<Department> listDepartment = this.departmentServices.findAll();
        observableListDepartment = FXCollections.observableArrayList(listDepartment);
        comboBoxDepartment.setItems(observableListDepartment);
    }

    private void serErrorMessages(Map<String, String> mapError) {
        Set<String> fields = mapError.keySet();

        // Verifica se o fields contém a chave nameError, caso tenha, mostra para o usuario o que está errado através do
        // Label que foi add na tela de departamento
        if (fields.contains("nameError")) {
            labelError.setText(mapError.get("nameError"));
        } else {
            labelError.setText("");
        }

        if (fields.contains("emailError")) {
            labelErrorEmail.setText(mapError.get("emailError"));
        } else {
         labelErrorEmail.setText("");   
        }

        if (fields.contains("baseSalaryError")) {
            labelErrorBaseSalary.setText(mapError.get("baseSalaryError"));
        } else {
            labelErrorBaseSalary.setText("");
        }

        if (fields.contains("dateError")) {
            labelErrorBirthDate.setText(mapError.get("dateError"));
        } else {
            labelErrorBirthDate.setText("");
        }

    }

    //Cria o combo box na tela
    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);
                setText(empty ? "" : department.getNome());
            }
        };

        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

}
