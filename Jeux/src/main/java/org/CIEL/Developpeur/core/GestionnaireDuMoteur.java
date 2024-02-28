package org.CIEL.Developpeur.core;

import org.CIEL.Developpeur.Lanceur;
import org.CIEL.Developpeur.core.utils.Constantes;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class GestionnaireDuMoteur {
    public static final long nanosecondes = 1000000000L;
    public static final float tauxImage = 1000;

    private static int fps;
    private static float tempsDeTrame = 1.0f / tauxImage;

    private boolean enCoursExecution;

    private GestionnaireDeFenetres fenetres;
    private GLFWErrorCallback erreurRappel;
    private iLogique logiqueDuJeu;

    private void initialisation() throws Exception{
        GLFW.glfwSetErrorCallback(erreurRappel = GLFWErrorCallback.createPrint(System.err));
        System.out.println("private void initialisation() throws Exception { GLFW.glfwSetErrorCallback(erreurRappel = GLFWErrorCallback.createPrint(System.err)); a rencontré une erreur ! GestionnaireDeMoteur.java    Line 22");

        fenetres = Lanceur.getWindow();

        logiqueDuJeu = Lanceur.getJeu();

        fenetres.initialisation();
        System.out.println("La fenêtre a été initialiser !");

        logiqueDuJeu.initialisation();
        System.out.println("La logique du jeu a été initialiser !");
    }

    public void start() throws Exception{
        initialisation();
        if(enCoursExecution){
            return;
        }
        run();
        System.out.println("Gestionnaire_de_moteur.java se ferme...");
    }

    public void run(){
        this.enCoursExecution = true;
        int trames = 0;
        long compteurDeTrames = 0;
        long derniereFois = System.nanoTime();
        double tempsNonTraite = 0;

        while(enCoursExecution){
            boolean rendu = false;
            long heureDeDebut = System.nanoTime();
            long tempsPasse = heureDeDebut - derniereFois;
            derniereFois = heureDeDebut;

            tempsNonTraite += tempsPasse / (double) nanosecondes;
            compteurDeTrames += tempsPasse;

            saisi();

            while (tempsNonTraite > tempsDeTrame){
                rendu = true;
                tempsNonTraite -= tempsDeTrame;

                if(fenetres.fenetreDevraitFermer()){
                    stop();
                }

                if(compteurDeTrames >= nanosecondes){
                    definirFps(trames);
                    fenetres.definirTitre(Constantes.TITRE + " " + obtenirFps());
                    trames = 0;
                    compteurDeTrames = 0;
                }
            }
            if(rendu){
                miseAJour();
                rendu();
                trames++;
            }
        }
        nettoyer();
    }

    private void stop(){
        if(!enCoursExecution){
            return;
        }
        enCoursExecution = false;
        System.out.println("GestionnaireDeMoteur.java va se fermer dans quelques secondes...");
    }

    private void saisi(){
        logiqueDuJeu.saisi();
    }

    private void rendu(){
        logiqueDuJeu.rendu();
        fenetres.miseAJour();
    }

    private void miseAJour(){
        logiqueDuJeu.miseAJour();
    }

    private void nettoyer(){
        fenetres.nettoyer();
        logiqueDuJeu.nettoyer();
        erreurRappel.free();
        GLFW.glfwTerminate();
    }

    public static int obtenirFps() {
        return fps;
    }

    public static void definirFps(int fps) {
        GestionnaireDuMoteur.fps = fps;
    }

}