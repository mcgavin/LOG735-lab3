package Communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{

	private int port;
	private ServerSocket serverSocket;
	private Socket connection;
	
	public ServerThread(Socket clientSocket){
		this.connection = clientSocket;
	}
	
	public ServerThread (int port) throws IOException
	{
		this.port = port;
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

		Socket clientSocket = null;
		System.out.println ("Le serveur est en marche, Attente de la connexion.....");
		
		//Boucle qui gère la création de thread pour chaques clients qui se connecte
		try { 
			while(true){
				clientSocket = serverSocket.accept();
				Runnable runnable = new ServerThread(clientSocket);
				Thread thread = new Thread(runnable);
				thread.start();
			}
			
        } 
		catch (IOException e) 
        { 
			System.err.println("Accept a échoué."); 
			System.exit(1); 
        } 	
		serverSocket.close(); 
	}
}
