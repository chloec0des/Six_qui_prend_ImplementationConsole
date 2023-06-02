package game;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {

    /** Nombre de tours dans la partie */
    private static final int NBTOUR = 10;

    /** Le paquet de la partie */
    private Paquet paquet;

    /** Le plateau de la partie */
    private Plateau plateau;

    /** ArrayList qui contient les joueurs */
    private ArrayList<Player> listeJoueur;

    /** ArrayList qui contient les joueurs qui ont ramasse des tetes de boeufs */
    private ArrayList<Player> ramassageJoueur;

    /** ArrayList qui contient les tetes de boeufs qu'ont ramasses les joueurs */
    private ArrayList<Integer> teteRamassees;

    /** Tableau qui contient les cartes qui ont ete posees */
    private int[] cartePosee;

    /** Initialise la partie
     * @throws FileNotFoundException
     */
    public Game() throws FileNotFoundException {
        paquet = new Paquet();
        plateau = new Plateau(paquet);
        listeJoueur = init(paquet);
        ramassageJoueur = new ArrayList<>();
        teteRamassees = new ArrayList<>();
        cartePosee = new int[listeJoueur.size()];
    }

    /** Cree les joueurs a partir d'un fichier texte
     * @param paquet
     * @return listeJoueur ArrayList contenant les joueurs de la partie
     * @throws FileNotFoundException
     */
    public ArrayList<Player> init(Paquet paquet) throws FileNotFoundException {
        ArrayList<Player> listeJoueur = new ArrayList<Player>();
        String name = "";
        FileInputStream doc = new FileInputStream("src/main/java/game/config.txt");
        Scanner obj = new Scanner(doc);
        while (obj.hasNextLine()) {
            name = obj.next();
            Player player = new Player(name, paquet);
            listeJoueur.add(player);
        }
        obj.close();
        System.out.print("Les " + listeJoueur.size() + " joueurs sont ");
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (i == 0)
                System.out.print(listeJoueur.get(i));
            else if (i == listeJoueur.size() - 1)
                System.out.print(" et " + listeJoueur.get(i));
            else
                System.out.print(", " + listeJoueur.get(i));
        }
        System.out.println(". Merci de jouer a 6 qui prend !");
        return listeJoueur;
    }

    /** Le joueur choisit la carte qu'il veut poser */
    public void choixJoueur() {
        for (int i2 = 0; i2 < listeJoueur.size(); ++i2) {
            cartePosee[i2] = 0;
        }
        ramassageJoueur.clear();
        teteRamassees.clear();
        for (int j = 0; j < listeJoueur.size(); j++) {
            System.out.println("A " + listeJoueur.get(j) + " de jouer.");
            util.Console.pause();
            System.out.println(plateau);
            System.out.println(listeJoueur.get(j).afficheMain());
            listeJoueur.get(j).jouer(plateau);
            cartePosee[j] = listeJoueur.get(j).ordre();
            util.Console.clearScreen();
        }
        Arrays.sort(cartePosee);
    }

    /** getter qui retourne le nombre de joueurs dans la partie
     * @return listeJoueur.size() la taille de l'ArrayList qui contient les joueurs
     */
    public int getNbJoueurs() {return listeJoueur.size();}

    /** pose une carte du joueur
     * @param k la kieme carte a poser
     * @return true si la carte est posee et false si elle ne l'est pas
     */
    public boolean poser(int k) {
        int cpt;
        if(k<listeJoueur.size()) {
            for (int l = 0; l < listeJoueur.size(); ++l) {
                if (cartePosee[k] == listeJoueur.get(l).ordre()) {
                    cpt = plateau.ajouter(avoirCarte(listeJoueur.get(l).ordre()));
                    if(cpt==-1) return false;
                    else if(cpt<5) {
                        listeJoueur.get(l).poser(cartePosee[k]);
                        teteRamassees.add(listeJoueur.get(l).ramasser(cpt + 1, plateau, avoirCarte(listeJoueur.get(l).ordre())));
                        ramassageJoueur.add(listeJoueur.get(l));
                        return true;
                    }
                    else {
                        listeJoueur.get(l).poser(cartePosee[k]);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Affichage du plateau
     * return plateau.toString()
     */
    public String toString() {
        return plateau.toString();
    }

    /** Getter qui retourne le nombre de tours dans une partie
     * @return NBTOUR la constante qui designe le nombre de tours
     */
    public int getNBTOUR() {return NBTOUR;}

    /** Conclut la partie */
    public void cloture() {
        for (int j = 0; j < listeJoueur.size(); j++) {
            for (int i = j+1; i < listeJoueur.size(); i++) {
                if (listeJoueur.get(j).penalite() > listeJoueur.get(i).penalite()) {
                    Collections.swap(listeJoueur, j, i);
                }
                else if (listeJoueur.get(j).penalite() == listeJoueur.get(i).penalite()
                        && listeJoueur.get(j).toString().compareToIgnoreCase(listeJoueur.get(i).toString()) > 0)
                    Collections.swap(listeJoueur, j, i);

            }
        }

        System.out.println("** Score final");
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (listeJoueur.get(i).penalite() == 0 || listeJoueur.get(i).penalite() == 1)
                System.out.println(
                        listeJoueur.get(i) + " a ramasse " + listeJoueur.get(i).penalite() + " tete de boeufs");
            else
                System.out.println(
                        listeJoueur.get(i) + " a ramasse " + listeJoueur.get(i).penalite() + " tetes de boeufs");
        }
    }

    /** Retourne une carte (numero et penalite) a partir de son numero
     * @param nombre le numero de la carte
     * @return Carte(nombre, nbTeteBoeuf) la carte
     */
    public Carte avoirCarte(int nombre) {
        int nbTeteBoeuf;
        if (nombre == 55)
            nbTeteBoeuf = 7;
        else if (nombre % 10 == 0)
            nbTeteBoeuf = 3;
        else if (nombre % 5 == 0)
            nbTeteBoeuf = 2;
        else if (nombre == 55)
            nbTeteBoeuf = 7;
        else if (nombre % 11 == 0)
            nbTeteBoeuf = 5;
        else
            nbTeteBoeuf = 1;
        return new Carte(nombre, nbTeteBoeuf);
    }

    /** Indique les cartes qui ont ete posee
     * @param posable indique si la carte a ete posee ou si elle va l'etre
     */
    public void affichagePose(boolean posable) {
        System.out.print("Les cartes ");
        for (int m = 0; m < listeJoueur.size(); m++) {
            for (int n = 0; n < listeJoueur.size(); n++) {
                if (cartePosee[m] == listeJoueur.get(n).ordre()) {
                    if (m == listeJoueur.size() - 1)
                        System.out.print(cartePosee[m] + " (" + listeJoueur.get(n) + ") ");
                    else if (m == listeJoueur.size() - 2)
                        System.out.print(cartePosee[m] + " (" + listeJoueur.get(n) + ") et ");
                    else
                        System.out.print(cartePosee[m] + " (" + listeJoueur.get(n) + "), ");
                }
            }
        }
        if (posable)System.out.println("ont ete pos�es.");
        else System.out.println("vont etre posees.");
    }

    /** Affiche si les joueurs ont ramasse des tetes de boeufs, ou pas */
    public void affichagePenalite() {
        if (ramassageJoueur.size() > 1) {
            for (int r = 0; r < teteRamassees.size(); ++r) {
                for (int s = r+1; s < teteRamassees.size(); ++s) {
                    if (teteRamassees.get(r) > teteRamassees.get(s)) {
                        Collections.swap(teteRamassees, s, r);
                        Collections.swap(ramassageJoueur, s, r);
                    } else if (teteRamassees.get(r) == teteRamassees.get(s)) {
                        if (ramassageJoueur.get(r).toString()
                                .compareToIgnoreCase(ramassageJoueur.get(s).toString()) > 0) {
                            Collections.swap(teteRamassees, s, r);
                            Collections.swap(ramassageJoueur, s, r);
                        }
                    }
                }
            }
        }
        if (!ramassageJoueur.isEmpty()) {
            for (int t = 0; t < ramassageJoueur.size(); ++t) {
                System.out.print(ramassageJoueur.get(t) + " a ramasse " + teteRamassees.get(t));
                if (teteRamassees.get(t) > 1)
                    System.out.println(" ttes de boeufs");
                else
                    System.out.println(" tEte de boeufs");
            }
        } else
            System.out.println("Aucun joueur ne ramasse de tete de boeufs.");

    }

    /** Le joueur choisit la serie qu'il va ramasser
     * @param k la serie
     */
    public void choixSerie(int k) {
        for(int p=0; p<listeJoueur.size(); ++p) {
            if(cartePosee[k]==listeJoueur.get(p).ordre()) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Pour poser la carte " + listeJoueur.get(p).ordre() + ", "
                        + listeJoueur.get(p) + " doit choisir la serie qu'il va ramasser.");
                System.out.println(plateau);
                System.out.print("Saisissez votre choix : ");
                int tmp2 = 0;
                String choix = "";
                int serie = 0;
                while (tmp2 == 0) {
                    choix = sc.next();
                    try {serie = Integer.parseInt(choix);}
                    catch (NumberFormatException n) {}
                    if (serie > 0 && serie <= 4) {tmp2 = -1;}
                    else System.out.print("Ce n'est pas une serie valide, saisissez votre choix : ");
                }
                listeJoueur.get(p).poser(cartePosee[k]);
                teteRamassees.add(listeJoueur.get(p).ramasser(serie, plateau,avoirCarte(listeJoueur.get(p).ordre())));
                ramassageJoueur.add(listeJoueur.get(p));
                break;
            }
        }

    }

}