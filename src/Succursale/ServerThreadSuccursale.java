package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerThreadSuccursale extends Thread{

	private int port;
	private ServerSocket serverSocket;
	private Socket connection;
	private Succursale succursale;
	
	public ServerThreadSuccursale(Socket clientSocket){
		this.connection = clientSocket;
	}
	
	public ServerThreadSuccursale (int port, Succursale succursale) throws IOException
	{
		this.port = port;
		this.succursale = succursale;
	}
	
	public void run() {
		try { 
			serverSocket = new ServerSocket(port); 
        } 
		catch (IOException e) 
        { 
			System.err.println("On ne peut pas écouter au  port: " + Integer.toString(port) + "."); 
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
				System.err.println("Accept de " + port + " a échoué."); 
				System.exit(1); 
		    } 
			
			
			//event but for client id
			CommunicatorSuccursale communicator = new CommunicatorSuccursale(clientSocket, succursale);
			communicator.start();
			succursale.addSurccusale(communicator);
		}
	}
}
