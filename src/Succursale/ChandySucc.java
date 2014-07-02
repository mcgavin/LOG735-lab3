package Succursale;

import java.util.ArrayList;

public class ChandySucc {

	private int id;
	private int etat;
	private ArrayList<Integer> listCanaux = new ArrayList();
	
	public ChandySucc(int id, int etat, int listcanaux){
		this.id = id;
		this.etat = etat;
		this.listCanaux = listCanaux;
	}
	
	public void initListCanaux(){
		
	}
	
}
