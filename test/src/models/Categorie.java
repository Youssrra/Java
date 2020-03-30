/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author ASUS
 */
public class Categorie {
    
    private int id;

    public Categorie(String nom) {
        this.nom = nom;
    }

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    private String nom ;
    private String description;
    Button modifier_categorienour ;

    public Categorie() {
    }
    private final StringProperty Nom = new SimpleStringProperty();

    public String getNom() {
        return Nom.get();
    }

    public void setNom(String value) {
        Nom.set(value);
    }

    public StringProperty NomProperty() {
        return Nom;
    }
    private final StringProperty Description = new SimpleStringProperty();

    public String getDescription() {
        return Description.get();
    }

    public void setDescription(String value) {
        Description.set(value);
    }

    public StringProperty DescriptionProperty() {
        return Description;
    }
    private final IntegerProperty Id = new SimpleIntegerProperty();

    public int getId() {
        return Id.get();
    }

    public void setId(int value) {
        Id.set(value);
    }

    public IntegerProperty IdProperty() {
        return Id;
    }

    
    public Categorie(String nom, String description) {
      
        this.nom = nom;
        this.description = description;
    }

    public Categorie(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categorie other = (Categorie) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", nom=" + nom + ", description=" + description + '}';
    }


  

   
}
