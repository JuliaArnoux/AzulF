package game;

public class LigneMotif extends ZonePrivee {

    /* LE CHAMP SIZE PERMET DE NE PAS DEPASSER LA TAILLE DE LA LIGNE DE MOTIF ( 1 A 5)
    * TOUT EN CONSERVANT LA STRUCTURE ARRAYLIST HERITEE DE LA CLASSE ABSTRAITE ZONE*/

    private int size;


    public LigneMotif(Joueur p, String n, int size){
        super(p,n);
        this.size=size;
    }


    public int getSize(){
        return this.size;
    }

    /* METHODE TO STRING POUR LE DEBUGAGE ET L AFFICHAGE TEXTUEL*/

    public String toString(){
        String s="|";
        if(this.contenu.size()==0){
            for(int i=0;i<size;i++){
                s+="  |";
            }
            return s;
        }

        for(int i=0; i<this.size;i++){
            if(i<this.contenu.size()){
                s+= this.contenu.get(i).toString();
            }

            else{
                s+=" ";
            }

            s+=" |";
        }


        return s;
    }

    /**
     * Méthode qui indique si on atteint la contenance max d'une ligne
     * @return un booléen
     */
    public boolean isFull(){

        return this.contenu.size()>=this.size;

    }


    /**
     * Méthode indiquant combien il y a de tuile Joker sur une ligne
     * @return un entier
     */
    public int containJoker(){

        int cpt=0;
        for(int i=0;i<size;i++){
            if(this.contenu.get(i).getColor()==-3){
                cpt++;
            }
        }
        return cpt;
    }
    
    /**
     * Méthode qui retourne la première tuile Joker
     * @return l'indice de la tuile
     */
    public int getJokerId(){
        for(int i=0;i<size;i++){
            if(this.contenu.get(i).getColor()==-3){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Méthode qui retourne la première tuile non Joker
     * @return l'indice de la tuile
     */
    public int getNonJokerId(){

        for(int i=0;i<size;i++){
            if(this.contenu.get(i).getColor()!=-3){
                return i;
            }
        }
        return -1;
    }
}
