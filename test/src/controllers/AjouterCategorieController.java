/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Categorie;
import services.CategorieService;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AjouterCategorieController implements Initializable {

    @FXML
    private Button btn_close;
    @FXML
    private JFXTextField nom_categorie;
    @FXML
    private Label errors_nom;
    @FXML
    private JFXTextArea description_categorie;
    @FXML
    private Label errors_description;
    @FXML
    private JFXButton ajouter_categorie_btn;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void Close_btn(ActionEvent event) {

        Stage stage = (Stage) btn_close.getScene().getWindow();

        stage.close();
    }

    /* private void AjouterCategorie(ActionEvent event) {

     System.out.println(description_categorie.getText() + nom_categorie.getText());
     String nom = nom_categorie.getText();
     String description = description_categorie.getText();
     Categorie c = new Categorie(nom ,description);
     CategorieService categorieService = new CategorieService();
     categorieService.insert(c);
     new Alert(Alert.AlertType.INFORMATION, "sucess").show();
     }*/
    @FXML
    private void AjouterCategorie(ActionEvent event) {

        if (nom_categorie.getText().isEmpty() || description_categorie.getText().isEmpty()) {
            if (nom_categorie.getText().isEmpty()) {
                errors_nom.setTextFill(Color.TOMATO);
                errors_nom.setText("Le nom ne peut pas étre un champ vide");
            } else if (description_categorie.getText().isEmpty()) {
                errors_description.setTextFill(Color.TOMATO);
                errors_description.setText("La description ne peut pas étre un champ vide");
            } else {
                errors_nom.setTextFill(Color.TOMATO);
                errors_nom.setText("Le nom ne peut pas étre un champ vide");
                errors_description.setTextFill(Color.TOMATO);
                errors_description.setText("La description ne peut pas étre un champ vide");
            }
        } else {

            try {
                con = ConnectionUtil.conDB();

                String st = "INSERT INTO categorie (nom,description) VALUES (?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setString(1, nom_categorie.getText());
                preparedStatement.setString(2, description_categorie.getText());

                preparedStatement.executeUpdate();
                new Alert(Alert.AlertType.INFORMATION, "sucess").show();

                clearFields();
               // AfficherCategorie() ;

            } catch (SQLException ex) {
                Logger.getLogger(AjouterCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void clearFields() {
        nom_categorie.clear();
        description_categorie.clear();
    }
    
    
       
}
