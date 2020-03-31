package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import models.Utilisateur;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceLogin {

    private static Connection connexion = null;

    public static Connection creationConnexion() {
        String dbName = "velo";
        String userName = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connexion = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connexion;
    }

    public static List<Utilisateur> getTtUtilisateur() {
        List<Utilisateur> list = new ArrayList<Utilisateur>();
        try {
            String sql = "select * from fos_user ";
            Connection connexion = ServiceLogin.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId_Utilisateur(resultSet.getInt("id"));
                utilisateur.setNom_Utilisateur(resultSet.getString("username"));
                utilisateur.setMotDePasse_Utilisateur(resultSet.getString("password"));

                list.add(utilisateur);
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list;

    }

    public static Utilisateur getUtilisateur(String nomUtilisateur) {
        Utilisateur utilisateur = new Utilisateur();
        try {
            String sql = "select * from fos_user where username = ?";
            Connection connexion = ServiceLogin.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setString(1, nomUtilisateur);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                utilisateur.setId_Utilisateur(resultSet.getInt("id"));
                utilisateur.setNom_Utilisateur(resultSet.getString("username"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setRole_Utilisateur(resultSet.getString("roles"));
            }

            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return utilisateur;

    }

    public static boolean testMotDePasse(String motDePasseGUI, String motDePasseBD) {

        boolean password_verified = false;

        if (null == motDePasseBD) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }
        password_verified = BCrypt.checkpw(motDePasseGUI, motDePasseBD.replaceFirst("2y", "2a"));

        return (password_verified);
    }

    public void supprimer(String x) {
        Connection con = ServiceLogin.creationConnexion();
        String sql = "DELETE FROM fos_user WHERE username = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, x);
            statement.executeUpdate();
            System.out.println("Utilisateur Supprimer");
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Utilisateur non Supprimer");
    }
}
