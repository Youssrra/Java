    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static controllers.VeloTnController.recupererUtilisateurConnecte;
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
import static java.util.Collections.list;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Categorie;
import models.Panier;
import models.Produit;
import services.CategorieService;
import services.ProduitService;
import services.ServiceLogin;
import services.ServicePanier;
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

    private ObservableList<Produit> masterData = FXCollections.observableArrayList();
    ObservableList<Produit> ListProduit = FXCollections.observableArrayList();

    public ArrayList<Panier> aa;
    @FXML
    private JFXButton btn_searchp;
    ToggleGroup gp = new ToggleGroup();
    @FXML
    private VBox filter_marque;
    ToggleGroup gp2 = new ToggleGroup();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherProduit();
        FiltreParCategorie();
        FiltreParMarque() ;
        btn_searchp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flowPane.getChildren().clear();
                Recherche(filterField.getText());
            }
        });

        gp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
                //print new selected value after change
                System.out.println("Selected Catégorie: " + ((RadioButton) newToggle).getText());
                String c = ((RadioButton) newToggle).getText();
                int cat = categorieService.getIdCategorie(c);
                // System.out.print("test categ    /"+cat);
                flowPane.getChildren().clear();
                int f = 1 ;
                filtre(cat,"",f);
            }

        });
        
          gp2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
                //print new selected value after change
                System.out.println("Selected Marque: " + ((RadioButton) newToggle).getText());
                String maq = ((RadioButton) newToggle).getText();
               // int cat = categorieService.getIdCategorie(c);
                System.out.print("test marque    /"+maq);
                flowPane.getChildren().clear();
                int f = 2 ;
                filtre(0,maq,f);
            }

        });

    }

    private void Recherche(String ch) {
        ProduitService sp = new ProduitService();
        List<Produit> list = null;
        list = sp.getAllSearchedProd(ch);
        ArrayList<VBox> vbx = new ArrayList<>();
        ServicePanier panierService = new ServicePanier();
        //ArrayList<Panier> listPanier = (ArrayList<Panier>) panierService.getPanier();

        ArrayList<Separator> as = new ArrayList<>();
        if (list.size() == 0) {
            Label donner = new Label("Aucun article trouver");
            flowPane.getChildren().add(donner);
        } else {
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
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(250);
                    String n = recupererUtilisateurConnecte.getNom_Utilisateur();
                    Label nom = new Label(list.get(i).getNom());
                    Label prix = new Label(Double.toString(list.get(i).getPrix()) + " DT");
                    Panier listp = new Panier(list.get(i).getId(),
                            list.get(i).getNom(),
                            list.get(i).getImage(),
                            list.get(i).getQuantite(),
                            list.get(i).getPrix(),
                            n
                    );

                    Button btnSupp = new Button("Ajouter au panier");
                    btnSupp.setStyle("-fx-background-color : #4099ff");
                    btnSupp.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            panierService.ajouterArticle(listp);

                        }
                    });

                    VBoxProduit.getChildren().add(imageView);
                    VBoxProduit.getChildren().add(nom);
                    VBoxProduit.getChildren().add(prix);
                    VBoxProduit.getChildren().add(btnSupp);

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

    public void FiltreParCategorie() {

        CategorieService sc = new CategorieService();
        List<Categorie> list = new ArrayList<>();
        list = sc.getAll();
        //create a toggle group

        for (int i = 0; i < list.size(); i++) {
            RadioButton b1 = new RadioButton(list.get(i).getNom());
            b1.setToggleGroup(gp);
          
            filter_categorie.setSpacing(10);
            filter_categorie.getChildren().addAll(b1);

        }

    }

    public void FiltreParMarque() {
        ArrayList<String> marque = new ArrayList<String>();
        marque.add("Atala");
        marque.add("Atom Bicycles");
        marque.add("BH Bikes");
        marque.add("Bianchi");
        marque.add("Bike by Me");
        marque.add("BMC");
        marque.add("BTwin");
        marque.add("Cannondale Bicycles");
        marque.add("Canyon");
        marque.add("Giant");
        
        

        for (int i = 0; i < marque.size(); i++) {
            RadioButton b1 = new RadioButton(marque.get(i));
            b1.setToggleGroup(gp2);
            filter_marque.setSpacing(10);
            filter_marque.getChildren().addAll(b1);
            
        }

    }

    private void afficherProduit() {
        ProduitService sp = new ProduitService();
        List<Produit> list = null;
        list = sp.getAllProduit();
        ArrayList<VBox> vbx = new ArrayList<>();
        ServicePanier panierService = new ServicePanier();
        //ArrayList<Panier> listPanier = (ArrayList<Panier>) panierService.getPanier();

        ArrayList<Separator> as = new ArrayList<>();

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
                imageView.setFitWidth(200);
                imageView.setFitHeight(250);
                String n = recupererUtilisateurConnecte.getNom_Utilisateur();
                Label nom = new Label(list.get(i).getNom());
                Label prix = new Label(Double.toString(list.get(i).getPrix()) + " DT");
                Panier listp = new Panier(list.get(i).getId(),
                        list.get(i).getNom(),
                        list.get(i).getImage(),
                        list.get(i).getQuantite(),
                        list.get(i).getPrix(),
                        n
                );

                Button btnSupp = new Button("Ajouter au panier");
                btnSupp.setStyle("-fx-background-color : #4099ff");
                btnSupp.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        panierService.ajouterArticle(listp);

                    }
                });

                VBoxProduit.getChildren().add(imageView);
                VBoxProduit.getChildren().add(nom);
                VBoxProduit.getChildren().add(prix);
                VBoxProduit.getChildren().add(btnSupp);

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

    private void aff_panier_action(ActionEvent event) {
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/views/Panier.fxml"));
//            String c = recupererUtilisateurConnecte.getNom_Utilisateur();
            // username_utilisateur.setText(c);
            //System.out.println(c);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void filtre(int cat,String m ,int f) {
         ProduitService sp = new ProduitService();
        List<Produit> list = null;
        if (f == 1)
        {
            list = sp.getAllFilterCatProd(cat);
        }
        else{
            list = sp.getAllFilterMaqProd(m);
        }
        ArrayList<VBox> vbx = new ArrayList<>();
        ServicePanier panierService = new ServicePanier();
        //ArrayList<Panier> listPanier = (ArrayList<Panier>) panierService.getPanier();

        ArrayList<Separator> as = new ArrayList<>();
        if (list.size() == 0) {
            Label donner = new Label("Aucun article trouver");
            flowPane.getChildren().add(donner);
        } else {
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
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(250);
                    String n = recupererUtilisateurConnecte.getNom_Utilisateur();
                    Label nom = new Label(list.get(i).getNom());
                    Label prix = new Label(Double.toString(list.get(i).getPrix()) + " DT");
                    Panier listp = new Panier(list.get(i).getId(),
                            list.get(i).getNom(),
                            list.get(i).getImage(),
                            list.get(i).getQuantite(),
                            list.get(i).getPrix(),
                            n
                    );

                    Button btnSupp = new Button("Ajouter au panier");
                    btnSupp.setStyle("-fx-background-color : #4099ff");
                    btnSupp.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            panierService.ajouterArticle(listp);

                        }
                    });

                    VBoxProduit.getChildren().add(imageView);
                    VBoxProduit.getChildren().add(nom);
                    VBoxProduit.getChildren().add(prix);
                    VBoxProduit.getChildren().add(btnSupp);

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
}
