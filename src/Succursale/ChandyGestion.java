package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChandyGestion extends Thread {

	private Succursale succursale;
	private boolean chandy;
	private syncedWriteList chandyEvent;
	
	
	public ChandyGestion(Succursale succursale){
		this.succursale = succursale;
		this.chandy = true;
		this.chandyEvent = new syncedWriteList();
		
	}
	
	public void endChandy(){

		succursale.envoieChandyMessageToAll("end-"+succursale.getSuccursaleId() );

		chandy=false;
	}
	
	
	public void run(){

		while(true){
			try {
				if (! (chandyEvent.isEmpty()) ){
					
					String[] chandyString = chandyEvent.get(0).split("-");
					
					if(chandyString[0].equals("M")){
						int sid = Integer.parseInt(chandyString[1]);
						
						
						//Etat global d'une succursale distante 
					}else if(chandyString[0].equals("etat")){
						
						int succid = Integer.parseInt(chandyString[1]);
						int montant = Integer.parseInt(chandyString[2]);
						
						
						//list of idFrom.montant
						String canaux[] = chandyString[3].split("|");
						
						for (String canal : canaux) {
							String[] canalInfo =  canal.split(".");
							//canal canalInfo[0] -> succid = canalInfo[1]
							//      succdistant vers succlocal : montant
						}
						
						
					}else if(chandyString[0].equals("end")){
						int montant = Integer.parseInt(chandyString[2]);
					}
					
					chandyEvent.remove(0);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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