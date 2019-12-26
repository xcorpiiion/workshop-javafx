/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.util;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;


/**
 *
 * @author User
 */
public class Utils {
    
    public static Stage currentStage(ActionEvent actionEvent){
        return (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }
    
    // Vai tentar converter um string para número, caso ele não consiga, vai retornar um valor nullo
    public static Integer tryParseToInt(String str){
        try {
            return  Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
        
    }
    
}
