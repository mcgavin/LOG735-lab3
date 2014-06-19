package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CommunicatorSuccursale extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Succursale succursale;
	
	public CommunicatorSuccursale(Socket clientSocket, Succursale succursale){
		
		try {
			this.succursale = succursale;
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
//	/*
//	 * Constructeur pour la cummunication avec la banque
//	 */
//	public CommunicatorSuccursale(Socket clientSocket){
//		
//		try {
//			oos = new ObjectOutputStream(clientSocket.getOutputStream());
//			ois = new ObjectInputStream(clientSocket.getInputStream());
//		}
//		catch(IOException ioe) {
//			ioe.printStackTrace();
//		}
//	}
	
	public void run(){
		//read and write stuff here ?
	
		System.out.println("allo banque");
		try {
			
			
			succursale.setSuccursaleId((Integer) ois.readObject());
			System.out.println("mon ID est "+ succursale.getSuccursaleId());
			//ois.clear();
			//succursale.((Integer) ois.readObject());
			//sending montant
			oos.writeObject(succursale.getTotal());
			while(true){
				System.out.println(ois.readObject());
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
