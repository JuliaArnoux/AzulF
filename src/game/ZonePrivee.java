package game;

/** 
 * CETTE CLASSE CORRESPOND AUX ZONES QUI SONT PRIVEES A UN JOUEUR. CE SONT LES SEULS
 * A POUVOIR AGIR DESSUS
 */

public class ZonePrivee extends Zone {

    private Joueur proprietaire;


    public ZonePrivee(Joueur p, String nom){
        super();
        this.proprietaire=p;
        p.addZone(nom,this);
    }
    
    /**
     * Retourne le propriétaire de la zone.
     * 
     * @return Une instance de Joueur qui correspond à la zone privé.
     */
    public Joueur getProprietaire(){
        return this.proprietaire;
    }
}
