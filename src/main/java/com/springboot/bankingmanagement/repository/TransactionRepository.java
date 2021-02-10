package com.springboot.bankingmanagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.bankingmanagement.model.Statement;

public interface TransactionRepository extends JpaRepository<Statement, Long> {

	@Query(value = "SELECT * FROM statement_history s WHERE s.account_number = :accountNumber AND MONTH(date) = :month AND YEAR(date) = :year", nativeQuery=true)
	List<Statement> getStatementHistory(@Param("accountNumber") Long accountNumber,@Param("month") String month,@Param("year") String year);
	
}
