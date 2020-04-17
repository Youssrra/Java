/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Categorie;
import services.CategorieService;

import models.Categorie;
import services.CategorieService;
import models.Produit;
import services.ProduitService;

/**
 *
 * @author oXCToo
 */
public class Main extends Application {

    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;
//GestionCategorie

    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
       // Parent root = FXMLLoader.load(getClass().getResource("/views/VeloTn.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setTitle("Se connecter");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

}
