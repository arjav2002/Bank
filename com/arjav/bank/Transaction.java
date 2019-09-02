package com.arjav.bank;

import java.util.Arrays;

public class Transaction {
	
	/* method to send a specified amount of money from an Account object with a specified
	 * account number to an Account object with a specified account number */
    public static void sendMoney(int a1, int a2, long balance) {
    	Account aI = getAccount(a1), aII = getAccount(a2);
    	aI.setBalance(aI.getBalance()-balance);
    	aII.setBalance(aII.getBalance()+balance);
        new Handler().updateFile();
    }
    
    // using binary seach to search for and return the account with the specified account number
    public static Account getAccount(int accountNumber) {
        int L = 0, U = Handler.a.length-1;
        while(L<=U) {
        	int M = (U+L)/2;
        	if(accountNumber > Handler.a[M].accountNumber) L = M + 1;
        	else if(accountNumber < Handler.a[M].accountNumber) U = M - 1;
        	else if(accountNumber == Handler.a[M].accountNumber) return Handler.a[M];
        }
        return null;
    }
    
    // method to deposit a specified amount of money to a specified Account object
    public static void deposit(Account a, long balance) {
    	a.setBalance(a.getBalance() + balance);
    }
    
    // method to withdraw a specified amount of money from a specified Account object
    public static void withdraw(Account a, long balance) {
    	a.setBalance(a.getBalance() - balance);
    }
    
    // method to create a new account with a specified name and PIN
    public static void createAccount(String name, int pin) {
    	Handler.a = Arrays.copyOf(Handler.a, Handler.a.length+1); // make the array larger by one element
		Handler.a[Handler.a.length-1] = new Account(name, 0, pin, Handler.getLargestAccountNumber()+1); 
		// creating a new account at the last location of the Accounts array 
    }
}