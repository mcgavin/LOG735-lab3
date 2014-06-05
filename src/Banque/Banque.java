package Banque;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Succursale.Succursale;

import Communication.ServerThread;


public class Banque extends Thread{

	private ServerThread serverThread;
	private int total;
	private List<Succursale> listSuccursale;
	private static int currentID = 0;
	public static synchronized int incrementNbReq() {
		currentID++;
		return currentID;
    }
	
	
	public static void main(String[] args) throws IOException {
		Banque banque = new Banque(12045);
		banque.start();
	}
	
	public Banque(int port) throws IOException{
		serverThread = new ServerThread(port);
		serverThread.start();
	}
	
	public void run() {
		System.out.println("test");
	}
}
