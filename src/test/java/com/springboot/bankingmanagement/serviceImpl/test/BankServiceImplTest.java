package com.springboot.bankingmanagement.serviceImpl.test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.bankingmanagement.exception.InsufficientAccountBalanceException;
import com.springboot.bankingmanagement.exception.InvalidAccountException;
import com.springboot.bankingmanagement.model.Account;
import com.springboot.bankingmanagement.model.Customer;
import com.springboot.bankingmanagement.model.Statement;
import com.springboot.bankingmanagement.repository.BankRepository;
import com.springboot.bankingmanagement.repository.CustomerRepository;
import com.springboot.bankingmanagement.repository.TransactionRepository;
import com.springboot.bankingmanagement.service.impl.BankServiceImpl;

@SpringBootTest
class BankServiceImplTest {
	
	@InjectMocks
	BankServiceImpl bankServiceImpl;

	@Mock
	BankRepository bankRepository;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	TransactionRepository transactionRepository;
	
	static Account account = new Account();
	static Customer customer = new Customer();
	static List<Statement> stmt = new ArrayList<>();
	
	@BeforeAll
	public static void setUp() {
		account.setAccountBalance(5000d);
		account.setAccountNumber(1000L);
		account.setUpdatedAt(new Date(System.currentTimeMillis()));
		customer.setCustomerName("Vandana");
		customer.setState("Maharashtra");
		customer.setAccount(account);	
		Statement statement = new Statement();
		statement.setAccountNumber(1000L);
		statement.setDate(new Date(System.currentTimeMillis()));
		statement.setDepositAmount(100d);
		statement.setStatementId(1l);
		statement.setTransactionId(12345);
		statement.setWithdrawalAmount(0d);
		stmt.add(statement);
		Statement statement1 = new Statement(new Date(System.currentTimeMillis()),12345,1000L,100d,0d);
		stmt.add(statement1);
		
	}

	@Test
	void testCreateAccountForSuccess() {
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		Customer customer1 = bankServiceImpl.createAccount(customer);
		assertNotNull(customer1);
	}
	
	@Test 
	void testGetStatement() { 
		Mockito.when(transactionRepository.getStatementHistory(1000L, "12", "2020")).thenReturn(stmt);
		 List<Statement> statementList = bankServiceImpl.getStatement(1000L, "12", "2020");
		assertNotNull(statementList); 
	}
	
	@Test
	void testWithdrawForSuccess() throws InsufficientAccountBalanceException{ 
		Statement statement2 = new Statement(new Date(System.currentTimeMillis()),12345,1000L,100d,0d);
		Mockito.when(bankRepository.findByAccountNumber(1000L)).thenReturn(account);
		Mockito.doNothing().when(bankRepository).updateAccountBalance(account.getAccountNumber(), account.getAccountBalance());
		//Mockito.doNothing().doThrow(new InsufficientAccountBalanceException("Insufficient balance in your account :-(")).when(transactionRepository).save(new Statement(new Date(System.currentTimeMillis()),12345,1000L,100d,0d));
		Mockito.when(transactionRepository.save(new Statement(statement2.getDate(),statement2.getTransactionId(),statement2.getAccountNumber(),statement2.getWithdrawalAmount(),0d))).thenReturn(statement2);
		bankServiceImpl.withdraw(1000L, 100d, 12345);
		assertNotNull(account); 
	}
	
	
	@Test
	@ExceptionHandler(InsufficientAccountBalanceException.class)
	void testWithdrawForException() throws InsufficientAccountBalanceException {
		account.setAccountBalance(0d);
		Mockito.when(bankRepository.findByAccountNumber(1000L)).thenReturn(account);
		Assertions.assertThrows(InsufficientAccountBalanceException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				bankServiceImpl.withdraw(1000L, 100d, 12345);
				assertFalse(account.getAccountBalance() > 100d);
			}
		});
	}
	 
	
	@Test
	void testDepositForSuccess(){ 
		Statement statement2 = new Statement(new Date(System.currentTimeMillis()),12345,1000L,0d,100d);
		Mockito.when(bankRepository.findByAccountNumber(1000L)).thenReturn(account);
		Mockito.doNothing().when(bankRepository).updateAccountBalance(account.getAccountNumber(), account.getAccountBalance());
		//Mockito.doNothing().doThrow(new InsufficientAccountBalanceException("Insufficient balance in your account :-(")).when(transactionRepository).save(new Statement(new Date(System.currentTimeMillis()),12345,1000L,100d,0d));
		Mockito.when(transactionRepository.save(new Statement(statement2.getDate(),statement2.getTransactionId(),statement2.getAccountNumber(),0d,statement2.getDepositAmount()))).thenReturn(statement2);
		bankServiceImpl.deposit(1000L, 100d, 12345);
		assertNotNull(account); 
	}
	
	/*
	 * @Test void testTransferFundForSuccess() throws
	 * InsufficientAccountBalanceException, InvalidAccountException {
	 * 
	 * Mockito.when(bankRepository.findByAccountNumber(1000L)).thenReturn(account);
	 * //
	 * Mockito.doNothing().when(bankServiceImpl).withdraw(account.getAccountNumber()
	 * , 100d, 12345); // Mockito.doNothing().when(bankServiceImpl).deposit(1001L,
	 * 100d, 12345); bankServiceImpl.transferFund(1000L, 1001L, 100d);
	 * assertNotNull(account);
	 * 
	 * }
	 */
	
	@Test
	@ExceptionHandler(InvalidAccountException.class)
	void testTransferFundForException() throws InsufficientAccountBalanceException, InvalidAccountException {
		
		Mockito.when(bankRepository.findByAccountNumber(1000L)).thenReturn(null);
		Assertions.assertThrows(InvalidAccountException.class, new Executable() {

			public void execute() throws Throwable {
				bankServiceImpl.transferFund(1000L, 1001L, 100d);
				assertNull(account);
			}
		});		
	}
}
