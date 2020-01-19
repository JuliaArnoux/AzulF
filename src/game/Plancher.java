package game;

import game.Joueur;
import game.ZonePrivee;

import java.io.IOException;

public class Plancher extends ZonePrivee {

    public static final int[] valeurs=new int[7];

    public static final int size=7;

    {
        valeurs[0]=-1;
        valeurs[1]=-1;
        valeurs[2]=-2;
        valeurs[3]=-2;
        valeurs[4]=-2;
        valeurs[5]=-3;
        valeurs[6]=-3;
    }

    public Plancher(Joueur p, String nom){

        super(p,nom);
    }

    public String toString(){
        String s="| ";
        for(int i=0;i<size;i++){
            if(contenu.size()-1<i){
                s+=" | ";
            }
            else{
                s+=contenu.get(i).toString()+"| ";
            }

        }
        return s;
    }

    /**
     * Méthode qui ajoute la tuile t au plancher
     * @param t une instance de la tuile déplacée
     * @return un booléen
     */
    public boolean ajoutPlancher(Tuile t){
            if(this.contenu.size()<size){
                try{
                    t.getCourant().deplacer(this,t);
                    return true;
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        return false;
    }

	public static int getSize() {
		return size;
	}
    
    /**
     * Calcul le score du plancher
     * @return un entier
     */
    public int scorePlancher(){

        int cpt=0;
        for(int i=0;i<this.contenu.size();i++){
            cpt+= -valeurs[i];
        }
        return cpt;
    }

    /**
     * Méthode vidant le plancher dans la defausse tout en prenant soin de remettre la tuile premier joueur au centre du plateau et de modifier l'ordre de jeu.
     * @param p le plateau où le jeu se déroule
     */
    public void videPlancher(Plateau p){
        this.getProprietaire().setOrdreJeu(-1);
        for(int i=0;i<this.contenu.size();i++){
            if(this.contenu.get(0).getColor()==-1){
                this.getProprietaire().setOrdreJeu(1);
                try{
                    this.deplacer(p.getZones().get("Centre"),this.contenu.get(0));
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            else{
                p.jetter(this.contenu.get(0),this);
            }
            i--;
        }
    }

}
