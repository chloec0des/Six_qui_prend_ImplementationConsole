package appli;

import java.io.FileNotFoundException;
import game.Game;

public class Application {
	public static void main(String[] args) throws FileNotFoundException {
		Game g = new Game();
		boolean posable = true;
		int k = 0;
		for (int i = 0; i < g.getNBTOUR(); i++) {
			k = 0;
			g.choixJoueur();
			while (k < g.getNbJoueurs()) {
				posable = g.poser(k);
				if (!posable)
					break;
				++k;
			}
			g.affichagePose(posable);
			if (!posable) {
				while (k < g.getNbJoueurs()) {
					posable = g.poser(k);
					if (!posable) {
						g.choixSerie(k);
						posable = true;
					}
					++k;
				}
				g.affichagePose(posable);
			}
			System.out.println(g);
			g.affichagePenalite();
		}
		g.cloture();
	}
}