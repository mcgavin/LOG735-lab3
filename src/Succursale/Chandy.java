package Succursale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chandy {

	private int id;
	private int etatSucc;
	private int banqueValue;
	private Map<Integer, ChandySucc> mapChandySucc;
	
	public Chandy(int id, int etatSucc, List<TunnelSuccursale> listSuccursale){
		this.id = id;
		this.etatSucc = etatSucc;
		this.mapChandySucc = new HashMap<Integer, ChandySucc>();
		System.out.println(listSuccursale.size());
		HashMap<Integer, Canal> listCanauxMontant = new HashMap<Integer, Canal>();
		for(int i = 0;i<listSuccursale.size();i++){
			listCanauxMontant.put(listSuccursale.get(i).getSuccID(), new Canal(0, true));
		}
		mapChandySucc.put(id, new ChandySucc(id, etatSucc, listCanauxMontant));
		for(int i = 0;i<listSuccursale.size();i++){
			mapChandySucc.put(listSuccursale.get(i).getSuccID(), new ChandySucc());
		}
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

	public ChandySucc getChandySucc(int mapKey){
		return mapChandySucc.get(mapKey);
	}

	public ChandySucc getMe(){
		return mapChandySucc.get(id);
	}
	public boolean isComplete(){
		
		for (Integer mapKey : mapChandySucc.keySet()) {
			if(mapChandySucc.get(mapKey).isComplete()){
				return false;
			}
		}
		return true;
	}


	public void printchandy() {
		for (Integer mapKey : mapChandySucc.keySet()) {
			mapChandySucc.get(mapKey).printSucc();
				
		}
		
	}
}
