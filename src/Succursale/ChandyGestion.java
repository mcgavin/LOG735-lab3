package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ChandyGestion extends Thread {

	private Succursale succursale;
	private boolean chandy;
	private int SuccIDStarter;
	private Map<Integer, Chandy> mapChandy;
	
	public ChandyGestion(Succursale succursale){
		this.succursale = succursale;
		this.chandy = true;
		this.mapChandy = new HashMap<Integer, Chandy>();
	}
	
	public void createChandy(int SuccIDStarter){
		mapChandy.put(SuccIDStarter, new Chandy());
	}
	
	public void endChandy(){
		for(int i=0;i<succursale.getListSuccursale().size();i++){
			succursale.getListSuccursale().get(i).sendMessage("Chandy:End-"+SuccIDStarter);
		}
			chandy=false;
	}
	
	public void startChandy(int SuccID){
		SuccIDStarter = SuccID;
		
	}
	
	public void run(){

		while(chandy){
			
		}
	}
	
}
