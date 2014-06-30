package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TransfertAuto extends Thread {

	private Succursale succursaleLocal;
	
	public TransfertAuto(Succursale succursaleLocal){
		this.succursaleLocal = succursaleLocal;
		
	}
	
	public void run(){
		//  prompt the user to enter their name
		//  read the username from the command-line; need to use try/catch with the
		//  readLine() method
		while(true){
			
			try {
				this.sleep(((int)(Math.random() * (5)) + 5)*1000);
				
				if(succursaleLocal.getListSuccursale().size()!=0){
					int idSuccEnvoie = succursaleLocal.getListSuccursale().get(((int) (Math.round(Math.random() * succursaleLocal.getListSuccursale().size())))).getSuccID();
					System.out.println(idSuccEnvoie);
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
