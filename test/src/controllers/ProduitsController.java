    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Categorie;
import models.Produit;
import services.CategorieService;
import services.ProduitService;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ProduitsController implements Initializable {

    @FXML
    private JFXTextField filterField;
    @FXML
    private VBox filter_categorie;
    @FXML
    private FlowPane flowPane;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement ste;
    CategorieService categorieService = new CategorieService();

    private ObservableList<Categorie> masterData = FXCollections.observableArrayList();
    ObservableList<Categorie> ListCategorie = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherProduit();
        FiltreParCategorie();

    }

    public void FiltreParCategorie() {

        CategorieService sc = new CategorieService();
        List<Categorie> list = new ArrayList<>();
        list = sc.getAll();
        //create a toggle group
        ToggleGroup gp = new ToggleGroup();

        for (int i = 0; i < list.size(); i++) {
            RadioButton b1 = new RadioButton(list.get(i).getNom());
            b1.setToggleGroup(gp);
            filter_categorie.setSpacing(10);
            filter_categorie.getChildren().addAll(b1);
        }
    }
/*
    public void afficherProduit() {
        ProduitService sp = new ProduitService();
        //flowPane.getChildren().remove(0, flowPane.getChildren().size());
        List<Produit> list = new ArrayList<>();
        list = sp.getAllProduit();
        System.out.println(list);

    }*/

    private void afficherProduit() {
        ProduitService sp = new ProduitService();
        List<Produit> list = null;
        list = sp.getAllProduit();

        ArrayList<Separator> as = new ArrayList<>();
        ArrayList<VBox> vbx = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            try {
                //separator vertical entre les produt
                Separator h = new Separator(Orientation.VERTICAL);
                h.setPrefWidth(17);
                h.setPrefHeight(44);
                h.setVisible(false);
                as.add(h);
                //creation de vbox pour contenir ele produit
                VBox VBoxProduit = new VBox();
                VBoxProduit.setSpacing(5);
                VBoxProduit.setAlignment(Pos.CENTER);
                VBoxProduit.setPrefHeight(100);
                VBoxProduit.setPrefWidth(100);

                //attribution des element
                FileInputStream input = new FileInputStream("./src/images/" + list.get(i).getImage());
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(250);
                imageView.setFitHeight(300);

                Label nom = new Label("Nom : " + list.get(i).getNom());
                Label prix = new Label("Prix : " + Double.toString(list.get(i).getPrix()));
                nom.getStyleClass().add("nom");
                prix.getStyleClass().add("prix");

                VBoxProduit.getChildren().add(nom);
                VBoxProduit.getChildren().add(prix);
                VBoxProduit.getChildren().add(imageView);

                //ajout des vbox du produit a vbox 
                vbx.add(VBoxProduit);
                flowPane.getChildren().add(vbx.get(i));
                flowPane.getChildren().add(as.get(i));
                
                //controle nombre de produit afficher par ligne
                if (i != 0) {
                    if (((i + 1) % 3) == 0) {
                        Separator sepHoriz = new Separator(Orientation.HORIZONTAL);
                        sepHoriz.setPrefWidth(1120);
                        sepHoriz.setPrefHeight(35);
                        sepHoriz.setVisible(false);
                        flowPane.getChildren().add(sepHoriz);
                    }
                } else {
                    System.out.println(i);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
