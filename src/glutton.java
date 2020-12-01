

import java.util.ArrayList;
import java.util.Arrays;

public class glutton {

	private Solution solution ;
	
	public glutton() {
		solution = new Solution() ;
		solution = remplissageGlutton();
		
	}
	
	
	public Solution remplissageGlutton() {
		
		
		for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {	
				if(Solution.getMatriceSouhaite()[p][se] == 1) {
					
							isEligibleToAddToSalle(p,se);
							isEligibleToAddToAmphi(p,se);
	
				}
					
			}
		}
		
		
		for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {	
				if(Solution.getMatriceSouhaite()[p][se] == 0) {
					
					
						isEligibleToAddToSalle(p,se);
						isEligibleToAddToAmphi(p,se);

					
				}
					
			}
		}
		//
		return solution ;
	}

	/**
	 * fonction qui essay d'affecte 1 a X, il verifie les contraintes afin de valider l'affection, sinon il rollback a un 0
	 * @param p
	 * @param se
	 * @return
	 */
	public boolean isEligibleToAddToSalle(int p, int se) {
		for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
	    	for(int g = 0 ; g < Solution.getNbrGroupsSalles() ; g++) {
		    	for(int sa = 0 ; sa < Solution.getNbrSalles() ; sa++) {
		    		if( solution.getX(p, g, se, sa, mo) >= 0) {
			    		solution.setX(p, g, se, sa, mo, 1);    
		    		
		    		if(!Solution.verifyNoOccuranceSeanceDsSalle(solution.getX()) || !Solution.verifyNoOccuranceProf(solution.getX(),solution.getY()) || !Solution.verifyChargeHoraireSalle(solution.getX())) {
			    		solution.setX(p, g, se, sa, mo, 0);   			    		
		    			continue ;
		    		}else {
		    			
		    		treatementDansY(g,se);
		    		return true ;
		    		
		    		}
		    	   }
		    	}
		    }
		}	
		return false ;
	}
	
	
	/**
	 * 	 * fonction qui essaye d'affecte 1 a Y, il verifie les contraintes afin de valider l'affection, sinon il rollback a un 0
	 * @param p
	 * @param se
	 * @return
	 */
	public boolean isEligibleToAddToAmphi(int p, int se) {
		for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
	    	for(int gr = 0 ; gr < Solution.getNbrGroupsAmphi() ; gr++) {
		    	for(int am = 0 ; am < Solution.getNbrAmphi() ; am++) {
		    		if( solution.getY(p, gr, se, am, mo) >= 0) {
		    			solution.setY(p, gr, se, am, mo, 1); 
		    		
		    		if(!Solution.verifyNoOccuranceSeanceDsAmphi(solution.getY()) || !Solution.verifyNoOccuranceProf(solution.getX(),solution.getY()) || !Solution.verifyChargeHoraireAmphi(solution.getY())) {
		    			solution.setY(p, gr, se, am, mo, 0);
		    			continue ;
		    		}else {
		    			
		    		treatementDansX(gr,se);
		    		return true ;
		    		}
		    	 }
		    	}
		    }
		}	
		return false ;
	}
	
	
	
	public void treatementDansX(int gr, int se) {
		if(gr == 0) {
			for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
				for(int g = 0 ; g < 4 ; g++) {
					for(int sa = 0 ; sa < Solution.getNbrSalles() ; sa++) {
						for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
							//if(solution.getX(p, g, se, sa, mo) != 0)
				    		solution.setX(p, g, se, sa, mo, -1);    
						}
					}
				}
			}
		}else if(gr == 1) {
			for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
				for(int g = 4 ; g < 8 ; g++) {
					for(int sa = 0 ; sa < Solution.getNbrSalles() ; sa++) {
						for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
							//if(solution.getX(p, g, se, sa, mo) != 0)
							solution.setX(p, g, se, sa, mo, -1);    

						}
					}
				}
			}
		}else if(gr == 2) {
			for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
				for(int g = 8 ; g < 12 ; g++) {
					for(int sa = 0 ; sa < Solution.getNbrSalles() ; sa++) {
						for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
							//if(solution.getX(p, g, se, sa, mo) != 0)
							solution.setX(p, g, se, sa, mo, -1);    

						}
					}
				}
			}
		}
		
	}
	/**
	 * cette fonction prend l'indice de grp de salle et de la seance ou ils etudient et ils affectent -1 pour le promo damphi qui l'appartient ce grp de salle.
	 * Cette affectation par -1  bloque le rest de promo d'etre affecte un 1 dans le future(detre libre pour etre enseignee un cour sans leur amis)
	 * @param g
	 * @param se
	 */
	public void treatementDansY(int g, int se) {
		if( g >= 0 && g < 4) {
			for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
					for(int am = 0 ; am < Solution.getNbrAmphi() ; am++) {
						for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
				    		//if(solution.getY(p, 0, se, am, mo) != 0)    
							solution.setY(p, 0, se, am, mo, -1);    

						}
					}
				
			}
		}else if(g >= 4 && g < 8) {
			for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
				for(int am = 0 ; am < Solution.getNbrAmphi() ; am++) {
					for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
						//if(solution.getY(p, 1, se, am, mo) != 0)   
						solution.setY(p, 1, se, am, mo, -1);    
					}
				}
			
		}
		}else if(g >= 8 && g < 12) {
			for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
				for(int am = 0 ; am < Solution.getNbrAmphi() ; am++) {
					for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
						//if(solution.getY(p, 2, se, am, mo) != 0)   
						solution.setY(p, 2, se, am, mo, -1);    
					}
				}
			
		}
		
	  }
	}
	
	
	
	
	
	public Solution getSolution() {
		return solution;
	}
	
	public static void main(String[] args) {
		
		glutton test = new glutton();
		
		int nb = 0; 
		int nbr2 = 0 ; int nbr3 = 0 ;  
		for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {
				int cmp = 0 ;
				for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
			    	for(int g = 0 ; g < Solution.getNbrGroupsSalles() ; g++) {
				    	for(int sa = 0 ; sa < Solution.getNbrSalles() ; sa++) {
				    			if(test.getSolution().getX(p, g, se, sa, mo) == 1) {
				    				nbr2++;
				    				cmp++ ;
				    			}else if(test.getSolution().getX(p, g, se, sa, mo) == 0) {
				    				nbr3++;
				    			}else if(test.getSolution().getX(p, g, se, sa, mo) == -1)
				    						nb++;
				    			
				    	    }
				    	  }

				        }

			      }	

			    	}

		
		System.out.println("\n\n\n");
		ArrayList<Integer> indice = new ArrayList<>();
		int nbr = 0 ;
		int nbr1 = 0;
		int nbr22 = 0 ; int nbr33 = 0 ;
		for(int p = 0 ; p < Solution.getNbrProfesseurs() ; p++) {
			for(int se = 0 ; se < Solution.getNbrSeances() ; se++) {
				for(int mo = 0 ; mo < Solution.getNbrModules() ; mo++) {
			    	for(int gr = 0 ; gr < Solution.getNbrGroupsAmphi() ; gr++) {
				    	for(int am = 0 ; am < Solution.getNbrAmphi() ; am++) {	
	    	    			//System.out.print(" " + test.getSolution().getY()[p][gr][se][am][mo]);
	    	    			nbr1++;
	    	    			if(test.getSolution().getY()[p][gr][se][am][mo] == 0)
	    	    				nbr++;
	    	    			
	    	    			if(test.getSolution().getY()[p][gr][se][am][mo] == 1) {
	    	    				indice.add(gr);
	    	    				nbr22++;
	    	    			}
	    	    			
	    	    			if(test.getSolution().getY()[p][gr][se][am][mo] == -1)
	    	    				nbr33++;
				    	  }
				    		
			    	  }
			        }
		    	  }	
		    	}
		System.out.println("Statistics X: | les 1:  " + nbr2 + " | les 0 : " + nbr3 + " | les -1: " + nb);//*/

		System.out.println("Statistics Y : " + nbr1 + "  | les 0 : " + nbr + " | les 1 : " + nbr22 + " | les -1 : " + nbr33);
	
		System.out.println(Solution.verifyChargeHoraireSalle(test.getSolution().getX()));
		System.out.println(Solution.verifyChargeHoraireAmphi(test.getSolution().getY()));
		System.out.println(Solution.verifyNoOccuranceSeanceDsSalle(test.getSolution().getX()));
		System.out.println(Solution.verifyNoOccuranceSeanceDsAmphi(test.getSolution().getY()));
		System.out.println(Solution.verifyNoOccuranceProf(test.getSolution().getX(), test.getSolution().getY()));
		System.out.println(test.getSolution().fonctionObjective());

	}
	
}
