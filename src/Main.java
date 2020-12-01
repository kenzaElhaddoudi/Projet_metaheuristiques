

public class Main {

	public static void main(String[] args) {

		glutton gl = new glutton();
	    System.out.println("cout  de la solution initiale");
		Solution solutionInitiale =  gl.getSolution();
		System.out.println( solutionInitiale.fonctionObjective());
	
	System.out.println("****************************************");
	    System.out.println("Debut de methode descente");
	    System.out.println("------------------------");
	    long chrono=System.currentTimeMillis();
	    Descente.algoDescente();       
	    long time2=System.currentTimeMillis()-chrono;
	    System.out.println("temps d execution de la methode descente est : " +time2);
	    
	    
	  
	    
        System.out.println("****************************************");
	    System.out.println("debut de recuit simule");
	    System.out.println("------------------------");
	    chrono=System.currentTimeMillis();
		RecuitSimule RS=new RecuitSimule();
		
		System.out.println("la solution RS : "+RS.methodeRecuitSimule(solutionInitiale).fonctionObjective());
		time2=System.currentTimeMillis()-chrono;
	    System.out.println("temps d execution de la methode Recui Simule est : " +time2);
	    
	    
	    
	    
        System.out.println("****************************************");
	    System.out.println("debut de Tabou");
	    System.out.println("------------------------");
	    chrono=System.currentTimeMillis();
	    Tabou.algoTabou();    
	    time2=System.currentTimeMillis()-chrono;
	    System.out.println("temps d execution de la methode descente est : " +time2);
					//System.out.println("Voisin "+compt+": "+solutionVoisin.fonctionObjective()+"\n");

		
//		Descente test=new Descente();
//		test.algoDescente();
		
	}

}
