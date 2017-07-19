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
public class Variable{
    
    
    private int index;
    private ArrayList <Double> domaine = new ArrayList<>();
    private ArrayList <Double> domaineFiltre = new ArrayList<>();

    public ArrayList<Double> getDomaineFiltre() {
        return domaineFiltre;
    }

    public void setDomaineFiltre(ArrayList<Double> domaineFiltre) {
        this.domaineFiltre = domaineFiltre;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<Double> getDomaine() {
        return domaine;
    }

    public void setDomaine(ArrayList<Double> domaine) {
        this.domaine = domaine;
    }

    @Override
    public String toString() {
        return "Variable{" + "index=" + index + ", domaine=" + domaine + ", domaineFiltre=" + domaineFiltre + '}';
    }

    
    
    
    
    
}
