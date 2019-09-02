package com.arjav.bank;

public class Account {
	
	// Account data type to be used for all the the accounts made
	
    String name ; // to store the name
    long balance ; // to store the balance in the account
    int pin, accountNumber; // to store the PIN and the account number respectively
    
    // constructor to inititialize all the variables
    public Account(String name, long balance, int pin, int accountNumber) {
        this.name = name;
        this.balance = balance;
        this.pin = pin;
        this.accountNumber = accountNumber;
    }
    
    // setter for the name 
    public void setName(String name) {
    	this.name = name;
    }
    
    //setter for the PIN
    public void setPin(int pin) {
    	this.pin = pin;
    }
    
    // setter for the balance
    public void setBalance(long balance) {
    	this.balance = balance;
    }
    
    // getter for the balance
    public long getBalance() {
    	return balance;
    }

    // getter for the name
	public String getName() {
		return name;
	}

	// getter for the PIN
	public int getPin() {
		return pin;
	}

	// getter for the account number
	public int getAccountNumber() {
		return accountNumber;
	}
}
