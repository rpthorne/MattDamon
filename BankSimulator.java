import java.util.Scanner;


public class BankSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Bank standardBank = new Bank();
		ATM curATM = new ATM(standardBank);
		String curAcc;
		//String curPin;
		int state = 0;
		String curLine;
		report("Hello! and welcome to Automated Teller Machine 30035!");
		report("input account number:");
		state = 1;
		curLine = sc.next();
		while(curLine.toLowerCase().compareTo("terminate") != 0)
		{
			switch (state)
			{
			case 1:
				curLine = curLine.trim();
				if(curLine.matches("[0-9][0-9][0-9][0-9]"))
				{
					//identify account curline
					state = 2;
					curATM.enter(curLine);
					curAcc = curLine;
					report("please authenticate account " + curLine);
					
				}
				else	
				{
					state = 1;
					report("invalid format, enter account number!");
				}
				break;
			case 2:
				curLine = curLine.trim();
				if(curLine.matches("[0-9][0-9][0-9][0-9]"))
				{
					if(curATM.validate(curLine).compareTo("Successfully validated Account/Pin"))
					{
						state = 3;
						report("Account Authenticated! welcome user " + curAcc + "!");
						report("would you like to withdraw, deposit or exit");
					}
					else
					{
						report("invalid credentials");
						report("input account number");
						state = 1;
					}
				}
				break;
			case 3:
				curLine = curLine.trim();
				if(curLine.toLowerCase().compareTo("deposit") == 0)
				{
					//deposit
					state = 4;//deposit cannot fail
					report("how much would you like to deposit?");

				}
				else if(curLine.toLowerCase().compareTo("withdraw") == 0)
				{
					//withdraw
					state = 5;
					report("how much would you like to withdraw?");
				}
				else if(curLine.toLowerCase().compareTo("exit") == 0)
				{
					//exit
					state = 1;
					curAcc = "";
				}
				else
				{
					report("I am sorry, I could not understand you. withdraw, deposit, or exit?");
				}
				break;
			case 4:
				//deposit state
				curLine = curLine.trim();
				if(curLine.matches("[0-9]*([.]?[0-9]{0,2})"))
				{
					double d = Double.parseDouble(curLine);
					curATM.deposit(d);
					state = 1;
					report(curATM.exit());
					report("input account number:");
				}
				break;
			case 5:
			//withdraw state
				curLine = curLine.trim();
				if(curLine.matches("[0-9]*([.]?[0-9]{0,2})"))
				{
					double d = Double.parseDouble(curLine);
					curATM.withdraw(d);
					state = 1;
					report(curATM.exit());
					report("input account number:");
				}
				break;
			default:
				report("state error");
			}
			
		}
	}

	public static void report(String s)
	{
		System.out.println(s);
	}
}


