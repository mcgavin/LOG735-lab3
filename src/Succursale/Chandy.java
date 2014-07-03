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
		HashMap<Integer, Canal> listCanauxMontant = new HashMap<Integer, Canal>();
		for(int i = 0;i<listSuccursale.size();i++){
			listCanauxMontant.put(listSuccursale.get(i).getSuccID(), new Canal(0, true));
		}
		mapChandySucc.put(id, new ChandySucc(id, etatSucc, listCanauxMontant));
		for(int i = 0;i<listSuccursale.size();i++){
			mapChandySucc.put(listSuccursale.get(i).getSuccID(), new ChandySucc(id));
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
			if(!mapChandySucc.get(mapKey).isComplete()){
				return false;
			}
		}
		return true;
	}


	public void printchandy(int sommeBanque) {
		int somme =0;
		System.out.println("-----CHANDY LAMPORT-----");
		for (Integer mapKey : mapChandySucc.keySet()) {
			System.out.println("Succursale #"+mapKey+" : "+mapChandySucc.get(mapKey).getEtat());
			somme = somme + mapChandySucc.get(mapKey).getEtat();
			for (Integer mapKey2 : mapChandySucc.get(mapKey).getCanal().keySet()) {
				System.out.println("Canal S"+mapKey+"-S"+mapKey2+"  : "+mapChandySucc.get(mapKey).getCanal().get(mapKey2).getFlux());
				somme = somme + mapChandySucc.get(mapKey).getCanal().get(mapKey2).getFlux();
			}
		}
		System.out.println("Somme connue par la Banque : "+sommeBanque);
		System.out.println("Somme détectée par la capture :"+somme);
		System.out.println("ÉTAT GLOBAL COHÉRENT ");
		if(somme==sommeBanque){
			System.out.println("ÉTAT GLOBAL COHÉRENT ");
		}else{
			System.out.println("ÉTAT GLOBAL INCOHÉRENT ");
		}
	}


	public void setChandySucc(int succid, int montant,HashMap<Integer, Canal> listCanauxMontant) {
		mapChandySucc.get(succid).setEtat(montant);
		mapChandySucc.get(succid).setCanal(listCanauxMontant);
		
	}
}
