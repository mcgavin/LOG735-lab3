package Banque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerThreadBanque extends Thread{

	private int port;
	private ServerSocket serverSocket;
	private Socket connection;
	private Banque banque;
	private int succursaleUniqueID = 0;
	
	public ServerThreadBanque(Socket clientSocket){
		this.connection = clientSocket;
	}
	
	public ServerThreadBanque (int port, Banque banque) throws IOException
	{
		this.port = port;
		this.banque = banque;
	}
	
	public void run() {
		try { 
			serverSocket = new ServerSocket(port); 
        } 
		catch (IOException e) 
        { 
			System.err.println("On ne peut pas �couter au  port: " + Integer.toString(port) + "."); 
			System.exit(1); 
        }
		
		while(true) {
			Socket clientSocket = null; 
			System.out.println ("Le serveur " + port + " est en marche, Attente de la connexion.....");

			
			try { 
				clientSocket = serverSocket.accept();
			} 
			catch (IOException e) 
		    { 
				System.err.println("Accept de " + port + " a echouer."); 
				System.exit(1); 
		    } 
			
			
			//event but for client id
			// EXIGENCE BANQUE - 01 et BANQUE - 06 (unique ID)
			CommunicatorBanque communicator = new CommunicatorBanque(clientSocket, banque, getSuccursaleUniqueID());
			communicator.start();
			banque.addSurccusale(communicator);
		}
	}
	
	private synchronized int getSuccursaleUniqueID(){
		return ++succursaleUniqueID;
	}
}
