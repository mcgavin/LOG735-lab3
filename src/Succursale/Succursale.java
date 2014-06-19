package Succursale;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Banque.Banque;
import Banque.CommunicatorBanque;
import Banque.ServerThreadBanque;

public class Succursale extends Thread {

	private ServerThreadSuccursale serverThread;
	private int total;
	private int port;
	private static List<TunnelBanque> listSuccursale;
	
	private TunnelBanque communicatorBanque ;
	
	private int succursaleId = 0;

	public static synchronized void addSurccusale(TunnelBanque lienSucc) {
		listSuccursale.add(lienSucc);
		//comment on gere la liste des succ POUR les succ ? difusion de la banque ou autre ????
		//send list succursale a tous le monde ? ou a juste 1 succur ?
		//update total ?
		//give unique id ?
    }
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("**** SUCCURSALLE ****");
		Succursale succursale = new Succursale(1000);
		succursale.start();
	}
	
	/**
	 * 
	 * @param port
	 * @param montant
	 * @throws IOException
	 */
	public Succursale(int montant) throws IOException{
		
		listSuccursale = new ArrayList<TunnelBanque>();
		
		this.setTotal(montant);
		//intervale random pour port entre 5000 et 10000
		this.port = (int) (5000 + (Math.random()* (10000-5000)));
		
		String banqueIP = "127.0.0.1";
		int BanquePort = 12045;
		Socket s = new Socket(banqueIP, BanquePort);
		
		communicatorBanque = new TunnelBanque(s,this);
		communicatorBanque.start();
		
		CommandLineTool commandLine = new CommandLineTool(this);
		commandLine.start();
		
		serverThread = new ServerThreadSuccursale(port, this);
		serverThread.start();
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
		return this.total;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void envoieArgent(int succId, int argent){
		
		for	(TunnelBanque tunnel :  listSuccursale){
			if(tunnel.getSuccID() == succId){
				tunnel.envoieArgent(argent);
			}
			
		}
	}
}
