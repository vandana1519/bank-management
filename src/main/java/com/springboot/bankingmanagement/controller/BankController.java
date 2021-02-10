package com.springboot.bankingmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankingmanagement.exception.InsufficientAccountBalanceException;
import com.springboot.bankingmanagement.exception.InvalidAccountException;
import com.springboot.bankingmanagement.model.Customer;
import com.springboot.bankingmanagement.model.MoneyTransaction;
import com.springboot.bankingmanagement.model.Statement;
import com.springboot.bankingmanagement.service.BankService;

@RestController
public class BankController {

	@Autowired
	private BankService bankService;

	@PostMapping("/customer")
	public ResponseEntity<Customer> createAccount(@RequestBody Customer customer) {
		
		return new ResponseEntity<>(bankService.createAccount(customer),HttpStatus.CREATED);
	}

	@GetMapping("/statement/{accountNumber}")
	public ResponseEntity<List<Statement>> getStatement(@PathVariable("accountNumber") Long accountNumber,
			@RequestParam String month, @RequestParam String year) {
		
		return new ResponseEntity<>(bankService.getStatement(accountNumber, month, year),HttpStatus.OK);
	}

	@PostMapping("/transferFund")
	public ResponseEntity<String> transferFund(@RequestBody MoneyTransaction moneyTransfer) {
		try {
			bankService.transferFund(moneyTransfer.getFromAccoutNumber(), moneyTransfer.getToAccoutNumber(),
					moneyTransfer.getAmount());
			return new ResponseEntity<>("Transaction Successful!!", HttpStatus.OK);
		} catch (InsufficientAccountBalanceException e) {
			return new ResponseEntity<>("Insufficient balance in your account", HttpStatus.EXPECTATION_FAILED);
		} catch (InvalidAccountException e) {
			return new ResponseEntity<>("Invalid Account Number", HttpStatus.BAD_REQUEST);
		}
	}
	 
}
