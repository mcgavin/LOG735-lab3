package Banque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class CommunicatorBanque extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int succursaleID;
	private Banque banque;
	private int succPort;

	public CommunicatorBanque(Socket clientSocket, Banque banque, int succursaleID){

		try {
			this.succursaleID =succursaleID;
			this.banque = banque;
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void run(){
		try {
			
		
			//read and write stuff here 
			
			//send ID
			System.out.println("sending ID");

			oos.writeObject(succursaleID);
			
			//recevoir montant
			System.out.println("recevoir montant");
			int montant  = (Integer) ois.readObject();
			succPort  = (Integer) ois.readObject();
			
			banque.addTotal(montant);
			
			System.out.println("j<ai recu " + montant);
			
			System.out.println(succPort+"-succursaleID");
			oos.writeObject("Ton id est : "+succursaleID);
			ArrayList listSucc = new ArrayList();
			for(int i = 0 ;i<banque.getListSucc().size()-1;i++){
				listSucc.add(banque.getListSucc().get(i).getSuccursaleID()+"-"+banque.getListSucc().get(i).getSuccPort());
			}
			
			System.out.println(succPort+"-liste Succ");
			oos.writeObject(listSucc);
			
			while(true){

				String leMessage;
				leMessage = (String) ois.readObject();
				System.out.println(leMessage);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSuccursaleID(){
		return succursaleID;
	}
	
	public int getSuccPort(){
		return succPort;
	}
}
