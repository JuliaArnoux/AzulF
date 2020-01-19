package graphicalDisplay;

import java.io.IOException;

import game.*;
public class Controler {
	
	/**
	 * CONTROLER DE L'INTERFACE GRAPHIQUE
	 * */
	
	private Model model;
	private View view;
	public int joueurActu = 0;
	
	public Controler(View vue, Model modele) {
		// TODO Auto-generated constructor stub
		this.view = vue;
		this.model = modele;
	}
	
	/** 
     * Méthode qui permet de couper une chaine de caractère.
     * 
     * @param s une chaine de caractère
 	 * @return un tableau contenant chaque mot après découpage.
     */
	public static String[] cut (String s) {
		String[] tab = new String[2];
		tab = s.split(" ");
		return tab;
	}
	
	/** 
     * Vide une ligne
     * 
     * @param line la ligne qui sera vidée.
     */
	public void viderLigne(int line) throws IOException {
		((Mur) model.getPlateau().getJoueurs().get(joueurActu).getZone("Mur")).videLigne(line, this.model.getPlateau());
		this.view.update(false);
	}
	
	/** 
     * Gère le tour pour un joueur
     * 
     * @param depart, les informations sur la tuile de départ
     * @param arrive, les informations sur la zone d'arrivé
     * @param joueur, le joueur effectuant l'action.
     */
	public void tourDeco(String[] depart, String[] arrive, int joueur) throws IOException {
		if(!model.getPlateau().decoFin()){
			String str = "";
			if(!depart[0].equals("0")) {
				str = model.getPlateau().rempLignMotif(model.getPlateau().getFabrique(Integer.valueOf(depart[0]))
						,Integer.valueOf(depart[1]), Integer.valueOf(arrive[1]), model.getPlateau().getJoueurs().get(joueur));
			}
			else if(depart[0].equals("0")){
				str = model.getPlateau().rempLignMotif(model.getPlateau().getCentre(),
						Integer.valueOf(depart[1]), Integer.valueOf(arrive[1]), model.getPlateau().getJoueurs().get(joueur));
			}
			if(str.equals("Action complétée")) {
				joueurActu++;
			}
			System.out.println(this.model.getPlateau().getCentre().toString());
			if(joueurActu >= model.getPlateau().getNbJoueurs()) {
				joueurActu = 0;
			}
		}

		
		if(model.getPlateau().decoFin()) {
			LigneMotif ligneMot;
			Plancher plancher;
			for(int i = 0; i < model.getPlateau().getNbJoueurs(); i++) {
				for(int j = 1; j < 6; j++) {

					ligneMot=(LigneMotif) model.getPlateau().getJoueurs().get(i).getZone("LigneMotif"+j);
					if(ligneMot.isFull()){
						model.getPlateau().rempliMur(model.getPlateau().getJoueurs().get(i), j, -1);
					}
				}
				plancher=(Plancher) model.getPlateau().getJoueurs().get(i).getZone("Plancher");
				plancher.videPlancher(model.getPlateau());
				model.getPlateau().getJoueurs().get(i).calculeScore();
			}
			
			if(model.getPlateau().finPartie()) {
				for(int i = 0; i < model.getPlateau().getNbJoueurs(); i++) {
					model.getPlateau().getJoueurs().get(i).calculeScoreFinal();
				}
				this.view.update(true);
				return;
			}
			else {
				this.view.premTour = true;
			}
		}
		this.view.update(false);
	}
	

	public Model getModel() {
		return model;
	}

	public View getView() {
		return view;
	}

	public int getJoueurActu() {
		return joueurActu;
	}
			  
}
