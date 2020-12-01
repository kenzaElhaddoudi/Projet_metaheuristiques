

import java.util.Arrays;
import java.util.Random;

public class Solution {
	int[][][][][] x ;
	int[][][][][] y ;
	static int[][] matriceSouhaite ;
	
	
	
	static int nbrSeances = 24 ;//4 ;
	static int nbrGroupsSalles = 20 ;//4 ;
	static int nbrProfesseurs = 10 ; //2 ;
	static int nbrSalles = 5 ; //;
	static int nbrModules = 6 ; // ;
	
	static int nbrGroupsAmphi = 3 ; //1 ;
	static int nbrAmphi = 2; //1 ;

	
	static int[] chargeHoraireSalle = new int [nbrModules] ;
	static int[] chargeHoraireAmphi = new int [nbrModules] ;
	
	
	//Bloc static s'execute une seul fois dans le programme
	static {
		/**
		 * exemple charge horaire en fonction de seance 
		 */
		for(int ch = 0 ; ch < Solution.getNbrModules() ; ch++) {
			chargeHoraireSalle[ch] = 1 ;
			chargeHoraireAmphi[ch] = 1 ;
		}
		
		
		matriceSouhaite = new int[Solution.getNbrProfesseurs()][Solution.getNbrSeances()];
		
		/**
		 * Exemple de remplissage de la matrice de souhaite,
		 */
		Random aleatoire = new Random();
		for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {
				if(p <= 3 && se < 8 )
					matriceSouhaite[p][se] = 1 ;
				else if (p < 6 && se >= 0 && se < 16 )
					matriceSouhaite[p][se] = 1 ;
				else 
					matriceSouhaite[p][se] = 0 ;

				
			}
		}
			
		System.out.println("Fin initialisation");
	}
	
	
	public Solution() {
		this.x = new int[nbrProfesseurs][nbrGroupsSalles][nbrSeances][nbrSalles][nbrModules];
		this.y = new int[nbrProfesseurs][nbrGroupsAmphi][nbrSeances][nbrAmphi][nbrModules];
	}

	
	

	public static int[][] getMatriceSouhaite() {
		return matriceSouhaite;
	}

	public static int[] getChargeHoraireSalle() {
		return chargeHoraireSalle;
	}




	public static int[] getChargeHoraireAmphi() {
		return chargeHoraireAmphi;
	}


	

	public int[][][][][] getX() {
		return x;
	}



	/**
	 * getteur pour un element d'un array
	 * @param p
	 * @param g
	 * @param se
	 * @param sa
	 * @param mo
	 * @return
	 */
	public int getX(int p, int g, int se, int sa, int mo) {
		return x[p][g][se][sa][mo];
	}
	

	
	/**
	 * setteur pour un element d'un array
	 */
	public void setX(int p, int g, int se, int sa, int mo, int x) {
		this.x[p][g][se][sa][mo] = x;
	}


	public int[][][][][] getY() {
		return y;
	}

	//meme chose que getX
	public int getY(int p, int gr, int se, int am, int mo) {
		return y[p][gr][se][am][mo];
	}
	
	
	
	//meme chose que setX
	public void setY(int p, int gr, int se, int am, int mo, int y) {
		this.y[p][gr][se][am][mo]  = y;
	}
	
	
	
	
	public static int getNbrSeances() {
		return nbrSeances;
	}


	public static int getNbrGroupsSalles() {
		return nbrGroupsSalles;
	}


	public static int getNbrProfesseurs() {
		return nbrProfesseurs;
	}


	public static int getNbrSalles() {
		return nbrSalles;
	}


	public static int getNbrModules() {
		return nbrModules;
	}


	public static int getNbrGroupsAmphi() {
		return nbrGroupsAmphi;
	}


	public static int getNbrAmphi() {
		return nbrAmphi;
	}

	/**
	 * contrainte 1
	 * @param exemple
	 * @return
	 */
	public static boolean verifyNoOccuranceSeanceDsSalle(int[][][][][] exemple) {
		for(int sa = 0 ; sa < getNbrSalles() ; sa++) {
			for(int se = 0 ; se < getNbrSeances() ; se++) {
				int contrainte = 0 ;
				for(int p = 0 ; p < getNbrProfesseurs() ; p++) {
					for(int g = 0 ; g < getNbrGroupsSalles() ; g++) {
						for(int mo = 0 ; mo < getNbrModules() ; mo++) {
							if(exemple[p][g][se][sa][mo] != -1)
							contrainte += exemple[p][g][se][sa][mo];
							
						}
					}
				}
				if(contrainte > 1) {
					return false ;
				}
			}
		}
		return true ;
	}
	
	
	/**
	 * contrainte sur l'amphi, peut pas etre occupé plus qu'une seul fois pour une seance donnee
	 * @param exemple
	 * @return
	 */
	public static boolean verifyNoOccuranceSeanceDsAmphi(int[][][][][] exemple){
		for(int am = 0 ; am < getNbrAmphi() ; am++) {
			for(int se = 0 ; se < getNbrSeances() ; se++) {
				int contrainte = 0 ;
				for(int p = 0 ; p < getNbrProfesseurs() ; p++) {
					for(int gr = 0 ; gr < getNbrGroupsAmphi() ; gr++) {
						for(int mo = 0 ; mo < getNbrModules() ; mo++) {
							if(exemple[p][gr][se][am][mo] != -1 ) 
							contrainte += exemple[p][gr][se][am][mo];
							
						}
					}
				}
				if(contrainte > 1) {
					return false ;
				}
			}
		}
		return true ;
	}
	
	/**
	 * contrainte de prof(n'enseigne au meme seance plus qu'un fois)
	 * @param exemple
	 * @return
	 */
	public static boolean verifyNoOccuranceProf(int[][][][][] exemple,int[][][][][] exemple2 ) {
		for(int p = 0 ; p < getNbrProfesseurs() ; p++) {
			for(int se = 0 ; se < getNbrSeances() ; se++) {
				int contrainte = 0 ;
				for(int mo = 0 ; mo < getNbrModules() ; mo++) {
			    	for(int g = 0 ; g < getNbrGroupsSalles() ; g++) {
				    	for(int sa = 0 ; sa < getNbrSalles() ; sa++) {
				    		if(exemple[p][g][se][sa][mo] != -1) 
							contrainte += exemple[p][g][se][sa][mo];
				    		
					     }
				      }
			    	for(int gr = 0 ; gr < getNbrGroupsAmphi() ; gr++) {
				    	for(int am = 0 ; am < getNbrAmphi() ; am++) {
				    		if(exemple[p][gr][se][am][mo] != -1) 
				    		contrainte += exemple2[p][gr][se][am][mo];
				    		
				    	}
				      }
				} 	
				if(contrainte > 1) {
					return false ;
				}
			}
		}
		return true ;
	}
	
	
	
	public static boolean verifyChargeHoraireSalle(int[][][][][] exemple) {
		for(int mo = 0 ; mo < getNbrModules() ; mo++) {
		 	for(int g = 0 ; g < getNbrGroupsSalles() ; g++) {
		 		int contrainte = 0 ;
		 		for(int se = 0 ; se < getNbrSeances() ; se++) {	
		 			for(int p = 0 ; p < getNbrProfesseurs() ; p++) {
		 				for(int sa = 0 ; sa < getNbrSalles() ; sa++) {
		 					if(exemple[p][g][se][sa][mo] != -1) 
		 					contrainte += exemple[p][g][se][sa][mo];
		 				  
		 				}
		 			}
		 		}
		 		//System.out.println("La charge horaire du module n " + mo + " pour le promo " + g + " est : " + contrainte );
		 		if(contrainte > chargeHoraireSalle[mo]) {
		 			return false ;
		 		}
		 	}
		 	
		 }
		
		return true ;
	}

	
	public static boolean verifyChargeHoraireAmphi(int[][][][][] exemple) {
		for(int mo = 0 ; mo < getNbrModules() ; mo++) {
		 	for(int gr = 0 ; gr < getNbrGroupsAmphi() ; gr++) {
		 		int contrainte = 0 ;
		 		for(int se = 0 ; se < getNbrSeances() ; se++) {	
		 			for(int p = 0 ; p < getNbrProfesseurs() ; p++) {
		 				for(int am = 0 ; am < getNbrAmphi() ; am++) {
		 					if(exemple[p][gr][se][am][mo] != -1)
		 					contrainte += exemple[p][gr][se][am][mo];
		 					
		 				}
		 			}
		 		}
		 		if(contrainte > chargeHoraireAmphi[mo]) {
		 			return false ;
		 		}
		 	}
		 	
		 }
		
		return true ;
	}
	
	
	
	/*
	 * la fonction objective est calcule par la formule suivant: la somme de la multiplication entre ensemble de tous les x et tous les cases de matrice
	 * et la multiplication entre l'ensemble de tous les y et tous les cases de la matrice de souhaite 
	 * 
	 
	public int fonctionObjective() {
		int total = 0 ;
		for(int sa = 0 ; sa < Solution.getNbrSalles() ; sa++) {
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {
				for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
					for(int g = 0 ; g < Solution.getNbrGroupsSalles() ; g++) {
						for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
							if(this.getX(p, g, se, sa, mo) != -1)
									total += (this.getX(p, g, se, sa, mo) * Solution.getMatriceSouhaite()[p][se]);
						}
					}
				}
			}
		}
		
		for(int am = 0 ; am < Solution.getNbrAmphi() ; am++) {
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {
				for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
					for(int gr = 0 ; gr < Solution.getNbrGroupsAmphi() ; gr++) {
						for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
							if(this.getY(p, gr, se, am, mo) != -1)
								total += (this.getY(p, gr, se, am, mo) * Solution.getMatriceSouhaite()[p][se]);
						}
					}
				}
			}
		}
		System.out.println("Le total est : " +  total);
		
		return total;
	}
	*/
	
	
	/**
	 * Fonction objective a le meme principe que la derniere avec une difference:
	 * Pour chaque prof et chaque seance, on cherche s'il l'enseigne, dés que l'on trouve cette seance on voit si elle
	 * fait partie des souhaits et on ajoute au fct obj, puis on passe au seance prochaine sans continuer d'itérer
	 * puisque c'est garantie qu'un prof enseigne une seule fois en une seance donnée
	 * @return
	 */
	public int fonctionObjective() {
		int valeur = 0;
		
		for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) 
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {
				boolean souhaitTrouve = false;
						
				for(int g = 0 ; g < Solution.getNbrGroupsSalles() ; g++) {
					for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
						for(int sa = 0 ; sa < Solution.getNbrSalles() ; sa++) {
							
							if(this.getX(p, g, se, sa, mo) == 1) {
								valeur += (this.getX(p, g, se, sa, mo) * Solution.getMatriceSouhaite()[p][se]);
								souhaitTrouve = true;
								break;
							}
							
						}
						if(souhaitTrouve) break;
					}
					if(souhaitTrouve) break;
				}
				
				if(souhaitTrouve) continue;
				
				
				for(int gr = 0 ; gr < Solution.getNbrGroupsAmphi() ; gr++) {
					for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
						for(int am = 0 ; am < Solution.getNbrAmphi() ; am++) {
							if(this.getY(p, gr, se, am, mo) == 1) {
								valeur += (this.getY(p, gr, se, am, mo) * Solution.getMatriceSouhaite()[p][se]);
								souhaitTrouve = true;
								break;
							}
								
						}
						
						if(souhaitTrouve) break;
					}
					if(souhaitTrouve) break;
				}
				
			}
		
		//System.out.println("Le total est : " +  valeur);
		return valeur;				

	}
	
	
	@Override
	public String toString() {
		return "Solution [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
	}
	
}
