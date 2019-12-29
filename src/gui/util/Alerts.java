/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


/**
 *
 * @author User
 */
public class Alerts {
    
    public static void showAlert(String name, String title, String cabecalio, String conteudo, AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(cabecalio);
        alert.setContentText(conteudo);
        alert.show();
    }
    
    // Cria um Alert do tipo confirmação
    public static Optional<ButtonType> showConfirmation(String title, String content){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        return  alert.showAndWait();
    }
    
}
