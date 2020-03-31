/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Categorie;
import models.Produit;
import models.Utilisateur;
import services.CategorieService;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AjouterProduitController implements Initializable {

    @FXML
    private JFXTextField reference_produit;
    @FXML
    private Label errors_reference;
    @FXML
    private JFXTextField nom_produit;
    @FXML
    private JFXTextField prix_produit;
    @FXML
    private Label errors_prix;
    @FXML
    private JFXTextField quantite_produit;
    @FXML
    private Label errors_quantite;
    @FXML
    private ComboBox<String> marque_produit;
    @FXML
    private Label errors_marque;
    @FXML
    private ComboBox<String> categorie_produit;
    @FXML
    private Label errors_categorie;
    @FXML
    private JFXTextArea description_produit;
    @FXML
    private JFXButton ajouter_produit_btn;

    private FileInputStream fis;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Button btn_close;
    @FXML
    private Label errors_nom;
    @FXML
    private Label errors_description;
    @FXML
    private VBox bp;
    private Object fileChooser;
    @FXML
    private ImageView imageProduitPreview;
    @FXML
    private Label errors_image;
    @FXML
    private Button importerImage;

    private String imageProduit;

    List<String> listFichier;
    private String nom;
    public static int id_user;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        AfficherCategorie();
        marque_produit.getItems().addAll(
                "Atala",
                "Atom Bicycles",
                "BH Bikes",
                "Bianchi",
                "Bike by Me",
                "BMC",
                "BTwin",
                "Cannondale Bicycles",
                "Canyon",
                "Giant",
                "Kestrel"
        );
        imageProduit = null;
        listFichier = new ArrayList<>();
        listFichier.add("*.png");
        listFichier.add("*.jpg");

    }

    @FXML
    private void Close_btn(ActionEvent event) {

        Stage stage = (Stage) btn_close.getScene().getWindow();

        stage.close();
    }

    @FXML
    @SuppressWarnings("empty-statement")
    private void AjouterProduit(ActionEvent event) throws FileNotFoundException, SQLException {

        File image = new File("C:/image.jpg");
        FileInputStream fis = null;

        int quantite = Integer.parseInt(quantite_produit.getText());
        double prix = Double.parseDouble(prix_produit.getText());
        // System.out.println(marque_produit.getValue());
        // System.out.println(categorie_produit.getValue());
        //CategorieService c = new CategorieService();
        //HashMap<String, Integer> mapCategorie =  selectAll22(nom) ;
        //int Categorie_id = mapCategorie.get(categorie_produit.getValue());

        // System.out.println(categorie_produit.getValue().toString());
        String conv = categorie_produit.getValue().toString();
        int categorie = categorie_nom(conv);
        Produit p = new Produit(reference_produit.getText(), nom_produit.getText(), imageProduit, prix, quantite, categorie, marque_produit.getValue(), description_produit.getText(), AccueilPartenaireController.recupererUtilisateurConnecte.getId_Utilisateur());
        id_user = p.getPartenaire();
        Utilisateur u = new Utilisateur();
        u.setId_Utilisateur(id_user);
        System.out.println(id_user);
        try {
            con = ConnectionUtil.conDB();
            String st = "INSERT INTO produit (nom,description,reference,image,prix,quantite,id_categorie,marque,partenaire) VALUES (?,?,?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1, nom_produit.getText());
            preparedStatement.setString(2, description_produit.getText());
            preparedStatement.setString(3, reference_produit.getText());
            // imageProduitPreview.setImage(null);
            preparedStatement.setString(4, imageProduit);
            preparedStatement.setDouble(5, prix);
            preparedStatement.setInt(6, quantite);
            preparedStatement.setInt(7, categorie);
            preparedStatement.setString(8, marque_produit.getValue());
            preparedStatement.setInt(9, p.getPartenaire());

            preparedStatement.executeUpdate();
            new Alert(Alert.AlertType.INFORMATION, "sucess").show();

            /* File file = new File("C:\\image.jpg");
             FileInputStream fis = new FileInputStream(file);
             PreparedStatement ps = con.prepareStatement("insert into save_image (name,image) values(?,?)");

             ps.setString(1, "image 1");
             ps.setBinaryStream(4, fis, (int) file.length()*/
            //clearFields();
            // AfficherCategorie() ;
        } catch (SQLException ex) {
            Logger.getLogger(AjouterCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
                categorie_produit.getItems().addAll(res.getString("nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void importerProduitImage(ActionEvent event) throws FileNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", listFichier));
        File f = fc.showOpenDialog(null);
        if (f != null) {

            errors_image.setText("Image selectionnée" + f.getAbsolutePath());
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(new File(f.getAbsolutePath()));
//             System.out.println("Working Directory = " +
//              System.getProperty("user.dir"));
//            System.out.println("nomfichier"+f.getName());
                os = new FileOutputStream(new File("./src/images/" + f.getName()));
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

            } finally {
                is.close();
                os.close();
            }

            File file = new File("./src/images/" + f.getName());
//            System.out.println(file.toURI().toString());
            imageProduitPreview.setImage(new Image(file.toURI().toString()));
            imageProduit = f.getName();
        } else if (f == null) {
            errors_image.setText("Erreur chargement de l'image");
        }
    }

    private int categorie_nom(String conv) throws SQLException {
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from categorie where nom =" + "'" + conv + "'";
        ResultSet res = con.createStatement().executeQuery(qry);
        while (res.next()) {
            return res.getInt(1);
        }
        return 0;

    }

}
/* 

 private void clearFields() {
 nom_categorie.clear();
 description_categorie.clear();
 }


 /*if (reference_produit.getText().isEmpty() || reference_produit.getText().isEmpty()) {
 if (reference_produit.getText().isEmpty()) {
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
 } else {*/
