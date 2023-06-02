package game;

public class Plateau {
	/** Le plateau du jeu */
	private Carte[][] plateau;

	/** Initialise le plateau
	 * @param p le paquet utilise pour le jeu
	 */
	public Plateau(Paquet p) {
		plateau = new Carte[4][5];
		for (int i = 0; i < 4; ++i) {
			plateau[i] = new Carte[5];
			plateau[i][0] = p.distributionCarte();
		}
	}

	/** Renseigne le sommet d'une serie
	 * @param la serie
	 * @return la carte au sommet
	 */
	public Carte sommet(int serie) {
		return plateau[serie][nbCarte(serie) - 1];
	}

	/** Depile la carte au sommet d'une serie
	 * @param la serie
	 */
	public void depiler(int serie) {
		plateau[serie][nbCarte(serie) - 1] = null;
	}

	/** Empile une carte au sommet
	 * @param la serie
	 * @param la carte a empiler
	 */
	public void empiler(int serie, Carte c) {
		plateau[serie][nbCarte(serie)] = c;
	}

	/** Renseigne le nombre de carte d'une serie
	 * @param la serie
	 * @return le nombre de cartes au sommet
	 */
	public int nbCarte(int serie) {
		for (int i = 4; i >= 0; --i) {
			if ((plateau[serie][i] != null))
				if (plateau[serie][i].getNumero() > 0 && plateau[serie][i].getNumero() <= 104)
					return i + 1;
		}
		return 0;
	}

	/** Affiche l'etat du plateau
	 * @return affichage l'etat du plateau
	 */
	public String toString() {
		String affichage = "";
		for (int j = 0; j < 4; ++j) {
			affichage += "- serie n " + (j + 1) + " : ";
			for (int i = 0; i < (this.nbCarte(j)); ++i) {
				if (i == 0)
					affichage += plateau[j][i].toString();
				else
					affichage += ", " + plateau[j][i].toString();
			}
			if (j != 3)
				affichage += "\n";
		}
		return affichage;
	}

	/** Ajoute une carte au plateau
	 * @param c la carte a ajouter
	 * @return -1 si la carte n'a pas ete ajoutee
	 * @return cpt la serie qui est complete
	 * @return 5 si la carte a ete ajoutee
	 */
	public int ajouter(Carte c) {
		int tmp = 104, cpt = -1;
		for (int i = 0; i < 4; ++i) {
			if (plateau[i][nbCarte(i) - 1].getNumero() < c.getNumero()
					&& c.getNumero() - plateau[i][nbCarte(i) - 1].getNumero() < tmp) {
				tmp = (c.getNumero() - plateau[i][nbCarte(i) - 1].getNumero());
				cpt = i;
			}
		}
		if (cpt == -1)
			return -1;
		else if (nbCarte(cpt) == 5)
			return cpt;
		else {
			plateau[cpt][nbCarte(cpt)] = c;
			return 5;
		}
	}

}