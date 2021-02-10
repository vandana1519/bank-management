package com.springboot.bankingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.bankingmanagement.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
