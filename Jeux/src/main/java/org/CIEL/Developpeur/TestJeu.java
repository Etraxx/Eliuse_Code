package org.CIEL.Developpeur;

import org.CIEL.Developpeur.core.GestionnaireDuRendu;
import org.CIEL.Developpeur.core.GestionnaireDeFenetres;
import org.CIEL.Developpeur.core.iLogique;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestJeu implements iLogique {

    private int direction = 0;
    private float couleur = 0.0f;

    private final GestionnaireDuRendu rendu;
    private final GestionnaireDeFenetres fenetres;

    public TestJeu(){
        rendu = new GestionnaireDuRendu();
        fenetres = Lanceur.getWindow();
    }

    @Override
    public void initialisation() throws Exception {
        rendu.init();
    }

    @Override
    public void saisi() {
        if(fenetres.touchePresser(GLFW.GLFW_KEY_UP)){
            direction = 1;
        } else if (fenetres.touchePresser(GLFW.GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void miseAJour() {
        couleur += direction * 0.01f;
        if(couleur > 1){
            couleur = 1.0f;
        } else if (couleur <= 0) {
            couleur = 0.0f;
        }
    }

    @Override
    public void rendu() {
        if(fenetres.estRedimensionner()){
            GL11.glViewport(0, 0, fenetres.obtenirLargeur(), fenetres.obtenirHauteur());
            fenetres.definirRedimensionner(true);
        }
        fenetres.definirCouleurClaire(couleur, couleur, couleur, 0.0f);
        rendu.clear();
    }

    @Override
    public void nettoyer() {
        rendu.nettoyer();
    }
}
