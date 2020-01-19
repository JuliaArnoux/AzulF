package game;

import java.io.IOException;

public class Fabrique extends Zone {

    /* TAILLE CORRESPOND AU NOMBRE MAXIMUM DE TUILE DANS UNE FABRIQUE. IL PERMET DE CONSEVER
    * LA STRUCTURE D ARRAY LIST HERITEE DE LA CLASSE ABSTRAITE ZONE SANS RENCONTRER DE PROBLEMES*/

    private final int size = 4; 
    private Plateau plateauActu;
    
    public Fabrique(Plateau plateau) {
    	plateauActu = plateau;
    }


    /*FONCTION TO STRING UTILISEE POUR L AFFICHAGE TEXTUEL ET LE DEBUGAGE*/

    public String toString(){

        String s="|";

        for(int i=0;i<this.size; i++){

            /* ON REMPLIE AVEC UN ESPACE SI LA FABRIQUE N EST PAS ENTIEREMENT REMPLIE */

            if(contenu.size()-1<i){

                s+=" ";
            }

            else{

                s+=contenu.get(i).toString();

            }

            s+=" ";
        }

        s+="|";

        return s;
    }
    
    /**
     * Méthode qui va remplir une fabrique.
     * @throws IOException
     */
    public void remplissage() throws IOException {
    	ZoneInvisible sac = this.plateauActu.getSac();
    	ZoneInvisible defausse = this.plateauActu.getDefausse();
    	for(int i = 0; i < size; i++) {
    		if(!(sac.contenu.size()==0)) {
    			this.ajoutDepuisSac(sac);
    		}else if(!(defausse.contenu.size()==0)){
    			this.ajoutDepuisDefausse(defausse);
    		}else {
    			break;
    		}
    	}
    }
    
    /**
     * Méthode qui va ajouter des tuiles à la fabrique depuis le sac
     * @param sac zone invisible
     * @throws IOException
     */
    private void ajoutDepuisSac(ZoneInvisible sac) throws IOException {
    	Tuile t = sac.getTuileAlea();
		sac.deplacer(this, t);
    }
    
    /**
     * Méthode qui va ajouter des tuiles à la fabrique depuis la defausse
     * @param defausse zone invisible
     * @throws IOException
     */
    private void ajoutDepuisDefausse(ZoneInvisible defausse) throws IOException {
    	Tuile t = defausse.getTuileAlea();
    	defausse.deplacer(this, t);
    }

    /**
     * @return la taille de la fabrique
     */
	public int getSize() {
		return size;
	}
    
    
}
