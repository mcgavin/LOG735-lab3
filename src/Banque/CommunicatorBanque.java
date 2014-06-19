package Banque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CommunicatorBanque extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int succursaleID;
	private Banque banque;

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
			
			banque.addTotal(montant);
			System.out.println("j<ai recu " + montant);
			
			oos.writeObject("Ton id est : "+succursaleID);
			for(int i = 0 ;i<banque.getListSucc().size();i++){
				int port = 12045;
				port += banque.getListSucc().get(i).getSuccursaleID();
				oos.writeObject("Liste banque : "+banque.getListSucc().get(i).getSuccursaleID()+"-"+port);
				
			}
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
}
