/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.util;

import javafx.scene.control.TextField;

/**
 *
 * @author User
 */
public class Constraints {

    public static void setTextFieldInteger(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                textField.setText(oldValue);
            }
        });
    }
    
    public static void setTextFieldMaxLength(TextField textField, int max) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > max) {
                textField.setText(oldValue);
            }
        });
    }

    public static void setTextFieldDouble(TextField textField, int max) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
                textField.setText(oldValue);
            }
        });
    }

}
