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
import models.Categorie;
import models.Utilisateur;
import services.ServiceLogin;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class GestionClientController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private TableView<Utilisateur> liste_client;
    @FXML
    private TableColumn<?, ?> id_client;
    @FXML
    private TableColumn<?, ?> nom_client;
    @FXML
    private TableColumn<?, ?> prenom_client;
    @FXML
    private TableColumn<?, ?> email_client;
    @FXML
    private TableColumn<?, ?> username_client;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ServiceLogin serviceLogin = new ServiceLogin();
    private ObservableList<Utilisateur> masterData = FXCollections.observableArrayList();
    ObservableList<Utilisateur> ListClient = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AfficherClient();
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
        String x = ClientSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer client");
        a1.setContentText("Vous voulez vraiment supprimer ce client ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            serviceLogin.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer client");
            a2.setContentText("Client supprimé avec succés!");
            a2.show();

            ListClient.clear();
            AfficherClient();

        } else {
            a1.close();
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        
    }

    @FXML
    private void reafficher_categorie(MouseEvent event) {
        ListClient.clear();
        AfficherClient() ;
        Recherche() ;
    }

    private void AfficherClient() {

        con = ConnectionUtil.conDB();
        String role = "a:1:{i:0;s:11:\"ROLE_CLIENT\";}";
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

                ListClient.add(utilisateur);

                id_client.setCellValueFactory(new PropertyValueFactory<>("id_Utilisateur"));
                nom_client.setCellValueFactory(new PropertyValueFactory<>("nom_Utilisateur"));
                prenom_client.setCellValueFactory(new PropertyValueFactory<>("prenom_Utilisateur"));
                username_client.setCellValueFactory(new PropertyValueFactory<>("username_Utilisateur"));
                email_client.setCellValueFactory(new PropertyValueFactory<>("email"));

                liste_client.setItems(ListClient);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Recherche() {
        FilteredList<Utilisateur> filteredData = new FilteredList<>(ListClient, p -> true);
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
        sortedData.comparatorProperty().bind(liste_client.comparatorProperty());
        liste_client.setItems(sortedData);
    }

    private String ClientSelectionner() {
        String selectedItem = liste_client.getSelectionModel().getSelectedItem().getUsername_Utilisateur();
        int selectedIndex = liste_client.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

}
