/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

/**
 *
 * @author User
 */
public class DepartmentServices {
    
    private final DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
    
    /* Cria uma lista de departamentos e retorna os departamentos criado */
    public List<Department>  findAll(){
        return departmentDao.findAll();
    }
    
    // Esse metodo verifica se eu salvo o departamento no banco, ou se eu atualizo o departamento.
    public void saveOrUpdate(Department department){
        // Verifica se o departamento possui um ID, ou seja, se ele possuir, eu só preciso atualizar o banco
        // Caso eu não tenha um ID, ele irá inserir no banco esse departamento
        if(department.getId() == null){
            departmentDao.insert(department);
        } else{
            departmentDao.update(department);
        }
        
    }
    
}
