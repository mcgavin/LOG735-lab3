package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class TunnelBanque extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Succursale succursaleLocal;
	private int succID;
	
	public TunnelBanque(Socket clientSocket, Succursale succursaleLocal){
		
		try {
			this.succursaleLocal = succursaleLocal;
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			
			ois = new ObjectInputStream(clientSocket.getInputStream());
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
			for(int i=0;i<listSucc.size();i++){
				//System.out.println("la succursale "+succursaleLocal.getSuccursaleId()+" est connecte a :"+listSucc.get(i).substring(0,listSucc.get(i).indexOf("-")));
				Socket s = new Socket("127.0.0.1", Integer.parseInt((listSucc.get(i).substring(listSucc.get(i).indexOf("-")+1))));
				TunnelSuccursale tunnelSuccursale = new TunnelSuccursale(s,succursaleLocal);
				tunnelSuccursale.setSuccID( Integer.parseInt(listSucc.get(i).substring(0,listSucc.get(i).indexOf("-"))));
				succursaleLocal.addSurccusale(tunnelSuccursale);
				tunnelSuccursale.start();
				//tunnelSuccursale.sendSuccID();
			}
			while(true){
				/*System.out.println(ois.readObject());
				String message = (String) ois.readObject();
				System.out.println(message);
				if(message.startsWith("Liste banque :")){
					
					int id = Integer.parseInt((message.substring(message.indexOf(":")+1, message.indexOf("-"))));
					int port = Integer.parseInt((message.substring(message.indexOf("-")+1)));
					Socket s = new Socket("127.0.0.1", port);
					
//					TunnelSuccursale tunnelSuccursale = new TunnelSuccursale(s,succursaleLocal);
//					succursaleLocal.addSurccusale(tunnelSuccursale);
//					tunnelSuccursale.start();
				}*/
			}
			
			//stuff pour succ
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void envoieArgent(int argent){
		System.out.println();
	}

	public void setSuccID(int succID) {
		this.succID = succID;
	}

	public int getSuccID() {
		return succID;
	}
}
