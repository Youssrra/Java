/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Categorie;
import models.Produit;
import utils.ConnectionUtil;

/**
 *
 * @author ASUS
 */
public class ProduitService implements IServiceProduit<Produit> {

    Connection con = null;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ProduitService() {
        con = ConnectionUtil.conDB();
    }

    @Override
    public void insertProduit(Produit p) {

        try {
            String requete = "insert into produit (nom,description,reference,image,prix,quantite,id_categorie,marque,partenaire) "
                    + "values ('" + p.getNom() + "','" + p.getDescription() + "','" + p.getReference() + "','" + p.getImage() + "','" + p.getPrix()
                    + "','" + p.getQuantite() + "','" + p.getId_categorie() + "','" + p.getMarque() + "','" + p.getPartenaire() + "')";

            ste = con.createStatement();
            ste.executeUpdate(requete);
            System.out.println("Produit ajoutée !");
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Produit> getAllProduit() {
        String requete = "select * from produit";
        List<Produit> list = new ArrayList<>();
        try {
            ste = con.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setMarque(rs.getString("marque"));
                p.setId_categorie(rs.getInt("id_categorie"));
                p.setPartenaire(rs.getInt("partenaire"));
                p.setPrix(rs.getDouble("prix"));
                p.setQuantite(rs.getInt("quantite"));
                p.setReference(rs.getString("reference"));
                p.setImage(rs.getString("image"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void supprimer(int x) {
        String sql = "DELETE FROM produit WHERE id = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Produit Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Produit non Supprimer");
    }

    public void modifier(int id, String reference, String nom, String image, double prix, int quantite, int categorie, String marque, String description) {

        String sql = "UPDATE produit SET reference=?,nom=?,image=?,prix=?,quantite=?,id_categorie=?,marque=?,description=? WHERE id =?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, reference);
            statement.setString(2, nom);
            statement.setString(3, image);
            statement.setDouble(4, prix);
            statement.setInt(5, quantite);
            statement.setInt(6, categorie);
            statement.setString(7, marque);
            statement.setString(8, description);
            statement.setInt(9, id);
            statement.executeUpdate();
            System.out.println("Produit modifier");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Produit> getInfo(int idp) {
        String sql = "SELECT * from produit where id=" + "'" + idp + "'";
        List<Produit> list = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Produit ps = new Produit();
                ps.setId(rs.getInt("id"));
                ps.setNom(rs.getString("nom"));
                ps.setDescription(rs.getString("description"));
                ps.setMarque(rs.getString("marque"));
                ps.setId_categorie(rs.getInt("id_categorie"));
                ps.setPartenaire(rs.getInt("partenaire"));
                ps.setPrix(rs.getDouble("prix"));
                ps.setQuantite(rs.getInt("quantite"));
                ps.setReference(rs.getString("reference"));
                ps.setImage(rs.getString("image"));

                list.add(ps);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public void ecrirePdf(int idp, File SavePath) {

        List<Produit> list = new ArrayList<>();
        list = this.getInfo(idp);
        Document doc = new Document();
        PdfWriter docWriter = null;
        DecimalFormat df = new DecimalFormat("0.00");

        try {
            //special font sizes

            //file path
            System.out.println(SavePath.toString() + "\\Produit.pdf");
            System.out.println("\\");
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(SavePath.toString() + "\\Prouduit.pdf"));
            //  Image background = Image.getInstance(width, height, true, typeCCITT, parameters, data, transparency)getInstance("./publicitePdfBackground.png");
            //document header attributes
            doc.addAuthor("Vélo.tn");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("vélo.tn");
            doc.addTitle("Information sur un produit");
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(50, 50, 150, 50);

            doc.open();

            PdfPTable table = new PdfPTable(2);
            doc.add(new Paragraph("Information relative au produit :" + list.get(0).getReference()));
            table.setSpacingBefore(50f);
            //just some random data to fill 
            for (int x = 0; x < list.size(); x++) {

                insertCell(table, "Image");
                Image img = Image.getInstance("./src/images/" + list.get(x).getImage());

                img.setAlignment(Image.MIDDLE);
                table.addCell(new PdfPCell(img, true));

                insertCell(table, "Identifiant");
                insertCell(table, String.valueOf(list.get(x).getId()));

                insertCell(table, "Réference");
                insertCell(table, String.valueOf(list.get(x).getReference()));

                insertCell(table, "Nom");
                insertCell(table, list.get(x).getNom());

                //  insertCell(table,img.scaleAbsolute(250f,350f)) ;
                //  insertCell(table, list.get(x).getImage());
                insertCell(table, "Prix");
                insertCell(table, String.valueOf(list.get(x).getPrix()) + "DT");

                insertCell(table, "Quantite");
                insertCell(table, String.valueOf(list.get(x).getQuantite()) + "Produit en stock");

                insertCell(table, "Marque");
                insertCell(table, list.get(x).getMarque());

                insertCell(table, "Déscription");
                insertCell(table, list.get(x).getDescription());
            }

            table.setSpacingAfter(30.2f);

            //add the PDF table to the paragraph
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            doc.add(table);

            doc.add(new Phrase("Telécharger le:" + sdf.format(date).toString()));

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
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

    public List<Produit> getAllSearchedProd(String search) {
        String c ="sq";
         String requete = "select * from produit where CONCAT(nom,description,reference,prix,marque) LIKE '%"+search+"%' ";
        List<Produit> list = new ArrayList<>();
        try {
            ste = con.createStatement();
            rs = ste.executeQuery(requete);
           
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setMarque(rs.getString("marque"));
                p.setId_categorie(rs.getInt("id_categorie"));
                p.setPartenaire(rs.getInt("partenaire"));
                p.setPrix(rs.getDouble("prix"));
                p.setQuantite(rs.getInt("quantite"));
                p.setReference(rs.getString("reference"));
                p.setImage(rs.getString("image"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
       
    }

    public List<Produit> getAllFilterCatProd(int cat){
        
        String requete = "select * from produit where id_categorie="+"'"+cat+"'";
        List<Produit> list = new ArrayList<>();
        try {
            ste = con.createStatement();
            rs = ste.executeQuery(requete);
           
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setMarque(rs.getString("marque"));
                p.setId_categorie(rs.getInt("id_categorie"));
                p.setPartenaire(rs.getInt("partenaire"));
                p.setPrix(rs.getDouble("prix"));
                p.setQuantite(rs.getInt("quantite"));
                p.setReference(rs.getString("reference"));
                p.setImage(rs.getString("image"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Produit> getAllFilterMaqProd(String m) {
        
         
        String requete = "select * from produit where marque="+"'"+m+"'";
        List<Produit> list = new ArrayList<>();
        try {
            ste = con.createStatement();
            rs = ste.executeQuery(requete);
           
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setMarque(rs.getString("marque"));
                p.setId_categorie(rs.getInt("id_categorie"));
                p.setPartenaire(rs.getInt("partenaire"));
                p.setPrix(rs.getDouble("prix"));
                p.setQuantite(rs.getInt("quantite"));
                p.setReference(rs.getString("reference"));
                p.setImage(rs.getString("image"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

 

}
