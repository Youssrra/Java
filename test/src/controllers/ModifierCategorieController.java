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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Categorie;
import services.CategorieService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ModifierCategorieController implements Initializable {

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
    private JFXButton modifier_categorie_btn;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private Categorie categorie;
    @FXML
    private JFXTextField id_categorie_modifier;

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

    @FXML
    private void ModifierCategorie(ActionEvent event) {

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

            CategorieService cs = new CategorieService();
            String nom = nom_categorie.getText();
            String description = description_categorie.getText();
            int id = Integer.valueOf(id_categorie_modifier.getText());

            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
            a1.setTitle("Modification d'une catégorie");
            a1.setContentText("vous voulez vraiment Mdofier cette catégorie ?");
            Optional<ButtonType> result = a1.showAndWait();
            if (result.get() == ButtonType.OK) {
                cs.modifier(id, nom, description);

            } else if (result.get() == ButtonType.CANCEL) {

            }

        }
    }

    public void setCategorie(Categorie c) {
        categorie = c;
        System.out.println(c) ;
        nom_categorie.setText(categorie.getNom());
        description_categorie.setText(categorie.getDescription());
        id_categorie_modifier.setText(String.valueOf(categorie.getId()));
    }

}
