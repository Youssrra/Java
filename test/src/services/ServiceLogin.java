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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionUtil;

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
                utilisateur.setEnabled(resultSet.getInt("enabled"));
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
            Logger.getLogger(ServiceLogin.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Utilisateur non Supprimer");
        }

    }

    public void Inscription(String username, String nom, String prenom, String email, String mdp, String role) {
        Connection con = ServiceLogin.creationConnexion();
        PreparedStatement preparedStatement = null;
        try {
            String st = "INSERT INTO fos_user ( username,nom,prenom,email,password,roles,enabled,username_canonical,email_canonical) VALUES (?,?,?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, mdp);
            preparedStatement.setString(6, role);
            preparedStatement.setInt(7, 1);
            preparedStatement.setString(8, username);
            preparedStatement.setString(9, email);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //@Override
    public List<Utilisateur> AfficherClien(String role) {

        Connection connection = ServiceLogin.creationConnexion();
        List<Utilisateur> u = new ArrayList<Utilisateur>();
        String req = "select * from fos_user where roles =" + "'" + role + "'";
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(req);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(4),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(8),
                        resultSet.getString(12),
                        resultSet.getInt(6)
                );

                u.add(utilisateur);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    public void modifier(int id, String nom, String prenom, String email, String username, int enabled) {
        Connection con = ServiceLogin.creationConnexion();
        //PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(req);
        String sql = "UPDATE fos_user SET nom=?, prenom=? , email=?, username=?, enabled=? WHERE id =?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            statement.setString(4, username);
            statement.setInt(5, enabled);
            statement.setInt(6, id);
            statement.executeUpdate();
            System.out.println("Utilisateur Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ResetPassword(String username, String confMotpasse) {
        Connection con = ServiceLogin.creationConnexion();

        //PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(req);
        String sql = "UPDATE fos_user SET password=? WHERE username=" + "'" + username + "'";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, confMotpasse);
            statement.executeUpdate();
            System.out.println("Mot de passe Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getUtilisateurUsername(String username) {
        Connection con = ServiceLogin.creationConnexion();
        String sql = "select * from fos_user WHERE username=" + "'" + username + "'";
         String email = null ;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            rs.next();
             email = rs.getString("email");
        } catch (SQLException ex) {
           // Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
            return email ;
        }
        return email;
    }

}
