/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import models.Categorie;
import models.Produit;
import models.Utilisateur;
import services.CategorieService;
import services.ProduitService;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class GestionStockController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private TableView<Produit> liste_produit;
    @FXML
    private TableColumn<?, ?> id_produit;
    @FXML
    private TableColumn<?, ?> reference_produit;
    @FXML
    private TableColumn<?, ?> nom_produit;
    @FXML
    private TableColumn<?, ?> image_produit;
    @FXML
    private TableColumn<?, ?> prix_produit;
    @FXML
    private TableColumn<?, ?> quantiter_produit;
    @FXML
    private TableColumn<?, ?> categorie_produit;
    @FXML
    private TableColumn<?, ?> marque_produit;
    @FXML
    private TableColumn<?, ?> description_produit;

    private ObservableList<Produit> masterData = FXCollections.observableArrayList();
    ObservableList<Produit> ListProduit = FXCollections.observableArrayList();
    ProduitService produitService = new ProduitService();
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement ste;
    @FXML
    private JFXTextField filterField;
    public static int id_user;

    // public static Utilisateur recupererUtilisateurConnecte;
    Utilisateur recupererUtilisateurConnecter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        AfficherProduit();
        Recherche();

    }

    private void AfficherProduit() {

        int idp = AccueilPartenaireController.recupererUtilisateurConnecte.getId_Utilisateur();
        System.out.println(idp);

        con = ConnectionUtil.conDB();
        String qry = "SELECT * from produit where partenaire =" + "'" + idp + "'";
        try {

            ResultSet res = con.createStatement().executeQuery(qry);

            while (res.next()) {
                Produit produit = new Produit();
                produit.setId(res.getInt("id"));
                produit.setReference(res.getString("reference"));
                produit.setNom(res.getString("nom"));
                produit.setImage(res.getString("image"));
                produit.setPrix(res.getDouble("prix"));
                produit.setQuantite(res.getInt("quantite"));
                produit.setId_categorie(res.getInt("id_categorie"));
                produit.setMarque(res.getString("marque"));
                produit.setDescription(res.getString("description"));
                ListProduit.add(produit);

                id_produit.setCellValueFactory(new PropertyValueFactory<>("id"));
                reference_produit.setCellValueFactory(new PropertyValueFactory<>("reference"));
                nom_produit.setCellValueFactory(new PropertyValueFactory<>("nom"));
                image_produit.setCellValueFactory(new PropertyValueFactory<>("image"));
                prix_produit.setCellValueFactory(new PropertyValueFactory<>("prix"));
                quantiter_produit.setCellValueFactory(new PropertyValueFactory<>("quantite"));
                categorie_produit.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
                marque_produit.setCellValueFactory(new PropertyValueFactory<>("marque"));
                description_produit.setCellValueFactory(new PropertyValueFactory<>("description"));

                liste_produit.setItems(ListProduit);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int ProduitSelectionner() {
        int selectedItem = liste_produit.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = liste_produit.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/views/AjouterUnProduit.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

    @FXML
    private void supprimer(ActionEvent event) {

        int x = ProduitSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Suppression d'un produit");
        a1.setContentText("Vous voulez vraiment supprimer ce produit ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            produitService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Suppression d'un produit");
            a2.setContentText("Produit supprimé avec succés!");
            a2.show();

            liste_produit.getItems().clear();
            AfficherProduit();

        } else {
            a1.close();
        }
    }

    @FXML
    private void modifier(ActionEvent event) throws SQLException {
        try {
            Produit produit = liste_produit.getSelectionModel().getSelectedItem();
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
        }
    }

    @FXML
    private void reafficher_categorie(MouseEvent event) {
        ListProduit.clear();
        AfficherProduit();
    }

    private String getNomCategorie(int id) throws SQLException {
        String categorie_nom;
        String req = "select nom from categorie where id =" + "'" + id + "'";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(req);
        while (resultSet.next()) {
            // System.out.println(resultSet.getString("nom"));
            categorie_nom = resultSet.getString("nom");
            return categorie_nom;
        }
        return null;
    }

    private void Recherche() {

        FilteredList<Produit> filteredData = new FilteredList<>(ListProduit, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produit -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (produit.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (produit.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Produit> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(liste_produit.comparatorProperty());
        liste_produit.setItems(sortedData);

    }

    @FXML
    private void detail(ActionEvent event) throws SQLException {
        try {
            Produit produit = liste_produit.getSelectionModel().getSelectedItem();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/views/DetailProduit.fxml"));
            Parent p = Loader.load();
            DetailProduitController display = Loader.getController();
            display.setProduit(produit);
            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(p);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exporterPDF(ActionEvent event) {
        Produit selectedItem = liste_produit.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {

            ProduitService ps = new ProduitService();

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choose title");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                ps.ecrirePdf(selectedItem.getId(), chooser.getSelectedFile());
            } else {
                System.out.println("No Selection ");
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un element à exporter.");

            alert.showAndWait();
        }
    }

    @FXML
    private void modifier(MouseEvent event) {
    }

}
