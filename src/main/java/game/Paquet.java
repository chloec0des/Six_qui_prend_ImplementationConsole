package game;

import java.util.*;

public class Paquet {
	/** Le paquet de carte */
	private static ArrayList<Carte> paquet;

	/** Le compteur du nombre de carte */
	private static int cpt = 104;

	/** Initialise un paquet */
	public Paquet() {
		int nbTete = 0;
		Paquet.paquet = new ArrayList<Carte>();
		for (int i = 0; i < 104; i++) {
			if ((i + 1) == 55)
				nbTete = 7;
			else if ((i + 1) % 10 == 0)
				nbTete = 3;
			else if ((i + 1) % 5 == 0)
				nbTete = 2;
			else if ((i + 1) == 55)
				nbTete = 7;
			else if ((i + 1) % 11 == 0)
				nbTete = 5;
			else
				nbTete = 1;
			Carte carte = new Carte(i + 1, nbTete);
			paquet.add(carte);
		}
	}

	/** Choisi une carte aleatoire dans le paquet
	 * @return tmp la carte
	 */
	public Carte distributionCarte() {
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(cpt);
		Carte tmp = new Carte();
		tmp = paquet.get(nombreAleatoire);
		paquet.remove(nombreAleatoire);
		cpt--;
		return tmp;
	}

}