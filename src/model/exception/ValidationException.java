/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.exception;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author User
 */
public class ValidationException extends RuntimeException{
    // Add mensagens de erros especifica para cada campo do meu departamento
    private Map<String, String> mapErros = new HashMap<>();

    public Map<String, String> getMapErros() {
        return mapErros;
    }
    
    public ValidationException(String msg){
        super(msg);
    }
    
    //Adiciona mensagens de error para mostrar ao usuario caso ele cometa algum erro
    public void addError(String fieldName, String mensagemErro){
        this.mapErros.put(fieldName, mensagemErro); 
    }
    
}
