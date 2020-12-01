
import java.util.Random;



public class RecuitSimule  {
	
	private int nombreIteration;
	private double temperature;
	private int nombreVoisins;
	
	public RecuitSimule( int nombreIteration, double temperature) {
		
		this.nombreIteration = nombreIteration;
		this.temperature = temperature;
		this.nombreVoisins = 100;
		
	}
	
	public RecuitSimule(int nombreIteration, double temperature, int nombreVoisins) {
		super();
		this.nombreIteration = nombreIteration;
		this.temperature = temperature;
		this.nombreVoisins = nombreVoisins;
	}

	public RecuitSimule() {
		super();
		this.temperature=1000000000;
		this.nombreIteration=50;
		this.nombreVoisins = 100;
	}
	
	public double boltzmann(double temperature,Solution solution,Solution solutionVoisin) {
        double f=0;
	    f=Math.exp((solution.fonctionObjective()-solutionVoisin.fonctionObjective())/temperature);
	    return f; 
		
	}
	
	
	public Solution methodeRecuitSimule(Solution SolutionInitiale) {
		
		Solution solution= SolutionInitiale;
		int compt=0;
		while(compt<=this.nombreIteration) {
			Solution solutionVoisin=Descente.choisirMeilleuroisin(Voisinage.genererEnsembleVoisins(solution,nombreVoisins));
			System.out.println("Voisin "+compt+": "+solutionVoisin.fonctionObjective()+"\n");
			Random rnd=new Random();
			double r=rnd.nextDouble();
			
			if(r<boltzmann(this.temperature,solution,solutionVoisin)) {
				   
						solution=solutionVoisin;
			 }else {
				 		compt++;	
			 }
						this.temperature=this.temperature/10;
				}
		
		return solution;
	}
	
}
