/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.ArrayList;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

/**
 *
 * @author User
 */
public class DepartmentServices {
    
    private DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
    
    /* Cria uma lista de departamentos e retorna os departamentos criado */
    public List<Department>  findAll(){
        return departmentDao.findAll();
    }
    
}
