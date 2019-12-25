/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


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
    
}
