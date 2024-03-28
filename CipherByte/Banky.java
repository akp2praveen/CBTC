package banky;

import java.io.*;
import java.util.*;

class Account implements Serializable {
	private String accountNumber;
	private String accountHolderName;
	private double balance;

	// Constructor
	public Account(String accountNumber, String accountHolderName, double balance) {
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
	}

	// Getters and setters
	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	// Methods for deposit and withdraw
	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount <= balance) {
			balance -= amount;
		} else {
			System.out.println("Insufficient funds");
		}
	}

	// Method to transfer funds to another account
	public void transfer(Account recipient, double amount) {
		if (amount <= balance) {
			withdraw(amount);
			recipient.deposit(amount);
			System.out.println("Transfer successful");
		} else {
			System.out.println("Insufficient funds for transfer");
		}
	}

	// Method to display account information
	public void display() {
		System.out.println("Account Number: " + accountNumber);
		System.out.println("Account Holder Name: " + accountHolderName);
		System.out.println("Balance: Rs" + balance);
	}
}

class Bank {
	private List<Account> accounts;
	private String filename;

	// Constructor
	public Bank(String filename) {
		this.filename = filename;
		accounts = new ArrayList<>();
		loadAccounts();
	}

	// Method to create a new account
	public void createAccount(String accountNumber, String accountHolderName, double balance) {
		Account account = new Account(accountNumber, accountHolderName, balance);
		accounts.add(account);
		saveAccounts();
		System.out.println("Account created successfully");
	}

	// Method to deposit funds into an account
	public void deposit(String accountNumber, double amount) {
		Account account = findAccount(accountNumber);
		if (account != null) {
			account.deposit(amount);
			saveAccounts();
			System.out.println("Deposit successful");
		} else {
			System.out.println("Account not found");
		}
	}

	// Method to withdraw funds from an account
	public void withdraw(String accountNumber, double amount) {
		Account account = findAccount(accountNumber);
		if (account != null) {
			account.withdraw(amount);
			saveAccounts();
			System.out.println("Withdrawal successful");
		} else {
			System.out.println("Account not found");
		}
	}

	// Method to transfer funds between accounts
	public void transfer(String senderAccountNumber, String recipientAccountNumber, double amount) {
		Account sender = findAccount(senderAccountNumber);
		Account recipient = findAccount(recipientAccountNumber);
		if (sender != null && recipient != null) {
			sender.transfer(recipient, amount);
			saveAccounts();
		} else {
			System.out.println("One or both accounts not found");
		}
	}

	// Method to find an account by account number
	private Account findAccount(String accountNumber) {
		for (Account account : accounts) {
			if (account.getAccountNumber().equals(accountNumber)) {
				return account;
			}
		}
		return null;
	}

	// Method to load accounts from file
	private void loadAccounts() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			accounts = (List<Account>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error loading accounts: " + e.getMessage());
		}
	}

	// Method to save accounts to file
	private void saveAccounts() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(accounts);
		} catch (IOException e) {
			System.out.println("Error saving accounts: " + e.getMessage());
		}
	}

	// Method to display all accounts
	public void displayAllAccounts() {
		if (accounts.isEmpty()) {
			System.out.println("No accounts found");
		} else {
			System.out.println("Accounts:");
			for (Account account : accounts) {
				account.display();
				System.out.println();
			}
		}
	}
}

public class Banky {
	public static void main(String[] args) {
		Bank bank = new Bank("accounts.dat");

		// Example usage
		bank.createAccount("123456", "John Doe", 1000.0);
		bank.createAccount("789012", "Jane Smith", 500.0);
		bank.displayAllAccounts();

		bank.deposit("123456", 200.0);
		bank.displayAllAccounts();

		bank.withdraw("789012", 100.0);
		bank.displayAllAccounts();

		bank.transfer("123456", "789012", 300.0);
		bank.displayAllAccounts();
	}
}
