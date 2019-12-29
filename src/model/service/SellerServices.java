/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

/**
 *
 * @author User
 */
public class SellerServices {

    private final SellerDao sellerDao = DaoFactory.createSellerDao();

    /* Cria uma lista de vendedores e retorna os vendedores criado */
    public List<Seller> findAll() {
        return sellerDao.findAll();
    }

    // Esse metodo verifica se eu salvo o vendedor no banco, ou se eu atualizo o vendedor.
    public void saveOrUpdate(Seller seller) {
        // Verifica se o vendedor possui um ID, ou seja, se ele possuir, eu só preciso atualizar o banco
        // Caso eu não tenha um ID, ele irá inserir no banco esse vendedor
        if (seller.getId() == null) {
            sellerDao.insert(seller);
        } else {
            sellerDao.update(seller);
        }

    }

    // Remove um departamento
    public void removeSeller(Seller seller) {

        sellerDao.deleteById(seller.getId());

    }

}
