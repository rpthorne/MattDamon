/**
 * The Class ATM.
 * @author vangrol2
 */
public class ATM {
	
	private Bank _bank;
	private String _pendingAccount, _activeAccount, _receipt;
	
	/**
	 * Instantiates a new ATM object.
	 * @param b the b
	 */
	public ATM(Bank b){_bank=b;}
	
	/**
	 * Enter a new account for transactions.
	 * @param account the account to load
	 */
	public void enter(String account){
		_receipt = "Transaction cancelled.";
		_pendingAccount = account;
	}
	
	/**
	 * Validate.
	 * @param pin the pin number
	 * @return the string indicating validation success/failure
	 */
	public String validate(String pin){
		if (_pendingAccount == null)
			System.out.println("Pin entered without inserting card.");
		else if (!_bank.validate(_pendingAccount,pin))
			return "Invalid Account/Pin combination";
		else{
			_activeAccount = _pendingAccount;
			return "Successfully validated Account/Pin";}
	}
	
	/**
	 * Attempt withdrawal of amount on _activeAccount.
	 * @param amount the amount to withdraw
	 */
	public void withdraw(double amount){
		if (amount <= 0)
			System.out.println("Cannot withdraw negative or zero amount.");
		else if (_activeAccount == null)
			System.out.println("No active account to withdraw from.");
		else
			_receipt = _bank.withdraw(amount);
	}
	
	/**
	 * Attempt deposit of amount on _activeAccount.
	 * @param amount the amount to deposit
	 */
	public void deposit(double amount){
		if (amount <= 0)
			System.out.println("Cannot deposit negative or zero amount.");
		else if (_activeAccount == null)
			System.out.println("No active account to deposit to.");
		else
			_receipt = _bank.deposit(amount);
	}
	
	/**
	 * Exit the current transaction.
	 * @return the receipt
	 */
	public String exit(){
		_pendingAccount = _activeAccount = null;
		return _receipt;
	}
}
