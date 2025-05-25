package fr.univartois.butinfo.ihm;

import javafx.fxml.FXML;

/**
 * Modèle du jeu Lights Off qui gère la logique du jeu.
 */
public class LightsOffModel {

    // La grille de jeu : true = lumière allumée, false = lumière éteinte
    private final boolean[][] grille;
    private final int longueur;
    private final int largeur;

    /**
     * Constructeur du modèle de jeu Lights Off.
     *
     * @param longueur La longueur de la grille
     * @param largeur La largeur de la grille
     */
    public LightsOffModel(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.grille = new boolean[longueur][largeur];
        initialiserGrille();
    }


    /**
     * Initialise la grille avec un état aléatoire.
     */
    private void initialiserGrille() {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                grille[i][j] = Math.random() < 0;  // 0% de chance d'être allumée
            }
        }
    }

    public void randomiserGrille() {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                grille[i][j] = Math.random() < 0.5;  // 50% de chance d'être allumée
            }
        }
    }

    public void tricheGrille() {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                grille[i][j] = Math.random() < 1;  // 50% de chance d'être allumée
            }
        }
    }


    /**
     * Retourne la longueur de la grille.
     */
    public int getLongueur() {
        return longueur;
    }

    /**
     * Retourne la largeur de la grille.
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Retourne l'état d'une lumière.
     *
     * @param x La position x dans la grille
     * @param y La position y dans la grille
     * @return true si la lumière est allumée, false sinon
     */
    public boolean getEtatLumiere(int x, int y) {
        return grille[x][y];
    }

    /**
     * Bascule l'état d'une lumière et de ses voisines.
     *
     * @param x La position x dans la grille
     * @param y La position y dans la grille
     */
    public void basculerLumiere(int x, int y) {
        // Change l'état de la lumière cliquée
        grille[x][y] = !grille[x][y];

        // Change l'état des lumières voisines
        if (x > 0) {
            grille[x-1][y] = !grille[x-1][y];  // Voisin gauche
        }
        if (x < longueur-1) {
            grille[x+1][y] = !grille[x+1][y];  // Voisin droit
        }
        if (y > 0) {
            grille[x][y-1] = !grille[x][y-1];  // Voisin haut
        }
        if (y < largeur-1) {
            grille[x][y+1] = !grille[x][y+1];  // Voisin bas
        }
    }

    /**
     * Vérifie si toutes les lumières sont éteintes.
     *
     * @return true si le jeu est terminé (toutes les lumières sont éteintes)
     */
    public boolean estTermine() {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (grille[i][j]) {
                    return false;  // Si une lumière est allumée, le jeu n'est pas terminé
                }
            }
        }
        return true;  // Toutes les lumières sont éteintes
    }
}
