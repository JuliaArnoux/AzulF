package game;

import java.io.IOException;
import java.util.ArrayList;

public class Mur extends ZonePrivee {

    /* NOMBRE MAXIMAL DE TUILES SUR LE MUR A NE PAS DEPASSER, PERMET
    * DE CONSERVER LA STRUCTUR EEN ARYLIST HERITEE DE LA CLASS ABSTRAITE ZONE */

    private static final int size=25;

    private static final ArrayList<Integer> couleursPredef=new ArrayList<Integer>();

    /* INITIALISATION DU TABLEAU INDIQUANT QUEL COULEUR EST ACCEPTEE POUR
    * CHAQUE CASE DU MUR */

    {
        couleursPredef.add(0);
        couleursPredef.add(1);
        couleursPredef.add(2);
        couleursPredef.add(3);
        couleursPredef.add(4);
        couleursPredef.add(4);
        couleursPredef.add(0);
        couleursPredef.add(1);
        couleursPredef.add(2);
        couleursPredef.add(3);
        couleursPredef.add(3);
        couleursPredef.add(4);
        couleursPredef.add(0);
        couleursPredef.add(1);
        couleursPredef.add(2);
        couleursPredef.add(2);
        couleursPredef.add(3);
        couleursPredef.add(4);
        couleursPredef.add(0);
        couleursPredef.add(1);
        couleursPredef.add(1);
        couleursPredef.add(2);
        couleursPredef.add(3);
        couleursPredef.add(4);
        couleursPredef.add(0);
    }

    public Mur(Joueur p, String nom){
        super(p, nom);
        for(int i=0;i<size;i++){
            this.contenu.add(new Tuile(-2));
        }
    }

    /* METHODE PERMENANT DOBTENIR LA STRING DUNE LIGNE DU MUR UTILSIEE POUR
    * L AFFICHAGE TEXTUEL */

    public String toStringLigne(int i, boolean variante)  {
        String s="| ";

        switch(i){
            case 0: for(int j=0; j<5;j++){
                s+=tuileString(j, variante)+" | ";

            }
                return s;
            case 1: for(int j=5; j<10;j++){
                s+=tuileString(j, variante)+" | ";
            }
                return s;
            case 2: for(int j=10; j<15;j++){
                s+=tuileString(j, variante)+" | ";
            }
                return s;
            case 3: for(int j=15; j<20;j++){
                s+=tuileString(j, variante)+" | ";
            }
                return s;
            case 4: for(int j=20; j<25;j++){
                s+=tuileString(j, variante)+" | ";
            }
                return s;

        }
        return s;
    }

    /**
     * Méthode qui affiche les tuiles dans le mur, avec une étoile si la case est vide.
     * 
     * @param i
     * @param variante
     * @return Une lettre représentant la couleur de la tuile.
     */
    private String tuileString(int i, boolean variante){
        if(this.contenu.get(i).getColor()!=-2){
            return this.contenu.get(i).toString()+" ";
        }

        if(variante){
            return " * ";
        }

        return Tuile.colortoString(couleursPredef.get(i))+"*";
    }

    /**
     * Fonction qui vérifie si il n'y pas d'autre tuile de la meme couleur dans une colonne
     * @param colonne indice de la colonne
     * @param tuile une instance de Tuile
     * @return un booléen
     */
    public boolean checkColonne(int colonne, Tuile tuile){
        for(int i=0;i<4;i++){
            if(this.getTuile(colonne)!=null){
                if(this.getTuile(colonne).getColor()==tuile.getColor()){
                    return false;
                }
            }

            colonne+=5;
        }

        return true;
    }

    
    /**
     * Cette fonction renvoie un indice en fonction de sa ligne et de sa colonne
     * @param ligne
     * @param colonne
     * @return un entier
     */
    public int getIndice(int ligne, int colonne){

        switch(ligne){
            case 0:

                return 0+colonne;


            case 1:

                return 5+colonne;


            case 2:

                return 10+colonne;


            case 3:

                return 15+colonne;

            case 4:

                return 20+colonne;

            default: return -1;

        }

    }

    /**
     * Méthode qui renvoit l'indice où se trouve la tuile de couleur color
     * @param color
     * @param ligne Sert quand il n'y a pas la variante
     * @return un entier qui est l'indice.
     */
    public int getIndiceColor(int color,int ligne){

        LigneMotif ligneMot=(LigneMotif) this.getProprietaire().getZone("LigneMotif"+ligne);

        if(ligneMot.containJoker()>0 && ligneMot.containJoker()<5){

            color=this.contenu.get(ligneMot.getNonJokerId()).getColor();
        }

        for(int i=(ligne-1)*5;i<((ligne-1)*5)+5;i++){

            if(couleursPredef.get(i)==color){
                return i;
            }

        }

        return -1;
    }

    /**
     * Pour une tuile donnée calcul le score avec les tuiles horizontales
     * @param indice de la tuile
     * @param ligne
     * @return un entier
     */
    private int adjaLigne(int indice, int ligne) {

        if(contenu.get(indice).getColor()==-2){
            return 0;
        }
        int cpt = 0;
        for(int i=indice; i<(ligne*5)+4;i++){
            if(contenu.get(i+1).getColor()==-2){
                break;
            }
            cpt++;
        }

        for(int i=indice; i>(ligne*5);i--){
            if(contenu.get(i-1).getColor()==-2){
                break;
            }
            cpt++;
        }
        return cpt;

    }


    /**
     * Pour une tuile donnée calcul le score aver les tuiles verticales
     * @param indice de la tuile
     * @param ligne
     * @return un entier
     */
    private int adjaColonne(int indice, int ligne) {

        if(contenu.get(indice).getColor()==-2){
            return 0;
        }

        int cpt = 0;

        for(int i=indice+5; i<=(indice%5)+20;i+=5){

            if(contenu.get(i).getColor()==-2){

                break;
            }

            cpt++;


        }

        for(int i=indice-5; i>=(indice%5);i-=5){

            if(contenu.get(i).getColor()==-2){

                break;
            }

            cpt++;


        }

        return cpt;

    }

    
    /**
     * Méthode qui calcule le score total hors bonus
     * @return un entier représentant le score.
     */
    public int calculeScore(){

        int score=0;
        int scoresave;

        for(int i=0;i<size;i++){
        	
        	System.out.print(i+" ");
        	System.out.println(adjaLigne(i, 0)+" "+adjaColonne(i, 0));

            if(i>=0 && i<= 4){
            
            	scoresave=score;
            	

                score+= adjaLigne(i, 0);
                score+= adjaColonne(i, 0);
                
                
                if(scoresave==score && this.contenu.get(i).getColor()!=-2){
                	score++;
                }

            }

            if(i>=5 && i<= 9){
            	
            	scoresave=score;

                score+= adjaLigne(i, 1);
                score+= adjaColonne(i, 1);
                
                if(scoresave==score && this.contenu.get(i).getColor()!=-2){
                	score++;
                }


            }

            if(i>=10 && i<= 14){

            	scoresave=score;

                score+= adjaLigne(i, 2);
                score+= adjaColonne(i, 2);
                
                if(scoresave==score && this.contenu.get(i).getColor()!=-2){
                	score++;
                }


            }

            if(i>=15 && i<= 19){
            	scoresave=score;

                score+= adjaLigne(i, 3);
                score+= adjaColonne(i, 3);
                
                if(scoresave==score && this.contenu.get(i).getColor()!=-2){
                	score++;
                }


            }

            if(i>=20 && i<= 24){
            	scoresave=score;

                score+= adjaLigne(i, 4);
                score+= adjaColonne(i, 4);
                
                if(scoresave==score && this.contenu.get(i).getColor()!=-2){
                	score++;
                }


            }
            
            



        }

        return score;



    }

    /**
     * Méthode qui calcule le score total avec bonus
     * @return un entier représentant le score.
     */
    public int scoreBonus(){

        int cpt=0;

        for(int i=0;i<5;i++){

            if(lignePleine(i)){

                cpt+=2;

            }

            if(colonnePleine(i)){

                cpt+=7;
            }

            if(couleurPleine(i)){

                cpt+=10;
            }


        }

        return cpt;

    }

    /**
     * Méthode qui indique si une ligne est pleine
     * @param ligne
     * @return un booléen
     */
    private boolean lignePleine(int ligne){

        for(int i=ligne; i<ligne+5;i++){

            if(this.contenu.get(i).getColor()==-2){

                return false;
            }

        }

        return true;

    }

    /**
     * Méthode qui indique si une colonne est pleine
     * @param colonne
     * @return un booléen.
     */
    private boolean colonnePleine(int colonne){


        for(int i=colonne; i<colonne+20;i+=5){

            if(this.contenu.get(i).getColor()==-2){

                return false;
            }

        }

        return true;


    }

	public static int getSize() {
		return size;
	}
  
	/**
	 * Méthode qui indique si une couleur a été rempli sur le mur
	 * @param couleur
	 * @return un booléen
	 */
    private boolean couleurPleine(int couleur){

        int cpt=0;

        for(int i=0;i<size;i++){

            if(this.contenu.get(i).getColor()==couleur){

                cpt++;
            }


        }

        return cpt==5;
    }


    /**
     * Vérifie si au moins une ligne est pleine dans le mur
     * @return un booléen
     */
    public boolean lignesPleines(){

        return lignePleine(0) || lignePleine(5) || lignePleine(10) || lignePleine(15) ||lignePleine(20);


    }



    /**
     * Vide une ligne de toute ses tuiles à la défausse
     * @param ligne
     * @param p
     */
    public void videLigne(int ligne, Plateau p){

        for(int i=ligne*5; i<(ligne*5)+5;i++){

            p.jetter(this.contenu.get(i),this);

        }

    }
    
    public boolean gotColor(int ligne, int color) {
    	
    	for(int i=(ligne-1)*5;i<((ligne-1)*5)+5;i++) {
    		
    		if(this.contenu.get(i).getColor()==color) {
    			return true;
    		}
    		
    		
    		
    	}
    	
    	return false;
    }

	public static ArrayList<Integer> getCouleurspredef() {
		return couleursPredef;
	}
    
    
}
