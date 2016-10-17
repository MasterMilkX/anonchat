/* 
 *	A generic chatbot - based on Pikachat
 *	Program by Milk
 *	Version 1.0
*/ 

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.awt.Desktop;

public class Anonchat{

	public static File logtxt;
	public static Desktop desktop = Desktop.getDesktop();
	public static FileWriter fw;
	public static BufferedWriter bw;
	public static String anonUser;
	public static String[] anonPhrases;
	public static String color;

	//make the output file
	public static void startUp(String aUser){
		try{
			//access the log files
			logtxt = new File("log.txt");
			if(!logtxt.exists())
				logtxt.createNewFile();
			fw = new FileWriter(logtxt, true);
	   		bw = new BufferedWriter(fw);

	   		//get the anon user
	   		anonUser = aUser;
	   		importProfile(anonUser);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	

	public Anonchat(){}

	public static void main(String[] args){
		//setup the convo
		startUp(args[0]);
		Scanner in = new Scanner(System.in);
		System.out.println("\u001B[31m" + "Say hi to " + anonUser +"... [type Bye to finish]" + "\u001B[0m");
		exportConvo("-------------" + anonUser +"-------------");

		//first line
		System.out.print("User >     ");
		String user = in.nextLine();
		exportConvo("User >     " + user);

		//while conversation is still going
		while(!user.toUpperCase().equals("BYE")){
			//get pikachu's response
			int randomTimes = 1 + (int)(Math.random() * 4);
			String yo = "";
			for(int a = 0; a < randomTimes;a++){
				yo += Anonchat.chat() + " ";
			}
			yo = yo.trim();
			String punc = newPunctuation(user);
			System.out.println("\u001B[33m" + anonUser + " >  " + yo + punc + "\u001B[0m");
			exportConvo(anonUser + " >  " + yo + punc);

			//get user's input
			System.out.print("User >     ");
			user = in.nextLine();
			exportConvo("User >     " + user);
		}
		try{bw.close();}catch(IOException e){e.printStackTrace();}
	}

	//decide for pikachu what to say
	public static String chat(){
		if(anonPhrases != null){
			int randomNum = (int)(Math.random() * anonPhrases.length); 
			return anonPhrases[randomNum];
		}
		else{
			throw new NullPointerException("ERROR: No anon phrases!");
		}
	}

	public static void importProfile(String profile){
		File anonTxt = new File(profile + ".txt");
		if(!anonTxt.exists())
			throw new IllegalArgumentException("ERROR: No such user!");

		try{
			Scanner newAnon = new Scanner(anonTxt);				//read the text file
			String line = newAnon.nextLine();					//get the first and only line
			anonPhrases = line.split(" : ");
			newAnon.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//randomize the punctation ending
	public static String newPunctuation(String userIn){
		int a = (int)(Math.random() * 4);
		if(a == 0)
			return ".";
		else if(a == 1)
			return "?";
		else if(a == 2)
			return "!";
		else
			return "...";
	}

	//export each line of conversation to a log
	public static void exportConvo(String line){
		try{
			bw.write(line);
			bw.newLine();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}	