package game;

import java.util.*;
import java.io.IOException;
import java.lang.*;

/** 
* LE CHOIX DUNE CLASSE ABSTRAITE PERMET DE DEFINIR DES METHODES COMMUNES A
* TOUTES LES ZONES TOUT EN EMPECHANT CETTE CLASS D ETRE INSTANCIEE
*/

public abstract class Zone{



    /**
     * ARRAY LIST CONTENANT LES TUILES PRESENTENT DANS UNE ZONE
     */
    protected ArrayList<Tuile> contenu;

    public Zone(){
        this.contenu=new ArrayList<Tuile>();
    }

	/**
	 * Méthode qui permet d'ajouter une tuile a une zone en vérifiant qu'elle n'existe pas déjà.
	 * 
	 * @param Une instance de la tuile qu'on veut déplacer.
	 */
    public void ajouterTuile(Tuile t) throws IOException{

        if(this.contenu.contains(t)){

            throw new IOException("Tuile déjà dans la Zone");
        }

        this.contenu.add(t);

        t.setCourant(this);

    }
    
    /**
     * Méthode qui permet d'ajouter une tuile a une zone en vérifiant 
     * qu'elle n'existe pas déjà à un endroit particulier.
     * 
     * @param Une instance de la tuile qu'on veut déplacer.
     */
    public void ajouterTuileIndice(Tuile t, int indice) throws IOException{

        if(this.contenu.contains(t)){

            throw new IOException("Tuile déjà dans la Zone");
        }

        this.contenu.set(indice, t);
        t.setCourant(this);

    }

    /**
     * Méthode qui permet de supprimer une tuile en vérifiant qu'elle est dans la zone.
     * 
     * @param Une instance de la tuile à supprimer.
     */
    public void supprimer(Tuile t) throws IOException{
        if(!this.contenu.contains(t)){
            throw new IOException("Tuile non existante dans la zone");
        }

        this.contenu.remove(t);
        t.setCourant(null);
    }

    /** 
     * Méthode qui permet de déplacer une tuile de l'objet this, à la zone passé en argument.
     * 
     * @param Une zone d'arrivé et la tuile que l'on veut déplacer.
     */
    public void deplacer(Zone z, Tuile t) throws IOException{

        try{
            this.supprimer(t);

            try{
                z.ajouterTuile(t);
            }
            catch(Exception e){
                throw e;
            }
        }

        catch(Exception e){
            throw e;
        }

    }
    
    /** 
     * Méthode qui permet de déplacer une tuile de l'objet this, à la zone passé en argument à un certain indice.
     * 
     * @param z la zone d'arrivé.
     * @param t la tuile qui est déplacé.
     * @param indice un emplacement dans la zone. 
     */
    public void deplacerIndice(Zone z, Tuile t, int indice) throws IOException{

        try{
            this.supprimer(t);

            try{
                z.ajouterTuileIndice(t,indice);
            }
            catch(Exception e){
                throw e;
            }
        }

        catch(Exception e){
            throw e;
        }

    }

    /* METHODE TO STRING PRINCIPALEMENT UTILISEE POUR LE DEBUGAGE*/

    public String toString(){
        String s="";
        for(int i=0; i<this.contenu.size();i++){
            s+=this.contenu.get(i).toString();
            if(i!=this.contenu.size()-1){
                s+=",";
            }

        }
        return s;
    }

    /**
     * Méthode qui vérifie si une zone est vide.
     * 
     * @return un booléen.
     */
    public boolean isEmpty(){
        return this.contenu.isEmpty();
    }

    /**
     * Donne accès a une tuile en particulier
     *	
     * @param  i l'indice voulu.
     * @return une instance de la tuile recherché
     */
    public Tuile getTuile(int i){
        return this.contenu.get(i);
    }

    /** 
     * Methode qui renvoit la première tuile de couleur c
     * 
     * @param c un entier représentant la couleur rechercher
     * @return un instance de tuile.
     * */
    public Tuile gotColor(int c){
        for(int i=0;i<this.contenu.size();i++){
            if(this.contenu.get(i).getColor()==c){
                return this.contenu.get(i);
            }
        }

        return null;
    }
    
    /** 
     * Methode qui renvoit le contenu des zones
     * */
	public ArrayList<Tuile> getContenu() {
		return contenu;
	}
    
	/** 
     * Methode qui renvoit la taille des zones
     */
    public int getActualSize(){
        return this.contenu.size();
    }

}
