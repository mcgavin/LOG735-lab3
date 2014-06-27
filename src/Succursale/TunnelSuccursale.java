package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TunnelSuccursale extends Thread{

//	private ObjectOutputStream oos;
//	private ObjectInputStream ois;
	
	private socketWriter outputTread;
	private socketListener inputTread;
	
	private ArrayList<String> writeList;
	
	//instance of the initiator
	private Succursale succursaleLocal;
	private int succID;
	
	public TunnelSuccursale(Socket clientSocket, Succursale succursaleLocal){
		
		try {
			this.succursaleLocal = succursaleLocal;
			
			writeList = new ArrayList<String>();
			
			outputTread = new socketWriter(clientSocket,writeList );
			inputTread = new socketListener(clientSocket,this );
			
//			oos = new ObjectOutputStream(clientSocket.getOutputStream());
//			ois = new ObjectInputStream(clientSocket.getInputStream());
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
	
	public void envoieArgent(int argent) throws InterruptedException{
		
		this.sleep(5000);
		writeList.add("transfer:"+argent);
			
	}

	public void recoisArgent(int argent){
		System.out.println("TODO Implement it.. : recois de "+argent+ "| "+this.getSuccID() +" -> "+ succursaleLocal.getSuccursaleId());
		succursaleLocal.addArgent(argent);
		System.out.println("Solde de"+succursaleLocal.getSuccursaleId()+" : "+succursaleLocal.getTotal());
		
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
	
}

	class socketWriter implements Runnable{
		
		private ObjectOutputStream oos;
		private ArrayList<String> stringToWrite;
		
		public socketWriter(Socket clientSocket, ArrayList<String> stringToWrite){
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
						System.out.println(stringToWrite.get(0));
						oos.writeObject(stringToWrite.get(0));
						stringToWrite.remove(0);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	
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
						//execute chandy
					}else if(event.startsWith("transfer:")){
						//money transfer
						String[] money = event.split(":");
						
//						System.out.println("receiving : "+ money[1]);
						tunnel.recoisArgent(Integer.parseInt(money[1]));
						
					}else if(event.startsWith("getID")){
						tunnel.sendSuccID();
						
					}else if(event.startsWith("succID:")){
						String[] succID = event.split(":");
						tunnel.setSuccID(Integer.parseInt(succID[1]));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
