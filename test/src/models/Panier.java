/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class Panier {

    private int id_produit;
    private String nom_produit;
    private int quantite_produit;
    private double prix_produit;
    private String image_produit;
    private int qt_total_produit ; 
    private String nom_client ;

    public Panier(int id_produit, String nom_produit, String image_produit, int quantite_produit, double prix_produit,String nom_client) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.quantite_produit = quantite_produit;
        this.prix_produit = prix_produit;
        this.image_produit = image_produit;
         this.nom_client =  nom_client ;
    }
    
    public Panier(int id_produit, String nom_produit, String image_produit, int quantite_produit,int qt_total_produit ,double prix_produit) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.quantite_produit = quantite_produit;
        this.prix_produit = prix_produit;
        this.image_produit = image_produit;
        this.qt_total_produit = qt_total_produit ;
      // this.nom_client =  nom_client ;
    }

    public Panier(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getQt_total_produit() {
        return qt_total_produit;
    }

    public void setQt_total_produit(int qt_total_produit) {
        this.qt_total_produit = qt_total_produit;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }
    

    public Panier() {
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public int getQuantite_produit() {
        return quantite_produit;
    }

    public void setQuantite_produit(int quantite_produit) {
        this.quantite_produit = quantite_produit;
    }

    public double getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(double prix_produit) {
        this.prix_produit = prix_produit;
    }

    public String getImage_produit() {
        return image_produit;
    }

    public void setImage_produit(String image_produit) {
        this.image_produit = image_produit;
    }

    public void ajouterQuantite(int qte) {
        this.quantite_produit += qte;
    }

    public int getPrixPanier() {
        int prix = (int) (this.getPrix_produit() * this.getQuantite_produit());
        return prix;
    }
    private final IntegerProperty qt = new SimpleIntegerProperty();
    private final IntegerProperty Quantite_produit = new SimpleIntegerProperty();

    

    public IntegerProperty Quantite_produitProperty() {
        return Quantite_produit;
    }

   
   
 


 

   

    
    
    
    

}
