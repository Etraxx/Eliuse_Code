package org.CIEL.Developpeur;

import org.CIEL.Developpeur.core.GestionnaireDuMoteur;
import org.CIEL.Developpeur.core.GestionnaireDeFenetres;
import org.CIEL.Developpeur.core.utils.Constantes;
import org.lwjgl.Version;

public class Lanceur {
    private static GestionnaireDeFenetres fenetres;
    private static GestionnaireDuMoteur moteur;
    private static TestJeu jeu;


    public static void main(String[] args){
        System.out.println(Version.getVersion());
        fenetres = new GestionnaireDeFenetres(Constantes.TITRE, 1920, 1080, false);
        System.out.println("Gestionnaire de fenetres défini sur 1920x1080 60fps plein écran défini sur false");
        jeu = new TestJeu();
        System.out.println("TestJeu Chargé !");
        moteur = new GestionnaireDuMoteur();
        System.out.println("Chargement du GestionnaireDeMoteur...");
        try{
            moteur.start();
            System.out.println("Le moteur est fermé.");
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Erreur au démarrage du moteur : " + e.getMessage());
        }
    }

    public static GestionnaireDeFenetres getWindow() {
        return fenetres;
    }

    public static TestJeu getJeu() {
        return jeu;
    }
}
