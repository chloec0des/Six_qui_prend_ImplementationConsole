package game;

public class Carte {
	/** numero sur la carte */
	private int numero;
	/** nombre de tete de boeuf sur la carte */
	private int nbTete;

	/** Constructeur initialise une carte avec un numero et un nombre de tete de
	 * boeuf
	 * @param numero le numero de la carte
	 * @param nbTete le nombre de tete de boeuf de la carte
	 */
	public Carte(int numero, int nbTete) {
		this.numero = numero;
		this.nbTete = nbTete;
	}

	/** Constructeur initialise une carte vide */
	public Carte() {
		this(0, 0);
	}

	/** Affiche la carte et son nombre de tete de boeuf s'il y en a return la chaine
	 * de caracteres
	 */
	public String toString() {
		if (nbTete > 1)
			return numero + " (" + nbTete + ")";
		else
			return Integer.toString(numero);
	}

	/** Renseigne le numero de la carte
	 * @return numero le numero de la carte
	 */
	public int getNumero() {
		return numero;
	}

	/** Renseigne le nombre de tete de boeuf de la carte
	 * @return nbTete le nombre de tete de la carte
	 */
	public int getNbTete() {
		return nbTete;
	}

}