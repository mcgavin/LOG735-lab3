package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CommunicatorSuccursaleIn extends Thread {

	private ObjectInputStream ois;
	private Succursale succursale;
	
	public CommunicatorSuccursaleIn(Socket clientSocket, Succursale succursale){
		
		try {
			this.succursale = succursale;
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
						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
