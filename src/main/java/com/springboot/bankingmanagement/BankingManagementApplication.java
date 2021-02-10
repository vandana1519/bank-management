package com.springboot.bankingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass=true)
@EnableEurekaClient
public class BankingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingManagementApplication.class, args);
	}

}
