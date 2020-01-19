package game;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Plateau {

    private ArrayList<Joueur> joueurs;
    private int nbJoueurs;
    private HashMap<String,Zone> zones;
    private int nbFabriques;
    private boolean variante;
    private boolean joker;

    public Plateau(int nbJoueurs, boolean variante, boolean joker) throws IOException {

        this.variante=variante;
        this.joker=joker;
        /* ON VERIFIE QUE LE NOMBRE DE JOUEUR EST BIEN COMPRIS ENTRE 2 ET 4*/
        if(nbJoueurs<2){
            throw new IOException("Pas assez de joueurs");
        }
        if(nbJoueurs>4){
            throw new IOException("Trop de joueurs");
        }
        /* ON INITIALISE LE SVARIABLES D INSTANCE*/
        this.nbJoueurs=nbJoueurs;
        this.joueurs=new ArrayList<Joueur>();
        this.zones=new HashMap<String,Zone>();
        /* ON AJOUTE LE BON NOMBRE DE JOUEURS*/
        ajoutJoueur();
        /* ON AJOUTE LES ZONES INVISIBLES AU JOUEUR IE SAC ET DEFAUSSE*/
        ajoutZoneInvisible();
        /* ON AJOUTE DES FABRIQUES EN FONCTION DU NOMBRE DE JOUEURS */
        ajoutFabrique();
        /* ON AJOUTE LE CENTRE DU PLATEAU */
        ajoutCentrePlateau();
        /* ON AJOUTE LES ZONES PROPRES AUX JOUEURS (MUR, LIGNES DE MOTIF , ETC )*/
        ajoutZoneJoueur();
        /* ON REMPLIT LE SAC */
        rempliSac();
    }
    
    /**
     * Ajoute les joueurs
     */
    private void ajoutJoueur() {
    	for(int i=0;i<this.nbJoueurs;i++){
            this.joueurs.add(new Joueur("Joueur"+Integer.valueOf(i+1)));
        }
    }
    
    /**
     * Ajoute les zones propre à chaque joueurs
     */
    private void ajoutZoneJoueur() {
	    for(int i=0;i<joueurs.size();i++){
	        for(int j=1;j<6;j++){
	            new LigneMotif(joueurs.get(i),"LigneMotif"+j,j);
	        }
	        new Mur(joueurs.get(i), "Mur");
	        new Plancher(joueurs.get(i),"Plancher");
	    }
    }
    
    /**
     * Ajoute les zones invisibles
     */
    private void ajoutZoneInvisible() {
    	this.zones.put("Sac",new ZoneInvisible());
        this.zones.put("Defausse", new ZoneInvisible());
    }
    
    /**
     * Ajoute le centre du plateau
     */
    private void ajoutCentrePlateau() {
    	zones.put("Centre",new CentreJeu());
    }
    
    /**
     * Ajoute les fabriques
     */
    private void ajoutFabrique() {
    	if(nbJoueurs==2){
        	this.nbFabriques = 6;
        }else if(nbJoueurs==3){
        	this.nbFabriques = 8;
        }else if(nbJoueurs==4){
        	this.nbFabriques = 10;
        }      
        for(int i=1; i<this.nbFabriques;i++){
            zones.put("Fabrique "+Integer.valueOf(i), new Fabrique(this));
        }
    }
    
    /**
     * Rempli toutes les fabriques
     * @throws IOException
     */
    public void rempliFabrique() throws IOException {
    	for(int i=1; i<this.nbFabriques;i++){
            getFabrique(i).remplissage();
        }
    }
    
    /**
     * Rempli le sac
     * @throws IOException
     */
    private void rempliSac() throws IOException {
    	ZoneInvisible sac = getSac();

    	int nbJok=2;
    	if(nbJoueurs==2){
    	    nbJok=1;
        }
    	if(!joker){
    	    nbJok=0;
        }

    	for(int i=0;i<5;i++){

            for(int j = 0; j< 20-nbJok; j++) {

                Tuile t = new Tuile(i);
                sac.ajouterTuile(t);
            }

            for(int j=0;j<nbJok;j++){

                sac.ajouterTuile(new Tuile(-3));
            }
        }
    }

  
    /**
     * Fonction qui déplace toute les tuiles de la même couleur d'une zone à la ligne de motif choisie.
     * @param origine la zone d'origine (fabrique ou centre)
     * @param tuile la tuile déplacé
     * @param ligneMot La ligne voulu
     * @param j une instance du Joueur qui a effectuer l'action.
     * @return Une chaîne de caractère permettant de gérer les erreurs. 
     */
    public String rempLignMotif(Zone origine, int tuile, int ligneMot, Joueur j) {

        /* ON VERIFIE QU IL Y A DES TUILES DANS LA ZONE D ORIGINE */
        if(origine.isEmpty()){
            return "Zone vide";
        }

        int couleur= origine.getTuile(tuile).getColor();
        Boolean b;
        Plancher p=(Plancher)j.getZone("Plancher");
        int size = origine.getActualSize();
        Mur mur=(Mur) j.getZone("Mur");

        /* GESTION DES ERREURES */
        
        
        
        if(ligneMot!=-1){
            LigneMotif tmp=(LigneMotif) j.getZone("LigneMotif"+String.valueOf(ligneMot));
            /* ON VERIFIE QUE LA LIGNE DE MOTIF NE SOIT PAS DEJA PLEINE */
            if(tmp.isFull() ){
                return "Ligne de motif déjà remplie";
            }
            /*ON VERIFIE QUE LA LIGNE NE SOIT PAS REMPLIE AVEC UNE AUTRE COULEUR */
            if(!tmp.isEmpty()){
                if(tmp.getTuile(0).getColor()!= origine.getTuile(tuile).getColor() && origine.getTuile(tuile).getColor()!=-3){
                    return "Ligne déjà remplie avec une autre couleur";
                }
            }
            
            if(mur.gotColor(ligneMot, couleur)) {
            	
            	return "Couleur déjà sur le mur";
            }
            

        }

        /* ON TRAITE LE CAS OU LA ZONE DORIGINE EST LE CENTRE DU PLATEAU */
        if(origine instanceof CentreJeu){
            /* ON TRANSFERE LA TUILE 1ER JOUEUR DANS LE PLANCHER SI ELLE EXISTE DANS LE CENTRE DU PLATEAU
            * (TOUJOURS EN POSITION 0)*/
                if(origine.getTuile(0).getColor()==-1){
                     b=p.ajoutPlancher(origine.getTuile(0));
                }

        }

        /*ON TRAITE LE CAS OU ON VEUT ENVOYER LES TUILES AU PLANCHER */
        if(ligneMot==-1){

            Plancher plancher=(Plancher) j.getZone("Plancher");
            boolean b2;

            /*ON PARCOURS LA ZONE D ORIGINE */
            for(int i=0;i<origine.getActualSize();i++){
                if(origine.getActualSize()==0){
                    return "Zone vide";
                }

                if(couleur==origine.getTuile(i).getColor()|| origine.getTuile(i).getColor()==-3){

                    b2=plancher.ajoutPlancher(origine.getTuile(i));
                    if(!b2){

                        this.jetter(origine.getTuile(i),origine);
                    }

                    i--;

                }

                else{
                    if(origine instanceof Fabrique){
                        try{
                            origine.deplacer(this.zones.get("Centre"),origine.getTuile(i));
                        }catch(IOException e){
                            e.printStackTrace();
                        }

                        i--;

                    }
                }



            }


            return "Action complétée";

        }


        /* ON TRAITE LE CAS GENERIQUE */
        LigneMotif tmp=(LigneMotif) j.getZone("LigneMotif"+String.valueOf(ligneMot));
            for(int i=0;i<size;i++){
                /* ON REMPLIE LA LIGNE DE MOTIF QUAND LES TUILES SONT DE LA BONNE COULEUR */
                if(couleur==origine.getTuile(0).getColor()|| origine.getTuile(0).getColor()==-3){
                    if(tmp.contenu.size()<tmp.getSize()){
                        try{
                            origine.deplacer(tmp,origine.getTuile(0));
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    /*ON ENVOIE AU PLANCHER OU A LA DEFAUSSE QUAND LE PLANCHER EST PLEIN SI ON DEPASSE LA
                    * CONTENANCE MAXIMALE DE LA LIGNE DE MOTIF */
                    else{
                         b=p.ajoutPlancher(origine.getTuile(0));
                         if(!b){
                             this.jetter(origine.getTuile(0),origine);
                         }
                    }
                }
                /* ON ENVOIT AU CENTRE DU PLATEAU LES AUTRES TUILES DE LA FABRIQUE */
                else{
                    try{
                        origine.deplacer(this.zones.get("Centre"),origine.getTuile(0));
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }


        return "Action complétée";
    }


    /*FONCTION QUI EST APPELLE LORSQU UNE LIGNE DE MOTIF EST PLEINE; REMPLI LA CASE CORRESPONDANTE
    * DE LA LIGNE DU MUR CORRESPONDANT AVEC UNE TUILE ET VIDE LE RESTE DE LA LIGNE MOTIF; SI LA VARIANTE
    * EST APPLIQUEE ON CHOISI LA CASE DU MUR DE COLONNE VARIANTE; SINON VARIANTE VAUT -1*/
    
    /**
     * Fonction appelé lorsqu'une ligne de motif est pleine, rempli la case correspondante de la ligne du mur correspondant
     * avec une tuile et vide le reste de la ligne motif.
     * Si la variante est appliquée, on choisit la case du mur de colonne variante
     * sinon variante vaut -1
     * @param j
     * @param indiceLigne
     * @param variante vaut -1 si la variante n'est pas appliqué.
     * @return
     */
    public String rempliMur(Joueur j, int indiceLigne, int variante){

        Mur mur=(Mur) j.getZone("Mur");
        LigneMotif ligneMot=(LigneMotif)  j.getZone("LigneMotif"+indiceLigne);

        /*ON GERE LE CAS DE LA VARIANTE AVEC LES SITUATION OU LE JOUEUR PEUT SE RETROUVER BLOQUER*/

        if(variante!=-1){

            if(!mur.checkColonne(variante,ligneMot.getTuile(0))){

                return "Il y a déjà une tuile de cette couleur dans cette colonne";

            }

            if(mur.getTuile(mur.getIndice(indiceLigne,variante)).getColor()!=-2){

                return "Case déjà occupée";
            }

        }
        /*ON GERE LE DEPLACEMENT DE LA PREMIER TUILE DE LA LIGNE DANS LE MUR*/

            try{

               int indice =0;
               if(ligneMot.containJoker()>0){
                    indice=ligneMot.getJokerId();
                }

                /*SI ON EST DANS LA VARIANTE, ON DEPLACE LA TUILE DANS LA CASE INDIQUEE*/

                if(variante !=-1){

                   ligneMot.deplacerIndice(mur,ligneMot.getTuile(indice),
                            ((indiceLigne-1)*5)+variante);
                }

                /*SI ON EST PAS DANS LA VARIANTE ON DEPLACE LA TUILE DANS LA CASE CORRESPONDANTE SELON L ORDRE PREFEDEFINI*/

                else{

                    ligneMot.deplacerIndice(mur,ligneMot.getTuile(indice),
                            mur.getIndiceColor(ligneMot.getTuile(indice).getColor(),indiceLigne));
                }

                /*ON VIDE LE RESTE DE LA LIGNE DE MOTIF DANS LA DEFAUSSE*/



               while(!ligneMot.isEmpty()){
                   this.jetter(ligneMot.contenu.get(0),ligneMot);
               }

            }

            catch(IOException  e){
                e.printStackTrace();

            }

            return "Action complétée";
    }
    
    /**
     * Fonction envoyant la tuile t de la zone z à la défausse
     * @param t une instance de la tuile jeté
     * @param z l'ensemble des zones du plateau.
     */
    public void jetter(Tuile t, Zone z){

        try{
           z.deplacer(this.zones.get("Defausse"),t);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui vérifie la fin de partie
     * @return un booléen
     */
    public boolean finPartie(){
    	
        Joueur tmp;
        Mur mur;
        for(int i=0;i<nbJoueurs;i++){

            tmp = (Joueur) this.joueurs.get((i));
            mur= (Mur) tmp.getZone("Mur");
            if(mur.lignesPleines()){

                return true;

            }
        }
       
        return false;
    }

   

    /**
     * Ajoute la tuile premier joueur en début de partie
     */
    public void addPremierJ(){
        Tuile pr=new Tuile(-1);
        pr.setCourant(this.zones.get("Centre"));
        this.zones.get("Centre").contenu.add(0,pr);
    }

    /* FONCION QUI DEFINIE DE MANIERE ALETAOIRE L ORDRE DE JEU. SI ON EST AU PREMIER TOUR ELLE DEFINIRA POUR TOUS LES JOUEURS
    * MAIS SINON ELLE IGNORERA LE JOUEUR DEJA DEFINI COMME PREMIER AVEC LA TUILE PREMIER JOUEUR*/
    
    /**
     * Fonction qui définie de maniere aleatoire l'ordre du jeu. Si on est au premier tour
     * elle definira pour tout les joueurs mais sinon elle ignorera le joueur deja defini comme premier
     * avec la tuile premier joueur.
     * @param premierTour
     */
    public void setOrdreJeu(boolean premierTour){
        Random rd=new Random();
        int j;
        int max=1;
        if(premierTour){
            max=0;
        }
        for(int i=nbJoueurs;i>max;i--){

            do{
                j=rd.nextInt(nbJoueurs);

            }while(joueurs.get(j).getOrdreJeu()!=-1 );

            joueurs.get(j).setOrdreJeu(i);


        }
    }

    
    /**
     * Indique si la phade de décoration est finie
     * @return un booléen
     */
    public boolean decoFin(){


        for(int i=1;i<nbFabriques;i++){

            if(!this.zones.get("Fabrique "+i).isEmpty()){

                return false;

            }
        }

        if(!this.zones.get("Centre").isEmpty()){
            return false;
        }
        return true;


    }


    /* GETTERS */

    public Joueur getJoueurOrdreJeu(int indice){
        for(int i=0;i<this.nbJoueurs;i++){
            if(this.joueurs.get(i).getOrdreJeu()==indice){
               return this.joueurs.get(i);
            }
        }
        return null;
    }

    public ArrayList<Joueur> getJoueurs(){
        return this.joueurs;
    }

    public int getNbJoueurs(){
        return this.nbJoueurs;
    }
    
    public int getNbFabriques() {
    	return this.nbFabriques;
    }

    public HashMap<String, Zone> getZones(){
        return this.zones;
    }
    
    public ZoneInvisible getSac() {
    	return (ZoneInvisible) this.zones.get("Sac");
    }
    
    public ZoneInvisible getDefausse() {
    	return (ZoneInvisible) this.zones.get("Defausse");
    }
    
    public Fabrique getFabrique(int i) {
    	return (Fabrique) this.zones.get("Fabrique "+Integer.valueOf(i));
    }

    public CentreJeu getCentre() {
    	return (CentreJeu) this.zones.get("Centre");
    }

    public boolean getVariante(){
        return this.variante;
    }
    
    public boolean getJoker() {
    	return this.joker;
    }

}
