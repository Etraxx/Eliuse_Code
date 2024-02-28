package org.CIEL.Developpeur.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class GestionnaireDeFenetres {

    // Champ de Vision
    public static final float pov = (float) Math.toRadians(60);
    public static final float zNear = 0.01f;
    public static final float zFar = 1000f;

    //Titre de la fenêtre
    private final String titre;

    //Taille de la fenêtre
    private int largeur, hauteur;
    // Fenêtre
    private long fenetre;

    // Changer la taille
    private boolean redimensionner, vSync;

    private final Matrix4f matrixProjection;

    //Launcher
    public GestionnaireDeFenetres(String titre, int largeur, int hauteur, boolean vSync) {
        this.titre = titre;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.vSync = vSync;
        matrixProjection = new Matrix4f();
        System.out.println("Matrix4f Charger !");
    }
    public void initialisation(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit())
            throw new IllegalStateException("Impossible d'initialiser GLFW !");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        boolean maximise = false;
        if(largeur == 0 || hauteur == 0){
            largeur = 100;
            hauteur = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximise = true;
        }
        fenetre = GLFW.glfwCreateWindow(largeur, hauteur, titre, MemoryUtil.NULL, MemoryUtil.NULL);
        if(fenetre == MemoryUtil .NULL){
            throw new RuntimeException("Échec de la création de la fenêtre GLFW !");
        }
        GLFW.glfwSetFramebufferSizeCallback(fenetre, (fenetre, largeur, hauteur)->{
            this.largeur = largeur;
            this.hauteur = hauteur;
            this.definirRedimensionner(true);
        });

        GLFW.glfwSetKeyCallback(fenetre, (fenetre, touche, scannerLeCode, action, mods)-> {
            if(touche == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE){
                GLFW.glfwSetWindowShouldClose(fenetre, true);
            }
        });
        if(maximise){
            GLFW.glfwMaximizeWindow(fenetre);
        } else {
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(fenetre, (vidMode.width() - largeur) /2,
                    (vidMode.height() - hauteur) /2 );
        }
        GLFW.glfwMakeContextCurrent(fenetre);

        if(isvSync()){
            GLFW.glfwSwapInterval(1);
        }

        GLFW.glfwShowWindow(fenetre);

        GL.createCapabilities();

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void miseAJour(){
        GLFW.glfwSwapBuffers(fenetre);
        GLFW.glfwPollEvents();
    }

    public void nettoyer(){
        GLFW.glfwDestroyWindow(fenetre);
    }

    public void definirCouleurClaire(float r, float g, float b, float a){
        GL11.glClearColor(r,g,b,a);
    }

    public boolean touchePresser(int codeCle){
        return GLFW.glfwGetKey(fenetre, codeCle) == GLFW.GLFW_PRESS;
    }

    public boolean fenetreDevraitFermer(){
        return GLFW.glfwWindowShouldClose(fenetre);
    }

    public String obtenirTitre() {
        return titre;
    }

    public void definirTitre(String titre){
        GLFW.glfwSetWindowTitle(fenetre, titre);
    }

    public boolean estRedimensionner() {
        return redimensionner;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void definirRedimensionner(boolean redimensionner) {
        this.redimensionner = redimensionner;
    }

    public int obtenirLargeur() {
        return largeur;
    }

    public int obtenirHauteur() {
        return hauteur;
    }

    public long obtenirFenetre() {
        return fenetre;
    }

    public Matrix4f obtenirMatrixProjection() {
        return matrixProjection;
    }

    public Matrix4f miseAJourMatrixProjection(){
        float ratioAspect = (float) largeur / hauteur;
        return matrixProjection.setPerspective(pov, ratioAspect, zNear, zFar);
    }

    public Matrix4f miseAJourMatrixProjection(Matrix4f matrix, int largeur, int hauteur){
        float aspectRatio = (float) largeur / hauteur;
        return matrix.setPerspective(pov, aspectRatio, zNear, zFar);
    }
}