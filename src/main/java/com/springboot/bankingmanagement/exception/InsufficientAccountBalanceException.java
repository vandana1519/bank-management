package com.springboot.bankingmanagement.exception;

public class InsufficientAccountBalanceException extends Exception {

	private static final long serialVersionUID = 4073428868108678532L;

	public InsufficientAccountBalanceException(String message) {
		super(message);
	}
}
