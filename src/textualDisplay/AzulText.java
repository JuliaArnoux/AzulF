package textualDisplay;
import game.*;

import java.io.IOException;
import java.util.Scanner;

public class AzulText {

    private static   Scanner sc=new Scanner(System.in);

    /*FONCTION PERMETTANT DAFFICHER LETAT DU PLATEAU DANS LA CONSOLE */

    public static void affichageTextuel(Plateau p){
    	
        Mur m;

        /*ON AFFICHE LES FABRIQUES*/

        if(p.getNbJoueurs()==2){
            for(int i=1; i<6; i++){
                System.out.print(p.getZones().get("Fabrique "+Integer.valueOf(i)).toString());
            }
        }

        if(p.getNbJoueurs()==3){
            for(int i=1; i<8; i++){
                System.out.print(p.getZones().get("Fabrique "+Integer.valueOf(i)).toString());
            }
        }

        if(p.getNbJoueurs()==4){
            for(int i=1; i<10; i++){
                System.out.print(p.getZones().get("Fabrique "+Integer.valueOf(i)).toString());
            }
        }

        /*ON AFFICHE LE CENTRE DU PLATEAU*/

        System.out.println("\n");

        System.out.println(p.getZones().get("Centre").toString());

        System.out.println("\n");

        /*ON AFFICHE LES ZONES PROPRES A CHAQUE JOUEUR */

        for(int i=0;i<p.getJoueurs().size();i++){
            System.out.println("\nJoueur "+(i+1));
            System.out.println("Score : "+p.getJoueurs().get(i).getScore());
            for(int j=1;j<6;j++){
                System.out.print(p.getJoueurs().get(i).getZone("LigneMotif"+j).toString());
                m=(Mur) p.getJoueurs().get(i).getZone("Mur");
                for(int k=0;k<5-j;k++){
                    System.out.print("   ");
                }
                System.out.print("           ");
                System.out.println(m.toStringLigne(j-1,p.getVariante()));
            }
            System.out.println("");
            System.out.println(p.getJoueurs().get(i).getZone("Plancher").toString());
            for(int l=0;l<Plancher.size;l++){
                System.out.print(Plancher.valeurs[l]+"  ");
            }
            System.out.println("");
        }

    }

    /*FONCTION QUI PERMET DE DEMANDER AU JOUEUR UNE VALEUR ENTIERE COMPRISE ENTRE MAX ET MIN*/

    public static int askInt(int min, int max){
        String str="";
        int i;
        do{
            System.out.println("Entrez un entier entre "+min+" et "+max);
            str=sc.nextLine();
            try{
                i=Integer.parseInt(str);
            }
            catch(Exception e){
                i=-1;
            }
        }while(i<min || i>max || i==-1);
        
        return Integer.parseInt(str);
    }

    /*FONCTION PERMETTANT DE DEMANDER AU JOUEUR UNE REPONSE SOUS FORME DE BOOLEAN*/

    public static boolean askBool(){

        String str="";

        do{

            System.out.println("Répondez par oui ou par non uniquement");
            str=sc.nextLine();

        }while(!str.equals("oui") && !str.equals("non"));

        if(str.equals("oui")){

            return true;
        }

        else{

            return false;
        }


    }

    /*FONCTION GERANT LE TOUR DE DECORATION POUR UN JOUEUR DONNE*/

    public static void tourDeco(Joueur j,Plateau p){

        String str;

        do{

            /*ON DEMANDE AU JOUEUR LES INFORMATIONS REQUISES POUR LANCER LA FONCTION REMPLI LIGNE MOTIF */


            int indice1;
            Zone origine;
            String tmp;
            do{
                System.out.println("Indiquez dans quelle zone vous souhaitez prendre des tuiles ( 1, 2, ... pour les fabriques, pour le centre du plateau 0 )");
                indice1= askInt(0,p.getNbFabriques()-1);
                if(indice1==0){
                    tmp="Centre";
                }

                else{
                    tmp="Fabrique "+indice1;
                }

            }while(p.getZones().get(tmp).isEmpty());
            int indice2;
            System.out.print("Indiquez le numero de la tuile qui vous intéresse dans cette zone ( 1, 2, ... )");


            /*ON GERE LE CAS OU LE JOUEUR PREND DS TUILES AU CENTRE DU PLATEAU */

            if(indice1==0){

                origine=p.getZones().get("Centre");
                indice2=askInt(1,origine.getActualSize())-1;
            }

            else{

                origine=p.getFabrique(indice1);
                indice2=askInt(1,origine.getActualSize())-1;
            }

            System.out.print("Indiquez le numéro de la ligne de motif ou déplacer les tuiles ( 1, 2, ...), 0 pour le plancher");
            int indice3=askInt(0,5);
            if(indice3==0){
                indice3=-1;
            }
            str=p.rempLignMotif(origine,indice2,indice3, j);
            System.out.println(str);

        }while(!str.equals("Action complétée")); /*ON S ASSURE QUE LACTION A ETE COMPLETEE SANS ENCOMBRE AVANT DE CONTINUER
        SI NON ON RECOMMENCE*/




    }

    /*FONCTION GERANT LE REMPLISSAGE DU MUR DE MANEIR AUTOMATIQUE OU NON SELON QU ON SE TROUVE DANS LA VARIANTE
    * ELLE EST SEPAREE DU PLATEAU CAR NECESSITE L APPEL A DES SCANNER*/

    public static void tourRemplissage(Joueur j, Plateau p){

        LigneMotif ligneMot;
        String str;
        int indice;

        /*ON PARCOURS TOUTES LES LIGNES DE MOTIF DU JOUEUR*/

        for(int i=1;i<6;i++){

            System.out.println("Ligne de motif n."+i);

            ligneMot=(LigneMotif) j.getZone("LigneMotif"+i);

            Mur mur=(Mur) j.getZone("Mur");

            /*ON AGIS QUE SI LA LIGNE EST REMPLIE*/


            if(ligneMot.isFull()){

                /*ON GERE LE CAS DE LA VARIANTE*/

                if(p.getVariante() || ligneMot.containJoker()==i){








                    /*ON DEMANDE AU JOUEUR OU IL SOUHAITE PLACER LA TUILE TANT QUE L EMPLACEMENT N EST PAS VALIDE
                    * ON LUI PROPOSE DE VIDER UNE LIGNE DU MUR AU CAS OU IL EST BLOQUER AVEC LA FONCTION VIDE LIGNE*/

                 do{

                     System.out.println("Où souhaitez vous placer la tuile sur le mur ?");
                     indice=askInt(1,5)-1;
                     str=p.rempliMur(j,i,indice);
                     System.out.println(str);
                     if(!str.equals("Action complétée")){

                         System.out.println("Souhaitez vous vider une ligne de votre mur à la défausse");
                         if(askBool()){

                             System.out.println("Quelle ligne souhaitez vous vider");
                             mur.videLigne(askInt(1,5)-1,p);

                         }



                     }

                 }while(!str.equals("Action complétée"));

                }

                /*SI ON EST PAS DANS LA VARIANTE LE MUR EST REMPLI AUTOMATIQUEMENT*/

                else{

                    p.rempliMur(j,i,-1);

                }



            }

        }

    }

    /*FONCTION DE JEU*/

    public static void jeu(){


        /*TRY CATCH POUR GERER LES POTENTIELLES EXCEPTIONS*/
        try{

            /*BOOLENS PERMETTANT DE VERIFIER A TOUT MOMENT SI LE JOUEUR
            * SOUHAITE CONTINUER A JOUEUR OU SI LA PHASE DE REMPLISSAGE DES LIGNES DE MOTIF EST TERMINEE*/

        boolean play=true;
        boolean decoFin=false;

            /*BOUCLE D UNE PARTIE*/

        while(play){

            /*ON DEMANDE AU JOUEUR LES PARAMATRES DE LA PARTIE*/

        System.out.println("Entrez le nombre de joueurs désiré ( 2 à 4 )");
        int nbJoueurs=askInt(2,4);
        System.out.println("Souhaitez vous jouer avec la variante ou le placement des tuiles est libre sur le mur ?");
        boolean variante=askBool();
        System.out.println("Souhaitez vous jouez avec la variante incluant des tuiles joker ?");
        boolean joker=askBool();

            /*ON CREER LE PLATEAU ET ON AJOUTE LA TUILE PREMIER JOUEUR AU CENTRE DU PLATEAU
            * ON CREER UN ORDRE DE JEU TOTALEMENT ALEATOIRE POUR LE PREMIER TOUR*/

            Plateau p=new Plateau(nbJoueurs, variante, joker);
            p.addPremierJ();
            p.setOrdreJeu(true);


            /*ON ENTRE DANS LA BOUCLE QUI GERE UN TOUR COMPLET DE JEU*/

            do{

                /*ON REMPLI LES FABRIQUES ET ON REINITIALISE LE BOOLEN DECOFIN*/

                p.rempliFabrique();
                affichageTextuel(p);
                System.out.println("Phase de remplissage des lignes de motifs");
                decoFin=false;

                /*ON ENTRE DANS LA BOUCLE DE LA PHASE DE REMPLISSAGE DES LIGNES DE MOTIF*/

                do {
                    for (int i = 1; i <= nbJoueurs; i++) {
                        System.out.println("Au tour du " + p.getJoueurOrdreJeu(i).getNom());
                        tourDeco(p.getJoueurOrdreJeu(i), p);
                        affichageTextuel(p);

                        /*APRES LE TOUR DE CHAQUE JOUEUR ON VERIFIE QU IL RESTE DES TUILES A PRENDRE
                        * POUR POUVOIR QUITTER CETTE PHASE DU JEU*/

                        decoFin=p.decoFin();
                        if(decoFin){
                            break;
                        }

                    }

                }while(!decoFin);

                /*ON ENTRE DANS LA PHASE DE DECORATION DES MURS*/

                System.out.println("Phase de décoration des murs");

                /*SI NOUS SOMMES DANS LA VARIANTE, TOUR REMPLISSAGE DEMANDE AU JOUEUR
                * OU IL SOUHAITE PLACER LA TUILE, SINON IL APPELLE AUTOMATIQUEMENT
                * REMPLI MUR AVEC -1; TOUR REMPLISSAGE S ASSURE EGALEMENT DAPPELLER
                * LA FONCTION SUR CHAQUE LIGNE DE MOTIF ET UNIQUEMENT SI ELLE EST PLEINE*/

                for(int i=0;i<nbJoueurs;i++){

                    System.out.println("\nAu tour du " + p.getJoueurs().get(i).getNom()+"\n");
                    tourRemplissage(p.getJoueurs().get(i), p);
                    p.getJoueurs().get(i).calculeScore();


                }

                /*GESTION DE LA FIN DUN TOUR; ON VIDE LES PLANCHER DE CHAQUE JOUEUR, ON MET A JOUR
                * L ORDRE DE JEU EN PRENANT COMPTE DE LA TUILE PREMIER JOUEUR ET ON LA REPLACE AU CENTRE DU PLATEAU*/


                Plancher plancher;

                for(int i=0;i<nbJoueurs;i++){

                    plancher=(Plancher) p.getJoueurs().get(i).getZone("Plancher");
                    plancher.videPlancher(p);

                }

                p.setOrdreJeu(false);

                /*ON VERIFIE QU AUCUN JOUEUR NA REMPLI DE LIGNE DE SON MUR AVANT DE RELNCER GRACE A LA FONCTION FINPARTIE*/

            }while(!p.finPartie());

            /*UNE FOIS LA PARTIE TERMINEE ON CALCULE LES POINTS BONUS OBTENUS*/

            for(int i=0;i<nbJoueurs;i++){

                p.getJoueurs().get(i).calculeScoreFinal();

            }

            affichageTextuel(p);

            /*ON DEMANDE AU JOUEUR SI IL SOUHAITE RELANCER UNE PARTIE*/

        System.out.println("Souhaitez vous rejouer ?");
        play=askBool();


        }

        }

        catch(IOException e){

            e.printStackTrace();
        }

        sc.close();
    }


    public static void main(String[]  args){
       jeu();
    }
}
