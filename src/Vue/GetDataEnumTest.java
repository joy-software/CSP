/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.util.ArrayList;

/**
 *
 * @author NDJAMA
 */
import Model.Variable;
import Model.Contrainte;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class GetDataEnumTest {
    
    public static ArrayList<Variable> variable = new ArrayList<>();
    public static Contrainte con = new Contrainte();
    public static Scanner scan = new Scanner(System.in);
   
    public static void getData()
    {
        
        System.out.println("\t\t\t ****************VARIABLES****************\n");
        int nbr = 0;
        do
        {
            System.out.println("Entrer le nombre de variables de votre problème (<= 10)");
            nbr = scan.nextInt();
        }
        while(nbr > 10);
        //System.out.println("valeur de nbr:" +nbr);
        System.out.println("\t\t\t*************");
        for(int i = 0; i < nbr; i++)
        {
            System.out.println("Entrer le nombre d'elements du domaine de la variable "+(i + 1));
            int elt = scan.nextInt();
            Variable var = new Variable();
            var.setIndex(i);
            System.out.println("\t\t\t*************");
            for(int j = 0; j < elt; j++)
            {
                System.out.println("Entrer la valeur "+(j+1) +" du  domaine de la variable "+(i + 1));
                Double temp = scan.nextDouble();
                var.getDomaine().add(temp);
                var.getDomaineFiltre().add(temp);
            }
            //
            variable.add(var);
            System.out.println("\t\t\t*************");
        }
        System.out.println();
        System.out.println("\t\t\t ****************CONTRAINTES****************\n");
        System.out.println("**********************************************************************************************\n");
        System.out.println("*****   Passons aux contraintes: Notons ici que vos variables sont notées de a vers z    *****\n"
                         + "*****           C'est à dire la première variable pour a la seconde pour b ...           *****\n"
                         + "*****                     egale se note == ex: a == b (a egale à b)                      *****\n"
                         + "*****                different se note != ex: a != b (a different de b)                  *****\n"
                         + "*****                         superieur ou égale se note >=                              *****\n"
                         + "*****                         inferieur ou égale se note <=                              *****\n"
                         + "*****           ex: a + b <= c (a plus b inférieur ou égal à la troisième variable c)    *****\n");
        System.out.println("**************************************** ^ A LIRE ^ ******************************************");
        
       System.out.println();
        
        System.out.println("Entrer le nombre de contraintes de votre problème");
        nbr = scan.nextInt();
          System.out.println();
        scan.nextLine();
        System.out.println("\t\t\t*************");
        for(int i = 0; i < nbr; i++)
        {
            System.out.println("Entrer la contrainte "+(i+1) +":\n");
            con.getConsta().add(scan.nextLine());
           
        }
        
        System.out.println("\t\t\t ****************RECUPERATION DES DONNEES TERMINEES****************\n");
        System.out.println();
        System.out.println();
        System.out.println("\t\t\t ****************RESULTAT****************\n");
    }
    
    
    
    public static void main(String[] args) {
        
        
        int a = 5;
        int b = 2;
         ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        //String foo = "40+2";
        String test = "var a = 4; var b =2; var c = 3;";
        try {
            //System.out.println(engine.eval(foo));
            System.out.println(engine.eval(test + "(b+c == a)&&(b+c == 5)") + " waouuuhhhh");
            
        } catch (ScriptException ex) {
            Logger.getLogger(GetDataEnumTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //getData();
    }
}
