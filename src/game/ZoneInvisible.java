package game;

import java.util.Random;

/**
 * CETTE CLASSE CORRESPOND A TOUTES LES ZONES NON VISIBLES PAR LES JOUEURS IE SAC ET DEFAUSSE
 */

public class ZoneInvisible extends Zone {

    /**
     * Prend une tuile aléatoirement dans la zone.
     * 
     * @return Une instance de tuile aléatoire.
     */

    public Tuile getTuileAlea(){
        Random r = new Random();
        int rand = r.nextInt(contenu.size());
        Tuile retour = contenu.get(rand);
        return retour;
    }
}
