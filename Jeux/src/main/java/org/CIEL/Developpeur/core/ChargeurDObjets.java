package org.CIEL.Developpeur.core;

import java.util.ArrayList;
import java.util.List;

import org.CIEL.Developpeur.core.entite.Modele;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class ChargeurDObjets {
    
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public Modele chargerModele(float[] sommets){

    }

    private int creeVAO(){

    }

    private void stockerLesDonneesDansLaListeDesAttributs(int attributNo, int nombreDeSommet, float[] donnees){
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
    }

    private void delier(){
        GL30.glBindVertexArray(0);
    }

    public void nettoyer(){
        for(int vao : vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int  vbo : vbos){
            GL30.glDeleteBuffers(vbo);
        }
    }
}
