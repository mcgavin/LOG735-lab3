package Succursale;

import java.util.ArrayList;
import java.util.HashMap;

public class ChandySucc {

	private int id;
	private int etat;
	private HashMap<Integer, Canal> listCanauxMontant = new HashMap<Integer, Canal>();

	public ChandySucc(int id){
		this.id=id;
		this.etat=0;
		listCanauxMontant.put(id, new Canal(0,true));
	}
	public ChandySucc(int id, int etat, HashMap<Integer, Canal> listCanauxMontant){
		this.id = id;
		this.etat = etat;
		this.listCanauxMontant = listCanauxMontant;
	}
	
	public void stopRecord(int canal){
		listCanauxMontant.get(canal).setRecord(false);
		isComplete();
	}
	public void ajouterArgent(int argent, int canal){
		listCanauxMontant.get(canal).addFlux(argent);
	}
	public String toString(){
		
		String string = "" ;
		
		string+= "etat-"+id+"-"+etat+"-";
		for (Integer mapKey : listCanauxMontant.keySet()) {
				string+= mapKey+"/"+listCanauxMontant.get(mapKey).getFlux()+"/";
			
		}
		
		return string;
		
	}
	public int getEtat(){
		return etat;
	}
	public void setEtat(int etat){
		this.etat = etat;
	}
	public HashMap<Integer, Canal>  getCanal(){
		return listCanauxMontant;
	}
	public void setCanal(HashMap<Integer, Canal> listCanauxMontant){
		this.listCanauxMontant = listCanauxMontant;
	}
	public boolean isComplete(){
		
		for (Integer mapKey : listCanauxMontant.keySet()) {
			if(listCanauxMontant.get(mapKey).isRecord()){
				return false;
			}
		}
		return true;
	}
}
