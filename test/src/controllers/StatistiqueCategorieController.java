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
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Categorie;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class StatistiqueCategorieController implements Initializable {

    @FXML
    private Button btn_close;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private BarChart<?, ?> categorie_statistique;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private Object rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AfficherCategorie();
    }

    @FXML
    private void Close_btn(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }

    private void AfficherCategorie() {
        XYChart.Series set1 = new XYChart.Series<>();
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from categorie";
        try {

            ResultSet res = con.createStatement().executeQuery(qry);

            while (res.next()) {
                int nb = produitCount(res.getInt(1));
                set1.getData().add(new XYChart.Data(res.getString(2), nb));
            }
            categorie_statistique.getData().add(set1);

        } catch (SQLException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int produitCount(int idc) throws SQLException {
        int numberRow = 0;
        System.out.println(idc);
        Statement statement = con.createStatement();
        resultSet = statement.executeQuery("select * from produit where id_categorie =" + "'" + idc + "'");
        while (resultSet.next()) {
            numberRow++;
        }
        System.out.println(numberRow);
        return numberRow ;
    }

}
