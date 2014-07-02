package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class ChandyStarter extends Thread {

	private Succursale succursaleLocal;
	
	public ChandyStarter(Succursale succursaleLocal){
		this.succursaleLocal = succursaleLocal;
		
	}
	
	public void run(){

		while(true){
			
			try {
				Thread.sleep(((int)((Math.random()) * (20)) + 10)*1000);
				
				succursaleLocal.startChandy();
	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
