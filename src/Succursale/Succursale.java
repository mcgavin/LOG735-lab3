package Succursale;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Banque.Banque;
import Banque.CommunicatorBanque;
import Banque.ServerThreadBanque;

public class Succursale extends Thread {

	private ServerThreadSuccursale serverThread;
	private int total;
	private int port;
	private static List<TunnelSuccursale> listSuccursale;
	
	private TunnelBanque tunnelBanque ;
	
	private int succursaleId = 0;

	public static synchronized void addSurccusale(TunnelSuccursale tunnelSuccursale) {
		listSuccursale.add(tunnelSuccursale);
    }
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("**** SUCCURSALLE ****");
		Succursale succursale = new Succursale(((int)(Math.random() * (8)) + 2)*1000);
		
		//run is empty
//		succursale.start();
		
	}
	
	/**
	 * 
	 * @param port
	 * @param montant
	 * @throws IOException
	 */
	public Succursale(int montant) throws IOException{
		System.out.println("solde : "+montant);
		listSuccursale = new ArrayList<TunnelSuccursale>();
		
		this.setTotal(montant);
		//intervale random pour port entre 5000 et 10000
		this.port = (int) (5000 + (Math.random()* (10000-5000)));
		
		String banqueIP = "127.0.0.1";
		int BanquePort = 12045;
		Socket s = new Socket(banqueIP, BanquePort);
		
		
		//connection a la banque
		tunnelBanque = new TunnelBanque(s,this);
		tunnelBanque.start();
		
		//thread qui ecoute les connection entrante
		serverThread = new ServerThreadSuccursale(port, this);
		serverThread.start();
		
		//thread pour ecrire des commande dans le terminal
		CommandLineTool commandLine = new CommandLineTool(this);
		commandLine.start();
		
		//thread de transfer d'argent automatique
		TransfertAuto transfertAuto = new TransfertAuto(this);
		transfertAuto.start();
		
		
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
	
	public void addArgent(int argent){
		this.total += argent;
	}
	
	public boolean enleveArgent(int argent){
		boolean b = false;
		
		if (total-argent>0){
			this.total -= argent;
			b =true;
		}
		return b;
	}
	
	
	public int getPort() {
		return this.port;
	}
	
	public boolean envoieArgent(int succId, int argent) {
		
		boolean transfer = false;
		
		for	(TunnelSuccursale tunnel :  listSuccursale){
			if(tunnel.getSuccID() == succId){
				if(argent<=this.getTotal()){
					transfer = true;
					System.out.println("retrait de "+argent+ " dans "+succursaleId);
					total -= argent;
					
					System.out.println("envoie de "+argent+ " de "+succursaleId+" vers "+succId);
					tunnel.envoieArgent(argent);
					System.out.println("solde de "+succursaleId+" : "+total);
				}else{
					System.out.println("Fond insuffisant dans "+succursaleId+",solde: "+total);
					transfer = false;
				}
			}
		}
		
		return transfer;
	}
	
	public String printAllTunnel(){
		
		String output = "";
		for (TunnelSuccursale tunnel : listSuccursale) {
			
			//TODO add money to tunnel print
			output += "\n"+ this.getSuccursaleId() + " -> "+tunnel.getSuccID()+" | money : ";
			
		}
		return output;
	}
	
	public List<TunnelSuccursale> getListSuccursale(){
		return listSuccursale;
		
	}
}





