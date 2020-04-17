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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceLogin;
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
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    static String code;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    @FXML
    private AnchorPane GUI2;
    @FXML
    private TextField txtcode;
    @FXML
    private Label lblErrors_code;
    @FXML
    private Button btn_verif_code;
    @FXML
    private HBox btn_close_code;

   static String nomFenere;
   static String nomScene;
   static String status = "Success";
   static String username;
   static String password;
   static String nom;
   static String prenom;
   static String email;
   static String mdp;
   static String role;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void handleButtonAction(MouseEvent event) throws MessagingException {
        if (event.getSource() == btnSignup) {
            //login here
          SignUp();
            try {
                
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                //  stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/views/VerifierInscriMail.fxml")));
                stage.getIcons().add(new Image("/images/logo.png"));
                stage.setTitle("Verification");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        }
    }

    @FXML
    private void handleButtonAction22(MouseEvent event) {
        if (event.getSource() == btn_verif_code) {
            if (txtcode.getText().equals(code)) {
                try {
                    System.out.println("Code :" + code);
                    lblErrors_code.setText("code verifier");
                    lblErrors_code.setTextFill(Color.GREEN);
                    ServiceLogin sl = new ServiceLogin();
                    sl.Inscription(username, nom, prenom, email, mdp, role);
                   // clearFields();

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
                    Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            lblErrors_code.setText("Code saisie incorrect veuiller réessayer");
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

    private void SignUp() {
        status = "Success";
        username = txtUsername.getText();
        password = txtPassword.getText();
        nom = txtNom.getText();
        prenom = txtPrenom.getText();
        email = txtEmail.getText();

        role = "a:1:{i:0;s:11:\"ROLE_CLIENT\";}";
        mdp = BCrypt.hashpw(password, BCrypt.gensalt(13));
        mdp = mdp.replaceFirst("2a", "2y");

        System.out.println(email.matches(".+@.+\\.[a-z]+"));

        if (prenom.isEmpty() || email.matches(".+@.+\\.[a-z]+") == false || nom.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblErrors.setText("Veuillez vérifier votre saisie...");
            status = "Error";
        }
       else {
            code = randomAlphaNumeric();
            String text = "Votre code de vérification est : " + code;
            String ut = txtEmail.getText();
            try {
                sendMail(ut, "Validation de votre adresse mail", text);
            } catch (MessagingException ex) {
                Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    public static String randomAlphaNumeric() {
        int count = 6;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public void sendMail(String addresse, String subject, String message) throws MessagingException {
        String from = "velotunisie5@gmail.com";
        String pass = "velotn2020";
        String[] to = {addresse};
        String host = "smtp.gmail.com";

        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.user", from);
        prop.put("mail.smtp.password", pass);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.host", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(prop);
        MimeMessage meg = new MimeMessage(session);
        meg.setFrom(new InternetAddress(from));
        InternetAddress[] toadress = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            toadress[i] = new InternetAddress(to[i]);
        }

        for (int i = 0; i < to.length; i++) {
            meg.setRecipient(Message.RecipientType.TO, toadress[i]);
        }

        meg.setSubject(subject);
        meg.setContent(message, "text/html; charset=utf-8");
        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(meg, meg.getAllRecipients());
        transport.close();

    }

}
