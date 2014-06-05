package Banque;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Succursale.Succursale;

import Communication.Communicator;
import Communication.ServerThread;


public class Banque extends Thread{

	private ServerThread serverThread;
	private int total;
	private static List<Communicator> listSuccursale;
	private static int currentID = 0;
	public static synchronized int getUniqueID() {
		currentID++;
		return currentID;
    }
	public static synchronized void addSurccusale(Communicator lienSucc) {
		listSuccursale.add(lienSucc);
		//send list succursale a tous le monde ? ou a juste 1 succur ?
		//update total ?
		//give unique id ?
    }
	
	
	public static void main(String[] args) throws IOException {
		Banque banque = new Banque(12045);
		banque.start();
	}
	
	public Banque(int port) throws IOException{
		serverThread = new ServerThread(port, this);
		serverThread.start();
	}
	
	public void run() {
		System.out.println("test1");
	}
}
