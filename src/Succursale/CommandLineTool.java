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
				
				if(commandArg[0].equals("chandy")){
				
				}else if(commandArg[0].equals("erreur")){
					int argent = Integer.parseInt(commandArg[1]);
					if(this.succursale.enleveArgent(argent)){
						System.out.println("!!!! Erreur introduite !!!!");
					}else{
						System.out.println("erreur en introduisant l'erreur");
					};
					
					
				}else if(commandArg[0].equals("envoie")){
					// EXIGENCE SUCCURSALE-06
					// parametre valider dans envoie argent
					
					try{
						int succID = Integer.parseInt(commandArg[1]);
						int montantArgent = Integer.parseInt(commandArg[2]);
						
						if (succursale.envoieArgent(succID, montantArgent)){
							System.out.println("transfer effectuer");
						}else{
							System.out.println("Erreur de transaction");
						}
						
					}catch(NumberFormatException e ){
						System.out.println("Mauvais argument");
					}
					
				//print Function
				}else if(commandArg[0].equals("print")){
					if(commandArg[1].equals("help")||commandArg[1].equals("aide")){
						printHelp();
						
					}else if(commandArg[1].equals("id") 	|| commandArg[1].equals("ID")){
						System.out.println("\nMy id is : "+succursale.getSuccursaleId()+"\n");
						
					}else if(commandArg[1].equals("montant")|| commandArg[1].equals("argent")){
						System.out.println("total is : "+succursale.getTotal());
						
					}else if(commandArg[1].equals("list") 	|| commandArg[1].equals("tunnel")){
						System.out.println(succursale.printAllTunnel());
					}else{
						printHelp();
					}
			
				}else{
					System.out.println("erreur commande inconnue");
					printHelp();
				}

			} catch (Exception e) {
				System.out.println("error trying to read your command!");
			}
		}
	}
	
	public void printHelp(){
		System.out.println("Commande de la Succursale\n" +
				"~~ COMMANDE ~~\n"+
				"chandy                     : Demarre chandy-lamport\n"+
				"envoie %succ% %montant%    : envoie le %montant% a la succursale %succ% (entier )\n" +
				"erreur %montant%           : introduit un erreur en enlevant le montant a la succursale\n"+
				"\n"+
				"~~ INFORMATION ~~\n"+
				"print help/aide            : print l'aide\n"+
				"print ID/id                : print le Id de la succursale presente\n"+
				"print montant/argent       : print l'argent succursale presente\n"+
				"print list/tunnel          : print toute les connections vers les autres succursales\n" 
				);
	}
}
