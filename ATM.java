public class ATM {
	private Bank _bank;
	private String _pendingAccount, _activeAccount, _receipt;
	
	public ATM(Bank b){_bank=b;}
	
	public void enter(String account){
		_receipt = null;
		_pendingAccount = account;
	}
	
	public void validate(String pin){
		if (_pendingAccount == null)
			System.out.println("Pin entered without inserting card.");
		else if (!_bank.validate(_pendingAccount,pin))
			System.out.println("Invalid account/pin.");
		else
			_activeAccount = _pendingAccount;
	}
	
	public void withdraw(double amount){
		if (amount <= 0)
			System.out.println("Cannot withdraw negative or zero amount.");
		else if (_activeAccount == null)
			System.out.println("No active account to withdraw from.");
		else
			_receipt = _bank.withdraw(amount);
	}
	
	public void deposit(double amount){
		if (amount <= 0)
			System.out.println("Cannot deposit negative or zero amount.");
		else if (_activeAccount == null)
			System.out.println("No active account to deposit to.");
		else
			_receipt = _bank.deposit(amount);
	}
	
	public String exit(){
		_pendingAccount = _activeAccount = null;
		return _receipt;
	}
}
