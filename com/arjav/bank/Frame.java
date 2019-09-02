package com.arjav.bank;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Frame extends JFrame implements ActionListener{
	
	// lp to store the label and bp to store the buttons
    JPanel lp = new JPanel(), bp = new JPanel();
    
    // text of the label in HTML format as swing components are compatible with it
    static String HTMLtext = "";
    
    // gets the content pane of the window
    Container c = getContentPane();
    
    // label to be used to display messages
    public static JLabel l = new JLabel();
    
    // JButton array of all the buttons
    public JButton[] b = new JButton[12];
    
    // the current state of the application
    State st;
    
    int a1, a2, pin; // variables to store the entered account numbers and a PIN respectively
    long balance; // to store any amount of money entered by the user
    
    // to store any name entered by the user
    String name = "";
    
    // booleans to check whether the user has entered something
    boolean a1E = false, a2E = false, pinE = false, nameE = false, balanceE = false; 
    
    Process p; // process of the application
    
     // acts as the initial text for the label
    String mmText = "Press 1 to send money \n Press 2 to create a new account \n Press 3 to change your name \n Press 4 to change your PIN \n Press 5 to display your balance \n Press 6 to deposit money \n Press 7 to withdraw money \n Press 8 to close your account";
    
     // text field to enter a name/String
    JTextField tf = new JTextField(20);
    
    /* method to bring the booleans back 
     * to their original position
    * called everytime a proceess has completed */
    private void refresh() {
    	a1E = false;
    	a2E = false;
    	p = null;
    	pinE = false;
    	nameE = false;
    	balanceE = false;
    	st = State.end;
    }
    
    // method used to add the text field to the window
    private void addTextField() {
    	System.out.println("Dafuq?");
    	setVisible(false);
    	
		tf.setOpaque(true);
		tf.setBackground(Color.WHITE);
		
		l.setBackground(Color.BLACK);
		
		lp = new JPanel();
		
		remove(bp);
		
		lp.add(tf);
		
		add(lp);
		add(bp);
		
		setVisible(true);
    }
    
    // to create a window and to initialize all the varibles
    public Frame(String title, int width, int height) {
    	
    	//Since the application will be at
    	//it's  initial position
    	st = State.start;
    	
    	// making the window
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(title);
        addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// not using
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// not using
				
			}

			// method called when the program closes
			@Override
			public void windowClosing(WindowEvent e) {
				new Handler().updateFile();
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// not using
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// not using
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// not using
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// not using
				
			}
        	
        });
        tf.addActionListener(this);
        
        // making the label with center alignment and white text and black background
        l.setOpaque(true);
        l.setBackground(Color.BLACK);
        l.setForeground(Color.WHITE);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setPreferredSize(new Dimension((int) (width/2 * 1.9), height/3));
        
     // setting the initial text of the label
        Handler.updateLabel(mmText);
        
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        bp.setLayout(new GridLayout(4, 3));
        
        lp.add(l); 
        
        // loop to initialize all the buttons
        for(int i = 0 ; i < b.length ; i++) { 
            b[i] = new JButton();
            bp.add(b[i]); // adding buttons
            
            b[i].addActionListener(this);
            b[i].setActionCommand(i+1 + ""); 
            /* it is i+1 because the the index of the first button is 0 
            * helps to distinguish between different buttons*/
            
         // setting specific texts for some buttons
            if(i<=8) {
            	if(i==1) b[i].setText("2/up");
            	else if(i==3) b[i].setText("4/left");
            	else if(i==5) b[i].setText("6/up");
            	else if(i==7) b[i].setText("8/down");
            	else b[i].setText(Integer.toString(i+1));
            }
            else if(i==9) b[i].setText("<html>Backspace/ <br> Main Menu/ <br> No");
            else if(i==10) b[i].setText("0");
            else if(i==11)b[i].setText("<html> Continue/ <br> Yes");
            // the JButton class can take HTML text
        }
        
        // setting the preferred size for both of the panels
        lp.setPreferredSize(new Dimension(width, height/3));
        bp.setPreferredSize(new Dimension(width, height/3*2));
        
        // adding both of the panels to the window
        c.add(lp);
        c.add(bp);
        
        pack();
        setVisible(true);
        setSize(width, height);
        setLocationRelativeTo(null);
    }
    
    /* method called whenever a button is pressed 
    due to the implementation of the Action Listener
    interface and addition of this
    class to the buttons as an action listener */
    
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		case "1": // if the button pressed is 1
			
			/* if the user wants to enter 1 and all the numeric information
			 * has not been acquired, then it will 
			 * add '1' to the text of the label */
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("1");
			
			else if(st==State.start) { 				
				// the screen is at main menu and 
				// hence the user wants to perform an action
				
				// prompting the user for initial information
				Handler.updateLabel("  Enter your account number : \n   ");
				st = State.enter;
				p = Process.sendMoney; // they want to send money in this case
			} // same for rest of the buttons
			break;
			
			
		case "2":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE))	Handler.addToLabel("2");
			
			else if(st==State.start) {
				p = Process.createAccount;
				st = State.enter;
				Handler.updateLabel("Enter the PIN you want for your account : \n");
			}
		    break;
		    
		    
		case "3":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("3");
			
			else if(st==State.start) {
				Handler.updateLabel("Enter your account number :\n");
				p = Process.changeName;
				st = State.enter;
			}
			break;
			
			
		case "4":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("4");
			
			else if(st==State.start) {
				Handler.updateLabel("Enter your account number :\n");
				p = Process.changePIN;
				st = State.enter;
			}
		    break;
		    
		    
		case "5":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("5");
			
			else if(st==State.start) {
				Handler.updateLabel("Enter your account number :\n");
				p = Process.displayBalance;
				st = State.enter;
			}
			break;
			
			
		case "6":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("6");
			
			else if(st==State.start) {
				Handler.updateLabel("Enter your account number :\n");
				p = Process.deposit;
				st = State.enter;
			}
		    break;
		    
		    
		case "7":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("7");
			
			else if(st==State.start) {
				Handler.updateLabel("Enter your account number :\n");
				p = Process.withdraw;
				st = State.enter;
			}
			break;
			
			
		case "8":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("8");
			
			else if(st==State.start) {
				Handler.updateLabel("Enter your account numnber :\n");
				p = Process.closeAccount;
				st = State.enter;
			}
		    break;
		    
		    
		case "9":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("9");
			break;
			
		// when the back/space/no button is pressed	
		case "10":
			//has the user entered anything?
			if(Handler.containsDigit(HTMLtext) && st == State.enter) {	
				// if so then delete the last character entered
				HTMLtext = HTMLtext.substring(0, HTMLtext.length()-1);
				Handler.updateLabel();
			}
			
			/* if the user is closing their account and is at the
			 *  last stage of whether they want to do it or not
			 * and they press the 'no' button
			 *then tell them that it has not been done */
			else if(p == Process.closeAccount && st == State.enter && a1E && pinE) {
				Handler.updateLabel("Your account has not been closed. \n");
				refresh();
			}
			
			// if there is nothing to erase, then the user will go back to main menu
			else if(!Handler.containsDigit(HTMLtext) && st == State.enter) {
				Handler.updateLabel(mmText);
				lp.remove(tf);
				st = State.start;
				p = null;
			}
			
			// if the user has finished a process, then they can go back to the main menu
			// by pressing the button
			else if(st == State.end) {
				st = State.start;
				p = null;
				Handler.updateLabel(mmText);
			}
		    break;
		    
		    
		case "11":
			if(st==State.enter && (!a1E || !a2E || !balanceE || !pinE)) Handler.addToLabel("0");
			break;
		
		/*     This is the logic of the code that follows -
		 * 
		 *								Choice entered by the user 
		 *											|
		 *											|
		 *										   \ /
		 *							Prompt the user for initial info 
		 *											|Press
		 *							    			|after entering
		 *											|initial 
		 *											|info
		 *										   \ /
		 *		End the excecution   <-No-   is info entered valid?   <--------
		 *		Tell the user to try				|Y						  |
		 *			again							|E						  |
		 * 											|S						  |
		 * 										   \ /						  |
		 * 										Store info					  |
		 * 											|						  |
		 *											|						  |
		 *										   \ /						  |
		 *		Give the desired <-No-	Is more info required?				  |
		 *			 result							|						  |
		 *											|						  |
		 *										   \ /						  |
		 *										 Prompt						  |
		 *											|						  |
		 *											| Press after entering	  |
		 *										    ---------------------------
		 *													the info
		 * */									   
		 
		// when the user has oressed the continue button
		case "12":
			// if the user is in the middle of a certain process
				switch(p) {
				
				// the process here is to send the money
				case sendMoney:
					
					if(!a1E) { // then check whether they have entered the information they were asked to enter
						
						// if they have not entered it before
						// then means that they have entered it now
						if(Handler.getNumFromString(HTMLtext)==0) { // then check whether it is valid or not
							Handler.updateLabel("Invalid account number \n Try again"); // if not then prompt the user 
							refresh(); // and refresh/end
						} 
						else {
							
							// else store it
							a1 = Handler.getNumFromString(HTMLtext); 
							a1E = true;
							
							if(Transaction.getAccount(a1)!=null) { // secondary but compulsary validation required for the info
								Handler.updateLabel("Enter the pin: \n"); // Prompt the user for next info
							}
							else {
								Handler.updateLabel("Account with the account number " + a1 + " \n does not exist. \n Please try again.");
								refresh();
							}
						}
					}
					
					/*if they have already entered it then we move on 
					  * to the next piece of information we need
					 * to perform the action/process */
					else if(!pinE) {
						if(Handler.getNumFromString(HTMLtext)==0) { // primary validation
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							pin = Handler.getNumFromString(HTMLtext);
							pinE = true;
							
							// secondary validation
							if(Handler.isCorrect(a1, pin)) Handler.updateLabel("Enter balance: \n");
							else {
								Handler.updateLabel("Incorrect PIN. Try again.");
								refresh();
							}
						}
					}
					
					else if(!balanceE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid amount \n Try again");
							refresh();
						} else {
							balance = Handler.getLongFromString(HTMLtext);
							balanceE = true;
							if(Transaction.getAccount(a1).getBalance() < balance) {
								Handler.updateLabel("You do not have enough money \n to complete the transaction. \n Please deposit more money.");
								refresh();
							}
							else Handler.updateLabel("Enter the account number of the receiver \n");
						}
					}
					
					else if(!a2E) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid account number \n Try again");
							refresh();
						} else {
							a2 = Handler.getNumFromString(HTMLtext);
							a2E = true;
							if(Transaction.getAccount(a2) == null) {
								Handler.updateLabel("Account with the account number " + a2 + " does not exist \n Please try again");
								refresh();
							} else {
								// since no more information is required, we can 
								Transaction.sendMoney(a1, a2, balance);
								Handler.updateLabel("Money sent!");
								refresh();
							}
						}
					}
				break;
				
				
				case changeName:
					Account a = null;
					if(!a1E) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid account number \n Try again");
							refresh();
						} else {
							a1 = Handler.getNumFromString(HTMLtext);
							a1E = true;
							
							/* we need to set these to true so thst no text
							 *can be entered by the buttons when we need a 
							 *string through the text field 
							 *(refer to the condition in the first line of
						     *every case before this one) */
							
							balanceE = true;
							a2E = true; 
							
							/* why? becuase the user cannot enter 
							 * alphabets throught the buttons and
							 * it looks bad if they enter numbers
							 * in front of a request for string*/
							
							if(Transaction.getAccount(a1)==null) {
								Handler.updateLabel("The account with the account number " + a1 + " \n does not exist. \n Please try again.");
								refresh();
							}
							else Handler.updateLabel("Enter your PIN \n");
						}
					}
					
					else if(!pinE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							pin = Handler.getNumFromString(HTMLtext);
							pinE = true;
							if(Handler.isCorrect(a1, pin)) {
								Handler.updateLabel("Enter your new name");
								addTextField();
							}
							else {
								Handler.updateLabel("Incorrect PIN \n Please try again");
								refresh();									}
						}
					}
					
					else if(!nameE) {
						a = Transaction.getAccount(a1);
						name = tf.getText();
						nameE = true;
						if(Handler.containsDigit(name) || Handler.containsSpecialCharacter(name)) {
							Handler.updateLabel("Sorry numbers or special characters are not allowed. \n Please try again");
						} else {
							a.setName(name);
							Handler.updateLabel("Your name has been updated");
						}
						refresh();
						lp.remove(tf);
					}
					break;
					
					
				case changePIN:
					int tempin = 0;
					if(!a1E) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid account number \n Try again");
							refresh();
						} else {
							a1 = Handler.getNumFromString(HTMLtext);
							a1E = true;
							if(Transaction.getAccount(a1)==null) {
								Handler.updateLabel("The account with the account number " + a1 + " \n does not exist. \n Please try again.");
								refresh();
							}
							else Handler.updateLabel("Enter your current PIN \n");
						}
					}
					
					else if(!pinE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							pin = Handler.getNumFromString(HTMLtext);
							pinE = true;
							if(Handler.isCorrect(a1, pin)) {
								Handler.updateLabel("Enter you new PIN\n");
							}
							else {
								Handler.updateLabel("Incorrect PIN \n Please try again");
								refresh();
							}
						}
					}
					
					else if(tempin == 0) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							tempin = Handler.getNumFromString(HTMLtext);
							Handler.updateLabel("Confirm new pin \n");
						}
					} else {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							if(Handler.getNumFromString(HTMLtext)==tempin) {
								
								if(Handler.pinIsAvailable(pin)) {
									Account b = Transaction.getAccount(a1);
									b.setPin(pin);
									Handler.updateLabel("Your PIN has been updated");
								}
								else {
									Handler.updateLabel("PIN not available. Please retry and enter a new PIN");
								}
								
							} else {
								Handler.updateLabel("This is not the PIN you had entered \n Please try again");
							}
							refresh();
						}
					}
					break;
					
					
				case closeAccount:
					if(!a1E) {
						
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid account number \n Try again");
							refresh();
						} else {
							a1 = Handler.getNumFromString(HTMLtext);
							a1E = true;
							if(Transaction.getAccount(a1)==null) {
								Handler.updateLabel("The account with the account number " + a1 + " \n does not exist. \n Please try again.");
								refresh();
							}
							else Handler.updateLabel("Enter your PIN \n");
						}
					}
					
					else if(!pinE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							pin = Handler.getNumFromString(HTMLtext);
							pinE = true;
							if(Handler.isCorrect(a1, pin)) Handler.updateLabel("Are you sure you want to close your account? \n");
							else {
								Handler.updateLabel("Incorrect PIN \n Please try again");
								refresh();
							}
						}
					} // end of !pinE
					
					else {
						Account g = Transaction.getAccount(a1);
						int nullIndex = Handler.getIndex(g);
						Handler.shiftOneBack(nullIndex);
						Handler.updateLabel("Your account has been closed");
						refresh();
					}
				break;
				
				
				case createAccount:
					if(!pinE) {
						
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							int tempin1 = Handler.getNumFromString(HTMLtext);
							if(Handler.pinIsAvailable(tempin1)) {
								pin = tempin1;
								pinE = true;
								
								a1E = true; // same reason as changeName case
								a2E = true; // (Frame.java:479)
								balanceE = true; // ctrl + click the link to go to the line in eclipse
								
								Handler.updateLabel("Enter your name");
								addTextField();
							}
							else {
								Handler.updateLabel("PIN not available. Please retry and enter a new PIN");
								refresh();
							}	
						}
					} else if(!nameE) {
						name = tf.getText();
						nameE = true;
						
						lp.remove(tf);
						setVisible(false); // so that the JFrame updates itself
						setVisible(true); // and the text field is cleared
						if(Handler.containsDigit(name) || Handler.containsSpecialCharacter(name)) {
							Handler.updateLabel("Sorry numbers or special characters are not allowed. \n Please try again");
							refresh();
						}
						else {
							Transaction.createAccount(name, pin);
							Handler.updateLabel("Your account has been made! \n Your account number is " + (Handler.a.length));
						}
					}
					break;
					
					
				case deposit:
					if(!a1E) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid account number \n Try again");
							refresh();
						} else {
							a1 = Handler.getNumFromString(HTMLtext);
							a1E = true;
							if(Transaction.getAccount(a1)==null) {
								Handler.updateLabel("The account with the account number " + a1 + " \n does not exist. \n Please try again.");
								refresh();
							}
							else Handler.updateLabel("Enter your PIN \n");
						}
					}
					
					else if(!pinE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							pin = Handler.getNumFromString(HTMLtext);
							pinE = true;
							if(Handler.isCorrect(a1, pin)) Handler.updateLabel("Enter the amount of money you want to deposit \n");
							else {
								Handler.updateLabel("Incorrect PIN \n Please try again");
								refresh();
							}
						}
					}
					
					else if(!balanceE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid amount \n Try again");
							refresh();
						} else {
							balance = Handler.getLongFromString(HTMLtext);
							balanceE = true;
							Account d = Transaction.getAccount(a1);
							Transaction.deposit(d, balance);
							Handler.updateLabel("Your money has been deposited. Your current balance is " + d.getBalance());
							refresh();
						}
					}
					break;
					
					
				case displayBalance:
					if(!a1E) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid account number \n Try again");
							refresh();
						} else {
							a1 = Handler.getNumFromString(HTMLtext);
							a1E = true;
							if(Transaction.getAccount(a1)==null) {
								Handler.updateLabel("The account with the account number " + a1 + " \n does not exist. \n Please try again.");
								refresh();
							}
							else Handler.updateLabel("Enter you PIN \n");
						}
					}
					
					else if(!pinE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							pin = Handler.getNumFromString(HTMLtext);
							pinE = true;
							if(Handler.isCorrect(a1, pin)) {
								Account c = Transaction.getAccount(a1);
								Handler.updateLabel("Your current balance is : \n" + c.getBalance());
							}
							else {
								Handler.updateLabel("Incorrect PIN \n Please try again");
							}
							refresh();
						}
					}
					break;
					
					
				case withdraw:
					if(!a1E) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid account number \n Try again");
							refresh();
						} else {
							a1 = Handler.getNumFromString(HTMLtext);
							a1E = true;
							if(Transaction.getAccount(a1)==null) {
								Handler.updateLabel("The account with the account number " + a1 + " \n does not exist. \n Please try again.");
								refresh();
							}
							else Handler.updateLabel("Enter your PIN \n");
						}
					}
					
					else if(!pinE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid PIN \n Try again");
							refresh();
						} else {
							pin = Handler.getNumFromString(HTMLtext);
							pinE = true;
							if(Handler.isCorrect(a1, pin)) Handler.updateLabel("Enter the amount of money you want to withdraw \n");
							else {
								Handler.updateLabel("Incorrect PIN \n Please try again");
								refresh();
							}
						}
					}
					
					else if(!balanceE) {
						if(Handler.getNumFromString(HTMLtext)==0) {
							Handler.updateLabel("Invalid amount \n Try again");
							refresh();
						} else {
							balance = Handler.getLongFromString(HTMLtext);
							balanceE = true;
							Account f = Transaction.getAccount(a1);
							if(balance > f.getBalance()) {
								Handler.updateLabel("You do not have enough money to complete the transaction. \n Please deposit more money");
								refresh();
							}
							else {
								Transaction.withdraw(f, balance);
								Handler.updateLabel("Your money has been withdrawn. \n Your current balance is " + f.getBalance());
								refresh();
							}
						}
					}
					
					break;
					
				default:
					System.exit(0);
					break;
				} // end of process switch
		    break;
		} // end of action command switch
	} // end of method body
} // end of class