package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CommunicatorSuccursaleOut extends Thread {

	private ObjectOutputStream oos;
	private Succursale succursale;
	
	public CommunicatorSuccursaleOut(Socket clientSocket, Succursale succursale){
		
		try {
			this.succursale = succursale;
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
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
			
			//sending montant
			oos.writeObject(succursale.getTotal());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
