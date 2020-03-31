/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class InscriptionController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignup;
    @FXML
    private Label lblErrors;
    @FXML
    private TextField txtNom;
    @FXML
    private Button btnSignin;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignup) {
            //login here
            if (SignUp().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/views/Login.fxml")));
                    stage.getIcons().add(new Image("/images/logo.png"));
                    stage.setTitle("Se connecter");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }

    @FXML
    private void handleButtonAction2(MouseEvent event) throws IOException {

        //add you loading or delays - ;-)
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/views/Login.fxml")));
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setTitle("Se connecter");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Vérifier votre connexion");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Vous pouvez vous connecter");
        }
    }

    public InscriptionController() {
        con = ConnectionUtil.conDB();
    }

    private void clearFields() {
        txtUsername.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtPassword.clear();
    }

    private String SignUp() {

        String status = "Success";
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email = txtEmail.getText();

        String role = "a:1:{i:0;s:11:\"ROLE_CLIENT\";}";
        String mdp = BCrypt.hashpw(password, BCrypt.gensalt(13));
        mdp = mdp.replaceFirst("2a", "2y");

        if (prenom.isEmpty() || nom.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Veuillez vérifier votre saisie...");
            status = "Error";
        } else {
            //query
            try {
                String st = "INSERT INTO fos_user ( username,nom,prenom,email,password,roles,enabled,username_canonical,email_canonical) VALUES (?,?,?,?,?,?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setString(1, txtUsername.getText());
                preparedStatement.setString(2, txtNom.getText());
                preparedStatement.setString(3, txtPrenom.getText());
                preparedStatement.setString(4, txtEmail.getText());
                preparedStatement.setString(5, mdp);
                preparedStatement.setString(6, role);
                preparedStatement.setInt(7,1);
                preparedStatement.setString(8, txtUsername.getText());
                preparedStatement.setString(9, txtEmail.getText());

                preparedStatement.executeUpdate();
                lblErrors.setTextFill(Color.GREEN);
                lblErrors.setText("Inscription effectuer avec succée voous allez bientot étre rediriger ...");
           // fetRowList();
                //clear fields
                clearFields();
                return "Success";

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText(ex.getMessage());
                return "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

}
