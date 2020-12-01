
import java.util.ArrayList;
import java.util.Random;


public class Voisinage {

	
	
	public static Solution genererVoisinAleatoire(Solution s) {
		Solution voisin = new Solution();
		voisin.x = copy(s.x);
		voisin.y = copy(s.y);
		
		Random aleatoire = new Random();

			
		
		int prof1, prof2;
		prof1 = aleatoire.nextInt(Solution.nbrProfesseurs);
		do {
			prof2 = aleatoire.nextInt(Solution.nbrProfesseurs);
		} while(prof1 == prof2);
		
		int seance1, salle1, amphi1, module1, groupeTD1, groupeCours1;
		int seance2, salle2, amphi2, module2, groupeTD2, groupeCours2;
		
		do {
			seance1 = aleatoire.nextInt(Solution.nbrSeances);
			salle1 = aleatoire.nextInt(Solution.nbrSalles);
			amphi1 = aleatoire.nextInt(Solution.nbrAmphi);
			module1 = aleatoire.nextInt(Solution.nbrModules);
			groupeTD1 = aleatoire.nextInt(Solution.nbrGroupsSalles);
			groupeCours1 = aleatoire.nextInt(Solution.nbrGroupsAmphi);
		} while(voisin.x[prof1][groupeTD1][seance1][salle1][module1] == 1 || voisin.y[prof1][groupeCours1][seance1][amphi1][module1] == 1);
		
		do {
			seance2 = aleatoire.nextInt(Solution.nbrSeances);
			salle2 = aleatoire.nextInt(Solution.nbrSalles);
			amphi2 = aleatoire.nextInt(Solution.nbrAmphi);
			module2 = aleatoire.nextInt(Solution.nbrModules);
			groupeTD2 = aleatoire.nextInt(Solution.nbrGroupsSalles);
				groupeCours2 = aleatoire.nextInt(Solution.nbrGroupsAmphi);
		} while(voisin.x[prof2][groupeTD2][seance2][salle2][module2] == 1 || voisin.y[prof2][groupeCours2][seance2][amphi2][module2] == 1);
		
		
		if(voisin.x[prof1][groupeTD1][seance1][salle1][module1] == 1) //La seance a retirer pour le prof1 est une seance de TD
		{
			
			voisin.x[prof1][groupeTD1][seance1][salle1][module1] = 0; //désaffecter la seance de TD du prof1
			
			if(voisin.x[prof2][groupeTD2][seance2][salle2][module2] == 1) //La seance a retirer pour le prof 2 est une seance de TD
			{
				voisin.x[prof2][groupeTD2][seance2][salle2][module2] = 0; //désaffecter la seance de TD du prof2
				
				voisin.x[prof1][groupeTD2][seance2][salle2][module2] = 1; //Donner au prof1 la seance de TD du prof2
				voisin.x[prof2][groupeTD1][seance1][salle1][module1] = 1; //Donner au prof2 la seance de TD du prof1
				
			} 
			
			else //La seance a retirer pour le prof 2 est une seance de cours
			{
				voisin.y[prof2][groupeCours2][seance2][amphi2][module2] = 0; //désaffecter la seance de cours du prof2
				
				voisin.y[prof1][groupeCours2][seance2][amphi2][module2] = 1; //Affecter au prof1 la seance de cours du prof2
				voisin.x[prof2][groupeTD1][seance1][salle1][module1] = 1; // Affecter au prof2 la seance du td du prof1
			}
				
			
			
		} 
		
		else //Si la seance a retier pour le prof1 est une seance de cours
		
		{
			voisin.y[prof1][groupeCours1][seance1][amphi1][module1] = 0; //Retirer la seance de cours du prof1
			
			if(voisin.x[prof2][groupeTD2][seance2][salle2][module2] == 1) // Si la seance a retirer pour le prof2 est une seance de td
			{
				voisin.x[prof2][groupeTD2][seance2][salle2][module2] = 0; //retirer la seance de td du prof2
				
				voisin.y[prof2][groupeCours1][seance1][amphi1][module1] = 1; //Donner la seance de cours du prof1 au prof2
				voisin.x[prof1][groupeTD2][seance2][salle2][module2] = 1; //Donner la seance de td du prof2 au prof1
			}
			
			else // Si la seance a retirer pour le prof2 est une seance de cours
			{
				voisin.y[prof2][groupeCours2][seance2][amphi2][module2] = 0; //retirer la seance de cours du prof2
				
				voisin.y[prof2][groupeCours1][seance1][amphi1][module1] = 1; //Donner la seance de cours du prof1 au prof2
				voisin.y[prof1][groupeCours2][seance2][amphi2][module2] = 1; //Donner la seance de cours du prof2 au prof1
			}
			
		}
		
		return voisin;
	}
	
	/**
	 * Generer les voisins de la solution s
	 * @param s
	 * @param nbrVoisins nombre de voisins désiré
	 * @return
	 */
	public static ArrayList<Solution> genererEnsembleVoisins(Solution s, int nbrVoisins) {
		ArrayList<Solution> voisins = new ArrayList<Solution>();
		int compt = nbrVoisins;
		while(compt != 0) {
			voisins.add(genererVoisinAleatoire(s));
			compt--;
		}
		return voisins;
	}
	
	
	/**
	 * Faire une copie de la matrice (deep copie)
	 * @param mat
	 * @return
	 */
	private static int[][][][][] copy(int[][][][][] mat) {
		int size1 = mat.length;
		int size2 = mat[0].length;
		int size3 = mat[0][0].length;
		int size4 = mat[0][0][0].length;
		int size5 = mat[0][0][0][0].length;
		
		int[][][][][] copy = new int[size1][size2][size3][size4][size5];
		
		for(int i = 0; i < size1; i++)
			for(int j = 0; j < size2; j++)
				for(int k = 0; k < size3; k++)
					for(int l = 0; l < size4; l++)
						for(int m = 0; m < size5; m++)
							copy[i][j][k][l][m] = mat[i][j][k][l][m];
		return copy;
	}
}
