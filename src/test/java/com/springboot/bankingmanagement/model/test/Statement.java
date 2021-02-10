/*
 * package com.springboot.bankingmanagement.model.test;
 * 
 * import java.util.Date;
 * 
 * import javax.persistence.Entity; import javax.persistence.GeneratedValue;
 * import javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.Table; import javax.persistence.Temporal; import
 * javax.persistence.TemporalType;
 * 
 * @Entity
 * 
 * @Table (name = "Statement_History") public class Statement {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long statementId;
 * private int transactionId; private Long accountNumber;
 * 
 * @Temporal(TemporalType.DATE) private Date date; private Double depositAmount;
 * private Double withdrawalAmount;
 * 
 * public Statement(int transactionId,Date date, Double withdrawalAmount, Double
 * depositAmount) { super(); this.transactionId = transactionId; this.date =
 * date; this.depositAmount = depositAmount; this.withdrawalAmount =
 * withdrawalAmount; } public Statement(Date date,int transactionId,Long
 * accountNumber, Double withdrawalAmount, Double depositAmount) { super();
 * this.date = date; this.transactionId = transactionId; this.accountNumber =
 * accountNumber; this.depositAmount = depositAmount; this.withdrawalAmount =
 * withdrawalAmount; } public Statement() {}
 * 
 * public Long getStatementId() { return statementId; } public void
 * setStatementId(Long statementId) { this.statementId = statementId; } public
 * int getTransactionId() { return transactionId; }
 * 
 * public void setTransactionId(int transactionId) { this.transactionId =
 * transactionId; }
 * 
 * public Long getAccountNumber() { return accountNumber; }
 * 
 * public void setAccountNumber(Long accountNumber) { this.accountNumber =
 * accountNumber; }
 * 
 * public Date getDate() { return date; }
 * 
 * public void setDate(Date date) { this.date = date; }
 * 
 * public Double getDepositAmount() { return depositAmount; }
 * 
 * public void setDepositAmount(Double depositAmount) { this.depositAmount =
 * depositAmount; }
 * 
 * public Double getWithdrawalAmount() { return withdrawalAmount; }
 * 
 * public void setWithdrawalAmount(Double withdrawalAmount) {
 * this.withdrawalAmount = withdrawalAmount; }
 * 
 * }
 */