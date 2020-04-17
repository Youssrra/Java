/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import models.Categorie;
import utils.ConnectionUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import static javax.swing.text.StyleConstants.FontFamily;
import static sun.plugin.javascript.navig.JSType.Image;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.sun.scenario.effect.ImageData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import java.io.File;

/**
 *
 * @author ASUS
 */
public class CategorieService implements IService<Categorie> {

    Connection con = null;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public CategorieService() {
        con = ConnectionUtil.conDB();
    }

    @Override
    public void insert(Categorie c) {
        try {
            String requete = "insert into categorie (nom,description) values ('" + c.getNom() + "','" + c.getDescription() + "')";
            ste.executeUpdate(requete);
            System.out.println("Categorie ajoutée !");
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Categorie> selectAll() {
        List<Categorie> categories = new ArrayList<Categorie>();
        String req = "select * from categorie ORDER BY(id) DESC";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(req);
            while (resultSet.next()) {
                Categorie c = new Categorie(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("description"));
                categories.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    public void supprimer(int x) {
        String sql = "DELETE FROM categorie WHERE id = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Categorie Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Categorie non Supprimer");
        }
       
    }

    public void modifier(int id, String nom, String description) {
        String sql = "UPDATE categorie SET nom=?, description=? WHERE id =?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nom);
            statement.setString(2, description);
            statement.setInt(3, id);
            statement.executeUpdate();
            System.out.println("Catégorie Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
     public List<Categorie> getAll() {
        String sql = "SELECT * from categorie";
        List<Categorie> list = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Categorie cc = new Categorie();
                cc.setId(rs.getInt(1));
                cc.setNom(rs.getString(2));
                cc.setDescription(rs.getString(3));
                list.add(cc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Categorie> getInfo(int c) {
        String sql = "SELECT * from categorie where id=" + "'" + c + "'";
        List<Categorie> list = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Categorie cc = new Categorie();
                cc.setId(rs.getInt(1));
                cc.setNom(rs.getString(2));
                cc.setDescription(rs.getString(3));
                list.add(cc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void ecrirePdf(int idc, File SavePath) {

        List<Categorie> list = new ArrayList<>();
        list = this.getInfo(idc);

        Document doc = new Document();
        PdfWriter docWriter = null;
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            //special font sizes

            //file path
            System.out.println(SavePath.toString() + "\\rendu.pdf");
            System.out.println("\\");
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(SavePath.toString() + "\\rendu.pdf"));
            //Image background = Image.getInstance("./publicitePdfBackground.png");
            //document header attributes
            doc.addAuthor("Overdose of knowledge");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("vélo.tn");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(50, 50, 150, 50);

            //open document
            doc.open();
            float width = doc.getPageSize().getWidth();
            float height = doc.getPageSize().getHeight();

            //create a paragraph
            float[] columnWidths = {4f, 1.5f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(2);
            // set table width a percentage of the page width

      
            //just some random data to fill 
            for (int x = 0; x < list.size(); x++) {
                
                insertCell(table, "Identifiant");
                 table.addCell(String.valueOf(list.get(x).getId()));
                 insertCell(table, "Nom");
                 table.addCell(list.get(x).getNom());
                insertCell(table, "Déscription");

                 table.addCell(list.get(x).getDescription());
                 
            }

            table.setSpacingAfter(30.2f);
            //paragraph.setSpacingAfter(30.2f);

            //add the PDF table to the paragraph
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            doc.add(table);
            // doc.add(paragraph);
            doc.add(new Phrase("Rapport crée avec les derniers mis à jour:" + sdf.format(date).toString()));

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    private void insertCell(PdfPTable table, String text) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim()));
        //set the cell alignment

        //in case there is no text and you wan to create an empty row
        //add the call to the table
        table.addCell(cell);

    }


    public int getIdCategorie(String c) {
        
        String sql = "select id from categorie where nom=" + "'" + c + "'";
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            rs.next();
            int id = rs.getInt(1);
            //System.out.println("Id fil cat "+id);
            return id;
         //  System.out.println("Id fil cat "+id);
          
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        

    }

}


/*

 public void insert(Categorie c) {
       
 try {
 String requete="insert into categorie (nom,description) values ('"+c.getNom()+"','"+c.getDescription()+"')";
 ste.executeUpdate(requete);
 System.out.println("Categorie ajoutée !");
 } 
 catch (SQLException ex) {
 Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
 }
        
 }
    
 public List<Categorie> getAll() {
 String requete="select * from categorie";
 List<Categorie> list=new ArrayList<>();
 try {
 ste=con.createStatement() ;
 rs=ste.executeQuery(requete);
 while(rs.next()){
 list.add(new Categorie(rs.getString("nom"), rs.getString(3)));
 }
 } catch (SQLException ex) {
 Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
 }
 return list;
 }
    
 public void delete(Categorie c) {
 try {
 String requete = "DELETE FROM categorie WHERE id=" + c.getId();
 ste=con.createStatement();
 ste.executeUpdate(requete);
 System.out.println("Catégorie supprimée !");

 } catch (SQLException ex) {
 System.err.println(ex.getMessage());
 }
 }
    
 public void edit(Categorie c) {
 try {
 String requete = "UPDATE categorie SET nom='" + c.getNom() + "',description='" + c.getDescription() + "' WHERE id=" + c.getId();
 ste=con.createStatement();
 ste.executeUpdate(requete);
 System.out.println("Catégorie modifiée !");
           
 } catch (SQLException ex) {
 System.err.println(ex.getMessage());
 }
 }*/
