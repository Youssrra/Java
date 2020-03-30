/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Produit;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailProduitController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Label id_produit;
    @FXML
    private Label nom_produit;
    @FXML
    private Label reference_produit;
    @FXML
    private Label marque_produit;
    @FXML
    private Label categorie_produit;
    @FXML
    private Label quantiter_produit;
    @FXML
    private Label prix_produit;
    @FXML
    private Label description_produit;
    @FXML
    private ImageView image_produit;
    @FXML
    private Label image_nom_produit;

    private Produit produit;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Button btn_close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void modifier(ActionEvent event) {
        /* try {
             
         Produit produit = produit(reference_produit.getText(),) ;
         FXMLLoader Loader = new FXMLLoader();
         Loader.setLocation(getClass().getResource("/views/ModifierUnProduit.fxml"));
         Parent p = Loader.load();
         ModifierProduitController display = Loader.getController();
         display.setProduit(produit);
         Dialog dialog = new Dialog();
         dialog.getDialogPane().setContent(p);
         dialog.initStyle(StageStyle.UNDECORATED);
         dialog.show();
         } catch (IOException ex) {
         Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    public void setProduit(Produit p) throws SQLException {
        produit = p;
         String categorie = "booo";
        //String categorie = categorie_nom(produit.getId_categorie());; //
    

        String prix = String.valueOf(produit.getPrix());
        String qantite = String.valueOf(produit.getQuantite());

        System.out.println(produit);
        reference_produit.setText(produit.getReference());
        nom_produit.setText(produit.getNom());
        prix_produit.setText(prix + " DT");
        quantiter_produit.setText(qantite);
        marque_produit.setText(produit.getMarque());
        categorie_produit.setText(categorie);
        description_produit.setText(produit.getDescription());
        id_produit.setText(String.valueOf(produit.getId()));

        System.out.println(produit.getImage());
        File file = new File("./src/images/" + produit.getImage());
        System.out.println(file.toURI().toString());
        image_produit.setImage(new Image(file.toURI().toString()));
        image_nom_produit.setText(produit.getImage());

    }

    @FXML
    private void Close_btn(ActionEvent event) {

        Stage stage = (Stage) btn_close.getScene().getWindow();

        stage.close();
    }

    private String categorie_nom(int id) throws SQLException {
        int idd = id;
         Statement st = con.createStatement();
        resultSet = st.executeQuery("select * from categorie where id =" + "'" + id + "'");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
            String c = resultSet.getString(2);
            return c;

        }
        return null;

    }

}
