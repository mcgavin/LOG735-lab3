package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class TransfertAuto extends Thread {

	private Succursale succursaleLocal;
	
	public TransfertAuto(Succursale succursaleLocal){
		this.succursaleLocal = succursaleLocal;
		
	}
	
	public void run(){

		while(true){
			
			try {
				Thread.sleep(((int)((Math.random()) * (5)) + 5)*1000);
				
				//EXIGENCE SUCCURSALE-05
				
				//if there is more than 1 succ and the local one has money
				if(succursaleLocal.getListSuccursale().size()!=0 && succursaleLocal.getTotal() > 0){

					Random random =new Random();
					
					int index = random.nextInt(succursaleLocal.getListSuccursale().size());
					
					int idSuccEnvoie = succursaleLocal.getListSuccursale().get(index).getSuccID();
					
					int argent = (int) (Math.round(Math.random()*(succursaleLocal.getTotal()/100))*100);
					succursaleLocal.envoieArgent(idSuccEnvoie, argent);	
				}else{
					
				}
				
	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
