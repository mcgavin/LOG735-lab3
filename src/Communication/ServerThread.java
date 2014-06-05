package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import Banque.Banque;

public class ServerThread extends Thread{

	private int port;
	private ServerSocket serverSocket;
	private Socket connection;
	private Banque banque;
	
	public ServerThread(Socket clientSocket){
		this.connection = clientSocket;
	}
	
	public ServerThread (int port, Banque banque) throws IOException
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
			Communicator communicator = new Communicator(clientSocket, banque);
			communicator.start();
			banque.addSurccusale(communicator);
		}
	}
}
