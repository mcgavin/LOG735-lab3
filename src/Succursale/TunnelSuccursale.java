package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TunnelSuccursale extends Thread{
	
	private socketWriter outputTread;
	private socketListener inputTread;
	
//	private ArrayList<String> writeList;
	private syncedWriteList writeList;
	//instance of the initiator
	private Succursale succursaleLocal;
	private int succID;
	private boolean record;
	
	public TunnelSuccursale(Socket clientSocket, Succursale succursaleLocal){
		
		try {
			this.succursaleLocal = succursaleLocal;
			setRecord(false);		
			writeList = new syncedWriteList();
			
			outputTread = new socketWriter(clientSocket,writeList );
			inputTread = new socketListener(clientSocket,this );
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public TunnelSuccursale(Socket clientSocket, Succursale succursaleLocal, int succID){
		
		try {
			this.succursaleLocal = succursaleLocal;
					
			writeList = new syncedWriteList();
			
			outputTread = new socketWriter(clientSocket,writeList );
			inputTread = new socketListener(clientSocket,this );
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void run(){
		
		new Thread(outputTread).start();
		new Thread(inputTread).start();
		
		//if succID if null or zero it mean that another succursale initiate the connection and we need is ID
		if(succID == 0 ){
			writeList.add("getID");
		}

	}
	
	public void envoieArgent(int argent) {
		
		writeList.add("transfer:"+argent);
			
	}

	public void recoisArgent(int argent){
		if(isRecord()){
			System.out.println("incremente canal");
			chandyMessage("transfert-"+argent+"-"+succID);
		}
		System.out.println("recois de "+argent+ "| "+this.getSuccID() +" -> "+ succursaleLocal.getSuccursaleId());
		succursaleLocal.addArgent(argent,this.getSuccID() );
		System.out.println("Solde de "+succursaleLocal.getSuccursaleId()+" : "+succursaleLocal.getTotal());
		
	}
	
	public void setSuccID(int succID) {
		this.succID = succID;
		System.out.println("la banque "+succursaleLocal.getSuccursaleId()+" est connecte a : "+this.getSuccID());
	}

	public int getSuccID() {
		return succID;
	}
	
	public void sendSuccID() {
		writeList.add("succID:"+succursaleLocal.getSuccursaleId());
	}
	
	public void sendMessage(String message){
		writeList.add(message);
	}
	
	public void chandyMessage(String message){
		
		this.succursaleLocal.notifyChandyGestionnaire(message+"-"+succID);
	}

	public boolean isRecord() {
		return record;
	}

	public void setRecord(boolean record) {
		this.record = record;
	}
	
	
}

	/**
	 * socketWriter
	 * 
	 * classe runnable pour ecrire dans le socket
	 * 
	 */
	class socketWriter implements Runnable{
		
		private ObjectOutputStream oos;
		private syncedWriteList stringToWrite;
		
		public socketWriter(Socket clientSocket, syncedWriteList stringToWrite){
			try {
				oos = new ObjectOutputStream(clientSocket.getOutputStream());
				this.stringToWrite = stringToWrite;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			//write whats in the list
			while(true){
				
				if (!stringToWrite.isEmpty()){
					try {
						if(stringToWrite.get(0).contains("transfert")){
							Thread.sleep(5000);
						}
						oos.writeObject(stringToWrite.get(0));
						stringToWrite.remove(0);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * socketWriter
	 * 
	 * classe runnable pour ecouter le socket et declancher des evenements selon le message
	 * 
	 * @author AJ60940
	 *
	 */
	class socketListener implements Runnable{
		
		private ObjectInputStream ois;
		private TunnelSuccursale tunnel;
		
		public socketListener(Socket clientSocket,TunnelSuccursale tunnel ){
			try {
				this.tunnel =  tunnel;
				ois = new ObjectInputStream(clientSocket.getInputStream());

				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			
			//Ecoute et traite les evenements
			while(true){
				try {
					String event = ois.readObject().toString();
					
					if (event.startsWith("chandy")){
						String[] message = event.split(":");
						
						tunnel.chandyMessage(message[1]);
						
					}else if(event.startsWith("transfer:")){
						//money transfer
						String[] money = event.split(":");
						
						tunnel.recoisArgent(Integer.parseInt(money[1]));
						
					}else if(event.startsWith("getID")){
						tunnel.sendSuccID();
						
					}else if(event.startsWith("succID:")){
						String[] succID = event.split(":");
						tunnel.setSuccID(Integer.parseInt(succID[1]));
						System.out.println("connecting succID is  : "+Integer.parseInt(succID[1]));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
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
	
