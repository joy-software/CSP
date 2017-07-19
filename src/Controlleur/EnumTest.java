/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;

import Model.Contrainte;
import Model.Variable;
import Vue.BienvenueController;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *cette classe permet de mettre en oeuvre l' algorithme enumerer puis tester 
 * cas des variables reelles
 * @author NDJAMA
 */
public class EnumTest {
    
    /**
     * contient les différentes affectations 
     */
    private static HashMap<String,Double> result = new HashMap();
    /**
     * Les variables utilisées dans le programme 
     */
    private static final String variab = "abcdefghijklmnopqrstuvwxyz"; 
    /**
     * permet de gerer les valeurs à affecter à chaque variable
     */
    private static int[] indexval;
    /**
     * permet de calculer le nombre total d'affectations possibles pour le cas en cours
     */
    private static int iterato = 0;
    /**
     * permet de selectionner une variable pas encore affectée 
     */
    private boolean trouve = false;
    /**
     * Le fichier dans lequel on enregistre les resultats de nos tests.
     */
    private PrintWriter fich = null;
    
    /**
     * permettent l'affichage d'une simple decoration dans notre fichier 
     * de resultat
    */
    String decoration= "",findeco = "",decor = "",deco="";
    
    
    /**
     * le rôle de cette méthode est de simuler notre algorithme generer et tester
     * @param variable  un arrayLIst qui contient toutes les variables de notre problème, chaque variable ayant son domaine associé comme attribut
     * @param con :  un object de type contrainte qui contient un arrayList comme attribut contenant l'ensemble des contraintes de notre problème 
     * @param varchoisie: la variable sur laquelle s'effectue l'itération
     * @return un boolean
     */
    public  boolean gentest(ArrayList<Variable> variable,Contrainte con,Variable varchoisie)
    {
        String carac;
        //on affiche notre arbre
        //System.out.println();
        //System.out.println("*******************************");
        //System.out.println("*********A = "+result+"*******");
        //System.out.println("******************************");
        decoration= "";findeco = "";decor = "";deco="";
        
        
        if(result.isEmpty())
        {
            fich.println("A = "+result);
        }
        else
        {
            decor =   "|----------";
            deco = "|          ";
            findeco = deco;
            if(result.size() == 1)
            {
                fich.println("|\n|");
                fich.println(decor +"A = "+result);
            }
            else
            {
                if(result.size() == variable.size())
                {
                    for(int k = 1; k < result.size(); k++)
                    {
                        findeco += deco;
                        decoration += deco;
                    }
                    findeco += "\n";
                    findeco += findeco;
                    decoration += decor;
                }
                else
                {
                    for(int k = 1; k < result.size(); k++)
                    {
                        findeco += deco;
                        decoration += deco;
                    }
                    findeco += "\n";
                    findeco += findeco;
                    decoration += decor;
            
                    fich.println(findeco+decoration +"A = "+result);
                }
            }
        }
        
        if(result.size() == variable.size())
        {
            return consistance(variable,con);
        }
        else
        {
           int i = 0; 
           do
            {
                //On choisie une varibale qui n'est pas affectée à A
                if(!result.containsKey(""+variab.charAt(i)))
                {
                    result.put(""+variab.charAt(i),null);
                    varchoisie = variable.get(i);
                    trouve = true;
                }
                i++;
            }
           while(i < variable.size() && !trouve);
            
           //On itère sur le domaine de la variable choisie
            for (indexval[varchoisie.getIndex()] = 0;indexval[varchoisie.getIndex()] < varchoisie.getDomaine().size(); indexval[varchoisie.getIndex()]++) {
                
                carac = ""+variab.charAt(varchoisie.getIndex());
                if(result.containsKey(""+carac))
                {
                    
                    if(!result.replace(""+carac,null,varchoisie.getDomaine().get(indexval[varchoisie.getIndex()])))
                    {
                        result.replace(""+carac,varchoisie.getDomaine().get(indexval[varchoisie.getIndex()] -1),varchoisie.getDomaine().get(indexval[varchoisie.getIndex()]));
                    } 
                }
                
                
               trouve = false; //doit être renitialisée pour permettre une nouvelle évalution à l'appel recursif de la fonction
               //Au cas où nous avons une solution
                if(gentest(variable, con,varchoisie))
                {
                    BienvenueController.resultat_resultat.add("A = "+result);
                    //System.out.println("****Nous avons une solution****");
                    //System.out.println("*******************************");
                    if(BienvenueController.algorithme_choixSolution == 1)
                    {
                        return true;
                    }
                    //
                }
                
            }
            
            //Permet de paser d'une branche à une autre
             for(int j = 0; j < result.size(); j++)
                {
                    if(j >= varchoisie.getIndex())
                    {
                      result.remove(""+variab.charAt(j));
                    }
                }
            return false;
        }
    }
    
    
    /**
     * le rôle de cette methode est de dire si les valeurs affectées respectent les contraintes
     * dans ce cas on dira que l'affectation est consitante
     * @param variable: un arrayLIst qui contient toutes les variables de notre problème, chaque variable ayant son domaine associé comme attribut
     * @param con :  un object de type contrainte qui contient un arrayList comme attribut contenant l'ensemble des contraintes de notre problème 
     * @return un boolean 
     */
    public  boolean consistance(ArrayList<Variable> variable,Contrainte con)
    {
        iterato++; //permet de compter le nombre d'affectations totales
        
        /**
         * On crèe un scriptEngine à l'aide du ScriptEngineManager  dont le role est d'executer des scripts 
         * javascript, utilisé ici pour évaluer la vérification de nos contraintes
        */
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
    
        String stringvariable = "";
        
        //On récupère les variables et on les déclare en javascript en prenant soin d'associer à chaque variable sa valeur
        //et en les séparant d'un point virgule
        for(int i = 0; i < result.size(); i++)
        {
            stringvariable += "var " +variab.charAt(i)+ " = " + result.get(""+variab.charAt(i))+";";
        }
        
        /**
         * on recupère les contraintes à évaluer par notre engine
         */
        String stringconst = "";
        for(int i = 0; i < con.getConsta().size(); i++)
        {
            if(stringconst.isEmpty())
            {
                stringconst += "("+con.getConsta().get(i)+")";
            }
            else
            {
                stringconst += " && "+"("+con.getConsta().get(i)+")";
            }
        }
        
        /**
         * on envoie les données à notre engine(moteur) pour qu'il evalue les données fournies 
         * il renvoit un object d'où l'importance de le transformer en object plus spécifique de
         * type boolean
         */
        boolean resul = false;
        try {
             resul = Boolean.parseBoolean(""+engine.eval(stringvariable + stringconst));
        } catch (ScriptException ex) {
            Logger.getLogger(BienvenueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(resul)
        {
           fich.println(findeco+decoration +"A = "+result +"  *****SOLUTION*****");
        }
        else
        {
            fich.println(findeco+decoration +"A = "+result);
        }
        return resul;
    }
            
    /**
     * Permet de lancer l'exécution de notre algorithme
     */
    public  void launch()
    {
        //on recupère les données 
        //BienvenueController.getData();
        //les déclarations : quelques variables qui pourront nous aider pour le bon deroulement de notre programme
        //Boolean condition;
        //int total = 1;
        
        /**
         * on ouvre le fichier qui resultat qui contiendra notre arbre
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
        String dateStr = sdf.format(new Date());
        String nom = "outputs/resultat ENUMTEST.jcsp";

	    try {
                    fich = new PrintWriter(new BufferedWriter(new FileWriter(nom)));
                    fich.println("\t\t\t\t *****************NOUVELLE EVALUATION (ALGORITHME ENUMTEST) DU "+dateStr+"*****************");			//true c'est elle qui permet d'écrire à la suite des donnée enregistrer et non de les remplacé 
		} catch (IOException e1) {
		} 
        
        //on initialise notre tableau d'indice de valeurs des variables manipulées
        indexval = new int[BienvenueController.variable.size()];
        fich.println();
        fich.println("Les variables:");
        //On compte le nombre total d'affectation qu'on aura a la fin de la génération de notre arbre
        for(Variable parcours : BienvenueController.variable)
        {
            //total *= parcours.getDomaine().size();
            parcours.getDomaine().clear();
            parcours.getDomaine().addAll(parcours.getDomaineFiltre());
            fich.println(variab.charAt(parcours.getIndex())+": " +parcours.getDomaine());
        }
        fich.println();
        fich.println("Les contraintes: " +BienvenueController.con);
        fich.println();
        //on affiche et construit notre arbre jusqu'a ce qu'on atteint le nombre d'iterations totales calculées ci-dessus
        //do
        //{
        //    condition  = gentest(BienvenueController.variable, BienvenueController.con,new Variable());
        //}
        //while(iterato < total );
        result.clear();
        gentest(BienvenueController.variable, BienvenueController.con,new Variable());
        fich.close();
    }
    
    
    public static void main(String[] args) {
        
        //ON instantie notre variable de test
        EnumTest enums = new EnumTest();
        enums.launch();
        
    }
    
}
