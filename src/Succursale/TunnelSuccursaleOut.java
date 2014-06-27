package Succursale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TunnelSuccursaleOut extends Thread{

	private ObjectOutputStream oos;
	private Succursale succursaleLocal;
	private int succID;
	
	public TunnelSuccursaleOut(Socket clientSocket, Succursale succursaleLocal){
		
		try {
			this.succursaleLocal = succursaleLocal;
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run(){
		
		try{
			while(true){
				
				if(true){
					
					
				}else{}
			}
			
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

