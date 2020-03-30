/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.HashMap;
import java.util.List;
import models.Categorie;

/**
 *
 * @author ASUS
 * @param <C>
 */
public interface IService<C> {
    /*void insert(C c);
     //public HashMap<String,String,Integer> selectAll();
     void delete(C c);
     void edit (C c) ;*/

    public void insert(Categorie c);

    public void supprimer(int x);

    List<Categorie> selectAll();

}
