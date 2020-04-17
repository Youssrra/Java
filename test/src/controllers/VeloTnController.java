/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.AccueilController.recupererUtilisateurConnecte;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import models.Utilisateur;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class VeloTnController implements Initializable {
    @FXML
    private BorderPane borderpane;
    @FXML
    private Button produit_btn;
    
    public static Utilisateur recupererUtilisateurConnecte;
    private String username_utilisateur;
    @FXML
    private Button panier_btn;
    @FXML
    private Label nom_utilisateur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AccueilLoad(MouseEvent event) {
    }

    @FXML
    private void accueil_btn(ActionEvent event) {
    }

    @FXML
    private void ProduitLoad(MouseEvent event) {
        loadUI("Produits") ;
    }
    
     private void loadUI(String ui) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/" + ui + ".fxml"));
             String n = recupererUtilisateurConnecte.getNom_Utilisateur();
              nom_utilisateur.setText(n);
           
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        borderpane.setCenter(root);
    }

    @FXML
    private void PanierLoad(MouseEvent event) {
          loadUI("Panier") ;
    }
    
}
