package atm;

import java.io.File;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AtmMachine {
	
	private double balance;
	Scanner input = new Scanner(System.in);

	public AtmMachine() {
		this(1.0);
	}
	
	public AtmMachine(double balance) {
		this.balance = balance;
	}
	
	public int userAccount() {
		System.out.print("Please insert you card! ");
		int account;
		account = input.nextInt();
		
		return account;
	}

	public int userPin() {
		System.out.print("Insert your pin number: ");
		int pin;
		pin = input.nextInt();
		String sPin = String.valueOf(pin);
		if (pin < 0 || sPin.length() != 4) {
			System.out.println("Invalid pin!");
			userPin();
		}

		return pin;
	}

	public void startAtm() {
		userAccount();
		userPin();
		drawMainMenu();
	}

	public void drawMainMenu() {
		int selection;
		System.out.println("\nATM main menu:");
		System.out.println("1 - View account balance");
		System.out.println("2 - Withdraw funds");
		System.out.println("3 - Add funds");
		System.out.println("4 - Terminate transaction");
		System.out.print("Choice: ");
		selection = input.nextInt();
		
		switch (selection) {
		case 1:
			viewAccountInfo();
			break;
		case 2:
			withdraw();
			break;
		case 3:
			addFunds();
			break;
		case 4:
			System.out.println("Thank you for using this ATM!");
			break;
		default:
			System.out.println("Invalid input! Enter a valid number");
			break;
		}
	}

	public void viewAccountInfo() {
		System.out.println("\nAccount Information:");
		System.out.println("\t--Total balance: $" + balance);
		continueOperation();
	}

	public void deposit(int depAmount) {
		System.out.println("\n***Please insert your money now...***");
		balance = balance + depAmount;
		continueOperation();
	}
	
	public static void playAudio(String path) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(path)));
			clip.start();
			while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkWithdrawal(int withdrawAmount) {
		if (balance < withdrawAmount) {
			System.out.println("\n***ERROR! Insufficient funds in you account***");
			continueOperation();
		}
		else {
			Thread thread1;
			Thread thread2;
			
			thread1 = new Thread() {
				@Override
				public void run() {
					playAudio("music/SoundEffect.au");
				}
			};
			thread2 = new Thread() {
				@Override
				public void run() {
					try {
						System.out.println("\n***Waiting...***");
						Thread.sleep(5000);
						balance = balance - withdrawAmount;

						System.out.println("\n***Please take your money now...***");
						System.out.println();
						System.exit(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			thread1.start();
			thread2.start();
		}
	}

	public void addFunds() {
		int addSelection;
		
		System.out.println("\nDeposit funds:");
		System.out.println("1 - $20");
		System.out.println("2 - $40");
		System.out.println("3 - $60");
		System.out.println("4 - $100");
		System.out.println("5 - Other");
		System.out.println("6 - Back to main menu");
		System.out.print("Choice: ");
		addSelection = input.nextInt();
		
		switch (addSelection) {
		case 1:
			deposit(20);
			break;
		case 2:
			deposit(40);
			break;
		case 3:
			deposit(60);
			break;
		case 4:
			deposit(100);
			break;
		case 5:
			System.out.println("\nHow much do you want to deposit: ");
			int amount = input.nextInt();
			deposit(amount);
			break;
		case 6:
			drawMainMenu();
			break;
		default:
			System.out.println("\nInvalid input! Enter a valid number");
			addFunds();
			break;
		}
	}

	public void withdraw() {
		int withdrawSelection;
		
		System.out.println("\nWithdraw money:");
		System.out.println("1 - $20");
		System.out.println("2 - $40");
		System.out.println("3 - $60");
		System.out.println("4 - $100");
		System.out.println("5 - Other");
		System.out.println("6 - Back to main menu");
		System.out.print("Choice: ");
		withdrawSelection = input.nextInt();
		
		switch (withdrawSelection) {
		case 1:
			checkWithdrawal(20);
			break;
		case 2:
			checkWithdrawal(40);
			break;
		case 3:
			checkWithdrawal(60);
			break;
		case 4:
			checkWithdrawal(100);
			break;
		case 5:
			System.out.println("\nHow much do you want to withdraw: ");
			int amount = input.nextInt();
			checkWithdrawal(amount);
			drawMainMenu();
			break;
		case 6:
			drawMainMenu();
			break;
		default:
			System.out.println("\nInvalid input! Enter a valid number");
			withdraw();
			break;
		}
	}
	
	public void continueOperation() {
		int continueSelection;
		
		System.out.println("\nDo you want to perform another action?");
		System.out.println("1 - Yes");
		System.out.println("2 - No");
		continueSelection = input.nextInt();
		
		if (continueSelection == 1) {
			drawMainMenu();
		}else {
			System.out.println("Thank you for using this ATM!");
		}
	}

	public static void main(String args[]) {
		AtmMachine myAtm = new AtmMachine(100);
		myAtm.startAtm();
	}
}