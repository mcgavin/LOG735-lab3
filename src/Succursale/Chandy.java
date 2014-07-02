package Succursale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chandy {

	private int id;
	private int etatSucc;
	private ChandySucc chandySucc;
	
	public Chandy(int id, int etatSucc, int nbSucc){
		this.id = id;
		this.etatSucc = etatSucc;
		for(int i = 0;i<=nbSucc;i++){
			//listEtat.add(-1);
		}
	}
	
	public void initCanaux(){
		
//		for(int i = 0;i<listEtat.size();i++){
//			
//		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getEtatSucc() {
		return etatSucc;
	}
	
	public void setEtatSucc(int etatSucc) {
		this.etatSucc = etatSucc;
	}

//	public Map<Integer, Integer> getCanaux() {
//		return canaux;
//	}

	public void setCanaux(Map<Integer, Integer> canaux) {
//		this.canaux = canaux;
	}
}
