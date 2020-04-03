/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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

    public Panier(int id_produit, String nom_produit, String image_produit, int quantite_produit, double prix_produit) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.quantite_produit = quantite_produit;
        this.prix_produit = prix_produit;
        this.image_produit = image_produit;
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

}
