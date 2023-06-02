package game;

import java.util.*;

public class Player {
	/** La main du joueur */
	private ArrayList<Carte> hand;

	/** Les penalites du joueurs */
	private int penalite;

	/** Le nom du joueur */
	private String name;

	/** La carte en train d'etre jouee */
	private int carteJouee;

	/** Initialise un joueur
	 * @param name le nom du joueur
	 * @param p    le paquet
	 */
	public Player(String name, Paquet p) {
		this.name = name;
		this.hand = new ArrayList<Carte>();
		for (int i = 0; i < 10; i++)
			hand.add(p.distributionCarte());
		triMain();
		this.penalite = 0;
		this.carteJouee = 0;

	}

	/** Ramasse la serie
	 * @param serie   la serie a ramasser
	 * @param plateau le plateau du jeu
	 * @param carte   la carte qui remplace la serie ramasser
	 * @return cpt le nombre de penalites ramassees
	 */
	public int ramasser(int serie, Plateau plateau, Carte carte) {
		int nbCartes = plateau.nbCarte(serie - 1), cpt = 0;
		for (int i = 0; i < nbCartes; ++i) {
			cpt += plateau.sommet(serie - 1).getNbTete();
			plateau.depiler(serie - 1);
		}
		penalite += cpt;
		plateau.empiler(serie - 1, carte);
		return cpt;
	}

	/** Renseigne la carte jouee d'un joueur */
	public int ordre() {
		return carteJouee;
	}

	/** Tri la main du joueur */
	public void triMain() {
		for (int i = 0; i < hand.size() - 1; i++) {
			while (hand.get(i).getNumero() > hand.get(i + 1).getNumero()) {
				Collections.swap(hand, i, i + 1);
				i = 0;
			}
		}
	}

	/** Enleve la carte de la main du joueur
	 * @param le numero de la carte
	 * @return tmp la carte enlevee de la main
	 */
	public Carte poser(int numeroCarte) {
		Carte tmp = new Carte();
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getNumero() == numeroCarte) {
				tmp = hand.get(i);
				hand.remove(i);
			}
		}
		return tmp;
	}

	/** Verifie que la carte est dans la main
	 * @param carte le numero de la carte
	 * @return true si la carte est dans la main
	 * @return false si la carte n'est pas dans la main
	 */
	public boolean verifCarte(int carte) {
		for (int i = 0; i < hand.size(); i++) {
			hand.get(i).getNumero();
			if (hand.get(i).getNumero() == carte) {
				return true;
			}
		}
		return false;
	}

	/** Renseigne le nombre de penalite
	 * @return penalite le nombre de penalites
	 */
	public int penalite() {
		return penalite;
	}

	/** Pose une carte sur le plateau
	 * @param  plateau
	 */
	public void jouer(Plateau plateau) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Saisissez votre choix : ");
		String choix = "";
		int carte = 0;
		int tmp = 0;
		while (tmp == 0) {
			choix = sc.next();
			try {
				carte = Integer.parseInt(choix);
			} catch (NumberFormatException n) {
			}
			if (verifCarte(carte)) {
				carteJouee = carte;
				tmp = -1;
			} else
				System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
		}

	}

	/** Affiche la main du joueur
	 * @return afficheMain la chaine de caractere de la main du joueur
	 */
	public String afficheMain() {
		String afficheMain = "";
		System.out.print("- Vos cartes : ");
		for (int i = 0; i < hand.size(); i++) {
			if (i == 0)
				afficheMain += hand.get(i).toString();
			else
				afficheMain += ", " + hand.get(i).toString();
		}
		return afficheMain;
	}

	/** Affiche le nom du joueur
	 * @return name le nom du joueur
	 */
	public String toString() {
		return name;
	}

}