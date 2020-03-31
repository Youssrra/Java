/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import models.Utilisateur;
import services.ServiceLogin;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class GestionAdminController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private TableView<Utilisateur> liste_admin;
    @FXML
    private TableColumn<?, ?> id_admin;
    @FXML
    private TableColumn<?, ?> nom_admin;
    @FXML
    private TableColumn<?, ?> prenom_admin;
    @FXML
    private TableColumn<?, ?> email_admin;
    @FXML
    private TableColumn<?, ?> username_admin;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ServiceLogin serviceLogin = new ServiceLogin();
    private ObservableList<Utilisateur> masterData = FXCollections.observableArrayList();
    ObservableList<Utilisateur> ListAdmin = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AfficherAdmin();
        Recherche();
    }

    @FXML
    private void modifier(MouseEvent event) {
    }

    @FXML
    private void ajouter(ActionEvent event) {
    }

    @FXML
    private void supprimer(ActionEvent event) {
        String x = AdminSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer administrateur");
        a1.setContentText("Vous voulez vraiment supprimer cette administrateur ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            serviceLogin.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer administrateur");
            a2.setContentText("Administrateur supprimé avec succés!");
            a2.show();

            ListAdmin.clear();
            AfficherAdmin();

        } else {
            a1.close();
        }
    }

    @FXML
    private void reafficher_categorie(MouseEvent event) {
        ListAdmin.clear();
        AfficherAdmin() ;
        Recherche() ;
    }
    
    private void AfficherAdmin() {

        con = ConnectionUtil.conDB();
        String role = "a:1:{i:0;s:10:\"ROLE_ADMIN\";}";
        String qry = "SELECT * from fos_user where roles =" + "'" + role + "'";
        try {

            ResultSet res = con.createStatement().executeQuery(qry);

            while (res.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId_Utilisateur(res.getInt("id"));
                utilisateur.setNom_Utilisateur(res.getString("nom"));
                utilisateur.setPrenom_Utilisateur(res.getString("prenom"));
                utilisateur.setUsername_Utilisateur(res.getString("username"));
                utilisateur.setEmail(res.getString("email"));
                utilisateur.setRole_Utilisateur(res.getString("roles"));
                utilisateur.setMotDePasse_Utilisateur(res.getString("password"));

                ListAdmin.add(utilisateur);

                id_admin.setCellValueFactory(new PropertyValueFactory<>("id_Utilisateur"));
                nom_admin.setCellValueFactory(new PropertyValueFactory<>("nom_Utilisateur"));
                prenom_admin.setCellValueFactory(new PropertyValueFactory<>("prenom_Utilisateur"));
                username_admin.setCellValueFactory(new PropertyValueFactory<>("username_Utilisateur"));
                email_admin.setCellValueFactory(new PropertyValueFactory<>("email"));

                liste_admin.setItems(ListAdmin);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Recherche() {
        FilteredList<Utilisateur> filteredData = new FilteredList<>(ListAdmin, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(utilisateur -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (utilisateur.getNom_Utilisateur().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (utilisateur.getPrenom_Utilisateur().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Utilisateur> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(liste_admin.comparatorProperty());
        liste_admin.setItems(sortedData);
    }

    private String AdminSelectionner() {
        String selectedItem = liste_admin.getSelectionModel().getSelectedItem().getUsername_Utilisateur();
        int selectedIndex = liste_admin.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

}
