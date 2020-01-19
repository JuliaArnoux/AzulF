# Azul
Bienvenue sur le README pour utiliser notre implémentation du jeu Azul.


# Comment démarrer le jeu ?

## Version textuelle
Dans l'archive du projet est fourni un fichier **AzulText.jar**.
Taper la commande suivante dans un terminal :
`java -jar AzulText.jar`
pour démarrer la version textuelle du jeu dans votre terminal.

## Version Graphique
De même, toujours dans l'archive du projet est fourni un fichier **AzulGraphic.jar**.
Taper la commande suivante dans un terminal:
`java -jar AzulGraphic.jar`
pour démarrer la version graphique du jeu.

# Comment jouer ?

## Version textuelle

Répondez aux questions apparaissant dans le terminal pour définir les paramètres de votre jeu.
On demandera à l'utilisateur : 
1. Le nombre de joueur (entre 2 et 4).
2. S'il souhaite jouer à la variante.
3. S'il souhaite utiliser les tuiles Joker.

Une fois démarrer, le terminal affichera les fabriques, le centre du jeu et les plateaux des joueurs.
Le terminal vous proposera ensuite les actions a faire et il suffira d'y répondre dans votre terminal pour effectuer les actions voulu en notifiant quel joueur doit effectuer ces actions.

* Dénomination des tuiles
    * BU : Bleu
    * BK : Noir
    * Y : Jaune
    * R : Rouge
    * W : Blanc
    * 1STP : Tuile premier joueur
    * JK : Joker.
    
Sur les *murs* des joueurs, les cases contenant * signifie que la case n'a pas été remplie. 
Ces actions sont répétés jusqu'à la fin de la partie.

## Version Graphique

Au démarrage de la version graphique, la fenêtre va demander à l'utilisateur :
1. Le nombre de joueur (entre 2 et 4).
2. S'il souhaite jouer à la variante.
3. S'il souhaite utiliser les tuiles Joker.

Ensuite le plateau démarre et les tuiles sont représentés par des carrés de couleur. 
La tuile premier joueur est représenté par un carré vert. 
Au milieu de la fenêtre est écrit qui doit jouer.
Pour pouvoir déposer des tuiles dans les lignes de motifs clique une fois sur la couleur voulu dans les fabriques ou au centre du jeu.
Ensuite cliquer sur la ligne voulu. 
Les tuiles correspondantes seront déposés dans la ligne et cela sera tour du joueur suivant.
Ces actions sont répétés jusqu'à la fin de la partie.

Si la variante est activé, sous le mur de chaque plateau de chaque joueur apparaît 5 boutons, permettant de vider la ligne correspondante du mur.
