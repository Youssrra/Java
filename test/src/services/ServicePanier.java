/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import models.Categorie;
import models.Panier;
import models.Produit;
import models.Utilisateur;

/**
 *
 * @author ASUS
 */
public class ServicePanier {

    public static ArrayList<Panier> articles;
    Connection con = ServiceLogin.creationConnexion();

    public void ajouterArticle(Panier p) {

        PreparedStatement preparedStatement = null;
        try {
            String st = "INSERT INTO panier (id_produit,nom_produit,image_produit,qt_produit,qt_total_produit,prix_produit,nom_client) VALUES (?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setInt(1, p.getId_produit());
            preparedStatement.setString(2, p.getNom_produit());
            preparedStatement.setString(3, p.getImage_produit());
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, p.getQuantite_produit());
            preparedStatement.setDouble(6, p.getPrix_produit());
            preparedStatement.setString(7, p.getNom_client());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* public Produit getArticle(int idp) {
       
     String sql = "SELECT * from produit where id=" + "'" + idp + "'";
     List<Produit> list = new ArrayList<>();
     try {
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery(sql);

     while (rs.next()) {
     Produit ps = new Produit();
     ps.setId(rs.getInt("id"));
     ps.setNom(rs.getString("nom"));
     ps.setPrix(rs.getDouble("prix"));
     ps.setQuantite(rs.getInt("quantite"));
     ps.setReference(rs.getString("reference"));
     ps.setImage(rs.getString("image"));
     list.add(ps);
     }

     } catch (SQLException ex) {
     Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
     }
     return list;
     }*/
    public List<Panier> getArticle(String c) {

        List<Panier> list = new ArrayList<Panier>();
        String requete = "select * from panier where nom_client=" + "'" + c + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(requete);

            while (rs.next()) {
                Panier p = new Panier();
                p.setId_produit(rs.getInt("id_produit"));
                p.setNom_produit(rs.getString("nom_produit"));
                p.setImage_produit(rs.getString("image_produit"));
                p.setQuantite_produit(rs.getInt("qt_produit"));
                p.setQt_total_produit(rs.getInt("qt_total_produit"));
                p.setPrix_produit(rs.getDouble("prix_produit"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void supprimerArticle(int id) {

        String sql = "DELETE FROM panier WHERE id_produit = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            System.out.println("Categorie Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Categorie non Supprimer");

    }

    public List<Categorie> getInfo(int c) {
        String sql = "SELECT * from categorie where id=" + "'" + c + "'";
        List<Categorie> list = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Categorie cc = new Categorie();
                cc.setId(rs.getInt(1));
                cc.setNom(rs.getString(2));
                cc.setDescription(rs.getString(3));
                list.add(cc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void modifierQuantiter(int id, int nq) {
        String sql = "UPDATE panier SET qt_produit=? WHERE id_produit =?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, nq);
            statement.setInt(2, id);

            statement.executeUpdate();
            System.out.println("Quantiter Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Double TotalPanier(String n) {

        String sql = "select SUM(prix_produit * qt_produit) from panier where nom_client=" + "'" + n + "'";
        Double t = null ;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            rs.next();
            String sum = rs.getString(1);
            System.out.println(sum);
            t = Double.valueOf(sum);

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;

    }
    
     public Double TotalUniter(int id) {

        String sql = "select SUM(prix_produit * qt_produit) from panier where id_produit=" + "'" + id + "'";
        Double t = null ;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            rs.next();
            String sum = rs.getString(1);
            System.out.println(sum);
            t = Double.valueOf(sum);

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;

    }

}
