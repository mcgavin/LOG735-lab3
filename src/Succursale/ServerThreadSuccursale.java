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
	private Succursale succursaleLocal;
	
	public ServerThreadSuccursale(Socket clientSocket){
		this.connection = clientSocket;
	}
	
	public ServerThreadSuccursale (int port, Succursale succursaleLocal) throws IOException
	{
		this.port = port;
		this.succursaleLocal = succursaleLocal;
	}
	
	public void run() {
		try { 
			serverSocket = new ServerSocket(port); 
        } 
		catch (IOException e) 
        { 
			System.err.println("On ne peut pas écouter au port: " + Integer.toString(port) + "."); 
			System.exit(1); 
        }
		System.out.println ("Le serveur est en marche, écoute au port " + port + ", Attente de la connexion.....");
		while(true) {
			Socket clientSocket = null; 
			

			
			try { 
				clientSocket = serverSocket.accept(); 
				System.out.println("Connection réussi, port : " + clientSocket.getPort());
			} 
			catch (IOException e) 
		    { 
				System.err.println("Connection échoué, port : " + clientSocket.getPort()); 
				System.exit(1); 
		    } 
			
			TunnelSuccursale tunnelSuccursale = new TunnelSuccursale(clientSocket,succursaleLocal);
			//System.out.println(2);
			succursaleLocal.addSurccusale(tunnelSuccursale);
			//System.out.println(3);
			tunnelSuccursale.start();
			//System.out.println(4);
			//event but for client id
//			CommunicatorSuccursale communicator = new CommunicatorSuccursale(clientSocket, succursale);
//			communicator.start();
//			succursale.addSurccusale(communicator);
		}
	}
}
