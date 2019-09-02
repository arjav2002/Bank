package com.arjav.bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Handler {
	
    public static Account[] a; // Array to store all the accounts
    
    public String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    // path of the exported jar file(application)
    
    // method to update the file with the accounts stored in the Account array a
    public void updateFile() { 	
    	try {
    		String pathFile = path.replace("Bank.jar", "");
    		// getting the path of the directory of the application
    		
    		File f = new File(pathFile+"Accounts.txt"); 
    		// getting the text file in the same directory as the application
    		
    		BufferedWriter bw = new BufferedWriter(new FileWriter(f, false)); 
    		// creating buffered writer object to write to the file
    		
			for(int i = 0 ; i < a.length; i++) {
				
				// write the information of each account in the following manner:
				// accountNumberName*PIN/balance
				bw.write(a[i].getAccountNumber() + a[i].getName() + "*" + a[i].getPin() + "/" + a[i].getBalance() + "\n");
				bw.flush();
			}
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Handler(String title, int width, int height) {
        Frame frame = new Frame(title, width, height); // creating a new window
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Accounts.txt"))); 
        // inititalizing buffered reader to read from the text file
        
        int acc = 0; // to store the number of accounts
        
        try {
        	// this stores the number of accounts
        	// in the text file in acc
			while(br.readLine() != null) { // while the end of the file has not been reached
				acc++;
			}
		} catch (IOException e1) {
				e1.printStackTrace();
		}
        
        br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Accounts.txt")));
        // initializing br again as it had reached the end of the file
        
        a = new Account[acc];
        
        int ab = 0; // to store the index of the element of the Account array being initialized
        String txt; // to store the current line
        
        try {
        	// while the end of the file has not been reached
			while((txt = br.readLine())!=null) { 
				int star = 0, slash = 0; 
				// to store the indexes of the '*' and '/' characters repectively
				
				// the index of both the characters being calculated and stored here
				for(int i = 0 ; i < txt.length() ; i++) {
					if(txt.charAt(i)=='*') star = i;
					else if(txt.charAt(i)=='/') slash = i;
				}
				
				// to store the name, PIN and balance of the element 
				// of the Account array we are going to initialize
				String name = "", pin = "", balance = "";
				
				/* they are initialized here by the following logic
				all the characters before '*' together make the name and the account number
				all the characters between '*' and '/' make the PIN
				all the character after '/' make the balance*/
				
				for(int a = 0 ; a < star ; a++) name += txt.charAt(a);
				for(int b = star + 1 ; b < slash ; b++) pin += txt.charAt(b);
				for(int c = slash + 1 ; c < txt.length(); c++) balance += txt.charAt(c);
				
				/* right now the value of the variables would be:-
				 * name = accountnumber of the account + Name of the account 
				 * pin = PIN  of the account 
				 * balance = balance of the account */
				
				// the name and account number are separated from each other 
				// and then passed as arguments to the Account constructor
				a[ab] = new Account(name.replace(getNumFromString(name)+"", ""), Long.parseLong(balance), Integer.parseInt(pin), getNumFromString(name));
				//element with the abth index in the Account array is initialized
				
				// both the variables reset
				star = 0;
				slash = 0;
				ab++; // done to initialize the next element in the account array
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
    }
    
    public Handler() {
		//Dummy constructor so that the updateFile method can be called
    	// updateFile method cannot be made static due to getClass() involved in the path variable
	}

    // method to get the largest account number
	public static int getLargestAccountNumber() {
    	int max = 0;
    	for(int i = 0 ; i < a.length ; i++) if(a[i] != null && max < a[i].accountNumber) max = a[i].accountNumber;
    	return max;
    }
    
	/* method to append text to the label
	 * it would be neater and more visually appealing
	 * to use a method for such a task
	 * than simply concatenate */
    public static void addToLabel(String text) {
    	String NewText = Frame.l.getText() + text ;
    	updateLabel(NewText);
    }
    
    // method to check if a certain String contains a number or not
    public static boolean containsDigit(String text) {
    	String t2 = text.replaceAll("[0-9]", "");
    	if(!t2.equals(text)) return true;
    	return false;
    }
    
    // method to check if a certain String containes a special character or not
    public static boolean containsSpecialCharacter(String text) {
    	text = text.replaceAll(" ", "");
    	String t2 = text.replaceAll("[^a-zA-z0-9]", "");
    	if(!t2.equals(text)) return true;
    	return false;
    }
    
    // method to update the label with a specified text
    public static void updateLabel(String text) {
    	// converting normal text to html format and storing it in HTMLtext
    	Frame.HTMLtext = "<html>" + (text.replaceAll("\n", "<br>")).replaceAll("<html>", "");
    	updateLabel();
    }
    
    // method to update the text of the label
    public static void updateLabel() {
    	Frame.l.setText(Frame.HTMLtext);
    }
    
    // method to derive a String in the normal format from a String in the HTML format
    public static String removeHTMl(String text) {
    	return text.replaceAll("<html>", "").replace("<br>", "\n");
    }
    
    // method to get numbers from a specified String
    public static int getNumFromString(String text) {
    	int a = 0;
    	try {
    		a = Integer.parseInt(text.replaceAll("[^0-9]", ""));
    	} catch(NumberFormatException e) {
    		return 0;
    	}
    	return a;
    }
    
    /* method to eliminate an instance of Account from
     * the account array at nullIndex*/
    public static void shiftOneBack(int nullIndex) {
		for(int i = nullIndex ; i < a.length - 1; i++) {
			a[i] = a[i+1];
		}
		a = Arrays.copyOf(a, a.length-1);
    }
    
    // method to get the index of a specified Account object in the Account array
    public static int getIndex(Account acc) {
    	for(int i = 0 ; i < a.length ; i++) {
    		if(acc == a[i]) return i;
    	}
    	return 0;
    }

    // method to get numbers from a specified String in the form of a long
    public static long getLongFromString(String text) {
    	return Long.parseLong(text.replaceAll("[^0-9]", ""));
    }
    
    /* method to check whether the PIN specified is correct for the
     *  Account object with the specified account number */
    public static boolean isCorrect(int a, int pin) {
    	if(Transaction.getAccount(a).pin == pin) {
    		return true;
    	}
    	else return false;
    }
    
    // method to check whether the PIN specified is available for another accoount
    public static boolean pinIsAvailable(int pin) {
    	
    	for(int i = 0 ; i < a.length ; i++) {
    		if(Handler.a[i].getPin()==pin) return false;
    	}
    	return true;
    }
    
    public static void main(String[] args) throws IOException {
        Handler h = new Handler("ATM", 350, 500);
    }
}