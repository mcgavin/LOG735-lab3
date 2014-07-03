package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChandyGestion extends Thread {

	private Succursale succursale;
	private boolean chandyRun;
	private syncedWriteList chandyEvent;
	private Chandy chandy;
	private Map<Integer, ChandySucc> mapChandyMe;
	
	public ChandyGestion(Succursale succursale){
		this.succursale = succursale;
		this.chandyRun = true;
		this.chandyEvent = new syncedWriteList();
		this.mapChandyMe = new HashMap<Integer, ChandySucc>();
		chandyRun = false;
	}
	

	/**
	 * 
	 */
	public void endChandy(){
		succursale.envoieChandyMessageToAll("end-"+succursale.getSuccursaleId() );
		chandyRun = false;
	}

	public void ajouterArgent(int argent, int canal){
		for (Integer mapKey : mapChandyMe.keySet()) {
			mapChandyMe.get(mapKey).ajouterArgent(argent, canal);

		}
	}
	
	public void stopRecord(int succID, int canal){
		succursale.stopRecord(canal);
		mapChandyMe.get(succID).stopRecord(canal);
		checkComplete(succID);
	}
	public void checkChandyComplete(){
		if(chandy.isComplete()){
			chandy.printchandy(succursale.getBanqueTotal());
			chandyRun = false;
		}
	}
	
	public void checkComplete(int succID){
		if(mapChandyMe.get(succID).isComplete()){
				succursale.envoieChandyMessage(mapChandyMe.get(succID).toString(), succID);
				mapChandyMe.remove(succID);		
		}
	}
	
	public void chandyStart(){		
		if(!chandyRun){
			chandy = new Chandy(succursale.getSuccursaleId(), succursale.getTotal(), succursale.getListSuccursale());
			succursale.envoieChandyMessageToAll("M-"+succursale.getSuccursaleId());
			chandyRun = true;
		}else{
			System.out.println("il y a deja un chandy qui roule");
		}
	}
	
	public void createMyChandySucc(int SuccIDStarter, int succID){
		
		HashMap<Integer, Canal> listCanauxMontant = new HashMap<Integer, Canal>();
		for(int i=0;i<succursale.getListSuccursale().size();i++){
			if(succID==succursale.getListSuccursale().get(i).getSuccID()){
				listCanauxMontant.put(succursale.getListSuccursale().get(i).getSuccID(), new Canal(0, false));
			}else{
				listCanauxMontant.put(succursale.getListSuccursale().get(i).getSuccID(), new Canal(0, true));
			}
		}
		mapChandyMe.put(SuccIDStarter, new ChandySucc(succursale.getSuccursaleId(), succursale.getTotal(), listCanauxMontant));
	}
	
	public void run(){

		while(true){
			try {

				if (! (chandyEvent.isEmpty()) ){
					String[] chandyString = chandyEvent.get(0).split("-");
					int sid = 0;
					if(chandyString[0].equals("M")){
						 sid = Integer.parseInt(chandyString[1]);
						 
						if(sid==succursale.getSuccursaleId()){
							chandy.getMe().stopRecord(Integer.parseInt(chandyString[2]));
							succursale.stopRecord(Integer.parseInt(chandyString[2]));
							checkChandyComplete();
							
						}else if(mapChandyMe.containsKey(sid)){
							stopRecord(sid,Integer.parseInt(chandyString[2]));	
							
						}else{
							createMyChandySucc(sid,Integer.parseInt(chandyString[2]));
							succursale.envoieChandyMessageToAll("M-"+sid);
							checkComplete(Integer.parseInt(chandyString[2]));
						}
						
						
						//Etat global d'une succursale distante 
					}else if(chandyString[0].equals("etat")){
						int succid = Integer.parseInt(chandyString[1]);
						int montant = Integer.parseInt(chandyString[2]);
						HashMap<Integer, Canal> listCanauxMontant = new HashMap<Integer, Canal>();
						//list of idFrom.montant
						String canaux[] = chandyString[3].split("/");
						
						for (int i=0;i<canaux.length;i=i+2) {
							listCanauxMontant.put(Integer.parseInt(canaux[i]), new Canal(Integer.parseInt(canaux[i+1]),false));
						}
						chandy.setChandySucc(succid,montant,listCanauxMontant);
						checkChandyComplete();
					}else if(chandyString[0].equals("end")){
						int montant = Integer.parseInt(chandyString[2]);
					}else if(chandyString[0].equals("transfert")){
						
						int montant = Integer.parseInt(chandyString[1]);
						int canal = Integer.parseInt(chandyString[2]);
						ajouterArgent(montant,canal);
					}
					chandyEvent.remove(0);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getBanqueTotal(){
		
		return succursale.getBanqueTotal();
	}

	//add a string to be parse
	public void addMessage(String s ){
		chandyEvent.add(s);
	}
	
	public void printResultat(){
		
	}
	

	
	//to sync the WriteList
	/**
	 * 
	 * arraylist with sync method 
	 * ( lock to acces the writeList)
	 *
	 */
	class syncedWriteList {
		
		private ArrayList<String> arraylist ;
		private Object lock = new Object();
		
		public syncedWriteList(){
			this.arraylist = new ArrayList<String>() ;
		}
		
		public void add(String s) {
		   synchronized(lock) {
			   this.arraylist.add(s);
	        }
		}

		public void remove(int index){
		   synchronized(lock) {
			   this.arraylist.remove(index);
	        }
		}
		
		/**
		 *  get by index
		 * @param index
		 * @return String at the index 
		 */
		public String get(int index){
			String s;
		    synchronized(lock) {
			   s = this.arraylist.get(index);
	        }
		    return s;
		}
		
		public boolean isEmpty(){
			boolean b;
			synchronized(lock) {
				 b = this.arraylist.isEmpty();
			}
			
			return b;
		}
	}
}
