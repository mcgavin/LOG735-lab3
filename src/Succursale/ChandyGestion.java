package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChandyGestion extends Thread {

	private Succursale succursale;
	private boolean chandy;
	public ChandyGestion(Succursale succursale){
		this.succursale = succursale;
		this.chandy = true;
		
	}
	
	public void endChandy(){
		for(int i=0;i<succursale.getListSuccursale().size();i++){
			
		}
		chandy=false;
	}
	public void run(){

		while(chandy){
			
		}
	}
	
}
