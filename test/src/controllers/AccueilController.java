/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Utilisateur;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AccueilController implements Initializable {

    @FXML
    private AnchorPane parent;

    private double x = 0, y = 0;
    @FXML
    private HBox btn_gest_cat;

    public static Utilisateur recupererUtilisateurConnecte;
    @FXML
    private BorderPane borderpane;
    public static int id_user;
    @FXML
    private Label username_utilisateur;
    @FXML
    private Button logout_btn;
    @FXML
    private HBox btn_gest_admin;
    @FXML
    private HBox btn_gest_part;
    @FXML
    private HBox btn_gest_client;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // makeDrag() ;

        /*id_user = f.getUser();
         Utilisateur u = new Utilisateur();
         u.setId_Utilisateur(id_user);
         System.out.println( );*/
    }

    @FXML
    private void CategorieLoad(MouseEvent event) {
        loadUI("GestionCategorie");
    }

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

    @FXML
    private void LogoutAction(ActionEvent event) {

        Stage stage = (Stage) logout_btn.getScene().getWindow();
        stage.close();
        loadUI("Login");

    }

    @FXML
    private void ClientLoad(MouseEvent event) {
        loadUI("GestionClient");
    }

    @FXML
    private void AdminLoad(MouseEvent event) {
        loadUI("GestionAdmin");
    }

    @FXML
    private void PartenaireLoad(MouseEvent event) {
        loadUI("GestionPartenaire");
    }

}
/*  private void makeDrag() {
 parent.setOnMousePressed(((event) -> {
 x = event.getSceneX();
 y = event.getSceneY();
 }));

 parent.setOnMouseDragged(((event) -> {
 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

 stage.setX(event.getScreenX() - x);
 stage.setY(event.getScreenY() - y);

 stage.setOpacity(0.8f);
 }));

 parent.setOnDragDone(((event) -> {
 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
 stage.setOpacity(1.0f);
 }));

 parent.setOnMouseReleased(((event) -> {
 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
 stage.setOpacity(1.0f);
 }));

 }
 */
