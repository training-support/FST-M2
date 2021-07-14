package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Activity2 {
	
	@Test
	public void notEnoughFunds()
	{
		BankAccount bankAcct= new BankAccount(9);
		assertThrows(NotEnoughFundsException.class, () -> bankAcct.withdraw(10),"Balance should be greater than withdrawal");
	}
	
	@Test
	public void enoughFunds()
	{
		BankAccount bankAcct= new BankAccount(100);
		assertDoesNotThrow(() -> bankAcct.withdraw(100));
	}

}
