package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TunnelSuccursale extends Thread{

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Succursale succursaleLocal;
	private int succID;
	
	public TunnelSuccursale(Socket clientSocket, Succursale succursaleLocal){
		
		try {
			this.succursaleLocal = succursaleLocal;
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run(){
		
		try{
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void envoieArgent(int argent){
		System.out.println();
	}

	public void setSuccID(int succID) {
		this.succID = succID;
	}

	public int getSuccID() {
		return succID;
	}
}
}
