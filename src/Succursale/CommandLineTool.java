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
		//  prompt the user to enter their name
		

		//  open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String command = null;

		//  read the username from the command-line; need to use try/catch with the
		//  readLine() method
		while(true){
			try {
				System.out.println("Enter your command: ");
				command = br.readLine(); 
				String[] commandArg = command.split(" ");
				
				if(commandArg[0].equals("printID")){
					System.out.println("My id is : "+succursale.getSuccursaleId());
				}else if(commandArg[0].equals("envoie")){
					
					succursale.envoieArgent(Integer.parseInt(commandArg[1]), Integer.parseInt(commandArg[2]));
				}
				//interprete command
				System.out.println("the command is : "+command);
				//sucursalle.runcommand();

			} catch (IOException ioe) {
				System.out.println("IO error trying to read your command!");
			}
		}
	}
}
