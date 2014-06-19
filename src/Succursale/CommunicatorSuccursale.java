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
	
	public CommunicatorSuccursale(Socket clientSocket, Succursale succursale, int succID){
		
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
	
		
		try {
			while(true){
				String message = (String) ois.readObject();
				if(message.startsWith("banque:")){
					System.out.println("allo banque");					
					succursale.setSuccursaleId(Integer.parseInt(message.substring(message.indexOf(":")+1)));
					System.out.println("mon ID est "+ succursale.getSuccursaleId());
					//ois.clear();
					//succursale.((Integer) ois.readObject());
					//Envoit des info à la banque
					oos.writeObject(succursale.getTotal());
					oos.writeObject(succursale.getPort());
					while(true){
						System.out.println(ois.readObject());
						message = (String) ois.readObject();
						System.out.println(message);
						if(message.startsWith("Liste banque :")){
							
							int id = Integer.parseInt((message.substring(message.indexOf(":")+1, message.indexOf("-"))));
							int port = Integer.parseInt((message.substring(message.indexOf("-")+1)));
							System.out.println("avant");
							Socket s = new Socket("127.0.0.1", port);
							/*CommunicatorSuccursale communicatorSucc = new CommunicatorSuccursale(s,succursale,id);
							System.out.println("apres");
							succursale.addSurccusale(communicatorSucc);*/
							
							//communicatorSucc.start();
						}
					}
				}else{
					
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
