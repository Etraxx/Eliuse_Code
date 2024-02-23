package org.CIEL.Developpeur.core.entite;

public class Modele {
    
    private int id; 
    private int nombreDeSommet;

    public Modele(int id, int nombreDeSommet){
        this.id = id;
        this.nombreDeSommet = nombreDeSommet;
    }

    public int getId() {
        return id;
    }

    public int getNombreDeSommet() {
        return nombreDeSommet;
    }

    

}
