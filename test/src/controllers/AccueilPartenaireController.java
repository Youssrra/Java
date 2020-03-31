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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Utilisateur;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AccueilPartenaireController implements Initializable {
    @FXML
    private AnchorPane parent;
    @FXML
    private BorderPane borderpane;
    @FXML
    private Label username_utilisateur;
    @FXML
    private Button logout_btn;
    @FXML
    private HBox btn_gest_cat;
    
    public static Utilisateur recupererUtilisateurConnecte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LogoutAction(ActionEvent event) {
         Stage stage = (Stage) logout_btn.getScene().getWindow();
        stage.close();
        loadUI("Login");
    }

    @FXML
    private void CategorieLoad(MouseEvent event) {
         loadUI("GestionCategorie");
    }

    @FXML
    private void ProduitLoad(MouseEvent event) {
        loadUI("GestionStock");
    }
    
    private void loadUI(String ui) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/" + ui + ".fxml"));
            String c = recupererUtilisateurConnecte.getNom_Utilisateur();
            username_utilisateur.setText(c);
            System.out.println(c);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        borderpane.setCenter(root);
    }
    
}
