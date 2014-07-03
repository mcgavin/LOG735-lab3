package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class TunnelBanque extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ArrayList<String> listeMessage;
	
	private int total=0;
	private boolean totalLock = true;
	
	
	private Succursale succursaleLocal;
	
	private int succID;
	
	public TunnelBanque(Socket clientSocket, Succursale succursaleLocal){
		
		try {
			this.succursaleLocal = succursaleLocal;
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
			
			listeMessage = new ArrayList<String>();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void run(){
		//read and write stuff here ?
		
		//stuff pour la banque
		System.out.println("Connection a la banque");
		try {
			
			//la banque nous envoie notre ID
			succursaleLocal.setSuccursaleId((Integer) ois.readObject());
			System.out.println("mon ID est "+ succursaleLocal.getSuccursaleId());
			
			
			//ois.clear();
			//succursale.((Integer) ois.readObject());
			
			//Envoit des info � la banque
			oos.writeObject(succursaleLocal.getTotal());
			oos.writeObject(succursaleLocal.getPort());
			
			//la banque nous envoie la liste de succursale
			//TODO RECEIVE LA LISTE de succursale (ARRAYLIST)
			//Creer un tunnel vers toute les succursales de la liste
			//communiquer le ID
			//ajouter toute les succursales a la liste de succursale de LocalSuccusale ( succursaleLocal.addSucc())
			ArrayList<String> listSucc = (ArrayList<String>) ois.readObject();
			System.out.println("liste des succursale" );
			
			for (String succString : listSucc) {
				
				// string arg is [0] = succID |[1] = IPaddress| [2]= port
				String[] stringArg = succString.split("-");
							
				Socket socket = new Socket(stringArg[1],Integer.parseInt(stringArg[2]));
				TunnelSuccursale tunnelSuccursale = new TunnelSuccursale(socket,succursaleLocal,Integer.parseInt(stringArg[0]));
				
    			succursaleLocal.addSurccusale(tunnelSuccursale);
				tunnelSuccursale.start();
			}
			
			while(true){
				
				if(!(listeMessage.isEmpty()) ){
					oos.writeObject(listeMessage.get(0));
					listeMessage.remove(0);
					total = (int)ois.readObject();
					totalLock = false;
				}
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getBanqueTotal() {
		
		listeMessage.add("requeteTotal");
		
		while (totalLock){
			//wait for total update
		}
		totalLock = true;
		
		return total;
		
	}
	
	
	public void setSuccID(int succID) {
		this.succID = succID;
	}

	public int getSuccID() {
		return succID;
	}
}
