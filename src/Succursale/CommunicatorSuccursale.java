package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CommunicatorSuccursale extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Succursale succursale;
	private int succID;
	
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
			//Envoit des info à la banque
			oos.writeObject(succursale.getTotal());
			oos.writeObject(succursale.getPort());
			while(true){
				System.out.println(ois.readObject());
				String message = (String) ois.readObject();
				System.out.println(message);
				if(message.startsWith("Liste banque :")){
					
					int id = Integer.parseInt((message.substring(message.indexOf(":")+1, message.indexOf("-"))));
					int port = Integer.parseInt((message.substring(message.indexOf("-")+1)));
					Socket s = new Socket("127.0.0.1", port);
					
					CommunicatorSuccursale communicatorSucc = new CommunicatorSuccursale(s,succursale);
					succursale.addSurccusale(communicatorSucc);
					communicatorSucc.start();
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
