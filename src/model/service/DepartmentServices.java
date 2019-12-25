/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.ArrayList;
import java.util.List;
import model.entities.Department;

/**
 *
 * @author User
 */
public class DepartmentServices {
    
    /* Cria uma lista de departamentos e retorna os departamentos criado */
    public List<Department>  findAll(){
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1, "Livros"));
        departmentList.add(new Department(2, "Operacional"));
        departmentList.add(new Department(3, "Eletrico"));
        
        return departmentList;
    }
    
}
