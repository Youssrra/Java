/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import models.Panier;
import models.Produit;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PanierController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private TableView<Panier> liste_produit;
    @FXML
    private TableColumn<?, ?> id_produit;
    @FXML
    private TableColumn<?, ?> nom_produit;
    @FXML
    private TableColumn<?, ?> image_produit;
    @FXML
    private TableColumn<?, ?> prix_produit;
    @FXML
    private TableColumn<?, ?> quantiter_produit;
    @FXML
    private TableColumn<?, ?> total_produit;
    ObservableList<Panier> ListPanier = FXCollections.observableArrayList();
    public static String recupererPanier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        System.out.println(recupererPanier);
/*
        Panier panier = new Panier();
        panier.setId_produit(recupererPanier.getId_produit());
        panier.setNom_produit(recupererPanier.getNom_produit());
        panier.setImage_produit(recupererPanier.getImage_produit());
        panier.setPrix_produit(recupererPanier.getPrix_produit());
        panier.setQuantite_produit(recupererPanier.getQuantite_produit());

        ListPanier.add(panier);

        id_produit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));

        nom_produit.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        image_produit.setCellValueFactory(new PropertyValueFactory<>("image_produit"));
        prix_produit.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
        quantiter_produit.setCellValueFactory(new PropertyValueFactory<>("quantite_produit"));

        liste_produit.setItems(ListPanier);*/

    }

    @FXML
    private void modifier(MouseEvent event) {
    }

    @FXML
    private void supprimer(ActionEvent event) {
    }

    @FXML
    private void modifier(ActionEvent event) {
    }

    @FXML
    private void exporterPDF(ActionEvent event) {
    }

}
