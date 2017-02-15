package mattdamon;

import java.util.*;

public class Bank {
	/**
	 * Stores all of the accounts in a map with their account number being the key.
	 */
	private Map<String, Account> accountMap;
	
	/**
	 * The last account to be validated, null if no account was validated.
	 */
	private Account currentAccount;
	
	private final String INVALID_ACCOUNT_STRING = 
			"AN: " + "INVALID ACCOUNT\n" +
			"PIN: " + "\n" +
			"WD: " + "\n" +
			"DP: " + "\n" +
			"BAL: ";
	
	/**
	 * Instantiates a Bank object with two accounts:
	 * - Account[number:1234, pin:6789, balance:80.0]
	 * - Account[number:6789, pin:4321, balance:60.0]
	 */
	public Bank() {
		accountMap = new HashMap<String, Account>();
		
		Account account1 = new Account("1234","6789",80.0);
		Account account2 = new Account("6789","4321",60.0);
		
		//Add the two accounts.
		accountMap.put(account1.accountNumber, account1);
		accountMap.put(account2.accountNumber, account2);
	}
	
	/**
	 * Ensures that an account exists, with the given account number, with the pin number that is given. 
	 * @param accountNumber - the account number corresponding
	 * @param accountPin
	 * @return
	 */
	boolean validate(String accountNumber, String accountPin) {
		Account account = accountMap.get(accountNumber);
		Account validatedAccount = null;
		
		//Account exists and the pin is correct for that account.
		if (account != null && account.validate(accountPin)) {
			validatedAccount = account;
		}
		
		return validatedAccount != null;
	}
	
	String withdraw(double amount) {
		if (currentAccount != null) {
			if (currentAccount.withdraw(amount)) {
				//Successful withdraw
				return 
					"AN: " + currentAccount.accountNumber + "\n" +
					"PIN: " + currentAccount.pin + "\n" +
					"WD: " + amount + "\n" +
					"DP: " + "" + "\n" +
					"BAL: " + currentAccount.balance + "";
			} else {
				//Not successful withdraw - insufficient funds
				return 
					"AN: " + currentAccount.accountNumber + "\n" +
					"PIN: " + currentAccount.pin + "\n" +
					"WD: " + "INSUFFICIENT FUNDS" + "\n" +
					"DP: " + "" + "\n" +
					"BAL: " + currentAccount.balance + "";
			}
		} else {
			return INVALID_ACCOUNT_STRING;
		}
	}
	
	String deposit(double amount) {
		if (currentAccount != null) {
			currentAccount.deposit(amount);
			return 
				"AN: " + currentAccount.accountNumber + "\n" +
				"PIN: " + currentAccount.pin + "\n" +
				"WD: " + "" + "\n" +
				"DP: " + amount + "\n" +
				"BAL: " + currentAccount.balance + "";
		} else {
			return INVALID_ACCOUNT_STRING;
		}
	}
	
	
	private class Account {
		private final String accountNumber;
		private String pin;
		private double balance;
		
		/**
		 * Creates an account with the account number, pin, and balance.
		 * @precondition balance is a non-negative number
		 * @param accountNumber - String representation of the account number
		 * @param pin - string representation of the pint
		 * @param balance - the beginning balance of the account.
		 */
		public Account(String accountNumber, String pin, double balance) {
			this.accountNumber = accountNumber;
			this.pin = pin;
			this.balance = balance;
		}
		
		/**
		 * Ensures that the pin corresponds to this account.
		 * @param pin - the pin to be tested against this account
		 * @return true if the pin corresponds to this account, false otherwise.
		 */
		boolean validate(String pin) {
			return this.pin.equals(pin);
		}
		
		/**
		 * Attempts to withdraw the amount from the Account's current balance. 
		 * A withdraw can only happen when amount is less than or equal to the current balance.
		 * @precondition amount must be non-negative
		 * @param amount - the amount to be withdrawn
		 * @return true if amount is withdrawn, false otherwise.
		 */
		boolean withdraw(double amount) {
			if (this.balance >= amount) {
				this.balance -= amount;
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * Deposits amount into this Account.
		 * @precondition amount must be non-negative
		 * @param amount to be deposited
		 */
		void deposit(double amount) {
			this.balance += amount;
		}
	}
	
	
}
