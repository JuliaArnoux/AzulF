package game;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Joueur {

    private String nom;
    private int scorePos;
    private int scoreNeg;
    private int score;
    private int ordreJeu=-1;

    /* ON UTILISE UNE HASHMAP POUR STOCKER LES ZONES PROPRES A UN JOUEUR (MUR, LIGNES D EMOTIFS , ETC )
    * ON ACCEDE A CES ZONES DIRECTEMENT DEPUIS LES OBJETS JOUEURS  */

    private HashMap<String, ZonePrivee> zones;
    
    public Joueur(String nom) {
        this.nom = nom;
        this.score = 0;
        this.zones = new HashMap<String, ZonePrivee>();
    }

    /**
     * Ajouter les zone privé a un joueur.
     * @param s nom de la zone (sac ou defausse)
     * @param z une instance de ZonePrivee
     */
    public void addZone(String s, ZonePrivee z){
        this.zones.put(s,z);
    }
    
    /**
     * Méthode qui calcule le score d'un joueur.
     */
    public void calculeScore(){

        Mur mur =(Mur) this.getZone("Mur");
        Plancher plancher= (Plancher) this.getZone("Plancher");

        this.scorePos=mur.calculeScore();
        this.scoreNeg=plancher.scorePlancher();
        score=scorePos+scoreNeg;
    }
    
    /**
     * Méthode qui calcule le score final d'un joueur.
     */
    public void calculeScoreFinal(){

        Mur mur =(Mur) this.getZone("Mur");
        score+=mur.scoreBonus();
    }

    /* GETTERS ET SETTERS */

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ZonePrivee getZone(String s) {
        return zones.get(s);
    }

    public void setZones(HashMap<String, ZonePrivee> zones) {
        this.zones = zones;
    }

    public void setOrdreJeu(int i){

        this.ordreJeu=i;
    }

    public int getOrdreJeu(){

        return this.ordreJeu;

    }






}
