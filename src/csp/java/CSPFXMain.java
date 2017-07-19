/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csp.java;

import Vue.BienvenueController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author NDJAMA
 */
public class CSPFXMain extends Application {
    
    /**
     * le stage qui permettra l'affichage de notre fenêtre javaFx
     */
    private static Stage primaryStage;
    /**
     * cet anchorpane va contenir le pane principal renvoyer par notre fichier FXML
     */
    private AnchorPane anchor;
    /**
     * le controleur de nos fenêtres JavaFx
     */
    public static BienvenueController controlleur ;

    /**
     * le getteur de notre PrimaryStage
     * @return primaryStage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
    
    
    /**
     * cette méthode permet de lancer notre interface graphique
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        
        CSPFXMain.primaryStage = primaryStage;
        CSPFXMain.primaryStage.setTitle("CSP Resolver");
        File f1 = new File("./photo/iconCSP.png");
        System.out.println("file "+f1.getAbsolutePath());
        CSPFXMain.primaryStage.getIcons().add(new Image("file:/"+f1.getAbsolutePath()));
        
        getBienvenue();
        getDonnee();
        getContrainte();
        getAlgorithme();
        getResultat();
        getInterface();
        
        
        
    }

    
    /**
     * cette méthode permet d'associer au stage de notre interface la scène graphique principale
     */
    private void getInterface()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            File f = new File("src/Vue/Bienvenue.fxml");
            loader.setLocation(new URL("file:/"+f.getAbsolutePath()));
            
            anchor = (AnchorPane)loader.load();
            controlleur = loader.getController();
            
            Scene scene = new Scene(anchor);
            primaryStage.setScene(scene);
            primaryStage.setOnShown((WindowEvent we) -> {
                controlleur.setSecondaire(BienvenueController.anchorBienvenue);
                System.out.println("Merci Joy je t'aime");
                File f1 = new File("./photo/Bienvenue.png");
                BienvenueController.anchorBienvenue.getChildren().get(0).setEffect(new ImageInput(new Image("file:/" + f1.getAbsolutePath())));
            }); 
            primaryStage.show();
                
            
        } catch (Exception e) {
            CSPFXMain.printStrace(e);
        }
    }
           
    
    
    /**
     * cette méthode permet de charger notre anchorpane de Bienvenue
     */
    private void getBienvenue()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            File f = new File("src/Vue/anchorBienvenue.fxml");
            loader.setLocation(new URL("file:/"+f.getAbsolutePath()));
            
            BienvenueController.anchorBienvenue = (AnchorPane)loader.load();
            //BienvenueController controlleur = loader.getController();
           loader.setController(controlleur);
           //controlleur.setSecondaire(BienvenueController.anchorBienvenue);
        } catch (Exception e) {
            CSPFXMain.printStrace(e);
        }
    }
    
    
    /**
     * cette méthode permet de charger notre anchorpane de recuperation des données
     */
    public static void getDonnee()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            File f = new File("src/Vue/anchorDonnee.fxml");
            loader.setLocation(new URL("file:/"+f.getAbsolutePath()));
            
            BienvenueController.anchorDonne = (AnchorPane)loader.load();
            //BienvenueController controlleur = loader.getController();
           loader.setController(controlleur);
          // controlleur.setSecondaire();
        } catch (Exception e) {
            CSPFXMain.printStrace(e);
        }
    }
    
    
    /**
     * cette méthode permet de charger notre anchorpane de contrainte
     */
    public static void getContrainte()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            File f = new File("src/Vue/anchorContrainte.fxml");
            loader.setLocation(new URL("file:/"+f.getAbsolutePath()));
            
            BienvenueController.anchorContrainte = (AnchorPane)loader.load();
            //BienvenueController controlleur = loader.getController();
           loader.setController(controlleur);
           //controlleur.setSecondaire(BienvenueController.anchorBienvenue);
        } catch (Exception e) {
            CSPFXMain.printStrace(e);
        }
    }
    
    
    /**
     * cette méthode permet de charger notre anchorpane servant au choix de l'algorithme à effectuer
     */
    private void getAlgorithme()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            File f = new File("src/Vue/anchorAlgorithme.fxml");
            loader.setLocation(new URL("file:/"+f.getAbsolutePath()));
            
            BienvenueController.anchorAlgorithme = (AnchorPane)loader.load();
            //BienvenueController controlleur = loader.getController();
           loader.setController(controlleur);
           //controlleur.setSecondaire(BienvenueController.anchorBienvenue);
        } catch (Exception e) {
            CSPFXMain.printStrace(e);
        }
    }
    
    
    /**
     * cette méthode permet de charger notre anchorpane servant a afficher le resultat de l'execution
     * de l'algorithme
     */
    private void getResultat()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            File f = new File("src/Vue/anchorResultat.fxml");
            loader.setLocation(new URL("file:/"+f.getAbsolutePath()));
            
            BienvenueController.anchorResultat = (AnchorPane)loader.load();
            //BienvenueController controlleur = loader.getController();
           loader.setController(controlleur);
           //controlleur.setSecondaire(BienvenueController.anchorBienvenue);
        } catch (Exception e) {
            CSPFXMain.printStrace(e);
        }
    }
    
    
        /**
	 * Cette méthode permet de recuperer une erreur
	 * et de l'afficher
	 * @param e
	 * @return
	 */
           public static String getStackTrace( Exception e ) { 
	    Writer result = new StringWriter(); 
	    PrintWriter printWriter = new PrintWriter( result ); 
	    e.printStackTrace( printWriter ); 
	    return result.toString(); 
	  }
    
           
         /**
	 * ecrire dans notre fichier log
	 * @param e
	 */
        public static  void printStrace(Exception e)
        {
	   
	    String nom = "./logs/log.del";
	    
	    PrintWriter fich = null;

	    try {
			fich = new PrintWriter(new BufferedWriter(new FileWriter(nom, true)));
			//true c'est elle qui permet d'écrire à la suite des donnée enregistrer et non de les remplacé 
		} catch (IOException e1) {
		} 
	    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
	    String dateStr = sdf.format(new Date());
	    fich.println(dateStr + " " + getStackTrace(e));
	    fich.println();
	    fich.println();
	    fich.close();
  }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
