

import java.util.ArrayList;

public class Descente {
	
	public static Solution algoDescente() {
		glutton gl = new glutton();
		Solution meilleur = gl.getSolution();
		
		System.out.println("Fonction obj glouton descente: " + meilleur.fonctionObjective());
		
		boolean solAmelioree = false; 
		
		do {
			Solution meilleurVoisin = choisirMeilleuroisin(Voisinage.genererEnsembleVoisins(meilleur, 20));
			if(meilleur.fonctionObjective() < meilleurVoisin.fonctionObjective()) {
				meilleur = meilleurVoisin;
				solAmelioree = true;
			} else
				solAmelioree = false;
				
		} while(solAmelioree);
		
		System.out.println("Fonction obj descente : " + meilleur.fonctionObjective() );
		return meilleur;
		
	}
	
	
	public static Solution choisirMeilleuroisin(ArrayList<Solution> solutions) {
		Solution meilleur = solutions.get(0);
		for(Solution s : solutions) {
			if(s.fonctionObjective() > meilleur.fonctionObjective())
				meilleur = s;
		}
		return meilleur;
	}

}
