/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceLogin;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class InscriptionAdminController implements Initializable {
    @FXML
    private Button btn_close;
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
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblErrors1;

        Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Close_btn(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignup) {
            //login here
            if (SignUp().equals("Success")) {
                System.out.println("Inscription admnistrateur effectuer avec succée");
            }
         }
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

        String role = "a:1:{i:0;s:10:\"ROLE_ADMIN\";}";
        String mdp = BCrypt.hashpw(password, BCrypt.gensalt(13));
        mdp = mdp.replaceFirst("2a", "2y");

        if (prenom.isEmpty() || nom.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Veuillez vérifier votre saisie...");
            status = "Error";
        } else {
            ServiceLogin sl = new ServiceLogin();
            sl.Inscription(username, nom, prenom, email, mdp, role);
            clearFields();
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
    
}
