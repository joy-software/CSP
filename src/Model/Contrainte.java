/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author NDJAMA
 */
public class Contrainte {
    
    private ArrayList <String> consta = new ArrayList<>();

    public ArrayList<String> getConsta() {
        return consta;
    }

    public void setConsta(ArrayList<String> consta) {
        this.consta = consta;
    }

    @Override
    public String toString() {
        return "Contrainte{" + "contraintes =" + consta + '}';
    }
    
    
}
