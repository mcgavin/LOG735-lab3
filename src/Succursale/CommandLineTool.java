package Succursale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineTool extends Thread {

	private Succursale succursale;
	
	public CommandLineTool(Succursale succursale){
		this.succursale = succursale;
		
	}
	
	public void run(){

		try {
			//this is for console output
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//  open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String command = null;

		//  read the username from the command-line; need to use try/catch with the
		//  readLine() method
		while(true){
			try {
				System.out.print("Enter your command : ");
				command = br.readLine(); 
				String[] commandArg = command.split(" ");
				
				if(commandArg[0].equals("printID")){
					System.out.println("\nMy id is : "+succursale.getSuccursaleId()+"\n");
					
				}else if(commandArg[0].equals("printSuccList")){
					
					System.out.println(succursale.printAllTunnel());
					
				}else if(commandArg[0].equals("envoie")){
					
					if (succursale.envoieArgent(Integer.parseInt(commandArg[1]), Integer.parseInt(commandArg[2]))){
						System.out.println("transfer effectuer");;
					}
					
				}else if(commandArg[0].equals("help")){
					printHelp();
				}else if(commandArg[0].equals("printMontant")){
					
					System.out.println(succursale.getTotal());
					
				}else{
					System.out.println("erreur commande Inconnue");
					printHelp();
				}
				//interprete command
//				System.out.println("the command is : "+command);
				//sucursalle.runcommand();

			} catch (IOException ioe) {
				System.out.println("IO error trying to read your command!");
			}
		}
	}
	
	public void printHelp(){
		System.out.println("Commande de la Succursale\n" +
				"printID                    : print le Id de la succursale presente\n" +
				"printSuccList              : print toute les connections vers les autres succursales\n" +
				"envoie %succ% %montant%    : envoie le %montant%( un entier ) a la succursale %succ% (entier )\n" +
				"printMontant               : print le montant de la succursale");
	}
}
