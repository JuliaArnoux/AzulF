package graphicalDisplay;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import game.*;

public class View extends JFrame{
	
	/**
	 * VUE DE L'INTERFACE GRAPHIQUE
	 */
	
	Model model;
	Controler controler;
	JPanel panFabrique = new JPanel();
	JPanel panJoueurs  = new JPanel();
	JPanel panCentre   = new JPanel();
	JPanel background  = new JPanel();
	static String[] depart;
	static String[] arrive;
	int nbJoueurs=2;
	boolean variante=false;
	boolean joker=false;
	boolean premTour=true;
	
	public View() throws IOException {

		JPanel container = new JPanel();
		JPanel container1 = new JPanel();
		JPanel container2 = new JPanel();
		JPanel container3 =new JPanel();
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		label1.setText("Choisir le nombre de joueurs");
		label2.setText("Jouer avec la variante ?");
		label3.setText("Jouer avec les tuiles joker ?");
		JRadioButton check2 = new JRadioButton("2");
		JRadioButton check3 = new JRadioButton("3");
		JRadioButton check4 = new JRadioButton("4");
		ButtonGroup nbPlayer=new ButtonGroup();
		nbPlayer.add(check2);
		nbPlayer.add(check3);
		nbPlayer.add(check4);
		JRadioButton check5 = new JRadioButton("Oui");
		JRadioButton check6 = new JRadioButton("Non");
		ButtonGroup varianteradio=new ButtonGroup();
		varianteradio.add(check5);
		varianteradio.add(check6);
		JRadioButton check7 = new JRadioButton("Oui");
		JRadioButton check8 = new JRadioButton("Non");
		ButtonGroup jokerradio=new ButtonGroup();
		jokerradio.add(check7);
		jokerradio.add(check8);
		JButton valider = new JButton();
		valider.setText("Valider");
		check2.addActionListener((ActionEvent e)->nbJoueurs = Integer.valueOf(((AbstractButton) e.getSource()).getText()));
		check3.addActionListener((ActionEvent e)->nbJoueurs = Integer.valueOf(((AbstractButton) e.getSource()).getText()));
		check4.addActionListener((ActionEvent e)->nbJoueurs = Integer.valueOf(((AbstractButton) e.getSource()).getText()));
		check5.addActionListener((ActionEvent e)->variante = true);
		check6.addActionListener((ActionEvent e)->variante = false);
		check7.addActionListener((ActionEvent e)->joker = true);
		check8.addActionListener((ActionEvent e)->joker= false);
		valider.addActionListener((event)->{
			try {
				System.out.print("Création");
				create();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(2000,2000));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JPanel param=new JPanel();
		param.setLayout((new GridLayout(4,0)));
		param.setSize(500,500);
		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		top.add(label1);
		top.add(check2);
		top.add(check3);
		top.add(check4);
		top.add(valider);
		center.add(label2);
		center.add(check5);
		center.add(check6);
		south.add(label3);
		south.add(check7);
		south.add(check8);
		container.add(top);
		container1.add(center);
		container2.add(south);
		param.add(container);
		param.add(container1);
		param.add(container2);
		param.add(container3);
		background.add(param);
		this.setContentPane(background);
		this.setTitle("Azul");
		this.setVisible(true);
	}
	
	/**
	 * Méthode qui va générer les fabriques
	 */
	private void creationFabrique(boolean end) throws IOException {
		if(!end) {
			
			if(premTour == true) {
				model.getPlateau().rempliFabrique(); //rempli les fabriques lorsqu'un tour débute.
			}
			
			
		}
		
		//Initialisation du nombre de fabrique
		panFabrique.setLayout(new GridLayout(0, model.getPlateau().getNbFabriques()-1));
	    if(model.getPlateau().getNbJoueurs()==2) {
	    	panJoueurs.setLayout(new GridLayout(0, model.getPlateau().getNbJoueurs()));
		}else if(model.getPlateau().getNbJoueurs()==3) {
	    	panJoueurs.setLayout(new GridLayout(1, model.getPlateau().getNbJoueurs()-1));
		}else if(model.getPlateau().getNbJoueurs()==4) {
	    	panJoueurs.setLayout(new GridLayout(2, model.getPlateau().getNbJoueurs()-2));
		}
	   
	    //Affichage graphique des tuiles.
		for(int i = 1; i < model.getPlateau().getNbFabriques(); i++) {
	    	JPanel tmp = new JPanel();
	    	tmp.setLayout(new GridLayout(2,2));
	    	int nb = 0;
	    	for(Tuile t : model.getPlateau().getFabrique(i).getContenu()) {
	    		JPanel tmp2 = new JPanel();
	    		if(t.getColor() == 0) {
	    			tmp2.setBackground(Color.BLUE);
	    		}else if(t.getColor() == 1) {	    			
	    			tmp2.setBackground(Color.YELLOW);
	    		}else if(t.getColor() == 2) {	    			
	    			tmp2.setBackground(Color.RED);
	    		}else if(t.getColor() == 3) {
	    			tmp2.setBackground(Color.BLACK);
	    		}else if(t.getColor() == 4) {	    	
	    			tmp2.setBackground(Color.GRAY);
	    		}else {
	    			tmp2.setBackground(Color.WHITE);
	    		}
	    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
    	    	tmp2.setName(i+" "+nb);
    	    	tmp2.addMouseListener(new MouseAdapter() {
	    			public void mousePressed(MouseEvent e) {
	    				depart = Controler.cut(e.getComponent().getName());
	    				System.out.println(Arrays.toString(depart));
	    			}
	    		});
    	    	nb++;
    	    	tmp.add(tmp2);
    	    }
    		tmp.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 3, Color.WHITE));
	    	panFabrique.add(tmp);
		}	

	}
	
	/**
	 * Méthode qui va créé le centre du plateau
	 */
	private void creationCentre() {
		int nb = 0;
		if(this.premTour == true) {
			this.premTour = false;
		}
		
		//Affichage graphique des tuiles
		for(Tuile t : model.getPlateau().getCentre().getContenu()) {
    		JPanel tmp2 = new JPanel();
    		if(t.getColor() == 0) {
    			tmp2.setBackground(Color.BLUE);
    		}else if(t.getColor() == 1) {
    			tmp2.setBackground(Color.YELLOW);
    		}else if(t.getColor() == 2) {
    			tmp2.setBackground(Color.RED);
    		}else if(t.getColor() == 3) {
    			tmp2.setBackground(Color.BLACK);
    		}else if(t.getColor() == 4) {
    			tmp2.setBackground(Color.GRAY);
    		}else if(t.getColor() == -1){
    			tmp2.setBackground(Color.GREEN);
    		}
    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	tmp2.setName(0+" "+nb);
	    	tmp2.setPreferredSize(new Dimension(100,100));
	    	tmp2.addMouseListener(new MouseAdapter() {
    			public void mousePressed(MouseEvent e) {
    				depart = Controler.cut(e.getComponent().getName());
    				System.out.println(Arrays.toString(depart));
    			}
    		});
	    	nb++;
	    	panCentre.add(tmp2);
	    }
		//Indique quel joueur doit jouer.
		JLabel texte = new JLabel();
		texte.setText("Au tour du joueur "+(controler.getJoueurActu()+1));
		panCentre.add(texte, BorderLayout.SOUTH);
		background.revalidate();
	}
	
	/**
	 * Méthode qui va crée les plateaux des joueurs
	 * */
	private void creationPlatJoueur() {
		//Itération sur le nombre de joueur dans la partie.
		for(int i = 0; i<model.getPlateau().getNbJoueurs(); i++) {
	    	JPanel tmp = new JPanel();
	    	TitledBorder title;
	    	title = BorderFactory.createTitledBorder("Joueur"+(i+1));
	    	tmp.setBorder(title);
	    	tmp.setLayout(new GridLayout(2,2));
	    	JPanel ligneMotif = new JPanel();
	    	ligneMotif.setLayout(new GridLayout(6,0));
	    	ligneMotif.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	JPanel ligne1 = new JPanel();
	    	ligne1.setLayout(new GridLayout(0,1));
	    	ligne1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	JPanel tmp2 = null;
	    	//Création des lignes de motifs
	    	for (int j = 0; j < ((LigneMotif) model.getPlateau().getJoueurs().get(i).getZone("LigneMotif1")).getSize(); j++) {
	    		tmp2 = new JPanel();
	    		try {
		    		if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif1").getContenu().get(j).getColor() == 0) {
		    			tmp2.setBackground(Color.BLUE.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif1").getContenu().get(j).getColor() == 1) {	    			
		    			tmp2.setBackground(Color.YELLOW.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif1").getContenu().get(j).getColor() == 2) {	    			
		    			tmp2.setBackground(Color.RED.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif1").getContenu().get(j).getColor() == 3) {
		    			tmp2.setBackground(Color.BLACK.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif1").getContenu().get(j).getColor() == 4) {	    	
		    			tmp2.setBackground(Color.GRAY.brighter());
		    		}
	    		}catch(Exception e) {
	    			tmp2.setBackground(Color.WHITE.brighter());
	    		}
	    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    		tmp2.setName("Ligne 1");
	    		tmp2.addMouseListener(new MouseAdapter() {
	    			public void mousePressed(MouseEvent e) {
	    				arrive = Controler.cut(e.getComponent().getName());
	    				System.out.println(Arrays.toString(arrive));
	    				try {
							controler.tourDeco(depart, arrive, controler.joueurActu);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				
	    			}
	    		});
	    		ligne1.add(tmp2);
	    	}
	    	JPanel ligne2 = new JPanel();
	    	ligne2.setLayout(new GridLayout(0,2));
	    	ligne2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	for(int j = 0; j < ((LigneMotif) model.getPlateau().getJoueurs().get(i).getZone("LigneMotif2")).getSize(); j++) {
	    		tmp2 = new JPanel();
	    		try {
		    		if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif2").getContenu().get(j).getColor() == 0) {
		    			tmp2.setBackground(Color.BLUE.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif2").getContenu().get(j).getColor() == 1) {	    			
		    			tmp2.setBackground(Color.YELLOW.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif2").getContenu().get(j).getColor() == 2) {	    			
		    			tmp2.setBackground(Color.RED.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif2").getContenu().get(j).getColor() == 3) {
		    			tmp2.setBackground(Color.BLACK.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif2").getContenu().get(j).getColor() == 4) {	    	
		    			tmp2.setBackground(Color.GRAY.brighter());
		    		}
	    		}catch(Exception e){
	    			tmp2.setBackground(Color.WHITE);
	    		}
	    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    		tmp2.setName("Ligne 2");
	    		tmp2.addMouseListener(new MouseAdapter() {
	    			public void mousePressed(MouseEvent e) {
	    				arrive = Controler.cut(e.getComponent().getName());
	    				System.out.println(Arrays.toString(arrive));
	    				try {
							controler.tourDeco(depart, arrive, controler.joueurActu);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				
	    			}
	    		});
	    		ligne2.add(tmp2);
	    	}
	    	JPanel ligne3 = new JPanel();
	    	ligne3.setLayout(new GridLayout(0,3));
	    	ligne3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	for(int j = 0; j < ((LigneMotif) model.getPlateau().getJoueurs().get(i).getZone("LigneMotif3")).getSize(); j++) {
	    		tmp2 = new JPanel();
	    		try {
		    		if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif3").getContenu().get(j).getColor() == 0) {
		    			tmp2.setBackground(Color.BLUE.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif3").getContenu().get(j).getColor() == 1) {	    			
		    			tmp2.setBackground(Color.YELLOW.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif3").getContenu().get(j).getColor() == 2) {	    			
		    			tmp2.setBackground(Color.RED.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif3").getContenu().get(j).getColor() == 3) {
		    			tmp2.setBackground(Color.BLACK.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif3").getContenu().get(j).getColor() == 4) {	    	
		    			tmp2.setBackground(Color.GRAY.brighter());
		    		}
	    		}catch(Exception e){
	    			tmp2.setBackground(Color.WHITE);
	    		}
	    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    		tmp2.setName("Ligne 3");
	    		tmp2.addMouseListener(new MouseAdapter() {
	    			public void mousePressed(MouseEvent e) {
	    				arrive = Controler.cut(e.getComponent().getName());
	    				System.out.println(Arrays.toString(arrive));
	    				try {
							controler.tourDeco(depart, arrive, controler.joueurActu);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				
	    			}
	    		});
	    		ligne3.add(tmp2);
	    	}
	    	JPanel ligne4 = new JPanel();
	    	ligne4.setLayout(new GridLayout(0,4));
	    	ligne4.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	for(int j = 0; j < ((LigneMotif) model.getPlateau().getJoueurs().get(i).getZone("LigneMotif4")).getSize(); j++) {
	    		tmp2 = new JPanel();
	    		try {
		    		if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif4").getContenu().get(j).getColor() == 0) {
		    			tmp2.setBackground(Color.BLUE.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif4").getContenu().get(j).getColor() == 1) {	    			
		    			tmp2.setBackground(Color.YELLOW.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif4").getContenu().get(j).getColor() == 2) {	    			
		    			tmp2.setBackground(Color.RED.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif4").getContenu().get(j).getColor() == 3) {
		    			tmp2.setBackground(Color.BLACK.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif4").getContenu().get(j).getColor() == 4) {	    	
		    			tmp2.setBackground(Color.GRAY.brighter());
		    		}
	    		}catch(Exception e){
	    			tmp2.setBackground(Color.WHITE);
	    		}
	    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    		tmp2.setName("Ligne 4");
	    		tmp2.addMouseListener(new MouseAdapter() {
	    			public void mousePressed(MouseEvent e) {
	    				arrive = Controler.cut(e.getComponent().getName());
	    				System.out.println(Arrays.toString(arrive));
	    				try {
							controler.tourDeco(depart, arrive, controler.joueurActu);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				
	    			}
	    		});
	    		ligne4.add(tmp2);
	    	}
	    	JPanel ligne5 = new JPanel();
	    	ligne5.setLayout(new GridLayout(0,5));
	    	ligne5.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	for(int j = 0; j < ((LigneMotif) model.getPlateau().getJoueurs().get(i).getZone("LigneMotif5")).getSize(); j++) {
	    		tmp2 = new JPanel();
	    		try {
		    		if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif5").getContenu().get(j).getColor() == 0) {
		    			tmp2.setBackground(Color.BLUE.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif5").getContenu().get(j).getColor() == 1) {	    			
		    			tmp2.setBackground(Color.YELLOW.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif5").getContenu().get(j).getColor() == 2) {	    			
		    			tmp2.setBackground(Color.RED.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif5").getContenu().get(j).getColor() == 3) {
		    			tmp2.setBackground(Color.BLACK.brighter());
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("LigneMotif5").getContenu().get(j).getColor() == 4) {	    	
		    			tmp2.setBackground(Color.GRAY.brighter());
		    		}
	    		}catch(Exception e){
	    			tmp2.setBackground(Color.WHITE);
	    		}
	    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    		tmp2.setName("Ligne 5");
	    		tmp2.addMouseListener(new MouseAdapter() {
	    			public void mousePressed(MouseEvent e) {
	    				arrive = Controler.cut(e.getComponent().getName());
	    				System.out.println(Arrays.toString(arrive));
	    				try {
							controler.tourDeco(depart, arrive, controler.joueurActu);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    			}
	    		});
	    		ligne5.add(tmp2);
	    	}
	    	ligneMotif.add(ligne1);
	    	ligneMotif.add(ligne2);
	    	ligneMotif.add(ligne3);
	    	ligneMotif.add(ligne4);
	    	ligneMotif.add(ligne5);
	    	
	    	//Création du mur
	    	JPanel mur = new JPanel();
	    	mur.setLayout(new GridLayout(6,5));
	    	mur.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	for(int j = 0; j < Mur.getSize(); j++) {
	    		tmp2 = new JPanel();
	    		try {
		    		if(model.getPlateau().getJoueurs().get(i).getZone("Mur").getContenu().get(j).getColor() == 0) {
		    			tmp2.setBackground(Color.BLUE);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Mur").getContenu().get(j).getColor() == 1) {	    			
		    			tmp2.setBackground(Color.YELLOW);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Mur").getContenu().get(j).getColor() == 2) {	    			
		    			tmp2.setBackground(Color.RED);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Mur").getContenu().get(j).getColor() == 3) {
		    			tmp2.setBackground(Color.BLACK);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Mur").getContenu().get(j).getColor() == 4) {	    	
		    			tmp2.setBackground(Color.GRAY);
		    		}else {
		    			if(Mur.getCouleurspredef().get(j) == 0) {
		    				tmp2.setBackground(Color.BLUE.darker().darker());
		    			}
						if(Mur.getCouleurspredef().get(j) == 1) {
							tmp2.setBackground(Color.YELLOW.darker().darker());	    				
						}
						if(Mur.getCouleurspredef().get(j) == 2) {
							tmp2.setBackground(Color.RED.darker().darker());
						}
						if(Mur.getCouleurspredef().get(j) == 3) {
							tmp2.setBackground(Color.BLACK.darker().darker());
						}
						if(Mur.getCouleurspredef().get(j) == 4) {
							tmp2.setBackground(Color.GRAY.darker().darker());
						}
		    			
		    		}
	    		}catch(Exception e){
	    			tmp2.setBackground(Color.WHITE);
	    		}
	    		tmp2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    		mur.add(tmp2);
	    	}
	    	
	    	//Affichage des boutons qui vide les lignes si la variante est active.
	    	if(model.getPlateau().getVariante()) {
	    		JButton button = new JButton();
	    		JLabel label = new JLabel();
	    		label.setText("Vider 1");
	    		button.add(label);
	    	    button.addActionListener((event)->{
					try {
						controler.viderLigne(0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	    	    JButton button1 = new JButton();
	    	    JLabel label1 = new JLabel();
	    		label1.setText("Vider 2");
	    		button1.add(label1);
	    	    button1.addActionListener((event)->{
					try {
						controler.viderLigne(1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	    	    JButton button2 = new JButton();
	    	    JLabel label2 = new JLabel();
	    		label2.setText("Vider 3");
	    		button2.add(label2);
	    	    button2.addActionListener((event)->{
					try {
						controler.viderLigne(2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	    	    JButton button3 = new JButton();
	    	    JLabel label3 = new JLabel();
	    		label3.setText("Vider 4");
	    		button3.add(label3);
	    	    button3.addActionListener((event)->{
					try {
						controler.viderLigne(3);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	    	    JButton button4 = new JButton();
	    	    JLabel label4 = new JLabel();
	    		label4.setText("Vider 5");
	    		button4.add(label4);
	    	    button4.addActionListener((event)->{
					try {
						controler.viderLigne(4);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	    	    mur.add(button);
	    	    mur.add(button1);
	    	    mur.add(button2);
	    	    mur.add(button3);
	    	    mur.add(button4);
	    	}
	    	
	    	//Création du plancher.
	    	JPanel plancher = new JPanel();
	    	plancher.setLayout(new GridLayout(0, 7));
	    	plancher.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	    	for(int j = 0; j < Plancher.getSize(); j++) {
	    		tmp2 = new JPanel();
	    		TitledBorder title1;
	    		if(j < 2)
	    			title1 = BorderFactory.createTitledBorder("-1");
	    		else if(j >= 2 && j < 5 )
	    			title1 = BorderFactory.createTitledBorder("-2");
	    		else
	    			title1 = BorderFactory.createTitledBorder("-3");
		    	title1.setTitleJustification(TitledBorder.CENTER);
		    	tmp2.setBorder(title1);
		    	tmp2.setName("Ligne -1");
	    		try {
		    		if(model.getPlateau().getJoueurs().get(i).getZone("Plancher").getContenu().get(j).getColor() == 0) {
		    			tmp2.setBackground(Color.BLUE);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Plancher").getContenu().get(j).getColor() == 1) {	    			
		    			tmp2.setBackground(Color.YELLOW);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Plancher").getContenu().get(j).getColor() == 2) {	    			
		    			tmp2.setBackground(Color.RED);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Plancher").getContenu().get(j).getColor() == 3) {
		    			tmp2.setBackground(Color.BLACK);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Plancher").getContenu().get(j).getColor() == 4) {	    	
		    			tmp2.setBackground(Color.GRAY);
		    		}else if(model.getPlateau().getJoueurs().get(i).getZone("Plancher").getContenu().get(j).getColor() ==-1) {
		    			tmp2.setBackground(Color.GREEN);
		    		}
	    		}catch(Exception e){
	    			tmp2.setBackground(Color.WHITE);
	    		}
	    		tmp2.addMouseListener(new MouseAdapter() {
	    			public void mousePressed(MouseEvent e) {
	    				arrive = Controler.cut(e.getComponent().getName());
	    				System.out.println(Arrays.toString(arrive));
	    				try {
							controler.tourDeco(depart, arrive, controler.joueurActu);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				
	    			}
	    		});
	    		plancher.add(tmp2);
	    	}
	    	JPanel score = new JPanel();
	    	JLabel label = new JLabel(); 
	    	label.setText("SCORE : "+String.valueOf(this.model.getPlateau().getJoueurs().get(i).getScore()));
	    	label.setPreferredSize(new Dimension(100,100));
	    	score.add(label);
	    	tmp.add(ligneMotif);
	    	tmp.add(mur);
	    	tmp.add(plancher);
	    	tmp.add(score);
	    	panJoueurs.add(tmp);
	    }
	}
	
	/**
	 * Méthode qui va mettre à jour la vue.
	 * @throws IOException.
	 */
	public void update(boolean end) throws IOException {
		this.model = controler.getModel();
		this.background.removeAll();
		this.panFabrique.removeAll();
		this.panCentre.removeAll();
		this.panJoueurs.removeAll();
		this.creationFabrique(end);
	    this.creationCentre();
	    this.creationPlatJoueur();
	    this.background.add(panFabrique, BorderLayout.NORTH);
        this.background.add(panCentre, BorderLayout.CENTER);
        this.background.add(panJoueurs, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
	}

	/**
	 * Méthode qui va crée le plateau de jeu en fonction des paramètre choisi par le joueur.
	 * @throws IOException
	 */
	public void create() throws IOException {
		Plateau p = new Plateau(this.nbJoueurs, this.variante, this.joker);
		this.model=new Model(p);
		background.removeAll();
		this.controler = new Controler(this, this.model);
		this.panFabrique.setPreferredSize(new Dimension(300,300));
		this.panJoueurs.setPreferredSize(new Dimension(550,550));
		this.background.setLayout(new BorderLayout());
		this.creationFabrique(false);
		this.creationCentre();
		this.creationPlatJoueur();
		model.getPlateau().addPremierJ();
		this.background.add(panFabrique, BorderLayout.NORTH);
		this.background.add(panCentre, BorderLayout.CENTER);
		this.background.add(panJoueurs, BorderLayout.SOUTH);
		this.pack();
		this.setSize(new Dimension(2000,2000));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.revalidate();
		this.repaint();
	}
	

	//CLASSE OBJET QUI INSERE UNE IMAGE DANS UN JPANEL ET LA REND DEPLACABLE AVEC LA SOURIS, UTILISABLE POUR UNE FUTURE AMELIORATION
	/*public class ImagePanel extends JPanel {

        private BufferedImage img;
        private Point offset = new Point(0, 0);

        public ImagePanel(String chemin) {
            try {
                img = ImageIO.read(new File(chemin));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            /*MouseAdapter ma = new MouseAdapter() {

                private Point startPoint;

                @Override
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                    startPoint.x -= offset.x;
                    startPoint.y -= offset.y;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    startPoint = null;
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    Point p = e.getPoint();
                    int x = p.x - startPoint.x;
                    int y = p.y - startPoint.x;
                    offset = new Point(x, y);
                    repaint();
                }

            };

            addMouseListener(ma);
            addMouseMotionListener(ma);
			
        }
           
        public BufferedImage resize(BufferedImage img, int height, int width) {
            Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            return resized;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
            	img = resize(img, 170, 200);
                Graphics2D g2d = (Graphics2D) g.create();
                if (offset == null) {
                    offset = new Point(0, 0);
                }
                g2d.drawImage(img, offset.x, offset.y, this);
                g2d.dispose();
            }
        }
	}*/
}
