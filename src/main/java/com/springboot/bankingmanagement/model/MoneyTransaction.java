package com.springboot.bankingmanagement.model;

import java.util.Date;
import javax.persistence.Id;

public class MoneyTransaction {

	@Id
	private Long transactionId;
	private Date date;
	private Double amount;
	private Long fromAccoutNumber;
	private Long toAccoutNumber;

	public MoneyTransaction() {
	}

	public MoneyTransaction(Double amount, Long fromAccout, Long toAccout) {
		super();
		this.amount = amount;
		this.fromAccoutNumber = fromAccout;
		this.toAccoutNumber = toAccout;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getFromAccoutNumber() {
		return fromAccoutNumber;
	}

	public void setFromAccoutNumber(Long fromAccoutNumber) {
		this.fromAccoutNumber = fromAccoutNumber;
	}

	public Long getToAccoutNumber() {
		return toAccoutNumber;
	}

	public void setToAccoutNumber(Long toAccoutNumber) {
		this.toAccoutNumber = toAccoutNumber;
	}

}
