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
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *cette classe permet de mettre en oeuvre l' algorithme tester puis enumerer 
 * cas des variables reelles
 * @author NDJAMA
 */
public class TestEnum2 {
   
    
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
     * cet arrayList nous aidera dans le filtrage des données
     */
   // private ArrayList<Variable> domaineAFiltrer;
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
    public  boolean anticipation(ArrayList<Variable> variable,Contrainte con,Variable varchoisie)
    {
        String carac;
        //on affiche notre arbre
        //System.out.println();
        //System.out.println("*******************************");
        //System.out.println("*********A = "+result+"*******");
        //System.out.println("******************************");
        decoration= "";findeco = "";decor = "";deco="";
        //boolean resul = false;
        
        
         /**
          * ici on gère l'affichage dans le fichier
          */
          decoration = "";findeco = "";decor = "";deco = "";
        
        
            decor =   "|----------";
            deco  =   "|          ";
            findeco = deco;
            if(result.isEmpty())
            {
                fich.println("A = "+result);
            }
            else if(result.size() == 1)
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
                    
                    fich.println(findeco+decoration +"A = "+result +"  *****SOLUTION*****");
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
        
        if(result.size() == variable.size())
        {
            return true;
        }
        else
        {
           int i = 0;
           
           
           do
            {
                //On choisie une varibale qui n'est pas affectée à A
                if(!result.containsKey(""+variab.charAt(i)))
                {
                    if(!variable.get(i).getDomaine().isEmpty())
                    {
                         result.put(""+variab.charAt(i),null);
                         varchoisie = variable.get(i);
                         trouve = true;
                    }
                   
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
                        result.replace(""+carac,result.get(carac),varchoisie.getDomaine().get(indexval[varchoisie.getIndex()]));
                    } 
                }
                
                trouve = false; //doit être renitialisée pour permettre une nouvelle évalution à l'appel recursif de la fonction
             
                int index = 0;
                ////System.out.println("before /******/"+variable);
                ////System.out.println("domaine /*****/"+variable);
                for(Variable refill :  variable)
                {
                    index++;
                    ////System.out.println("refill "+refill);
                    if(!result.containsKey(""+variab.charAt(refill.getIndex())))
                    {
                        refill.getDomaine().clear();
                        ////System.out.println("on est entré");
                        refill.getDomaine().addAll(variable.get( index - 1).getDomaineFiltre());
                    }
                }
                ////System.out.println("on a redefini *********" + variable);
               index = 0;
               for(Variable filtre: variable)
               {
                   index++;
                   if(!result.containsKey(""+variab.charAt(filtre.getIndex())))
                   {
                       filtrage(variable,con,varchoisie,filtre);
                   }
                }
            //Au cas où nous avons une solution
                if(anticipation(variable, con,varchoisie))
                {
                    BienvenueController.resultat_resultat.add(""+result);
                    //System.out.println("****Nous avons une solution****");
                    //System.out.println("*******************************");
                    if(BienvenueController.algorithme_choixSolution == 1)
                    {
                        return true;
                    }
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
     * le rôle de cette methode est de dire est de filtrer le domaine de la variable passée en paramètre
     * @param variable: un arrayLIst qui contient toutes les variables de notre problème, chaque variable ayant son domaine associé comme attribut
     * @param con :  un object de type contrainte qui contient un arrayList comme attribut contenant l'ensemble des contraintes de notre problème
     * @param varchoisie
     * @param filtre : la variable dont le domaine doit être filtré
     */
    public  void filtrage(ArrayList<Variable> variable,Contrainte con,Variable varchoisie,Variable filtre)
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
        
        String tempvar = stringvariable;
        Boolean resul ;
         if(!stringvariable.isEmpty())
         {
            
        ArrayList<Double> tempDomaine = new ArrayList<>();
        tempDomaine.addAll(filtre.getDomaine());
        //System.out.println("********************"+result+"************ "+tempDomaine);
        
        
        Variable varchoisie1;
        for(double val : tempDomaine)
        {
            resul = true;
            stringvariable = tempvar;
            stringvariable += "var " +variab.charAt(filtre.getIndex())+ " = " + val +";";
            
            /**
            * on recupère les contraintes à évaluer par notre engine
            */
            String stringconst;
      
                for(int i = 0; i < con.getConsta().size(); i++)
                {
                    stringconst = "("+con.getConsta().get(i)+")";
            
                    boolean temp;
                    try {
                             /**
                              * on envoie les données à notre engine(moteur) pour qu'il evalue les données fournies 
                              * il renvoit un object d'où l'importance de le transformer en object plus spécifique de
                              * type boolean
                              */
                            temp = Boolean.parseBoolean(""+engine.eval(stringvariable + stringconst));   
                            ////System.out.println("string variable "+stringvariable+"  stringconst "+stringconst);
                        } catch (ScriptException ex) {
                            //Logger.getLogger(BienvenueController.class.getName()).log(Level.SEVERE, null, ex);
                            ////System.out.println("Erreur "+iterato +"********* variable " +stringvariable +"******* contrainte " + stringconst);
                            temp = true;
                        }
                   resul = resul && temp;
                    if(!temp)
                    {
                        ////System.out.println("val  "+val);
                        filtre.getDomaine().remove(val);
                        //System.out.println("filtre remove1 "+filtre);
                    }
                }
                //System.out.println("variable /*************/"+variable);
                varchoisie1 = new Variable();
                if(resul)
                {
                         Boolean trouv = false;
                        int j = 0;  
                        
                        do
                        {
                            //On choisie une varibale qui n'est pas affectée à A
                            if(!result.containsKey(""+variab.charAt(j)) && (j > filtre.getIndex()))
                            {
                                varchoisie1 = variable.get(j);
                                //System.out.println("varchoisie " +varchoisie1);
                                trouv = true;
                            }
                            j++;
                        }
                        while(j < variable.size() && !trouv);
                        if(trouv)
                        {
                       
                            if(varchoisie1.getDomaine().isEmpty())
                            {
                                 variable.get(varchoisie1.getIndex()).getDomaine().addAll(variable.get(varchoisie1.getIndex()).getDomaineFiltre());
                            }
                        ArrayList<Double> tempDomain = new ArrayList<>();
                        //System.out.println("domaine add"+varchoisie1.getDomaine());
                        tempDomain.addAll(varchoisie1.getDomaine());
                        String tempvar1 = stringvariable;
                        
                        for(double val1 : tempDomain)
                        {
                            stringvariable = tempvar1;
                            stringvariable += "var " +variab.charAt(varchoisie1.getIndex())+ " = " + val1 +";";
                            //System.out.println("****************contraintes********************* "+stringvariable);
                            /**
                            * on recupère les contraintes à évaluer par notre engine
                            */
      
                            for(int k = 0; k < con.getConsta().size(); k++)
                            {
                                stringconst = "("+con.getConsta().get(k)+")";
            
                                boolean temp1;
                                try {
                                    /**
                                    * on envoie les données à notre engine(moteur) pour qu'il evalue les données fournies 
                                    * il renvoit un object d'où l'importance de le transformer en object plus spécifique de
                                    * type boolean
                                    */
                                    temp1 = Boolean.parseBoolean(""+engine.eval(stringvariable + stringconst));   
                                    ////System.out.println("string variable "+stringvariable+"  stringconst "+stringconst);
                                } catch (ScriptException ex) {
                                    //Logger.getLogger(BienvenueController.class.getName()).log(Level.SEVERE, null, ex);
                                    ////System.out.println("Erreur "+iterato +"********* variable " +stringvariable +"******* contrainte " + stringconst);
                                    temp1 = true;
                                }
                                if(!temp1)
                                {
                                    ////System.out.println("val  "+val);
                                    varchoisie1.getDomaine().remove(val1);
                            
                                }
                            }
                            //System.out.println("domaine final"+varchoisie1.getDomaine());
                             //variable.get(j - 1).setDomaine(varchoisie1.getDomaine());
                        }
                        if(varchoisie1.getDomaine().isEmpty())
                        {
                           filtre.getDomaine().remove(val); 
                            //System.out.println("filtre remove 2 "+filtre);
                        }
                        if((variable.size() - varchoisie1.getIndex())>1)
                            {
                                 variable.get(varchoisie1.getIndex()).getDomaine().clear();
                                 variable.get(varchoisie1.getIndex()).getDomaine().addAll(variable.get(varchoisie1.getIndex()).getDomaineFiltre());
                            }
                        }
                }
                variable.get(filtre.getIndex()).setDomaine(filtre.getDomaine());
                //variable.get(varchoisie1.getIndex()).getDomaine().addAll(variable.get(varchoisie1.getIndex()).getDomaineFiltre());
                    
        }
        
        if(filtre.getDomaine().isEmpty())
        {
            varchoisie.getDomaine().remove(result.get(""+variab.charAt(varchoisie.getIndex())));
            //System.out.println("variable finale **********"+result.get(""+variab.charAt(varchoisie.getIndex()))+"************* "+varchoisie);
        }
        variable.get(varchoisie.getIndex()).setDomaine(varchoisie.getDomaine());
    }
 }
    
    
    /**
     * Permet de lancer l'exécution de notre algorithme
     */
    public void launch()
    {
        //on recupère les données 
        //BienvenueController.getData();
        //les déclarations : quelques variables qui pourront nous aider pour le bon deroulement de notre programme
        //Boolean condition;
        //int total = 1;
        
        /**
         * on ouvre le fichier qui resultat qui contiendra notre arbre
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
        String dateStr = sdf.format(new Date());
        String nom = "outputs/resultat TestEnum_fort.jcsp";

	    try {
			fich = new PrintWriter(new BufferedWriter(new FileWriter(nom)));
                        fich.println("\t\t\t\t *****************NOUVELLE EVALUATION (ALGORITHME TestEnum fort) DU "+dateStr+"*****************");
                        //true c'est elle qui permet d'écrire à la suite des donnée enregistrer et non de les remplacé 
		} catch (IOException e1) {
		} 
        //*/
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
        //domaineAFiltrer = (ArrayList<Variable>) BienvenueController.variable.clone();
        ////System.out.println("domaine filtrer "+BienvenueController.variable);
        result.clear();
        anticipation(BienvenueController.variable, BienvenueController.con,new Variable());
      
       // while(iterato < total );
        fich.close();
    }
    
    public static void main(String[] args) {
        
        TestEnum2 test = new TestEnum2();
        test.launch();
        
    }
    
}
