package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Utilisateur;
import utils.ConnectionUtil;
import org.apache.commons.codec.digest.DigestUtils;
import services.ServiceLogin;

/**
 *
 * @author ASUS
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnSignup;

    /// -- 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Label btnForgot;
    @FXML
    private HBox btn_close;
    @FXML
    private AnchorPane GUI;

    @FXML
    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btn_close) {
            System.exit(0);
        }
    }

    @FXML
    public void handleButtonAction2(MouseEvent event) {

        if (event.getSource() == btnSignup) {

            try {

                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/views/Inscription.fxml")));
                stage.getIcons().add(new Image("/images/logo.png"));
                stage.setTitle("Inscription");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Error Server : Vérifier votre connexion");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Vous pouvez vous connecter");
        }
    }

    public LoginController() {
        con = ConnectionUtil.conDB();
    }

    public void connexionUtilisateur(ActionEvent event) throws IOException {

        if (txtUsername.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erreure de saisie ! ");
            alert.setContentText("Veuillez saisir votre nom d'utilisateur");
            alert.showAndWait();
            return;

        } else if (txtPassword.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erreure de saisie !");
            alert.setContentText("Veuillez saisir votre mot de passe");
            alert.showAndWait();
        }

        List<Utilisateur> listUtilisateur = ServiceLogin.getTtUtilisateur();
        HashMap<String, String> hashmapUtilisateur = new HashMap<>();

        for (Utilisateur utilisateur : listUtilisateur) {
            hashmapUtilisateur.put(utilisateur.getNom_Utilisateur(), utilisateur.getMotDePasse_Utilisateur());
        }

      /*
        for (Map.Entry<String, String> Uti : hashmapUtilisateur.entrySet()) {
            System.out.println(Uti.getKey() + " / " + Uti.getValue());

        }*/
        String nomUtilisater = txtUsername.getText();
        String motDePasseUtilisateur = txtPassword.getText();
        for (Map.Entry<String, String> Uti : hashmapUtilisateur.entrySet()) {
            if (nomUtilisater.equals(Uti.getKey())) {
                if (ServiceLogin.testMotDePasse(motDePasseUtilisateur, Uti.getValue())) {
                    Utilisateur utilisateur = ServiceLogin.getUtilisateur(Uti.getKey());
                    if (utilisateur.getRole_Utilisateur().equals("a:1:{i:0;s:9:\"ROLE_PART\";}")) {

                        Stage stage = (Stage) GUI.getScene().getWindow();
                        stage.close();

                         System.out.println("vous etre connecté entant que partenaire");
                         System.out.println(ServiceLogin.getUtilisateur(Uti.getKey()).getId_Utilisateur());

                         FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AccueilPartenaire.fxml"));
                         Parent root = loader.load();
                         AccueilPartenaireController controller = (AccueilPartenaireController) loader.getController();
                           
                         controller.recupererUtilisateurConnecte = (ServiceLogin.getUtilisateur(Uti.getKey()));
                         
                        
                         
                         Stage primaryStage = new Stage();
                         Scene scene = new Scene(root) ;
                         primaryStage.setScene(scene);
                         primaryStage.show();
                        return;
                    } else if (utilisateur.getRole_Utilisateur().equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}")) {
                        System.out.println("Vous etre connecté en tant qu'administrateur");
                        System.out.println(ServiceLogin.getUtilisateur(Uti.getKey()).getId_Utilisateur());
                        //XMLLoader loader = new FXMLLoader(getClass().getResource("views"));
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
                        Parent root = loader.load();
                        AccueilController controller = (AccueilController) loader.getController();
                        controller.recupererUtilisateurConnecte = (ServiceLogin.getUtilisateur(Uti.getKey()));
                        Stage primaryStage = new Stage();
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        return;
                    }
                    else {
                        
                        System.out.println("Vous etre connecté en tant que client");
                        System.out.println(ServiceLogin.getUtilisateur(Uti.getKey()).getId_Utilisateur());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VeloTn.fxml"));
                        Parent root = loader.load();
                        VeloTnController controller = (VeloTnController) loader.getController();
                        controller.recupererUtilisateurConnecte = (ServiceLogin.getUtilisateur(Uti.getKey()));
                        Stage primaryStage = new Stage();
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        return;
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Mot de passe incorrect");
                    alert.showAndWait();
                    return;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Nom d'utilisateur incorrect");
        alert.showAndWait();

    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}
