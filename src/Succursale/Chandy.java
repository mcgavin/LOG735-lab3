package Succursale;

import java.util.HashMap;
import java.util.Map;

public class Chandy {

	private int id;
	private int etatSucc;
	private Map<Integer, Integer> canaux;
	public Chandy(int id, int etatSucc){
		this.id = id;
		this.etatSucc = etatSucc;
		this.setCanaux(new HashMap<Integer, Integer>());
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

	public Map<Integer, Integer> getCanaux() {
		return canaux;
	}

	public void setCanaux(Map<Integer, Integer> canaux) {
		this.canaux = canaux;
	}
}
