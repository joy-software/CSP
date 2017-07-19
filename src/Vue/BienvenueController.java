/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controlleur.EnumTest;
import Controlleur.SimRetAr;
import Controlleur.TestEnum;
import Controlleur.TestEnum2;
import Model.Contrainte;
import Model.Variable;
import csp.java.CSPFXMain;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author NDJAMA
 */
public class BienvenueController implements Initializable {

    /**
     * le toolbar qui gère la page de bienvenue
     */
    @FXML
    private ToolBar bienvenue;
    /**
     * le toolbar qui gère la page de collecte des données
     */
    @FXML
    private ToolBar donnees;
    /**
     * le toolbar qui gère la page de collecte des contraintes
     */
    @FXML
    private ToolBar contraintes;
    /**
     * le toolbar qui gère la page du choix des algorithmes
     */
    @FXML
    private ToolBar algorithme;
    /**
     * le toolbar qui gère la page des resultats
     */
    @FXML
    private ToolBar resultat;
    /**
     * le button precédent
     */
    @FXML
    private Button precedent;   
    /**
     * le button suivant
     */
    @FXML
    private Button suivant;  
    /**
     * l'entier qui nous permettra de savoir à quelle page nous nous trouvons
     */
    private static int page = 1;
    /**
     * le label qui permet de nous donner des informations sur la page en cours 
     */
    @FXML
    private Label pagelab;
    /**
     * L'Anchorpane de la page Bienvenue
     */
    @FXML
    public static AnchorPane anchorBienvenue;
    /**
     * L'Anchorpane de la page Donnees
     */
    @FXML
    public static AnchorPane anchorDonne;
    /**
     * L'Anchorpane de la page Contraintes
     */
    @FXML
    public static AnchorPane anchorContrainte;
     /**
     * L'Anchorpane de la page algorithme
     */
    @FXML
    public static AnchorPane anchorAlgorithme;
    /**
     * L'Anchorpane de la page resultat
     */
    @FXML
    public static AnchorPane anchorResultat;
    /**
     * Le Splitpane qui contiendra les anchorpanes ci-dessus
     */
    @FXML
    private SplitPane split;
    /**
     * le textfield contenant le nombre de variables lors de la phase de collecte des données
     */
    @FXML
    private TextField donnees_nbVar;
    /**
     * le textfield contenant une valeur du domaine de la variable selectionnée
     */
    @FXML
    private TextField donnees_val;
    /**
     * le choiceBox permettant de selectionner la variable en cours lors 
     * du remplissage des données
     */
    @FXML
    private ChoiceBox donnees_var;
    /**
     * la table representant le domaine de la variable selectionnée grâce au choiceBox
     */
    @FXML
    private TableView<VarFx> donnees_table;
    /**
     * la colonne contrainte de la  table representant le domaine de la variable selectionnée grâce au choiceBox
     */
    @FXML
    private TableColumn<VarFx,String> donnees_colindice;
    /**
     * la colonne valeur de la table representant le domaine de la variable selectionnée grâce au choiceBox
     */
    @FXML
    private TableColumn<VarFx,String> donnees_colvaleur;
    /**
     * L'anchorPane permettant d'entrer les données proprement dites
     */
    @FXML
    private AnchorPane donnees_anchor;
    /**
     * le label contenant notre image dans la page de Bienvenue
     */
    @FXML
    private Label photo;
    /**
     * cet attribut contiendra toutes nos variables au cours d'une exécution de l'application
     */
    public static ArrayList<Variable> variable = new ArrayList<>();
    /**
     * cet attribut contiendra toutes nos contraintes au cours d'une exécution de l'application
     */
    public static Contrainte con = new Contrainte();
     /**
      * chaine de caractères contenant l'ensemble des lettres de l'alphabet français
      */
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    /**
     * cette liste contiendra les données à afficher dans notre tableview 
     */
    private final ObservableList<VarFx> donnees_data = FXCollections.observableArrayList();
    /**
     * le button permettant d'ajouter un nouveau element à notre tableView
     */
    @FXML
    private Button donnees_button;
    /**
     * le button permettant d'entrer le nombre de variables 
     */
    @FXML
    private Button donnees_button2;
    /**
     * L'anchorpane qui nous permettra d'entrer nos contraintes
     */
    @FXML
    private AnchorPane contraintes_anchor;
    /**
     * Le bouton d'ajout d'une nouvelle contrainte
     */
    @FXML
    private Button contraintes_button;
    /**
     * Le textfield où nous entrons nos contraintes
     */
    @FXML
    private TextField contraintes_text;
    /**
     * le tableau de contraintes
     */
    @FXML
    private TableView<ContrainteFx> contraintes_table;
    /**
     * la colonne de notre tableau de contrainte
     */
    @FXML
    private TableColumn<ContrainteFx,String> contraintes_cont;
    /**
     * Notre liste de contraintes
     */
    private final ObservableList<ContrainteFx> contraintes_data = FXCollections.observableArrayList();
    /**
     * le checkBox de la page Algorithme aidant au choix de l'algorithme à utiliser
     */
    @FXML
    private CheckBox algorithme_enumtest;
    /**
     * le checkBox de la page Algorithme aidant au choix de l'algorithme à utiliser
     */
    @FXML
    private CheckBox algorithme_simar;
    /**
     * le checkBox de la page Algorithme aidant au choix de l'algorithme à utiliser
     */
    @FXML
    private CheckBox algorithme_filtrefaible;
    /**
     * le checkBox de la page Algorithme aidant au choix de l'algorithme à utiliser
     */
    @FXML
    private CheckBox algorithme_filtrefort;
    /**
     * le checkBox de la page Algorithme aidant au choix du type de solution
     */
    @FXML
    private CheckBox algorithme_solution;
    /**
     * le checkBox de la page Algorithme aidant au choix du type de solution
     */
    @FXML
    private CheckBox algorithme_toutesolution;
    /**
     * cet entier permettra de savoir quel algorithme nous avons choisi d'implemanter
     */
    private static int algorithme_algo = 0;
    /**
     * cet entier permettra de savoir quelle type de solutions nous avons choisi 
     */
    public static int algorithme_choixSolution = 0;
    /**
     * le label contenant notre photo pour la page choix de l'algorithme
     */
    @FXML
    private Label algorithme_photo;
    /**
     * le progressBar indiquant que l'algorithme est en cours d'éxécution
     */
    @FXML
    private ProgressIndicator resultat_progress;
    /**
     * le label indiquant à quel niveau se trouve
     * l'algorithme est en cours d'éxécution
     */
    @FXML
    Label resultat_label;
    /**
     * L'anchorpane secondaire de notre page resultat
     */
    @FXML
    private AnchorPane resultat_anchor;
    /**
     * Le gridpane qui contiendra le resultat de l'éxecution
     */
    @FXML
    private ListView<String> resultat_listview;
    /**
     * le label qui contiendra la photo de notre page resultat
     */
    @FXML
    private Label resultat_photo;
    /**
     * Le checkBox servant à afficher ou non le graphe de résolution
     */
    @FXML
    private CheckBox resultat_graphe;
    /**
     * Le label indiquant que nous n'avons pas trouve de solution
     */
    @FXML
    private Label resultat_vide;
    /**
     * L'arrayList qui contiendra nos différentes solutions
     */
    public static ArrayList<String> resultat_resultat = new ArrayList();
    /**
     * L'arrayList qui servira a l'affichage nos différentes solutions
     */
    private ObservableList<String> resultat_resulta = FXCollections.observableArrayList();
    /**
     * permet de renseigner sur le choix d'un nouvel essai
     */
    private boolean reset = false;
    /**
     * le menu vider les contraintes
     */
    @FXML
    private MenuItem menu_contraintes;
    /**
     * le menu vider les variable
     */
    @FXML
    private MenuItem menu_variables;
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                  
    }    

    
    
    /**
     * cette méthode permet de modifier l'anchorpane secondaire de 
     * notre Slitpane permettant ainsi de pouvoir modifier dynamiquement
     * le contenu de notre page
     * @param pane 
     */
    public void setSecondaire(AnchorPane pane) {
         if(pane != null)
         {
             split.getItems().remove(1);
             split.getItems().add(pane);
         }
    }
    
    
    /**
     * la fonction qui gère l'affichage des pages 
     */
    @FXML
    private void shownextPages()
    {
        page++;
        handlePage();
    }
    
    /**
     * la fonction qui gère l'affichage des pages 
     */
    @FXML
    private void showPreviousPages()
    {
        page--;
        handlePage();
    }
    
    /**
     * la fonction qui gère l'affichage de la page Bienvenue
     */
    @FXML
    private void showBienvenue()
    {
        page = 1;
        handlePage();
    }
    
    /**
     * la fonction qui gère l'affichage de la page donnees
     */
    @FXML
    private void showDonnees()
    {
        page = 2;
        handlePage();
        
    }
    
    /**
     * la fonction qui gère l'affichage de la page des contraintes
     */
    @FXML
    private void showContraintes()
    {
        page = 3;
        handlePage();
    }
    
    /**
     * la fonction qui gère l'affichage de la page choix des algorithmes
     */
    @FXML
    private void showAlgorithme()
    {
        page = 4;
        handlePage();
    }
    
   /**
     * la fonction qui gère l'affichage de la page des resultats
     */
    @FXML
    private void showResultat()
    {
        page = 5;
        handlePage();
    } 
   
    /**
     * cette méthode permet de recuperer le mnemonic et de le donner au bouton des donnees
     */
    @FXML
    private void donnees_getMnemonic()
    {
        donnees_button.getScene().getAccelerators().
           put(new KeyCodeCombination(KeyCode.ENTER), (Runnable) () -> {
            donnees_button.fire();
        });
    }
    
    /**
     * cette méthode permet de recuperer le mnemonic et de le donner au bouton des contraintes
     */
    @FXML
    private void contraintes_getMnemonic()
    {
        mnemonic(contraintes_button);
    }
    
    /**
     * cette méthode permet de recuperer le mnemonic et de le donner au bouton des contraintes
     */
    @FXML
    private void variables_getMnemonic()
    {
        mnemonic(donnees_button2);
    }
    
    /**
     * cette méthode permet de lancer la bonne page dans notre fenêtre fonction
     * de l'attribut page passé en paramètres au test de contrôle switch
     */
    private void handlePage()
    {
        switch(page)
        {
            case 1:
                precedent.setDisable(true);
                suivant.setDisable(false);
                bienvenue.setVisible(true);
                donnees.setVisible(false);
                contraintes.setVisible(false);
                algorithme.setVisible(false);
                resultat.setVisible(false);
                pagelab.setText("Bienvenue dans notre CSP Resolver");
                setSecondaire(BienvenueController.anchorBienvenue);
                File f = new File("./photo/Bienvenue.png");
                BienvenueController.anchorBienvenue.getChildren().get(0).setEffect(new ImageInput(new Image("file:/"+f.getAbsolutePath())));
                menu_contraintes.setVisible(true);
                menu_variables.setVisible(true);
                break;
            case 2:
                precedent.setDisable(false);
                suivant.setDisable(false);
                bienvenue.setVisible(true);
                donnees.setVisible(true);
                contraintes.setVisible(false);
                algorithme.setVisible(false);
                resultat.setVisible(false);
                pagelab.setText("Cliquer sur suivant pour entrer les contraintes");
                setSecondaire(BienvenueController.anchorDonne);
                menu_contraintes.setVisible(true);
                menu_variables.setVisible(false);
                //mnemonic(donnees_button);
                break;
            case 3:
                precedent.setDisable(false);
                suivant.setDisable(false);
                bienvenue.setVisible(true);
                donnees.setVisible(true);
                contraintes.setVisible(true);
                algorithme.setVisible(false);
                resultat.setVisible(false);
                pagelab.setText("Cliquer sur suivant pour le choix de l'algorithme");
                setSecondaire(BienvenueController.anchorContrainte);
                menu_contraintes.setVisible(false);
                menu_variables.setVisible(true);
                //mnemonic(contraintes_button);
                break;
            case 4:
                precedent.setDisable(false);
                suivant.setDisable(false);
                bienvenue.setVisible(true);
                donnees.setVisible(true);
                contraintes.setVisible(true);
                algorithme.setVisible(true);
                resultat.setVisible(false);
                pagelab.setText("Choississez un algorithme");
                setSecondaire(BienvenueController.anchorAlgorithme);
                f = new File("./photo/algo.jpg");
                BienvenueController.anchorAlgorithme.getChildren().get(3).setEffect(new ImageInput(new Image("file:/"+f.getAbsolutePath())));
                menu_contraintes.setVisible(true);
                menu_variables.setVisible(true);
                break;
            case 5:
                //System.out.println(" algo "+algorithme_algo+ " choix "+algorithme_choixSolution);
                if((algorithme_algo != 0) && (algorithme_choixSolution != 0))
                {
                    precedent.setDisable(false);
                    suivant.setDisable(true);
                    bienvenue.setVisible(true);
                    donnees.setVisible(true);
                    contraintes.setVisible(true);
                    algorithme.setVisible(true);
                    pagelab.setText("Voici les resultats ");
                    resultat.setVisible(true);
                    setSecondaire(BienvenueController.anchorResultat);
                    f = new File("./photo/resultat.jpg");
                    BienvenueController.anchorResultat.getChildren().get(2).setEffect(new ImageInput(new Image("file:/"+f.getAbsolutePath())));
                    menu_contraintes.setVisible(true);
                    menu_variables.setVisible(true);
                    for(Variable var : variable)
                    {
                        Collections.sort(var.getDomaine());
                        Collections.sort(var.getDomaineFiltre());
                    }
                    resultat_resultat.clear();
                    response();
                    loadAlgo.start();
                    
                }
                else
                {
                    messageErreur("Du choix de l'algorithme", 
                            "Vous devez choisir un algorithme parmi les quatre proposés \n"
                              + "Puis choisir quel type de solution vous voulez.");
                    page--;
                }
                break;
        }
        
    }
    
    
    
    
    /**
     * Cette fonction permet de recuperer le nombre de variables de notre CSP tout en 
     * activant l'anchorpane servant à entrer les autres données 
     */
    @FXML
    private void donnees_activeDonnees()
    {
        Boolean ok = true;
        int val = 0;
        try {
            val = Integer.parseInt(donnees_nbVar.getText());
        } catch (Exception e) {
            ok = false;
        }
        
        if(ok)
        {
            if(val < 26)
            {
                donnees_anchor.setDisable(false);
                fillChoiceBox(val);
            }
            else
            {
                messageErreur("du nombre de variable","Choississez une valeur plus petite"
                        + " ou égale à 26");
            }
        }
    }
    
    
    /**
     * cette méthode permet de remplir notre ChoiceBox fonction du nombre de variables
     * @param nbr : le nombre de variable de notre CSP
     */
    private void fillChoiceBox(int nbr)
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        for(int i = 0 ; i < nbr; i++)
        {
            list.add(""+alphabet.charAt(i));
        }
         
        for(int i = variable.size() ; i < nbr; i++)
        {
            variable.add(new Variable());
        }
        //on associe notre liste à notre choiceBox
        donnees_var.setItems(list);
        donnees_var.setValue(list.get(0));//on met un élément par défaut.
        donnees_table.setItems(donnees_data);
        donnees_colindice.setCellValueFactory(cellData -> cellData.getValue().getIndice());
        donnees_colvaleur.setCellValueFactory(cellData -> cellData.getValue().getValue());
        mnemonic(donnees_button);
    }
    
    
    /**
     * cette méthode gère les messages d'erreur de notre application
     */
    private void messageErreur(String titre,String sms)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(CSPFXMain.getPrimaryStage()); 
            message(alert,titre,sms);
    }
    
    
    
    /**
     * cette méthode gère les messages d'information de notre application
     */
    private void messageInformation(String titre,String sms)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            message(alert,titre,sms);
    }
    
    
    
    /**
     * cette méthode permet de génerer la boite de dialogue d'erreur proprement dite
     */
    private void message(Alert alert,String titre,String messag)
    {
        alert.initOwner(CSPFXMain.getPrimaryStage()); 
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            
            File f = new File("./photo/iconCSP.png");
            
            // Add a custom icon.
            stage.getIcons().add(new Image("file:/"+f.getAbsolutePath()));
           
            alert.setTitle("Message d'erreur");
            alert.setHeaderText("\t\t\tA propos "+titre);
            alert.setContentText(messag);
            alert.showAndWait();
    }
    
    /**
     * cette méthode permet de une nouvelle valeur et à l'ajouter au domaine de la variable selectionnée
     */
    @FXML
    private void donnee_addvalue()
    {
        Boolean ok = true;
         Double val = 0.0;
        try {
           val = Double.parseDouble(donnees_val.getText());
        } catch (Exception e) {
            ok = false;
            messageErreur("de la valeur du domaine de la variable "+donnees_var.getValue(),
                    "Entrer des nombres réels uniquement");
        }
        //si c'est bien un nombre qu'on a entré
        if(ok)
        {
            
            String variab = ""+donnees_var.getValue();
            int index = alphabet.indexOf(variab);
            
            //si ce  nombre a déja été enregistrer
            if(recherche(val,index))
            {
                messageInformation("de la valeur du domaine de la variable "+variab,
                        "Le domaine de la variable "+variab+" contient déja cette valeur");
            }
            else
            {
                donnees_val.setText(null);
                variable.get(index).getDomaine().add(val);
                variable.get(index).setIndex(index);
                variable.get(index).getDomaineFiltre().add(val);
                donnees_data.add(new VarFx(""+variab, ""+val));
            }
        }
    }
    
    
    /**
     * cette méthode permet de vérifier qu'on entre pas deux valeurs identiques 
     * dans un même domaine
     */
    private boolean recherche(double val,int index)
    {
        return variable.get(index).getDomaine().contains(val);
    }
    
    
    /**
     * Cette methode nous permettra d'activer notre anchorpane pour la page des contraintes
     */
    @FXML
    private void contraintes_activeAnchor()
    {
        contraintes_anchor.setDisable(false);
        contraintes_table.setItems(contraintes_data);
        contraintes_cont.setCellValueFactory(cellData -> cellData.getValue().getContrainte());
        mnemonic(contraintes_button);
    }
    
    
    /**
     *Cette méthode permet d'ajouter un mnemonic à un bouton 
     */
    private void mnemonic(Button button)
    {
       // button.getScene().getAccelerators().clear();
        button.getScene().getAccelerators().
           put(new KeyCodeCombination(KeyCode.ENTER), (Runnable) () -> {
            button.fire();
        });
    }
    
    
    /**
     * cette méthode permet d'enregistrer notre nouvelle contrainte
     */
    @FXML
    private void contraintes_add()
    {
        String contrainte = contraintes_text.getText();
        
        if(contraintes_isValid(contrainte))
        {
            contraintes_text.clear();
            contraintes_data.add(new ContrainteFx(contrainte));
            con.getConsta().add(contrainte);
        }
    }
    
    
    /**
     * cette méthode permet de vérifier si une contrainte est valide
     */
    private boolean contraintes_isValid(String contrainte)
    {
        String minalphabet  = alphabet.substring(0, variable.size());
        String chiffres = "0123456789";
        String operateur = "!+- */<>=.";
        //System.out.println("mini alphabet "+minalphabet);
        
        if(contrainte.isEmpty() || contrainte.trim().isEmpty())
        {
            return false;
        }
        
        for(char carac : contrainte.toCharArray())
        {
            if((minalphabet.indexOf(carac) == -1) && (chiffres.indexOf(carac) == -1)
                    && (operateur.indexOf(carac) == -1))
            {
                messageErreur("de la contrainte ajoutée", ""
                        + "Les caractères autorisés pour les contraintes sont:\n"
                        + "\t"+minalphabet + chiffres + operateur);
                return false;
            }
        }
    
        return true;
    }
    
    /**
     * cette méthode permet de selectionner le type d'algorithme à exécuter
     */
    @FXML
    private void algorithme_chooseEnum()
    {
        algorithme_algo = 1;
        //System.out.println(" algo "+algorithme_algo);
        algorithme_filtrefaible.setSelected(false);
        algorithme_filtrefort.setSelected(false);
        algorithme_simar.setSelected(false);
    }
    
    /**
     * cette méthode permet de selectionner le type d'algorithme à exécuter
     */
    @FXML
    private void algorithme_chooseSimar()
    {
        //System.out.println(" algo "+algorithme_algo);
        algorithme_algo = 2;
        //System.out.println(" algo sima "+algorithme_algo);
        algorithme_filtrefaible.setSelected(false);
        algorithme_filtrefort.setSelected(false);
        algorithme_enumtest.setSelected(false);
    }
    
    /**
     * cette méthode permet de selectionner le type d'algorithme à exécuter
     */
    @FXML
    private void algorithme_chooseFiltreFaible()
    {
        algorithme_algo = 3;
        algorithme_enumtest.setSelected(false);
        algorithme_filtrefort.setSelected(false);
        algorithme_simar.setSelected(false);
    }
    
    /**
     * cette méthode permet de selectionner le type d'algorithme à exécuter
     */
    @FXML
    private void algorithme_chooseFiltreFort()
    {
        algorithme_algo = 4;
        algorithme_filtrefaible.setSelected(false);
        algorithme_enumtest.setSelected(false);
        algorithme_simar.setSelected(false);
    }
    
    /**
     * cette méthode permet de selectionner le type de solution pour notre algorithme
     */
    @FXML
    private void algorithme_choosesolution()
    {
        algorithme_choixSolution = 1;
        algorithme_toutesolution.setSelected(false);
    }
    
    /**
     * cette méthode permet de selectionner le type de solution pour notre algorithme
     */
    @FXML
    private void algorithme_chooseToutesolution()
    {
        algorithme_choixSolution = 2;
        algorithme_solution.setSelected(false);
    }
    
    /**
     * Le service qui démarre l'éxécution des algorithmes
     */
    private final Service<Void> loadAlgo = new Service<Void>(){

     @Override
    protected Task<Void> createTask() {
        return new Task<Void>(){

        @Override
        protected Void call() 
            {
                
                switch(algorithme_algo)
                {
                    case 1:
                        EnumTest test = new EnumTest();
                        //resultat_resultat.clear();
                        test.launch();
                        updateProgress(1000, 1000);
                        break;
                    case 2:
                        SimRetAr test1 = new SimRetAr();
                        //resultat_resultat.clear();
                        test1.launch();
                        updateProgress(1000, 1000);
                        break;
                    case 3:
                        TestEnum test2 = new TestEnum();
                        //resultat_resultat.clear();
                        test2.launch();
                        updateProgress(1000, 1000);
                        break;
                    case 4:
                        TestEnum2 test3 = new TestEnum2();
                        //resultat_resultat.clear();
                        test3.launch();
                        updateProgress(1000, 1000);
                        break;
                }
                return null;
            };
        };
                }
    };
    
    
    /**
     * cette méthode permet d'arreter le service 
     */
    private void response()
    {
        AnchorPane temp = (AnchorPane)anchorResultat.getChildren().get(3);
        HBox tempbar = (HBox)temp.getChildren().get(0);
        if(reset)
        {
            tempbar.getChildren().remove(1);
        }
        Label label = (Label)tempbar.getChildren().get(0);
        label.setText("En cours d'execution ...");
        resultat_progress = new ProgressIndicator();
        resultat_progress.progressProperty().unbind();
        resultat_progress.progressProperty().bind(loadAlgo.progressProperty());
        tempbar.getChildren().add(resultat_progress);
         loadAlgo.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, 
                 Worker.State oldState, Worker.State newState) -> {
             switch (newState) {
                 case SCHEDULED:
                     break;
                 case READY:
                 case RUNNING:
                     break;
                 case SUCCEEDED:
                     //AnchorPane temp = (AnchorPane)anchorResultat.getChildren().get(3);
                     label.setText("Execution terminée.");
                     anchorResultat.getChildren().get(1).setDisable(false);
                     putResult();
                     //resultat_progress.setProgress(1);
                     loadAlgo.reset();
                     tempbar.getChildren().remove(1);
                     resultat_progress = new ProgressIndicator();
                     resultat_progress.setProgress(1);
                     tempbar.getChildren().add(resultat_progress);
                     reset = true;
                     //tempbar.progressProperty().unbind();
                     break;
                 case CANCELLED:
                     
                     break;
                 case FAILED:
                     
                     break;
                     
             }
        });
    
    }
    
    
    /**
     * cette méthode permet d'enregistrer nos resultats dans notre gridPane pour affichage
     */
    private void putResult()
    {
        AnchorPane temp = (AnchorPane)anchorResultat.getChildren().get(1);
        ListView<String> templist = (ListView<String>)temp.getChildren().get(0);
        if(resultat_resultat.isEmpty())
        {
            temp.getChildren().get(3).setVisible(true);
        }
        else
        {
            temp.getChildren().get(3).setVisible(false); 
            if(BienvenueController.algorithme_choixSolution == 1)
            {
                String tempstr = resultat_resultat.get(0);
                resultat_resultat.clear();
                resultat_resultat.add(tempstr);
            }
        }
       
        
        //.out.println("les resultats "+resultat_resultat);
        resultat_resulta.clear();
        resultat_resulta.addAll(resultat_resultat);
        templist.setItems(resultat_resulta);
      
    }
    
    
    /**
     * cette méthode permet de montrer à l'utilisateur le graphe obtenu lors de l'exécution 
     */
    @FXML
    private void showGraphe()
    {
            showGraphe1();
    
    }
    
    /**
     * cette méthode permet de montrer à l'utilisateur le graphe obtenu lors de l'exécution 
     */
    private void showGraphe1()
    {
        File f;
        switch(algorithme_algo)
                {
                    case 1:
                        f = new File("outputs/resultat ENUMTEST.jcsp");
                        {
                            try {
                                messageInformation("Le graphe","Patienter quelques instants votre ordinateur affichera le fichier");
                                 Desktop.getDesktop().open(f);
                                 upload(f);
                                } catch (IOException ex) {
                                     CSPFXMain.printStrace(ex);
                                }
                        }
                        break;
                    case 2:
                        f = new File("outputs/resultat SRA.jcsp");
                        {
                            try {
                                 messageInformation("Le graphe","Patienter quelques instants votre ordinateur affichera le fichier");
                                 Desktop.getDesktop().open(f);
                                 upload(f);
                                } catch (IOException ex) {
                                     CSPFXMain.printStrace(ex);
                                }
                        }
                        break;
                    case 3:
                        f = new File("outputs/resultat TestEnum_faible.jcsp");
                        {
                            try {
                                 messageInformation("Le graphe","Patienter quelques instants votre ordinateur affichera le fichier");
                                 Desktop.getDesktop().open(f);
                                 upload(f);
                                } catch (IOException ex) {
                                     CSPFXMain.printStrace(ex);
                                }
                        }
                        break;
                    case 4:
                        f = new File("outputs/resultat TestEnum_fort.jcsp");
                        {
                            try {
                                 messageInformation("Le graphe","Patienter quelques instants votre ordinateur affichera le fichier");
                                 Desktop.getDesktop().open(f);
                                 upload(f);
                                } catch (IOException ex) {
                                     CSPFXMain.printStrace(ex);
                                }
                        }
                        break;
                }
        resultat_graphe.setSelected(false);
    }
    
    /**
     * Demande a l'utilisateur s'il voudrait uploader un fichier
     */
    private void upload(File f)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Dialogue de Confirmation");
        alert.setHeaderText("Nous voulons nous rassurer:");
        alert.setContentText("Souhaitez-vous uploader le fichier contenant le graphe?");
        alert.initOwner(CSPFXMain.getPrimaryStage()); 
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            
            File f1 = new File("./photo/iconCSP.png");
            
            // Add a custom icon.
            stage.getIcons().add(new Image("file:/"+f1.getAbsolutePath()));
            
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            FileChooser fileChooser = new FileChooser();

        // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "csp resolver (*.jcsp)","*.jcsp");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName(f.getName());

            // Show save file dialog
            File file = fileChooser.showSaveDialog(CSPFXMain.getPrimaryStage());
            Path monFichier = Paths.get(f.getAbsolutePath());
            Path monFichierCopie = Paths.get(file.getAbsolutePath());
            try {
                     Files.copy(monFichier, monFichierCopie);
            } catch (IOException ex) {
                CSPFXMain.printStrace(ex);
            }
        } else {
                // ... user chose CANCEL or closed the dialog
        }
    }
    
    /**
     * affiche l'apropos
     */
    @FXML
    private void aboutUs()
    {
        messageInformation("des Auteurs"," \t\t\t\tNDJAMA JOY JEDIDJA \n"
        +"\t\t\tDJOUBISSIE OLAMA MARCELIN \n"
        +"\t\t\t Elèves Ingénieurs à l'Ecole Nationale\n"+
        " \t\t\t\tSupérieure Polytechnique\n"+
        " \t\t\tCopyright (C) Novembre 2015\n"+
        " \t\t\t\t\t GENIE INFOS \n");
    }
    
    /**
     * vider nos contraintes
     */
    @FXML
    private void removeContraintes()
    {
        CSPFXMain.getContrainte();
        contraintes_data.clear();
        con.getConsta().clear();
    }
    
    
    
    /**
     * vider le domaine
     */
    @FXML
    private void removeDomaine()
    {
        CSPFXMain.getDonnee();
        donnees_data.clear();
        variable.clear();
    }
    
    /**
     * update a constraint
     */
    @FXML
    private void updateConstrainte()
    {
        if(contraintes_table.getSelectionModel().getSelectedIndex() >= 0)
        {
            String contrainte = contraintes_table.getSelectionModel().getSelectedItem().getContrainte().getValue();
            contraintes_text.setText(contrainte);
            con.getConsta().remove(contrainte);
            contraintes_data.remove(contraintes_table.getSelectionModel().getSelectedItem());
        }
    }
    
    /**
     * update a data
     */
    @FXML
    private void updateDonnees()
    {
        if(donnees_table.getSelectionModel().getSelectedIndex() >= 0)
        {
            Double d = Double.parseDouble(donnees_table.
                    getSelectionModel().getSelectedItem().getValue().getValue());
            String value = donnees_table.getSelectionModel().getSelectedItem().getIndice().getValue();
            donnees_val.setText(""+d);
            donnees_data.remove(donnees_table.getSelectionModel().getSelectedItem());
            donnees_var.setValue(value);
            variable.get(alphabet.indexOf(value)).getDomaine().
                    remove(d);
            variable.get(alphabet.indexOf(value)).getDomaineFiltre().
                    remove(d);
                    
        }
    }
    
    /**
     * close the app
     */
    @FXML
    private void closeApp()
    {
        CSPFXMain.getPrimaryStage().close();
    }
}




/**
 * cette classe nous permettra de remplir notre tableau de valeurs du domaine d'une variable donnée
 */
class VarFx
{
        private  final SimpleStringProperty contrainte;
        private final SimpleStringProperty value;

    public VarFx(String contrainte, String value) {
        this.contrainte = new SimpleStringProperty(contrainte);
        this.value = new SimpleStringProperty(value);
    }

    public SimpleStringProperty getIndice() {
        return contrainte;
    }

    public SimpleStringProperty getValue() {
        return value;
    }
}

/**
 * cette classe nous permettra de remplir notre tableau de contraintes
 */
class ContrainteFx
{
        private  final SimpleStringProperty contrainte;
        

    public ContrainteFx(String contrainte) {
        this.contrainte = new SimpleStringProperty(contrainte);
    }

    public SimpleStringProperty getContrainte() {
        return contrainte;
    }

    @Override
    public String toString() {
        return "ContrainteFx{" + "contrainte=" + contrainte + '}';
    }

       
}