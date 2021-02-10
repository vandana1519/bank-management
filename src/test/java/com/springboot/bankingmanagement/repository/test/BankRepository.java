/*
 * package com.springboot.bankingmanagement.repository.test;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Modifying; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.stereotype.Repository; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.springboot.bankingmanagement.model.Account;
 * 
 * @Repository public interface BankRepository extends
 * JpaRepository<Account,Long>{
 * 
 * Account findByAccountNumber(Long accountNumber);
 * 
 * @Modifying
 * 
 * @Transactional
 * 
 * @Query(value =
 * "UPDATE account a set a.account_balance = :accountBalance WHERE a.account_number = :accountNumber"
 * , nativeQuery=true) void updateAccountBalance(@Param("accountNumber") Long
 * accountNumber, @Param("accountBalance") Double accountBalance);
 * 
 * 
 * }
 */