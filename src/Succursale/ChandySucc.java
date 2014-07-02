package Succursale;

import java.util.ArrayList;
import java.util.HashMap;

public class ChandySucc {

	private int id;
	private int etat;
	private HashMap<Integer, Integer> listCanauxMontant = new HashMap();

	private ArrayList<Integer> listCanaux = new ArrayList();
	
	public ChandySucc(int id, int etat, int listcanaux){
		this.id = id;
		this.etat = etat;
		this.listCanaux = listCanaux;
	}
	
	public void initListCanaux(){
		
	}
	
	
	public String toString(){
		
		String string = "" ;
		
		string+= "etat-"+id+"-"+etat+"-";
		
		for (int i = 0; i < listCanauxMontant.size(); i++) {
			string+= i+"."+listCanauxMontant.get(i)+"|";
		}
		
		return string;
		
	}

}
