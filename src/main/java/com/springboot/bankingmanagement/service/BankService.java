
package com.springboot.bankingmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankingmanagement.exception.InsufficientAccountBalanceException;
import com.springboot.bankingmanagement.exception.InvalidAccountException;
import com.springboot.bankingmanagement.model.Customer;
import com.springboot.bankingmanagement.model.Statement;

@Service
public interface BankService {

	Customer createAccount(Customer customer);

	void withdraw(Long account, Double amount, int transactionId) throws InsufficientAccountBalanceException;

	void deposit(Long account, Double amount, int transactionId);

	void transferFund(Long fromAccount, Long toAccount, Double amount)
			throws InsufficientAccountBalanceException, InvalidAccountException;

	List<Statement> getStatement(Long accountNumber, String month, String year);
}
