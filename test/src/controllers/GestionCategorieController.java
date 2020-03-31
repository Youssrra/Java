package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXTextField;
import static com.sun.deploy.util.Waiter.set;
import static com.sun.javafx.fxml.expression.Expression.set;
import static com.sun.scenario.Settings.set;
import java.io.IOException;
import static java.lang.reflect.Array.set;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JFileChooser;
import models.Categorie;
import services.CategorieService;
import sun.plugin2.jvm.RemoteJVMLauncher.CallBack;
import utils.ConnectionUtil;

/**
 *
 * @author ASUS
 */
public class GestionCategorieController implements Initializable {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement ste;
    @FXML
    private BorderPane borderpane;
    @FXML
    private TableView<Categorie> liste_categorie;
    @FXML
    private TableColumn<?, ?> id_categorie;
    @FXML
    private TableColumn<?, ?> nom_categorie;
    @FXML
    private TableColumn<?, ?> description_categorie;

    CategorieService categorieService = new CategorieService();
    @FXML
    private JFXTextField filterField;
    private ObservableList<Categorie> masterData = FXCollections.observableArrayList();
    ObservableList<Categorie> ListCategorie = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //AfficherCategorie();
        //MiseAJourAffichage();
        //con = ConnectionUtil.conDB();
        AfficherCategorie();
        Recherche();

        /*CategorieService cs = new CategorieService();
         ArrayList arrayList = (ArrayList) cs.selectAll();

         ObservableList observableList = FXCollections.observableArrayList(arrayList);

         id_categorie.setCellValueFactory(new PropertyValueFactory<>("id"));
         nom_categorie.setCellValueFactory(new PropertyValueFactory<>("nom"));
         description_categorie.setCellValueFactory(new PropertyValueFactory<>("description"));
         liste_categorie.setItems(observableList);*/
    }

    private void AfficherCategorie() {

        con = ConnectionUtil.conDB();

        String qry = "SELECT * from categorie";
        try {

            ResultSet res = con.createStatement().executeQuery(qry);

            while (res.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(res.getInt("id"));
                categorie.setNom(res.getString("nom"));
                categorie.setDescription(res.getString("description"));

                ListCategorie.add(categorie);

                id_categorie.setCellValueFactory(new PropertyValueFactory<>("id"));
                nom_categorie.setCellValueFactory(new PropertyValueFactory<>("nom"));
                description_categorie.setCellValueFactory(new PropertyValueFactory<>("description"));

                liste_categorie.setItems(ListCategorie);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int CategorieSelectionner() {
        int selectedItem = liste_categorie.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = liste_categorie.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    @FXML
    private void supprimer(ActionEvent event) {
        int x = CategorieSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer categorie");
        a1.setContentText("Vous voulez vraiment supprimer cette categorie ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            categorieService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer categorie");
            a2.setContentText("Categorie supprimé avec succés!");
            a2.show();

            liste_categorie.getItems().clear();
            AfficherCategorie();

        } else {
            a1.close();
        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/views/AjouterUneCategorie.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

    @FXML
    private void modifier(ActionEvent event) {

        try {
            Categorie c = liste_categorie.getSelectionModel().getSelectedItem();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/views/ModifierUneCategorie.fxml"));
            Parent p = Loader.load();
            ModifierCategorieController display = Loader.getController();
            display.setCategorie(c);
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
        ListCategorie.clear();
        AfficherCategorie();
    }

    private void Recherche() {
        FilteredList<Categorie> filteredData = new FilteredList<>(ListCategorie, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(categorie -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (categorie.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (categorie.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Categorie> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(liste_categorie.comparatorProperty());
        liste_categorie.setItems(sortedData);
    }

    private void exporterPDF(ActionEvent event) {
        Categorie selectedItem = liste_categorie.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem.getId());
        if (selectedItem != null) {

            CategorieService cs = new CategorieService();

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choose title");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                cs.ecrirePdf(selectedItem.getId(), chooser.getSelectedFile());
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

    @FXML
    private void statistique(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/views/StatistiqueCategorie.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

}

/*



 Callback<TableColumn<Categorie,String>,TableCell<Categorie,String>> cellFactory ;
 cellFactory = (param) -> { 
 final TableCell<Categorie,String> cell=new TableCell<Categorie,String>(){
              



 public void ModifierCategorie(String item,boolean empty){
 super.updateItem(item, empty);
 if (empty){
 setGraphic(null) ;
 setText(null) ;
 }
 else {
 final Button btn_mod_cat = new Button("EDIT") ;
 btn_mod_cat.setOnAction(event -> {
 Categorie categorie = getTableView().getItems().get(getIndex()) ;
                        
 Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
 alert.setContentText("Vous avez selectioner cette categorie :\n"+categorie.getId()+"\n"+categorie.getNom()+"\n"+categorie.getDescription()) ;
 alert.show();
 });
 setGraphic(btn_mod_cat) ;
 setText(null);
 }
                        
 };
 };
 return cell;
 };
                
                
 col_action.setCellFactory(cellFactory);






 private void AfficherCategorie() {
 ListCategorie = FXCollections.observableArrayList();
 String qry = "SELECT * from categorie";
 try {
            
 ResultSet res = con.createStatement().executeQuery(qry);

 while (res.next()) {
 Categorie categorie = new Categorie();
 categorie.setId(res.getInt("id"));
 categorie.setNom(res.getString("nom"));
 categorie.setDescription(res.getString("description"));

 ListCategorie.add(categorie);

 col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
 col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
 col_description.setCellValueFactory(new PropertyValueFactory<>("description"));

 addButtonToTable();
 tab_aff_cat.setItems(ListCategorie);

 }
 } catch (SQLException ex) {
 Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
 }
 }

 private void addButtonToTable() {

 Callback<TableColumn<Categorie, Void>, TableCell<Categorie, Void>> cellFactory = new Callback<TableColumn<Categorie, Void>, TableCell<Categorie, Void>>() {
 @Override
 public TableCell<Categorie, Void> call(final TableColumn<Categorie, Void> param) {
 final TableCell<Categorie, Void> cell = new TableCell<Categorie, Void>() {

 private final Button btn = new Button("");

 @Override
 public void updateItem(Void item, boolean empty) {
 super.updateItem(item, empty);
 if (empty) {
 setGraphic(null);
 setText(null);
 } else {
                         
 btn.setOnAction(event -> {
 Categorie categorie = getTableView().getItems().get(getIndex());
                                
 idd=categorie.getId() ;
 txt_nom_cat.setText(categorie.getNom());
 txt_desc_cat.setText(categorie.getDescription());
                               

 });
 setGraphic(btn);
 setText(null);
 }

 }
 ;
 };
 return cell;
 }
 };

 col_action.setCellFactory(cellFactory);
 tab_aff_cat.getColumns().add(col_action);

 }

 public void update() {
 // idd=3 ;
 try {
 String st = "UPDATE categorie SET nom='" + txt_nom_cat.getText() + "',description='" + txt_desc_cat.getText() + "' WHERE id=" + idd;
                       
 ste = con.createStatement();    
                      
 ste.executeUpdate(st);
 System.out.println("Catégorie modifiée !");
 lbl_error.setTextFill(Color.GREEN);
 lbl_error.setText("Catégorie modifer avec succés");
 //Mise ajour de la table view
                       
                        
                        
 tab_aff_cat.refresh();
                        
                        
 //Suppression du contenue des champ
 clearFields();
                      

 } catch (SQLException ex) {
 System.out.println(ex.getMessage());
 lbl_error.setTextFill(Color.TOMATO);
 lbl_error.setText(ex.getMessage());

 }

 }

 }










 */
