package org.CIEL.Developpeur.core;

import org.CIEL.Developpeur.Lanceur;
import org.lwjgl.opengl.GL11;

public class GestionnaireDuRendu {
    
    private final GestionnaireDeFenetres fenetres;

    public GestionnaireDuRendu() {
        fenetres = Lanceur.getWindow();
    }

    public void init() throws Exception {

    }

    public void rendu(){

    }

    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void nettoyer(){
        
    }
}
