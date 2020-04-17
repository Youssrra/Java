package models;

import javafx.scene.control.CheckBox;

public class Utilisateur {

    private int id_Utilisateur;
    private String username_Utilisateur;
    private String email;
    private String nom_Utilisateur;
    private String prenom_Utilisateur;
    private String motDePasse_Utilisateur;
    private String role_Utilisateur;
    private int enabled ;

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

   

    public Utilisateur() {
    }

    public Utilisateur(int id_Utilisateur, String email, String nom_Utilisateur, String motDePasse_Utilisateur, String role_Utilisateur) {
        this.id_Utilisateur = id_Utilisateur;
        this.email = email;
        this.nom_Utilisateur = nom_Utilisateur;
        this.motDePasse_Utilisateur = motDePasse_Utilisateur;
        this.role_Utilisateur = role_Utilisateur;
    }
    
     public Utilisateur(int id_Utilisateur, String email, String nom_Utilisateur , String role_Utilisateur) {
        this.id_Utilisateur = id_Utilisateur;
        this.email = email;
        this.nom_Utilisateur = nom_Utilisateur;
        this.role_Utilisateur = role_Utilisateur;
    }
     
     

    public Utilisateur(int id_Utilisateur, String username_Utilisateur, String email, String nom_Utilisateur, String motDePasse_Utilisateur, String role_Utilisateur) {
        this.id_Utilisateur = id_Utilisateur;
        this.username_Utilisateur = username_Utilisateur;
        this.email = email;
        this.nom_Utilisateur = nom_Utilisateur;
        this.motDePasse_Utilisateur = motDePasse_Utilisateur;
        this.role_Utilisateur = role_Utilisateur;
    }

    public Utilisateur(int id_Utilisateur, String username_Utilisateur, String email, String nom_Utilisateur, String prenom_Utilisateur, String motDePasse_Utilisateur, String role_Utilisateur,int enabled) {
        this.id_Utilisateur = id_Utilisateur;
        this.username_Utilisateur = username_Utilisateur;
        this.email = email;
        this.nom_Utilisateur = nom_Utilisateur;
        this.prenom_Utilisateur = prenom_Utilisateur;
        this.motDePasse_Utilisateur = motDePasse_Utilisateur;
        this.role_Utilisateur = role_Utilisateur;
        
        //,int enabled
        this.enabled = enabled;
    }
    

 
     

    public String getUsername_Utilisateur() {
        return username_Utilisateur;
    }

    public void setUsername_Utilisateur(String username_Utilisateur) {
        this.username_Utilisateur = username_Utilisateur;
    }
     
     

    public int getId_Utilisateur() {
        return id_Utilisateur;
    }

    public void setId_Utilisateur(int id_Utilisateur) {
        this.id_Utilisateur = id_Utilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom_Utilisateur() {
        return nom_Utilisateur;
    }

    public void setNom_Utilisateur(String nom_Utilisateur) {
        this.nom_Utilisateur = nom_Utilisateur;
    }

    public String getPrenom_Utilisateur() {
        return prenom_Utilisateur;
    }

    public void setPrenom_Utilisateur(String prenom_Utilisateur) {
        this.prenom_Utilisateur = prenom_Utilisateur;
    }
    

    public String getMotDePasse_Utilisateur() {
        return motDePasse_Utilisateur;
    }

    public void setMotDePasse_Utilisateur(String motDePasse_Utilisateur) {
        this.motDePasse_Utilisateur = motDePasse_Utilisateur;
    }

    public String getRole_Utilisateur() {
        return role_Utilisateur;
    }

    public void setRole_Utilisateur(String role_Utilisateur) {
        this.role_Utilisateur = role_Utilisateur;
    }
    
    

}
