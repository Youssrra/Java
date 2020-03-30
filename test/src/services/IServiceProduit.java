/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;

/**
 *
 * @author ASUS
 * @param <P>
 */
public interface IServiceProduit<P> {
    
    void insertProduit(P p);
    List<P> getAllProduit();
    public void supprimer(int x);
    
}
