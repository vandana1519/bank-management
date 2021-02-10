package com.springboot.bankingmanagement.service.impl;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.bankingmanagement.exception.InsufficientAccountBalanceException;
import com.springboot.bankingmanagement.exception.InvalidAccountException;
import com.springboot.bankingmanagement.model.Account;
import com.springboot.bankingmanagement.model.Customer;
import com.springboot.bankingmanagement.model.Statement;
import com.springboot.bankingmanagement.repository.BankRepository;
import com.springboot.bankingmanagement.repository.CustomerRepository;
import com.springboot.bankingmanagement.repository.TransactionRepository;
import com.springboot.bankingmanagement.service.BankService;

@Service
public class BankServiceImpl implements BankService{

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Customer createAccount(Customer customer) {
		Account account = customer.getAccount();
		account.setUpdatedAt(new Date(System.currentTimeMillis()));
		return customerRepository.save(customer);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, timeout = 100, rollbackFor = Exception.class)
	public void transferFund(Long fromAccount, 
			Long toAccount, Double amount)
			throws InsufficientAccountBalanceException, InvalidAccountException {

		Account fromAccountDetail = bankRepository.findByAccountNumber(fromAccount);
		Account toAccountDetail = bankRepository.findByAccountNumber(toAccount);

		SecureRandom ranGen = new SecureRandom();
		int transactionId = ranGen.nextInt(99999);

		if (!(fromAccountDetail == null || toAccountDetail == null)) {
			withdraw(fromAccount, amount, transactionId);
			deposit(toAccount, amount, transactionId);
		} else {
			throw new InvalidAccountException("Invalid Account!!!");
		}

	}

	@Override
	public List<Statement> getStatement(Long accountNumber, String month, String year) {
		return transactionRepository.getStatementHistory(accountNumber, month, year);
	}

	@Override
	public void withdraw(Long accountNumber, Double amount, int transactionId)
			throws InsufficientAccountBalanceException {

		Account accountInfo = bankRepository.findByAccountNumber(accountNumber);
		if (accountInfo.getAccountBalance() >= amount) {
			Double accountBalance = accountInfo.getAccountBalance() - amount;
			bankRepository.updateAccountBalance(accountNumber, accountBalance);
			transactionRepository.save(
					new Statement(new Date(System.currentTimeMillis()), transactionId, accountNumber, amount, 0.0));
		} else {
			throw new InsufficientAccountBalanceException("Insufficient balance in your account :-(");
		}
	}

	@Override
	public void deposit(Long accountNumber, Double amount, int transactionId) {

		Account accountInfo = bankRepository.findByAccountNumber(accountNumber);
		Double accountBalance = accountInfo.getAccountBalance() + amount;
		bankRepository.updateAccountBalance(accountNumber, accountBalance);
		transactionRepository
				.save(new Statement(new Date(System.currentTimeMillis()), transactionId, accountNumber, 0.0, amount));
	}

}
