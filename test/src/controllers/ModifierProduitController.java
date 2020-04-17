/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
public class ModifierProduitController implements Initializable {

    @FXML
    private Button btn_close;
    @FXML
    private VBox bp;
    private JFXTextField reference_produit;
    private JFXTextField nom_produit;
    @FXML
    private Label errors_nom;
    private JFXTextField prix_produit;
    private JFXTextField quantite_produit;
    private ComboBox<String> marque_produit;
    private ComboBox<String> categorie_produit;
    private JFXTextArea description_produit;
    private ImageView imageProduitPreview;
    @FXML
    private Label errors_image;
    @FXML
    private JFXButton modifier_produit_btn;

    private Produit produit;
    private String imageProduit;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private JFXTextField id_produit_modifier;
    List<String> listFichier;
  

    /**
     * Initializes the controller class.
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
    private void ModifierProduit(ActionEvent event) throws SQLException {
        ProduitService ps = new ProduitService();
        String reference = reference_produit.getText();
        String nom = nom_produit.getText();
        double prix = Double.parseDouble(prix_produit.getText());
        int quantite = Integer.parseInt(quantite_produit.getText());
        String conv = categorie_produit.getValue().toString();
        int categorie = 0;
        String req = "select id from categorie where nom =" + "'" + conv + "'";
        Statement statement = con.createStatement() ;
        ResultSet resultSet = statement.executeQuery(req);
        while (resultSet.next()) {
            categorie = resultSet.getInt("id");
        }
        String marque = marque_produit.getValue() ;
        String image = imageProduit ;
        String description = description_produit.getText();
        int id = Integer.valueOf(id_produit_modifier.getText());

        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("Modification d'un produit");
        a1.setContentText("vous voulez vraiment Modifier ce produit?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            ps.modifier(id,reference,nom,image,prix,quantite,categorie,marque,description);

        } else if (result.get() == ButtonType.CANCEL) {

        }
    }

    public void setProduit(Produit p) throws SQLException {
        produit = p;
        String nom_categorie = null;
        int id = produit.getId_categorie();
        String req = "select nom from categorie where id =" + "'" + id + "'";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(req);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("nom"));
            nom_categorie = resultSet.getString("nom");
        }

        String prix = String.valueOf(produit.getPrix());
        String qantite = String.valueOf(produit.getQuantite());

        System.out.println(produit);
        reference_produit.setText(produit.getReference());
        nom_produit.setText(produit.getNom());
        prix_produit.setText(prix);
        quantite_produit.setText(qantite);
        marque_produit.setValue(produit.getMarque());
        categorie_produit.setValue(nom_categorie);
        description_produit.setText(produit.getDescription());
        id_produit_modifier.setText(String.valueOf(produit.getId()));

        System.out.println(produit.getImage());
        File file = new File("./src/images/" + produit.getImage());
        System.out.println(file.toURI().toString());
        imageProduitPreview.setImage(new Image(file.toURI().toString()));
        errors_image.setText(produit.getImage());

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

    void setUtilisateur(Utilisateur utilisateur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
