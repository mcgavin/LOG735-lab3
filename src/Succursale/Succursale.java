package Succursale;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import Banque.Banque;
import Banque.CommunicatorBanque;
import Banque.ServerThreadBanque;

public class Succursale extends Thread {

	private ServerThreadSuccursale serverThread;
	private int total;
	//private int port = 12046;
	private static List<CommunicatorSuccursale> listSuccursale;
	
	private CommunicatorSuccursale communicatorBanque ;
	
	private int succursaleId = 0;

	public static synchronized void addSurccusale(CommunicatorSuccursale lienSucc) {
		listSuccursale.add(lienSucc);
		//comment on gere la liste des succ POUR les succ ? difusion de la banque ou autre ????
		//send list succursale a tous le monde ? ou a juste 1 succur ?
		//update total ?
		//give unique id ?
    }
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("**** SUCCURSALLE ****");
		Succursale succursale = new Succursale(12046, 1000);
		succursale.start();
	}
	
	/**
	 * 
	 * @param port
	 * @param montant
	 * @throws IOException
	 */
	public Succursale(int port, int montant) throws IOException{
		this.setTotal(montant);
		serverThread = new ServerThreadSuccursale(port, this);
		serverThread.start();
		
		String banqueIP = "127.0.0.1";
		int BanquePort = 12045;
		Socket s = new Socket(banqueIP, BanquePort);
		
		communicatorBanque = new CommunicatorSuccursale(s,this);
		communicatorBanque.start();
		
	}
	
	
	
	public void run() {
	}


	public void setSuccursaleId(int succursaleId) {
		this.succursaleId = succursaleId;
	}


	public int getSuccursaleId() {
		return succursaleId;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getTotal() {
		return total;
	}
	
}
