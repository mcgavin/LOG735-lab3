package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class transfert extends Thread {

	private Succursale succursaleLocal;
	public transfert(Succursale succursaleLocal){
		this.succursaleLocal = succursaleLocal;
		
	}
	
	public void run(){
		//  prompt the user to enter their name
		//  read the username from the command-line; need to use try/catch with the
		//  readLine() method
		while(true){
			
			try {
				this.sleep(50000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
