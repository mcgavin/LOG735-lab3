package Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Banque.Banque;

public class Communicator extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public Communicator(Socket clientSocket, Banque banque){
		
		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run(){
		//read and write stuff here ?
		System.out.println("test2");
	}
}
