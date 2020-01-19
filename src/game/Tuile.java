package game;

import java.io.IOException;
import java.lang.*;
import java.util.NoSuchElementException;


public class Tuile{

    /*LA COULEUR D UNE TUILE EST STOCKEE COMME UN INT QU ON FAIT CORRESPONDRE A UN INDICE DU
    TABLEAU STATIC DE COULEURS*/

    public static final String[] colors ={"BU","Y","R","BK","W"};

    private int color;

    /* LE CHAMP COURANT RENSIGNE LA ZONE DANS LAQUELLE SE TROUVE LA TUILE */

    private Zone courant;

    /* ON THROW UNE EXCEPTION POUR EVITER DES ERREURS PLUS LOIN DANS LE CODE EN
    * CREANT UNE TUILE AVEC UNE COULEUR QUI N EXISTE PAS
    * LES TUILES A -2 CORRESPONDENT A DES TUILES VIDES POUR EVITER DES PROBLEMES DINDEX DANS LA GESTION DES MURS
    * LA TUILE A -1 CORRESPOND A LA TUILE 1ER JOUEUR
    * -3 CORRESPOND AUX TUILES JOKER*/

    public Tuile(int c) throws NoSuchElementException {
        if(c<-3|| c>4 ){
            throw new NoSuchElementException("Couleur non valide");
        }

        else{
            this.color=c;
        }

    }

    /* METHODE TOSTRING UTILISEE POUR L AFFICHAGE TEXTUEL ET LE DEBUGAGE*/

    public String toString(){if(this.color==-1){return "1STP";}
        if(this.color==-2){return "";}
        if(this.color==-3){return "JK";}
        return Tuile.colortoString(color);
    }

    /*METHODE STATIC QUI RENVOIT LA STRING DUNE COULEUR EN FONCTION DE SON INDICE*/

    public static String colortoString(int color){
        return colors[color];
    }

    /* GETTER ET SETTER */

    public int getColor(){
        return this.color;
    }

    public void setCourant(Zone z){
        this.courant=z;
    }

    public Zone getCourant(){
        return this.courant;
    }




}
